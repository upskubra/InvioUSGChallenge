package com.example.inviousgchallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inviousgchallenge.R
import com.example.inviousgchallenge.data.model.CatImageItem
import com.example.inviousgchallenge.databinding.CatImagesItemBinding
import com.example.inviousgchallenge.util.OnDoubleClickListenerAdapter
import com.example.inviousgchallenge.util.loadUrl
import com.example.inviousgchallenge.util.setOnDoubleClickListener

class CatListAdapter(
    private val catList: List<CatImageItem>,
    private val onDoubleClickListenerAdapter: OnDoubleClickListenerAdapter
) : ListAdapter<CatImageItem, CatListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cat_images_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(catList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = CatImagesItemBinding.bind(itemView)
        fun bind(catImageItem: CatImageItem) {
            binding.apiTitle.text = catImageItem.name
            catImageItem.image?.url?.let { binding.apiImage.loadUrl(it) }
            binding.apiCardView.setOnDoubleClickListener {
                onDoubleClickListenerAdapter.onClick(adapterPosition)
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<CatImageItem>() {
        override fun areItemsTheSame(oldItem: CatImageItem, newItem: CatImageItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CatImageItem, newItem: CatImageItem): Boolean {
            return oldItem == newItem
        }
    }
}
