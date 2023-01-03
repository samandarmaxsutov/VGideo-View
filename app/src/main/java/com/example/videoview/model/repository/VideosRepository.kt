package com.example.videoview.model.repository

import androidx.lifecycle.LiveData
import com.example.videoview.model.data.VideoData

interface VideosRepository {
    val progressBar: LiveData<Boolean>
    fun addVideo(videoData: VideoData): LiveData<Result<Unit>>
    fun getAll(): LiveData<Result<List<VideoData>>>
    fun getAll2(): LiveData<Result<List<VideoData>>>
    fun update(videoData: VideoData): LiveData<Result<Unit>>
}