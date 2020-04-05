package com.icongkhanh.gameworld.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.icongkhanh.gameworld.R
import com.icongkhanh.gameworld.databinding.ItemGenreGameDetailBinding
import com.icongkhanh.gameworld.databinding.ItemScreenshotBinding
import com.icongkhanh.gameworld.model.GameDetailGenreUiModel
import com.icongkhanh.gameworld.model.ScreenshotModelUi

class ListGameDetailGenreAdapter (val context: Context): RecyclerView.Adapter<ListGameDetailGenreAdapter.GameDetailGenreHolder>() {

    private val listScreenShotUrl = mutableListOf<GameDetailGenreUiModel>()

    inner class GameDetailGenreHolder(val binding: ItemGenreGameDetailBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GameDetailGenreUiModel) {

            binding.genre.text = item.genre.name

            //on click
            binding.root.setOnClickListener {
                item.onClick(item.genre)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameDetailGenreHolder {
        val binding = ItemGenreGameDetailBinding.inflate(LayoutInflater.from(context), parent, false)
        return GameDetailGenreHolder(binding)
    }

    override fun getItemCount(): Int {
        return listScreenShotUrl.size
    }

    override fun onBindViewHolder(holder: GameDetailGenreHolder, position: Int) {
        holder.bind(listScreenShotUrl[position])
    }

    fun update(list: List<GameDetailGenreUiModel>) {
        listScreenShotUrl.clear()
        listScreenShotUrl.addAll(list)
        notifyDataSetChanged()
    }
}