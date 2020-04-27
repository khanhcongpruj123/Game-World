package com.icongkhanh.gameworld.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.icongkhanh.gameworld.databinding.ItemSearchGameBinding
import com.icongkhanh.gameworld.model.SearchGameUiModel

class ListSearchGameAdapter(context: Context) :
    ListAdapter<SearchGameUiModel, ListSearchGameAdapter.GameSearchHolder>(GameDiff) {


    inner class GameSearchHolder(val binding: ItemSearchGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(game: SearchGameUiModel) {
            binding.thumbnail.post {
                Glide.with(binding.thumbnail)
                    .load(game.thumbnailUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.thumbnail)
            }

            binding.name.post {
                binding.name.text = game.name
            }

            itemView.setOnClickListener {
                game.onClick()
            }
        }

    }

    private object GameDiff : DiffUtil.ItemCallback<SearchGameUiModel>() {
        override fun areItemsTheSame(
            oldItem: SearchGameUiModel,
            newItem: SearchGameUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchGameUiModel,
            newItem: SearchGameUiModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameSearchHolder {
        return GameSearchHolder(
            ItemSearchGameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GameSearchHolder, position: Int) {
        holder.bind(getItem(position))
    }
}