package com.example.stunitassampleapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.stunitassampleapp.data.KakaoDataSource
import com.example.stunitassampleapp.data.KakaoDataSourceFactory
import com.example.stunitassampleapp.network.vo.KakaoImageResponse
import java.util.concurrent.Executors

class MainViewModel : ViewModel() {
    val queryString = MutableLiveData<String>()
    var kakaoPagedList: LiveData<PagedList<KakaoImageResponse.Document>>
    private var dataSourceFactory: KakaoDataSourceFactory = KakaoDataSourceFactory("")

    init {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(KakaoDataSource.PAGE_SIZE)
            .setEnablePlaceholders(true)
            .setPageSize(KakaoDataSource.PAGE_SIZE)
            .setPrefetchDistance(5)
            .build()

        kakaoPagedList = LivePagedListBuilder(dataSourceFactory, config)
            .setFetchExecutor(Executors.newFixedThreadPool(5))
            .build()
    }

    fun searchQuery() {
        dataSourceFactory.setQuery(queryString.value!!)
        dataSourceFactory.getLiveDataSource().value?.invalidate()
    }

}