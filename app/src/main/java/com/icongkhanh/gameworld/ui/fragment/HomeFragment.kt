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
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.icongkhanh.gameworld.R
import com.icongkhanh.gameworld.databinding.FragmentHomeBinding
import com.icongkhanh.gameworld.viewmodel.HomeFragmentViewModel
import com.icongkhanh.gameworld.widget.ListGameRecyclerView.ListTopGameAdapter
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    companion object {
        val TAG = "HomeFragment"
    }

    lateinit var binding: FragmentHomeBinding
    val vm by viewModel<HomeFragmentViewModel>()
    var player: SimpleExoPlayer? = null
    lateinit var dataSourceFactory: DataSource.Factory
    lateinit var listTopGameAdapter: ListTopGameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        player = ExoPlayerFactory.newSimpleInstance(requireContext())
        player?.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
                when(playbackState) {
                    Player.STATE_ENDED -> {
                        player?.seekTo(0)
                    }
                }
            }
        })
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

        setupListGame()
        setupToolbar()
        setupTopGame()

        subscribeUi()
    }

    private fun setupTopGame() {
        player?.volume = 0f
        binding.playerView.player = player
        binding.playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
    }

    private fun setupToolbar() {
        binding.toolbar.setupWithNavController(Navigation.findNavController(requireActivity(), R.id.fragment_container))
        binding.toolbar.title = ""
        binding.toolbar.setOnMenuItemClickListener {item ->
            val navController = Navigation.findNavController(requireActivity(), R.id.fragment_container)
            Log.d("AppLog", "${item.itemId}")
            item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

    private fun setupListGame() {
        listTopGameAdapter =
            ListTopGameAdapter(
                requireContext()
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

                    val targetPosition = when {
                        startPosition == 0 -> startPosition
                        endPosition == listTopGameAdapter.itemCount - 1 -> endPosition
                        else -> startPosition + 1
                    }

                    listTopGameAdapter.playPosition = targetPosition

                }
            }
        })
    }

    private fun subscribeUi() {

        vm.topRatingGame.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "Top game: ${it[0].rating}")

            val topGame = it[0]
            binding.nameTopGame.text = topGame.name
            binding.starPointTopGameTv.text = topGame.rating.toString()

            val listTopGame = it.subList(1, it.size)
            val videoSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(topGame.clipUrl))
            player?.prepare(videoSource)
            player?.playWhenReady = true
            listTopGameAdapter.updateListGame(listTopGame)
        })
    }

    override fun onDestroy() {
        player?.release()
        player = null
        super.onDestroy()
    }

}
