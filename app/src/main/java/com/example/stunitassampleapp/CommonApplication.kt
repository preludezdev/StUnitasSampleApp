package com.example.stunitassampleapp

import android.app.Application
import com.example.stunitassampleapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CommonApplication : Application() {
    private val moduleList = listOf(
        viewModelModule
    )

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CommonApplication)

            modules(moduleList)
        }
    }
}