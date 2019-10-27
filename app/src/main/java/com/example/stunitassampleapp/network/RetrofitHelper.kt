package com.example.stunitassampleapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper private constructor() {
    private val retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService by lazy { retrofit.create(StUnitasService::class.java) }


    companion object {
        const val API_URL = "https://dapi.kakao.com/"
        private var INSTANCE: RetrofitHelper? = null

        fun getInstance() = INSTANCE ?: RetrofitHelper().apply { INSTANCE = this }
    }
}