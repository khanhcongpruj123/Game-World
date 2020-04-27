package com.icongkhanh.gameworld.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.icongkhanh.common.event.EventObserver
import com.icongkhanh.common.showOrHide
import com.icongkhanh.gameworld.R
import com.icongkhanh.gameworld.adapter.ListGameDetailGenreAdapter
import com.icongkhanh.gameworld.adapter.ListMoreGameAdapter
import com.icongkhanh.gameworld.adapter.ListScreenshotAdapter
import com.icongkhanh.gameworld.databinding.FragmentGameDetailBinding
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.model.GameDetailUiModel
import com.icongkhanh.gameworld.viewmodel.GameDetailFragmentViewModel
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class GameDetailFragment : Fragment() {

    companion object {
        private val TAG = "GameDetailFragment"
    }

    //view model
    val vm by viewModel<GameDetailFragmentViewModel>()

    //view binding
    lateinit var binding: FragmentGameDetailBinding

    //argument pass from other fragment
    val args by navArgs<GameDetailFragmentArgs>()

    //exoplayer to play clip game
    var player: SimpleExoPlayer? = null

    // url clip factory for exoplayer
    lateinit var dataSourceFactory: DataSource.Factory

    //ui
    lateinit var screenshotAdapter: ListScreenshotAdapter
    lateinit var gameDetailGenreAdapter: ListGameDetailGenreAdapter
    lateinit var moreGameAdapter: ListMoreGameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataSourceFactory = DefaultDataSourceFactory(
            context, Util.getUserAgent(context, "Game World")
        )

        vm.initial(args.gameItem)
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

        binding.buy.setOnClickListener {
            buyGame(vm.getCurrentGame())
        }

        setupToolbar()
        setupOnBackPress()
        setupListScreenshot()
        setupListMoregame()
        setupGenre()

        subscribeUi()
    }

    private fun setupListMoregame() {
        moreGameAdapter = ListMoreGameAdapter()
        binding.listMoregame.apply {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = RecyclerView.HORIZONTAL
                layoutAnimation
            }
            adapter = moreGameAdapter
        }
    }

    private fun buyGame(game: Game) {

        Log.d(TAG, "game website: ${game.stores[0]}")

        if (game.stores.isEmpty() || game.stores[0].website.isNullOrBlank()) return
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(game.stores[0].website)
        startActivity(intent)
    }

    fun subscribeUi() {

        vm.gameUiModel.observe(viewLifecycleOwner, Observer { game ->

            screenshotAdapter

            gameDetailGenreAdapter.submitList(game.listGenre)

            screenshotAdapter.update(game.listScreenShot)

            // info game
            displayInfoGame(game)

            // setup listener
            setupListener(game)

            binding.playerView.postDelayed({
                playClipGame()
            }, 200)
        })

        vm.listMoreGameUiModel.observe(viewLifecycleOwner, Observer {
            moreGameAdapter.submitList(it)
        })

        vm.navigateToBuy.observe(viewLifecycleOwner, EventObserver {
            buyGame(it)
        })

        vm.navigateToViewClip.observe(viewLifecycleOwner, EventObserver {
            navigateToViewClip(it)
        })

        vm.navigateToViewImage.observe(viewLifecycleOwner, EventObserver {
            navigateToViewImage(it)
        })

        vm.navigateToDetail.observe(viewLifecycleOwner, EventObserver {
            navigateToDetail(it)
        })
    }

    private fun navigateToDetail(it: Game) {
        val navController =
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
        val action = GameDetailFragmentDirections.actionGameDetailFragmentSelf(it)
        navController.navigate(action)
    }

    private fun navigateToViewImage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setType("image/*")
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun setupListener(game: GameDetailUiModel) {
        binding.buy.setOnClickListener {
            game.onClickBuy()
        }

        binding.playerView.setOnClickListener {
            game.onClickClip()
        }
    }

    private fun setupListScreenshot() {
        screenshotAdapter = ListScreenshotAdapter(requireContext())
        binding.listScreenshot.layoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.HORIZONTAL
        }
        binding.listScreenshot.adapter = screenshotAdapter
    }

    private fun setupGenre() {
        gameDetailGenreAdapter = ListGameDetailGenreAdapter(requireContext())
        binding.listGenre.layoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.HORIZONTAL
        }
        binding.listGenre.adapter = gameDetailGenreAdapter
    }

    override fun onStart() {
        super.onStart()

        setupPlayer()
        playClipGame()
    }

    private fun setupPlayer() {
        player = ExoPlayerFactory.newSimpleInstance(requireContext())
        player?.volume = 0f
        player?.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)

                when (playbackState) {
                    Player.STATE_READY -> {
                        binding.thumbnailVideo.showOrHide(false)
                        binding.playerView.showOrHide(true)
                    }
                }
            }
        })

        binding.playerView.player = player
    }

    private fun displayInfoGame(game: GameDetailUiModel) {

        binding.reviewCount.text = game.reviewCount.toString()

        binding.suggestionCount.text = game.suggestionCount.toString()

        //name game
        binding.nameGame.text = game.name

        //star point
        binding.starPoint.text = game.rating.toString()

        //description
        binding.introduce.setContent(
            Html.fromHtml(game.description, Html.FROM_HTML_MODE_COMPACT).toString()
        )

        //requirement
        binding.minimum.text = Html.fromHtml(game.requirement, Html.FROM_HTML_MODE_COMPACT)
        binding.recommend.text = Html.fromHtml(game.recommened, Html.FROM_HTML_MODE_COMPACT)

        binding.thumbnailVideo.postDelayed({
            Glide.with(this)
                .load(game.clipPreviewUrl)
                .into(binding.thumbnailVideo)
        }, 100)

        binding.thumbnail.postDelayed({
            Glide.with(this)
                .load(game.imgUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.thumbnail)
        }, 100)
    }

    fun setupToolbar() {
        binding.toolbar.setupWithNavController(findNavController())
    }

    fun setupOnBackPress() {

//        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
//            findNavController().navigateUp()
//            true
//        }

    }

    override fun onStop() {
        super.onStop()

        player?.release()
        player = null
    }

    private fun playClipGame() {
        if (player?.playbackState == Player.STATE_IDLE) {
            val path = vm.getCurrentGame().clipUrl
            path?.let {
                val videoSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(path))
                player?.prepare(videoSource)
                player?.playWhenReady = true
            }
        }
    }

    private fun navigateToViewClip(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setType("video/*")
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}
