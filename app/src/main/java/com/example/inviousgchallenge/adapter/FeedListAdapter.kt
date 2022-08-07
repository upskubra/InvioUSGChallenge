package com.example.inviousgchallenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inviousgchallenge.data.model.Image
import com.example.inviousgchallenge.databinding.FeedImagesItemBinding
import com.example.inviousgchallenge.util.OnDoubleClickListenerAdapter
import com.example.inviousgchallenge.util.loadUrl
import com.example.inviousgchallenge.util.setOnDoubleClickListener

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
        holder.bind(imageList[position])
    }

    inner class ImageViewHolder(private val binding: FeedImagesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            binding.apply {
                image.uri?.let { feedImage.loadUrl(it) }
                feedTitle.text = image.id
                deleteButton.setOnDoubleClickListener {
                    onDoubleClickListenerAdapter.onClick(adapterPosition)
                }
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