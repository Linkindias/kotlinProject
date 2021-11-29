package com.example.kotlinsampleapplication

import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.kotlinsampleapplication.MediaDomain.MediaScheduleService
import com.example.kotlinsampleapplication.Service.ScheduleDownLoadService
import com.example.kotlinsampleapplication.ViewModel.VideoDetial
import java.util.*


class MediaActivity : AppCompatActivity()  {
    val tag: String = "MediaActivity"

    var video: VideoView? = null
    var img: ImageView? = null
    var sound: MediaPlayer? = null

    var serviceIntent: Intent? = null
    var mediaService: MediaScheduleService? = null

    val receiver: ResultReceiver = object : ResultReceiver(Handler()) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {

            if (resultCode == ScheduleDownLoadService.downLoadflag) {
                mediaService?.getMediaSchedule(resultData.getParcelableArrayList<VideoDetial>("schedules") as ArrayList<VideoDetial>)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE; //轉橫向

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        try {
            video = findViewById<View>(R.id.videoView1) as VideoView
            img = findViewById<View>(R.id.imageView1) as ImageView
            sound = MediaPlayer();

            mediaService = MediaScheduleService(this, video!!, img!!, sound!!, ContextCompat.getDrawable(this, R.drawable.netcore)            )

            video?.setOnPreparedListener { video -> //cycle play
                video.isLooping = true
                video.start()
            }

            video?.setOnTouchListener { v, event ->  //not touch
                true
            }

            video?.setOnCompletionListener { video -> //played restart
                video.start()
            }

            sound?.setOnPreparedListener { sound ->
                sound.isLooping = true;
                sound.setVolume(100f, 100f) //muted
                sound.start()
            }

            sound?.setOnCompletionListener { sound ->
                sound.start()
            }

            serviceIntent = Intent(this, ScheduleDownLoadService::class.java) //open background service
            serviceIntent!!.putExtra("receiver", receiver);
            startService(serviceIntent)

        } catch (e: Exception) {
            Log.i(tag, e.message.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(serviceIntent);
        mediaService?.onDestory()
    }
}