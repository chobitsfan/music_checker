package com.example.myapplication

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.PowerManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import java.util.*

class MyService : Service() {
    /*class MusicChecker(val am : AudioManager): TimerTask() {
        override fun run() {
            if (am.isMusicActive) {
                val handler = Handler(Looper.getMainLooper())
                handler.post{
                    val toast = Toast.makeText(applicationContext, "music playing", 1)
                    toast.show()
                }
            }
        }
    }*/
    private val timer = Timer()

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //val task = MusicChecker(getSystemService(Context.AUDIO_SERVICE) as AudioManager)
        //timer.schedule(task, 1000, 5000)
        timer.schedule(object : TimerTask() {
            val am : AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val pm : PowerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
            val wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "MyApp::MyService")
            override fun run() {
                if (am.isMusicActive) {
                    if (!wakeLock.isHeld) {
                        val handler = Handler(Looper.getMainLooper())
                        handler.post {
                            val toast = Toast.makeText(applicationContext, "music playing", Toast.LENGTH_SHORT)
                            toast.show()
                        }
                        wakeLock.acquire(30*60*1000)
                    }
                } else if (wakeLock.isHeld) {
                    val handler = Handler(Looper.getMainLooper())
                    handler.post {
                        val toast = Toast.makeText(applicationContext, "music stopped", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                    wakeLock.release()
                }
            }
        }, 1000, 5000)
        return START_NOT_STICKY
    }
}