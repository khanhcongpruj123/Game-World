package com.icongkhanh.gameworld.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.icongkhanh.gameworld.R
import com.icongkhanh.gameworld.databinding.ItemScreenshotBinding
import com.icongkhanh.gameworld.model.ScreenshotModelUi

class ListScreenshotAdapter (val context: Context): RecyclerView.Adapter<ListScreenshotAdapter.ScreensShotHolder>() {

    private val listScreenShotUrl = mutableListOf<ScreenshotModelUi>()

    inner class ScreensShotHolder(val binding: ItemScreenshotBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScreenshotModelUi) {

            val size = context.resources.getDimensionPixelSize(R.dimen.size_screenshot)

            binding.itemScreenshot.postDelayed({
                Glide.with(context)
                    .load(item.url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .override(size, size)
                    .into(binding.itemScreenshot)
            }, 1000)

            //on click
            binding.root.setOnClickListener {
                item.onClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreensShotHolder {
        val binding = ItemScreenshotBinding.inflate(LayoutInflater.from(context), parent, false)
        return ScreensShotHolder(binding)
    }

    override fun getItemCount(): Int {
        return listScreenShotUrl.size
    }

    override fun onBindViewHolder(holder: ScreensShotHolder, position: Int) {
        holder.bind(listScreenShotUrl[position])
    }

    fun update(list: List<ScreenshotModelUi>) {
        listScreenShotUrl.clear()
        listScreenShotUrl.addAll(list)
        notifyDataSetChanged()
    }
}