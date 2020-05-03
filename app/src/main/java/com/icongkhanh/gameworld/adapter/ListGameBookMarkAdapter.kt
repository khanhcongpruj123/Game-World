package com.icongkhanh.gameworld.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.icongkhanh.gameworld.databinding.ItemBookmarkBinding
import com.icongkhanh.gameworld.model.GameBookmarkUiModel

class ListGameBookMarkAdapter(val context: Context) :
    ListAdapter<GameBookmarkUiModel, ListGameBookMarkAdapter.BookMarkGameHolder>(GameBookMarkDiff) {

    inner class BookMarkGameHolder(val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(game: GameBookmarkUiModel) {
            binding.name.text = game.name

            binding.rating.text = game.rating.toString()

            Glide.with(context)
                .load(game.imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(binding.thumbnail)

            itemView.setOnClickListener {
                game.onClick()
            }
        }

    }

    object GameBookMarkDiff : DiffUtil.ItemCallback<GameBookmarkUiModel>() {
        override fun areItemsTheSame(
            oldItem: GameBookmarkUiModel,
            newItem: GameBookmarkUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GameBookmarkUiModel,
            newItem: GameBookmarkUiModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMarkGameHolder {
        val binding =
            ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookMarkGameHolder(binding)
    }

    override fun onBindViewHolder(holder: BookMarkGameHolder, position: Int) {
        holder.bind(getItem(position))
    }
}