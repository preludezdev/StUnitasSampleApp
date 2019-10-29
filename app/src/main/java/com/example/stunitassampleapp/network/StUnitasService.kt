package com.example.stunitassampleapp.network

import com.example.stunitassampleapp.network.vo.KakaoImageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface StUnitasService {

    @GET("v2/search/image")
    fun searchImagesByQuery(
        @Header("Authorization") key: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<KakaoImageResponse>
}