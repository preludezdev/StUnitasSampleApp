package com.example.stunitassampleapp.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stunitassampleapp.network.RetrofitHelper
import com.example.stunitassampleapp.network.vo.KakaoImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val queryString = MutableLiveData<String>()

    private val _imageUrls = MutableLiveData<List<String>>()
    val imageUrls: LiveData<List<String>> get() = _imageUrls

    fun searchQuery() {
        RetrofitHelper
            .getInstance()
            .apiService
            .searchImagesByQuery(API_KEY, queryString.value ?: "")
            .enqueue(object : Callback<KakaoImageResponse> {
                override fun onFailure(call: Call<KakaoImageResponse>, t: Throwable) {
                    Log.d("test", "fail to connect kakao Api")
                }

                override fun onResponse(
                    call: Call<KakaoImageResponse>,
                    response: Response<KakaoImageResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()

                        if (data != null) {
                            _imageUrls.value = data.documents.map { it.imageUrl }
                        } else {
                            Log.d("test", "data is null")
                        }
                    } else {
                        Log.d("test", "${response.code()} ${response.message()}")
                    }
                }
            })
    }

    companion object {
        const val API_KEY = "KakaoAK 53cccb2e09fc1dfeec6ec2755b3e9325"
    }
}