package com.example.stunitassampleapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _queryString = MutableLiveData<String>()
    val queryString: LiveData<String> get() = _queryString

    fun searchQuery() {

    }
}