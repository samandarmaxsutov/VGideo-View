package com.example.videoview.model.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.videoview.model.data.Mapper
import com.example.videoview.model.data.VIDEOS
import com.example.videoview.model.repository.VideosRepository
import com.example.videoview.model.data.VideoData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VideosRepositoryImpl private constructor(): VideosRepository {

    companion object{
        private var videosRepositoryImpl: VideosRepository?=null
        fun init(){
            if (videosRepositoryImpl ==null){
                videosRepositoryImpl = VideosRepositoryImpl()
            }
        }
        fun getVideosRepository()= videosRepositoryImpl!!
    }

    private val db = Firebase.firestore

    override val progressBar = MutableLiveData<Boolean>()

    override fun addVideo(videoData: VideoData): LiveData<Result<Unit>> {
        progressBar.value = true
        val liveData = MutableLiveData<Result<Unit>>()
        db.collection(VIDEOS).document(videoData.name).set(videoData)
            .addOnSuccessListener {
                liveData.value = Result.success(Unit)
            }
            .addOnFailureListener {
                liveData.value = Result.failure(it)
            }
        progressBar.value=false
        return liveData
    }

    override fun getAll(): LiveData<Result<List<VideoData>>> {
        progressBar.value = true
        val liveData = MutableLiveData<Result<List<VideoData>>>()
        db.collection(VIDEOS).get()
            .addOnSuccessListener {
                val ls = it.documents.map { item -> Mapper.run { item.toVideoData() } }
                liveData.value = Result.success(ls)
            }
            .addOnFailureListener { liveData.value = Result.failure(it) }

        progressBar.value=false
        return liveData
    }

    override fun getAll2(): LiveData<Result<List<VideoData>>> {
        progressBar.value = true
        val liveData = MediatorLiveData<Result<List<VideoData>>>()
        liveData.addDisposable(getAll()) { liveData.value = it }

        db.collection(VIDEOS).addSnapshotListener { _, _ ->
            liveData.addDisposable(getAll()) { liveData.value = it }
        }

        progressBar.value=false
        return liveData
    }

    override fun update(videoData: VideoData): LiveData<Result<Unit>> {
        progressBar.value = true
        val liveData = MutableLiveData<Result<Unit>>()
        db.collection(VIDEOS).document(videoData.name).set(videoData)
            .addOnSuccessListener {
                liveData.value = Result.success(Unit)
            }
            .addOnFailureListener {
                liveData.value = Result.failure(it)
            }
        return liveData
    }

    private fun <T, K> MediatorLiveData<T>.addDisposable(source: LiveData<K>, block: Observer<K>) {
        addSource(source) {
            block.onChanged(it)
            removeSource(source)
        }
    }

}
