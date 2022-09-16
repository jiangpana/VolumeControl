package com.jansir.volumecontrol

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.os.Build
import androidx.annotation.RequiresApi


object VolumeUtils {

    fun getVolumeLabel(context: Context): String {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val cur = audioManager.getStreamVolume(3)
        val max = audioManager.getStreamMaxVolume(3)
        return "音量:$cur/$max"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isMute(context: Context): Boolean {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return audioManager.isStreamMute(3) || audioManager.getStreamVolume(3) == 0
    }

    fun isMax(context: Context): Boolean {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return audioManager.getStreamVolume(3) == audioManager.getStreamMaxVolume(3)
    }

    fun showVolumeDialog(context: Context) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.adjustStreamVolume(3, 0, 1)
    }

    fun decrease(context: Context) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.adjustStreamVolume(3, -1, 0)

    }

    fun increase(context: Context) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.adjustStreamVolume(3, 1, 0);


    }
}