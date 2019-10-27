package com.example.stunitassampleapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stunitassampleapp.databinding.KakaoItemLayoutBinding

class KakaoImageAdapter : RecyclerView.Adapter<KakaoImageAdapter.KakaoViewHolder>() {
    private val imageUrls = mutableListOf<String>()

    fun setData(urls: List<String>?) {
        if (!urls.isNullOrEmpty()) {
            imageUrls.clear()
            imageUrls.addAll(urls)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KakaoViewHolder {
        val binding =
            KakaoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return KakaoViewHolder(binding)
    }

    override fun getItemCount(): Int = imageUrls.size

    override fun onBindViewHolder(holder: KakaoViewHolder, position: Int) {
        holder.bind(imageUrls[position])
    }

    class KakaoViewHolder(private val binding: KakaoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
            Glide
                .with(binding.root)
                .load(url)
                .into(binding.ivImage)
        }
    }
}