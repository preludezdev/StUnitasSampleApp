package com.example.stunitassampleapp.network

import com.example.stunitassampleapp.network.vo.KakaoImageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface StUnitasService {

    @Headers("Authorization: KakaoAK 53cccb2e09fc1dfeec6ec2755b3e9325")
    @GET("v2/search/image/")
    fun searchImagesByQuery(@Query("query") query: String): Call<KakaoImageResponse>
}