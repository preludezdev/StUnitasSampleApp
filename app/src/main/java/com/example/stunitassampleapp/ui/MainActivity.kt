package com.example.stunitassampleapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.stunitassampleapp.R
import com.example.stunitassampleapp.databinding.ActivityMainBinding
import com.example.stunitassampleapp.vm.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        MainViewModel()
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }
}
