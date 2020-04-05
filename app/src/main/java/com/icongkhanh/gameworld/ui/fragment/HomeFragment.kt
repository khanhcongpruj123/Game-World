package com.icongkhanh.gameworld.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
import com.icongkhanh.common.hideOrShow
import com.icongkhanh.gameworld.R
import com.icongkhanh.gameworld.databinding.FragmentHomeBinding
import com.icongkhanh.gameworld.viewmodel.HomeFragmentViewModel
import com.icongkhanh.gameworld.viewmodel.TabContainerViewModel
import com.icongkhanh.gameworld.adapter.ListTopGameAdapter
import org.koin.android.viewmodel.ext.android.sharedViewModel
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
    val tabContainerViewModel by sharedViewModel<TabContainerViewModel>()
    private var player: SimpleExoPlayer? = null
    lateinit var dataSourceFactory: DataSource.Factory
    lateinit var listTopGameAdapter: ListTopGameAdapter

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

        Log.d(TAG, "On View Created!")

        tabContainerViewModel.isLoading(true)

        binding.detail.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.fragment_container)
            val action = TabContainerFragmentDirections.actionTabContainerFragmentToGameDetailFragment(vm.topRatingGame.value!!)
            navController.navigate(action)
        }

        setupListGame()
        setupToolbar()
        setupTopGame()

        subscribeUi()
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
                        binding.thumbnailVideo.hideOrShow(false)
                        binding.playerView.hideOrShow(true)
                    }
                    Player.STATE_IDLE -> {
                        binding.thumbnailVideo.hideOrShow(true)
                        binding.playerView.hideOrShow(false)
                    }
                }
            }
        })

        //bind player to playerview
        binding.playerView.player = player

        //bind player to list top game

        //play clip top game
        playClipTopGame()

        listTopGameAdapter.onStart()
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
        listTopGameAdapter =
            ListTopGameAdapter(
                requireContext(),
                dataSourceFactory
            )

        listTopGameAdapter.setOnItemClicked { i, g ->
            val navController = Navigation.findNavController(requireActivity(), R.id.fragment_container)
            val action = TabContainerFragmentDirections.actionTabContainerFragmentToGameDetailFragment(g)
            navController.navigate(action)
        }

        binding.listTopGame.apply {
            adapter = listTopGameAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        //notify current position focus and play game clip
        binding.listTopGame.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val startPosition = layoutManager.findFirstVisibleItemPosition()
                    val endPosition = layoutManager.findLastVisibleItemPosition()

                    val isStartOfList = !recyclerView.canScrollVertically(-1)
                    val canScrollUp = recyclerView.canScrollVertically(1)
                    val canScrollDown = recyclerView.canScrollVertically(-1)
                    val isEndOfList = !recyclerView.canScrollVertically(1)

                    Log.d(TAG, "${canScrollDown} ${canScrollUp}")

                    val targetPosition = when {
                        canScrollDown && canScrollUp && startPosition == 0 -> 0
                        isStartOfList -> -1
                        isEndOfList -> listTopGameAdapter.itemCount - 1
                        else -> startPosition
                    }

                    if (targetPosition == -1) playClipTopGame()
                    else stopClipTopGame()

                    listTopGameAdapter.playPosition = targetPosition

                }
            }
        })
    }

    /**
     * subscribe viewmodel
     * */
    private fun subscribeUi() {

        var listTopGameCompleted = false
        var topRatingGameCompleted = false

        //when list top rating game changed, updated list game
        vm.listTopRatingGame.observe(viewLifecycleOwner, Observer {listTopGame ->
            listTopGameAdapter.updateListGame(listTopGame)

            //show or hide loading view
            listTopGameCompleted = true
            tabContainerViewModel.isLoading(!(listTopGameCompleted && topRatingGameCompleted))
        })

        //when top rating game changed, updated top rating game
        vm.topRatingGame.observe(viewLifecycleOwner, Observer {topGame ->
            binding.nameTopGame.text = topGame.name
            binding.starPointTopGameTv.text = topGame.rating.toString()

            Glide.with(this)
                .load(topGame.clipPreviewUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.thumbnailVideo)

            if (player?.playbackState == Player.STATE_IDLE) playClipTopGame()

            //show or hide loading view
            topRatingGameCompleted = true
            tabContainerViewModel.isLoading(!(listTopGameCompleted && topRatingGameCompleted))
        })
    }

    /**
     * release exo player of fragment and exoplayer of list game
     * */
    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
        listTopGameAdapter.onStop()
    }

    fun playClipTopGame() {
        Log.d(TAG, "Play clip")
        if (player?.playbackState == Player.STATE_IDLE) {
            val path = vm.topRatingGame.value?.clipUrl
            path?.let {
                val videoSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(path))
                player?.prepare(videoSource)
                player?.playWhenReady = true
            }
        }
    }

    private fun stopClipTopGame() {
        player?.stop()
    }

}
