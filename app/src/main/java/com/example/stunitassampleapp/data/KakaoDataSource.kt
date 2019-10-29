package com.example.stunitassampleapp.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.stunitassampleapp.network.RetrofitHelper
import com.example.stunitassampleapp.network.vo.KakaoImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoDataSource(private var query: String) :
    PageKeyedDataSource<Int, KakaoImageResponse.Document>() {

    private val callbackMessage = MutableLiveData<String>()
    private val isLoading = MutableLiveData<Boolean>()

    fun getCallbackMsgLiveData() = callbackMessage
    fun getIsLoadingLiveData() = isLoading

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, KakaoImageResponse.Document>
    ) {
        showProgressBar()

        RetrofitHelper
            .getInstance()
            .apiService
            .searchImagesByQuery(API_KEY, query, FIRST_PAGE, PAGE_SIZE)
            .enqueue(object : Callback<KakaoImageResponse> {
                override fun onFailure(call: Call<KakaoImageResponse>, t: Throwable) {
                    callbackMessage.postValue("데이터 통신에 실패했습니다.")
                    hideProgressBar()
                }

                override fun onResponse(
                    call: Call<KakaoImageResponse>,
                    response: Response<KakaoImageResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()

                        if (data != null) {
                            if (data.meta.totalCount == 0) {
                                callbackMessage.postValue("검색결과가 없습니다.")
                            } else {
                                callback.onResult(data.documents, null, FIRST_PAGE + 1)
                            }
                        }
                    }

                    hideProgressBar()
                }
            })
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, KakaoImageResponse.Document>
    ) {
        showProgressBar()

        RetrofitHelper
            .getInstance()
            .apiService
            .searchImagesByQuery(API_KEY, query, params.key, PAGE_SIZE)
            .enqueue(object : Callback<KakaoImageResponse> {
                override fun onFailure(call: Call<KakaoImageResponse>, t: Throwable) {
                    callbackMessage.postValue("데이터 통신에 실패했습니다.")
                    hideProgressBar()
                }

                override fun onResponse(
                    call: Call<KakaoImageResponse>,
                    response: Response<KakaoImageResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()

                        if (data != null) {
                            if (data.meta.totalCount == 0) {
                                callbackMessage.postValue("검색결과가 없습니다.")
                            } else {
                                val key = if (params.key > 1) params.key + 1 else null
                                callback.onResult(data.documents, key)
                            }
                        }
                    } else {
                        callbackMessage.postValue("데이터 통신에 실패했습니다.")
                    }

                    hideProgressBar()
                }
            })
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, KakaoImageResponse.Document>
    ) {
        showProgressBar()

        RetrofitHelper
            .getInstance()
            .apiService
            .searchImagesByQuery(API_KEY, query, params.key, PAGE_SIZE)
            .enqueue(object : Callback<KakaoImageResponse> {
                override fun onFailure(call: Call<KakaoImageResponse>, t: Throwable) {
                    callbackMessage.postValue("데이터 통신에 실패했습니다.")
                    hideProgressBar()
                }

                override fun onResponse(
                    call: Call<KakaoImageResponse>,
                    response: Response<KakaoImageResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()

                        if (data != null) {
                            if (data.meta.totalCount == 0) {
                                callbackMessage.postValue("검색결과가 없습니다.")
                            } else {
                                val key = if (params.key > 1) params.key - 1 else null
                                callback.onResult(data.documents, key)
                            }
                        }
                    } else {
                        callbackMessage.postValue("데이터 통신에 실패했습니다.")
                    }

                    hideProgressBar()
                }
            })
    }

    private fun showProgressBar() {
        isLoading.postValue(true)
    }

    private fun hideProgressBar() {
        isLoading.postValue(false)
    }

    companion object {
        const val API_KEY = "KakaoAK 53cccb2e09fc1dfeec6ec2755b3e9325"
        const val PAGE_SIZE = 20
        private const val FIRST_PAGE = 1
    }
}