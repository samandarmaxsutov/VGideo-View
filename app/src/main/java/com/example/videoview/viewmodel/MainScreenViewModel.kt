package com.example.videoview.viewmodel

import androidx.lifecycle.LiveData
import com.example.videoview.model.data.VideoData

interface MainScreenViewModel {
    val getAllLiveData:LiveData<List<VideoData>>
    val massageLiveData:LiveData<String>
    val progressLiveData:LiveData<Boolean>

}