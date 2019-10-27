package com.example.stunitassampleapp.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stunitassampleapp.network.RetrofitHelper
import com.example.stunitassampleapp.network.vo.KakaoImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val queryString = MutableLiveData<String>()

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
                        Log.d("test", "success")
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