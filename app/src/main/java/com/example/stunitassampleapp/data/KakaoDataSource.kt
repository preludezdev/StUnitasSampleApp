package com.example.stunitassampleapp.data

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.stunitassampleapp.network.RetrofitHelper
import com.example.stunitassampleapp.network.vo.KakaoImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoDataSource(private var query: String) :
    PageKeyedDataSource<Int, KakaoImageResponse.Document>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, KakaoImageResponse.Document>
    ) {
        Log.d("test", "load initial")
        RetrofitHelper
            .getInstance()
            .apiService
            .searchImagesByQuery(API_KEY, query, FIRST_PAGE, PAGE_SIZE)
            .enqueue(object : Callback<KakaoImageResponse> {
                override fun onFailure(call: Call<KakaoImageResponse>, t: Throwable) {
                    Log.d("test", "fail")
                }

                override fun onResponse(
                    call: Call<KakaoImageResponse>,
                    response: Response<KakaoImageResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()

                        if (data != null) {
                            callback.onResult(
                                data.documents, null, FIRST_PAGE + 1
                            )
                        }
                    } else {
                        Log.d("test", "${response.errorBody()}")
                    }
                }
            })
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, KakaoImageResponse.Document>
    ) {
        Log.d("test", "loadAfter")
        RetrofitHelper
            .getInstance()
            .apiService
            .searchImagesByQuery(API_KEY, query, params.key, PAGE_SIZE)
            .enqueue(object : Callback<KakaoImageResponse> {
                override fun onFailure(call: Call<KakaoImageResponse>, t: Throwable) {
                    Log.d("test", "fail")
                }

                override fun onResponse(
                    call: Call<KakaoImageResponse>,
                    response: Response<KakaoImageResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()

                        if (data != null) {
                            val key = if (params.key > 1) params.key + 1 else null
                            callback.onResult(data.documents, key)
                        } else {
                            Log.d("test", "data is null")
                        }
                    } else {
                        Log.d("test", "Response is not successful")
                        Log.d("test", "${response.errorBody()}")
                    }
                }
            })
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, KakaoImageResponse.Document>
    ) {
        Log.d("test", "loadBefore")
        RetrofitHelper
            .getInstance()
            .apiService
            .searchImagesByQuery(API_KEY, query, params.key, PAGE_SIZE)
            .enqueue(object : Callback<KakaoImageResponse> {
                override fun onFailure(call: Call<KakaoImageResponse>, t: Throwable) {
                    Log.d("test", "fail")
                }

                override fun onResponse(
                    call: Call<KakaoImageResponse>,
                    response: Response<KakaoImageResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()

                        if (data != null) {
                            val key = if (!data.meta.isEnd) params.key - 1 else null
                            callback.onResult(data.documents, key)
                        } else {
                            Log.d("test", "data is null")
                        }
                    } else {
                        Log.d("test", "Response is not successful")
                        Log.d("test", "${response.errorBody()}")
                    }
                }
            })
    }

    companion object {
        const val API_KEY = "KakaoAK 53cccb2e09fc1dfeec6ec2755b3e9325"
        const val PAGE_SIZE = 20
        private const val FIRST_PAGE = 1
    }
}