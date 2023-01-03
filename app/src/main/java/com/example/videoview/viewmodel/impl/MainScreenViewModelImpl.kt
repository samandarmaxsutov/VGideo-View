package com.example.videoview.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.videoview.model.data.VideoData
import com.example.videoview.model.repository.VideosRepository
import com.example.videoview.model.repository.impl.VideosRepositoryImpl
import com.example.videoview.viewmodel.MainScreenViewModel

class MainScreenViewModelImpl : MainScreenViewModel, ViewModel() {
    override val getAllLiveData = MediatorLiveData<List<VideoData>>()
    override val massageLiveData = MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()

    private val videosRepositoryImpl: VideosRepository = VideosRepositoryImpl.getVideosRepository()

    init {
        getAllLiveData.addSource(videosRepositoryImpl.getAll2()) {
            if (it.isSuccess) {
                getAllLiveData.postValue(it.getOrNull())
                progressLiveData.value = false
            }else {
                massageLiveData.postValue(it.exceptionOrNull().toString())
                progressLiveData.value = false
            }

        }
        getAllLiveData.addSource(videosRepositoryImpl.progressBar) {
            progressLiveData.value = it
        }
    }
}