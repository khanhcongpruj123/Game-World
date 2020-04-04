package com.icongkhanh.gameworld.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.icongkhanh.common.hideOrShow
import com.icongkhanh.gameworld.adapter.ListScreenshotAdapter
import com.icongkhanh.gameworld.databinding.FragmentGameDetailBinding
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.model.ScreenshotModelUi


/**
 * A simple [Fragment] subclass.
 */
class GameDetailFragment : Fragment() {

    companion object {
        private val TAG = "GameDetailFragment"
    }

    //view binding
    lateinit var binding: FragmentGameDetailBinding

    //argument pass from other fragment
    val args by navArgs<GameDetailFragmentArgs>()

    //exoplayer to play clip game
    var player: SimpleExoPlayer? = null

    // url clip factory for exoplayer
    lateinit var dataSourceFactory: DataSource.Factory

    // game which must display info to UI
    var game: Game? = null

    //ui
    lateinit var screenshotAdapter: ListScreenshotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataSourceFactory = DefaultDataSourceFactory(
            context, Util.getUserAgent(context, "Game World")
        )

        game = args.gameItem
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupOnBackPress()
        displayInfoGame()
        setupListScreenshot()
        setupDescription()
    }

    private fun setupDescription() {
        binding.desciption.text = game?.description
    }

    private fun setupListScreenshot() {
        screenshotAdapter = ListScreenshotAdapter(requireContext())
        binding.listScreenshot.layoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.HORIZONTAL
        }
        binding.listScreenshot.adapter = screenshotAdapter

        screenshotAdapter.update(game?.screenShort?.map {
            ScreenshotModelUi(
                it
            ) { url ->
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setType("image/*")
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }?: emptyList())
    }

    override fun onStart() {
        super.onStart()

        setupPlayer()
        playClipTopGame()
    }

    private fun setupPlayer() {
        player = ExoPlayerFactory.newSimpleInstance(requireContext())
        player?.volume = 0f
        player?.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)

                when (playbackState) {
                    Player.STATE_READY -> {
                        binding.thumbnailVideo.hideOrShow(false)
                        binding.playerView.hideOrShow(true)
                    }
                }
            }
        })

        binding.playerView.player = player
        binding.playerView.setOnClickListener {
            game?.clipUrl?.let {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setType("video/*")
                intent.data = Uri.parse(it)
                startActivity(intent)
            }
        }
    }

    private fun displayInfoGame() {
        binding.nameGame.text = game?.name
        binding.starPoint.text = game?.rating.toString()

        Glide.with(this)
            .load(game?.clipPreviewUrl)
            .into(binding.thumbnailVideo)

        Glide.with(this)
            .load(game?.imgUrl)
            .transform(RoundedCorners(100))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.thumbnail)
    }

    fun setupToolbar() {
        binding.toolbar.setupWithNavController(findNavController())
        binding.toolbar.title = ""
    }

    fun setupOnBackPress() {

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
            true
        }

    }

    override fun onStop() {
        super.onStop()

        player?.release()
        player = null
    }

    fun playClipTopGame() {
        if (player?.playbackState == Player.STATE_IDLE) {
            val path = game?.clipUrl
            path?.let {
                val videoSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(path))
                player?.prepare(videoSource)
                player?.playWhenReady = true
            }
        }
    }

}
