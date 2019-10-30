package com.example.stunitassampleapp.ui

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.stunitassampleapp.R
import com.example.stunitassampleapp.databinding.ActivityMainBinding
import com.example.stunitassampleapp.vm.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val kakaoAdapter by lazy { KakaoImageAdapter() }

    private lateinit var binding: ActivityMainBinding

    private var timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = mainViewModel
        binding.lifecycleOwner = this

        initRecyclerView()
        initEvent()
        initCallback()
    }

    private fun initRecyclerView() {
        binding.rvImages.adapter = kakaoAdapter
    }

    private fun initEvent() {
        binding.etSearch.setOnKeyListener { _, keyCode, keyEvent ->
            if ((keyCode == KeyEvent.KEYCODE_ENTER) && (keyEvent.action == KeyEvent.ACTION_DOWN)) {
                val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
            }
            false
        }
    }

    private fun initCallback() {
        mainViewModel.kakaoPagedList.observe(this, Observer {
            kakaoAdapter.submitList(it)
        })

        mainViewModel.queryString.observe(this, Observer {
            timer.cancel()
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    mainViewModel.searchQuery()
                }
            }, 1000)
        })

        mainViewModel.callbackMessage.observe(this, Observer {
            showToastMessage(it)
        })
    }

    private fun showToastMessage(msg: String?) {
        if (!msg.isNullOrEmpty()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

}
