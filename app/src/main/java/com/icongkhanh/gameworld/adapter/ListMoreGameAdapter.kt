package com.icongkhanh.gameworld.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.icongkhanh.gameworld.databinding.ItemMoreGameBinding
import com.icongkhanh.gameworld.model.ItemMoreGameUiModel

class ListMoreGameAdapter() :
    ListAdapter<ItemMoreGameUiModel, ListMoreGameAdapter.MoreGameHolder>(MoreGameDiff) {

    inner class MoreGameHolder(val binding: ItemMoreGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(game: ItemMoreGameUiModel) {

            itemView.setOnClickListener {
                game.onClick()
            }

            binding.nameGame.text = game.name

            Glide.with(binding.root)
                .load(game.imgUrl)
                .into(binding.thumbnail)
        }
    }


    object MoreGameDiff : DiffUtil.ItemCallback<ItemMoreGameUiModel>() {
        override fun areItemsTheSame(
            oldItem: ItemMoreGameUiModel,
            newItem: ItemMoreGameUiModel
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: ItemMoreGameUiModel,
            newItem: ItemMoreGameUiModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreGameHolder {
        return MoreGameHolder(
            ItemMoreGameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MoreGameHolder, position: Int) {
        holder.bind(getItem(position))
    }
}