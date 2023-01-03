package com.example.videoview.model.data

import com.google.firebase.firestore.DocumentSnapshot

const val VIDEOS="videos"
object Mapper {
    fun DocumentSnapshot.toVideoData() = VideoData(
        name = this["name"].toString(),
        description = this["description"].toString(),
        url=this["url"].toString()
        )
}