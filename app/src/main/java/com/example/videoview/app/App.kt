package com.example.videoview.app

import android.app.Application
import com.example.videoview.model.repository.impl.VideosRepositoryImpl

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        VideosRepositoryImpl.init()
    }
}