package com.example.stunitassampleapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stunitassampleapp.R
import com.example.stunitassampleapp.databinding.KakaoItemLayoutBinding
import com.example.stunitassampleapp.network.vo.KakaoImageResponse

class KakaoImageAdapter :
    PagedListAdapter<KakaoImageResponse.Document, KakaoImageAdapter.KakaoViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KakaoViewHolder {
        val binding =
            KakaoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return KakaoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KakaoViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class KakaoViewHolder(private val binding: KakaoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(document: KakaoImageResponse.Document) {
            Glide
                .with(binding.root)
                .load(document.imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(binding.ivImage)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<KakaoImageResponse.Document>() {
            override fun areItemsTheSame(
                oldItem: KakaoImageResponse.Document,
                newItem: KakaoImageResponse.Document
            ) =
                oldItem.imageUrl === newItem.imageUrl

            override fun areContentsTheSame(
                oldItem: KakaoImageResponse.Document,
                newItem: KakaoImageResponse.Document
            ) =
                oldItem.imageUrl == newItem.imageUrl
        }
    }
}