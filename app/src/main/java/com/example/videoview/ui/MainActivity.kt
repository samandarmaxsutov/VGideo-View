package com.example.videoview.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.videoview.R
import com.example.videoview.utils.hideSystemUI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }
}