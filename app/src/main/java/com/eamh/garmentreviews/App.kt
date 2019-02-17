package com.eamh.garmentreviews

import android.app.Application
import com.eamh.garmentreviews.di.DependencyModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(DependencyModule))
    }
}