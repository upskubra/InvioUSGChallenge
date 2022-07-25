package com.example.inviousgchallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inviousgchallenge.R
import com.example.inviousgchallenge.util.OnDoubleClickListenerAdapter
import com.example.inviousgchallenge.util.loadUrl
import com.example.inviousgchallenge.util.setOnDoubleClickListener
import com.example.inviousgchallenge.data.model.ApiImageItem
import com.example.inviousgchallenge.databinding.ApiImagesItemBinding

class ApiImagesRecyclerAdapter(
    private val apiImageList: List<ApiImageItem>,
    private val onDoubleClickListenerAdapter: OnDoubleClickListenerAdapter
) : ListAdapter<ApiImageItem, ApiImagesRecyclerAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.api_images_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return apiImageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(apiImageList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ApiImagesItemBinding.bind(itemView)
        fun bind(apiImageItem: ApiImageItem) {
            binding.apiTitle.text = apiImageItem.name
            apiImageItem.image?.url?.let { binding.apiImage.loadUrl(it) }
            binding.apiCardView.setOnDoubleClickListener {
                onDoubleClickListenerAdapter.onClick(adapterPosition)
            }
        }

    }

    // add cloud gallery item to the list
    class DiffCallback : DiffUtil.ItemCallback<ApiImageItem>() {
        override fun areItemsTheSame(oldItem: ApiImageItem, newItem: ApiImageItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ApiImageItem, newItem: ApiImageItem): Boolean {
            return oldItem == newItem
        }
    }
}
