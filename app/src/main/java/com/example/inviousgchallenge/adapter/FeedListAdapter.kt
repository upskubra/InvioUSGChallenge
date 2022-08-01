package com.example.inviousgchallenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inviousgchallenge.data.model.Image
import com.example.inviousgchallenge.databinding.FeedImagesItemBinding
import com.example.inviousgchallenge.util.OnDoubleClickListenerAdapter
import com.example.inviousgchallenge.util.loadUrl

class FeedListAdapter(
    private val imageList: List<Image>,
    private val onDoubleClickListenerAdapter: OnDoubleClickListenerAdapter
) :
    ListAdapter<Image, FeedListAdapter.ImageViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            FeedImagesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class ImageViewHolder(private val binding: FeedImagesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            binding.apply {
                Glide.with(this.root)
                    .load(image.uri)
                    .into(feedImage)
            }
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    //  will review for trash operation
    class DiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.uri == newItem.uri
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }
}