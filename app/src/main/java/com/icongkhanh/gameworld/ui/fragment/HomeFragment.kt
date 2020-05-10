package com.icongkhanh.gameworld.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.icongkhanh.common.event.EventObserver
import com.icongkhanh.common.showOrHide
import com.icongkhanh.common.widget.endlesscrollrecyclerview.InfiniteScrollListener
import com.icongkhanh.gameworld.R
import com.icongkhanh.gameworld.adapter.ListTopGameAdapter
import com.icongkhanh.gameworld.databinding.FragmentHomeBinding
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.util.LogTool
import com.icongkhanh.gameworld.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    companion object {
        private val TAG = "HomeFragment"
    }

    lateinit var binding: FragmentHomeBinding
    private val vm by viewModel<HomeFragmentViewModel>()
    private var player: SimpleExoPlayer? = null
    lateinit var dataSourceFactory: DataSource.Factory
    lateinit var listTopGameAdapter: ListTopGameAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataSourceFactory = DefaultDataSourceFactory(
            context, Util.getUserAgent(context, "Game World")
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setupWithNavController(findNavController())

        setupListGame()
        setupToolbar()
        setupTopGame()
        view.postDelayed({
            subscribeUi()
        }, 300)
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "On Start!")
        //setup exoplayer to play game clip without sound and auto replay
        player = ExoPlayerFactory.newSimpleInstance(requireContext())
        player?.volume = 0f
        player?.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
                when(playbackState) {
                    Player.STATE_ENDED -> {
                        //auto replay
                        player?.seekTo(0)
                    }
                    Player.STATE_READY -> {
                        binding.thumbnailVideo.showOrHide(false)
                        binding.playerView.showOrHide(true)
                    }
                    Player.STATE_IDLE -> {
                        binding.thumbnailVideo.showOrHide(true)
                        binding.playerView.showOrHide(false)
                    }
                }
            }
        })

        //bind player to playerview
        binding.playerView.player = player

        //bind player to list top game

        playClipTopGame()
    }

    private fun setupTopGame() {
        //setup resize mode
        binding.playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        binding.playerView.useController = false
    }

    /**
     * setup toolbar with navigation
     * */
    private fun setupToolbar() {
        binding.toolbar.setupWithNavController(Navigation.findNavController(requireActivity(), R.id.fragment_container))
        binding.toolbar.title = ""
        binding.toolbar.setOnMenuItemClickListener {item ->
            val navController = Navigation.findNavController(requireActivity(), R.id.fragment_container)
            Log.d("AppLog", "${item.itemId}")
            item.onNavDestinationSelected(navController)
        }
    }

    /**
     * setup listgame recyclerview, notify current position focus and play game clip
     * */
    private fun setupListGame() {
        val linear = LinearLayoutManager(requireContext())
        val infiniteScrollListener = object : InfiniteScrollListener(linear) {
            override fun onLoadMore() {
                vm.nextPageTopGame()
            }

            override fun isDataLoading(): Boolean {
                return vm.isLoading.value?.peek() ?: false
            }

        }
        listTopGameAdapter =
            ListTopGameAdapter(
                requireContext(),
                dataSourceFactory
            ).apply {
                stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }

        linearLayoutManager = linear
        binding.listTopGame.apply {
            adapter = listTopGameAdapter
            addOnScrollListener(infiniteScrollListener)
            layoutManager = linearLayoutManager
        }
    }

    /**
     * subscribe viewmodel
     * */
    private fun subscribeUi() {

        vm.navigateToGameDetail.observe(viewLifecycleOwner, EventObserver {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragment_container)
            val action =
                TabContainerFragmentDirections.actionTabContainerFragmentToGameDetailFragment(it)
            navController.navigate(action)
        })

        vm.navigateToBuyGame.observe(viewLifecycleOwner, EventObserver {
            buyGame(it)
        })

        //when list top rating game changed, updated list game
        vm.listTopRatingGameUiModel.observe(viewLifecycleOwner, Observer { listTopGame ->
            listTopGame ?: return@Observer

            binding.root.post {
                listTopGameAdapter.submitList(listTopGame.list)
            }
        })

        //when top rating game changed, updated top rating game
        vm.topRatingGameUiModel.observe(viewLifecycleOwner, Observer { topGame ->
            topGame ?: return@Observer

            binding.root.postDelayed({

                playClipTopGame()

                binding.nameTopGame.text = topGame.nameGame

                binding.starPointTopGameTv.text = topGame.starPoint.toString()

                binding.detail.setOnClickListener {
                    topGame.onClickDetail()
                }

                binding.buy.setOnClickListener {
                    topGame.onClickBuy()
                }

                Glide.with(this)
                    .load(topGame.clipPreviewUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.thumbnailVideo)
            }, 200)
        })

        vm.isError.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                binding.root.post {
                    val dialog = MaterialAlertDialogBuilder(requireContext())
                        .setMessage("No internet!")
                        .setNegativeButton("Retry") { dialog, which ->
                            vm.loadGame()
                        }
                        .setCancelable(true)
                        .create()
                    dialog.show()
                }
            }
        })

        vm.isLoading.observe(viewLifecycleOwner, EventObserver {
//            binding.loadingView.showOrHide(it)
            if (it) listTopGameAdapter.dataStartedLoading()
            else listTopGameAdapter.dataFinishedLoading()

        })
    }

    /**
     * release exo player of fragment and exoplayer of list game
     * */
    override fun onStop() {
        LogTool.d("AppLog", "Stop!")
        super.onStop()
        player?.release()
        player = null
        listTopGameAdapter.onStop()
    }

    fun playClipTopGame() {

        binding.root.postDelayed({
            Log.d(TAG, "Play clip")
            if (player?.playbackState == Player.STATE_IDLE) {
                val path = vm.getTopGame()?.clipUrl
                path?.let {
                    val videoSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(path))
                    lifecycleScope.launch(Dispatchers.Main) {
                        player?.prepare(videoSource)
                    }
                    player?.playWhenReady = true
                }
            }
        }, 200)

    }

    private fun stopClipTopGame() {
        player?.stop()
    }

    private fun buyGame(game: Game) {

        if (game.stores.isEmpty() || game.stores[0].website.isNullOrBlank()) return
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(game.stores[0].website)
        startActivity(intent)
    }

}
