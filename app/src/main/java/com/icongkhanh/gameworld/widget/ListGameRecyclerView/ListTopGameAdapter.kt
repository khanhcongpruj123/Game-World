package com.icongkhanh.gameworld.widget.ListGameRecyclerView

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
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
import com.icongkhanh.gameworld.databinding.ItemTopGameBinding
import com.icongkhanh.gameworld.domain.model.Game

class ListTopGameAdapter(val context: Context) : RecyclerView.Adapter<ListTopGameAdapter.GameHolder>() {

    val mainHandler = Handler(Looper.getMainLooper())
    val listGame = mutableListOf<Game>()
    private var onSelected: (index: Int, game: Game) -> Unit = {i, g ->}
    var playPosition = -1
        set(value) {
            if (field != value) {
                val oldPos = field
                field = value
                notifyItemChanged(field)
                notifyItemChanged(oldPos)
            }
        }
    val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "Game World"))


    inner class GameHolder(val binding: ItemTopGameBinding): RecyclerView.ViewHolder(binding.root) {

        var player: SimpleExoPlayer?

        init {
            player = ExoPlayerFactory.newSimpleInstance(context)
            player?.volume = 0f
            binding.playView.player = player
            binding.playView.useController = false
            binding.playView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        }

        fun bind(game: Game) {
            binding.nameGame.text = game.name

            val genre = StringBuilder()
            game.genre.forEach {
                genre.append("${it.name}, ")
            }

            binding.genre.text = genre.toString()
            binding.starPointTv.text = game.rating.toString()
            binding.ratingCount.text = game.ratingsCount.toString()

            Glide.with(context)
                .load(game.clipPreviewUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.thumbnailVideo)
            Glide.with(context)
                .load(game.imgUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.thumbnail)

            binding.root.setOnClickListener {
                onSelected(adapterPosition, game)
            }

            if (playPosition == adapterPosition && oldPosition != playPosition) {
                binding.playView.hideOrShow(true)
                binding.thumbnailVideo.hideOrShow(false)
                if (game.clipUrl.isNotEmpty()) {
                    val videoSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(game.clipUrl))
                    player?.prepare(videoSource)
                    player?.playWhenReady = true
                    player?.addListener(object : Player.EventListener {
                        override fun onPlayerStateChanged(
                            playWhenReady: Boolean,
                            playbackState: Int
                        ) {
                            super.onPlayerStateChanged(playWhenReady, playbackState)

                            when(playbackState) {
                                Player.STATE_ENDED -> {
                                    player?.seekTo(0)
                                }
                            }
                        }
                    })
                }
            } else {
                binding.playView.hideOrShow(false)
                binding.thumbnailVideo.hideOrShow(true)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val binding = ItemTopGameBinding.inflate(LayoutInflater.from(context), parent, false)
        return GameHolder(binding)
    }

    override fun getItemCount(): Int {
        return listGame.size
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.bind(listGame[position])
    }

    fun updateListGame(list: List<Game>) {
        listGame.clear()
        listGame.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClicked(listener: (i: Int, g: Game) -> Unit) {
        onSelected = listener
    }
}