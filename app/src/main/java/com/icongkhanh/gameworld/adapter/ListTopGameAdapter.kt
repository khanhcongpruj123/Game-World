package com.icongkhanh.gameworld.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.icongkhanh.common.showOrHide
import com.icongkhanh.gameworld.databinding.ItemTopGameBinding
import com.icongkhanh.gameworld.model.ItemTopGameUiModel

class ListTopGameAdapter(val context: Context, val dataSourceFactory: DataSource.Factory) :
    ListAdapter<ItemTopGameUiModel, ListTopGameAdapter.GameHolder>(GameDiff) {

    var onReachedEnd: () -> Unit = {}
        set
    var playPosition = -1
        set(value) {
            if (field != value) {
                val oldPos = field
                field = value
                if (oldPos != field) {
                    notifyItemChanged(oldPos)
                    notifyItemChanged(field)
                }

            }
        }

//    var player: SimpleExoPlayer? = null

    inner class GameHolder(val binding: ItemTopGameBinding): RecyclerView.ViewHolder(binding.root) {

        init {
//            binding.playView.useController = false
//            binding.playView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        }

        fun bind(game: ItemTopGameUiModel) {

            binding.nameGame.text = game.name

            val genre = StringBuilder()
            game.listGenre.forEach {
                genre.append("${it}, ")
            }

            binding.genre.text = genre.toString()
            binding.starPointTv.text = game.rating.toString()
            binding.ratingCount.text = game.ratingCount.toString()


            binding.thumbnailVideo.postDelayed({
                Glide.with(context)
                    .load(game.clipPreviewUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.thumbnailVideo)
            }, 100)

            binding.thumbnail.postDelayed({
                Glide.with(context)
                    .load(game.thumbnailUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.thumbnail)
            }, 100)

            binding.root.setOnClickListener {
                game.onClick()
            }

            if (playPosition == adapterPosition) {
                binding.root.requestFocus()
//                binding.playView.player = player
//                binding.playView.showOrHide(true)
//                player?.addListener(object : Player.EventListener {
//                    override fun onPlayerStateChanged(
//                        playWhenReady: Boolean,
//                        playbackState: Int
//                    ) {
//                        super.onPlayerStateChanged(playWhenReady, playbackState)
//
//                        when(playbackState) {
//                            Player.STATE_ENDED -> {
//                                player?.seekTo(0)
//                            }
//                            Player.STATE_READY -> {
//                                this@GameHolder.binding.thumbnailVideo.showOrHide(false)
//                            }
//                        }
//                    }
//                })
                if (game.clipUrl.isNotEmpty()) {
                    val videoSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(game.clipUrl))
//                    player?.prepare(videoSource)
//                    player?.playWhenReady = true
                }
            } else {
//                binding.playView.player = null
//                binding.playView.showOrHide(false)
                binding.thumbnailVideo.showOrHide(true)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    object GameDiff : DiffUtil.ItemCallback<ItemTopGameUiModel>() {

        override fun areItemsTheSame(
            oldItem: ItemTopGameUiModel,
            newItem: ItemTopGameUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ItemTopGameUiModel,
            newItem: ItemTopGameUiModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val binding = ItemTopGameBinding.inflate(LayoutInflater.from(context), parent, false)
        return GameHolder(binding)
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun onStop() {
//        player?.release()
//        player = null
    }

}