package com.example.stunitassampleapp.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stunitassampleapp.ui.KakaoImageAdapter

@BindingAdapter("setData")
fun RecyclerView.setData(imageUrls: List<String>?) {
    (adapter as? KakaoImageAdapter)?.setData(imageUrls)
}