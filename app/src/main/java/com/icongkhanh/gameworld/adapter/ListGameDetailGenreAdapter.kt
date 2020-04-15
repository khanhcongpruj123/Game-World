package com.icongkhanh.gameworld.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.icongkhanh.gameworld.databinding.ItemGenreGameDetailBinding
import com.icongkhanh.gameworld.model.ItemGenreUiModel

class ListGameDetailGenreAdapter(val context: Context) :
    ListAdapter<ItemGenreUiModel, ListGameDetailGenreAdapter.GameDetailGenreHolder>(GenreDiff) {

    inner class GameDetailGenreHolder(val binding: ItemGenreGameDetailBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemGenreUiModel) {

            binding.genre.text = item.name

            //on click
            binding.root.setOnClickListener {
                item.onClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameDetailGenreHolder {
        val binding = ItemGenreGameDetailBinding.inflate(LayoutInflater.from(context), parent, false)
        return GameDetailGenreHolder(binding)
    }

    override fun onBindViewHolder(holder: GameDetailGenreHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object GenreDiff : DiffUtil.ItemCallback<ItemGenreUiModel>() {

        override fun areItemsTheSame(
            oldItem: ItemGenreUiModel,
            newItem: ItemGenreUiModel
        ): Boolean {
            return oldItem.name.equals(newItem.name)
        }

        override fun areContentsTheSame(
            oldItem: ItemGenreUiModel,
            newItem: ItemGenreUiModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}