package com.example.stunitassampleapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.stunitassampleapp.data.KakaoDataSource
import com.example.stunitassampleapp.data.KakaoDataSourceFactory
import com.example.stunitassampleapp.network.vo.KakaoImageResponse
import java.util.concurrent.Executors


class MainViewModel : ViewModel() {
    private var dataSourceFactory: KakaoDataSourceFactory = KakaoDataSourceFactory("")
    var kakaoPagedList: LiveData<PagedList<KakaoImageResponse.Document>>
    var callbackMessage: LiveData<String>
    var isLoading: LiveData<Boolean>
    val queryString = MutableLiveData<String>()

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

        callbackMessage = Transformations.switchMap(
            dataSourceFactory.getLiveDataSource()
        ) { dataSource -> (dataSource as KakaoDataSource).getCallbackMsgLiveData() }

        isLoading = Transformations.switchMap(
            dataSourceFactory.getLiveDataSource()
        ) { dataSource -> (dataSource as KakaoDataSource).getIsLoadingLiveData() }
    }

    fun searchQuery() {
        dataSourceFactory.setQuery(queryString.value!!)
        dataSourceFactory.getLiveDataSource().value?.invalidate()
    }

}