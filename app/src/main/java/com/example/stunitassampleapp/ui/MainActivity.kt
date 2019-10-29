package com.example.stunitassampleapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.stunitassampleapp.R
import com.example.stunitassampleapp.databinding.ActivityMainBinding
import com.example.stunitassampleapp.vm.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val kakaoAdapter by lazy { KakaoImageAdapter() }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = mainViewModel
        binding.lifecycleOwner = this

        initRecyclerView()
        initCallback()
    }

    private fun initRecyclerView() {
        binding.rvImages.adapter = kakaoAdapter
    }

    private fun initCallback() {
        mainViewModel.kakaoPagedList.observe(this, Observer {
            kakaoAdapter.submitList(it)
        })

    }

}
