package com.example.stunitassampleapp.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.DataSource.Factory
import androidx.paging.PageKeyedDataSource
import com.example.stunitassampleapp.network.vo.KakaoImageResponse

class KakaoDataSourceFactory(private var query: String) :
    Factory<Int, KakaoImageResponse.Document>() {

    private val kakaoLiveDataSource =
        MutableLiveData<PageKeyedDataSource<Int, KakaoImageResponse.Document>>()

    override fun create(): DataSource<Int, KakaoImageResponse.Document> {
        val kakaoDataSource = KakaoDataSource(query)
        kakaoLiveDataSource.postValue(kakaoDataSource)
        return kakaoDataSource
    }

    fun getLiveDataSource() = kakaoLiveDataSource

    fun setQuery(query: String) {
        this.query = query
    }

}
