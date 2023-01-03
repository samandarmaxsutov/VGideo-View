package com.example.videoview.ui.adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.videoview.R
import com.example.videoview.model.data.VideoData

class VideoViewAdapter:RecyclerView.Adapter<VideoViewAdapter.Holder>() {
    private val data=ArrayList<VideoData>()
    private var listener:((VideoData)->Unit)?=null



    @SuppressLint("NotifyDataSetChanged")
    fun submitItems(new:List<VideoData>){
        data.clear()
        data.addAll(new)
        notifyDataSetChanged()
    }

    inner class Holder(view:View):ViewHolder(view){
        private  val videoView:VideoView=view.findViewById(R.id.video_view)
        private val titleText:TextView=view.findViewById(R.id.title_txt)
        private  val descriptionText:TextView = view.findViewById(R.id.description_txt)
        fun bind(){
            val item=data[adapterPosition]
            val uri: Uri = Uri.parse(item.url)
            videoView.setVideoURI(uri)
            val mediaController = MediaController(itemView.context)
            mediaController.setAnchorView(videoView)
            mediaController.setMediaPlayer(videoView)
            videoView.setMediaController(mediaController)
            videoView.start()
            titleText.text=item.name
            descriptionText.text=item.description

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.page_video,parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind()

    override fun getItemCount()=data.size
}