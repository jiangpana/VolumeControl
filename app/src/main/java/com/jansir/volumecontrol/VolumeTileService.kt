package com.jansir.volumecontrol

import android.os.Build
import android.os.IBinder
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi
import org.lsposed.hiddenapibypass.HiddenApiBypass
import java.lang.reflect.Field


@RequiresApi(Build.VERSION_CODES.N)
class VolumeTileService : TileService() {

//    private val observer: ContentObserver by lazy {
//        object : ContentObserver(Handler()) {
//            override fun onChange(selfChange: Boolean) {
//                updateTile()
//            }
//        }
//    }

    override fun onStartListening() {
//        applicationContext.contentResolver
//            .registerContentObserver(System.CONTENT_URI, true, observer)
        updateTile()
    }

    override fun onStopListening() {
//        applicationContext.contentResolver.unregisterContentObserver(observer);

    }

    private fun updateTile() {
        qsTile.state = if (VolumeUtils.isMute(this)) 1 else 2
        qsTile.label = VolumeUtils.getVolumeLabel(this)
        qsTile.updateTile()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onClick() {
        try {
            val mService =
                (HiddenApiBypass.getInstanceFields(javaClass.superclass) as List<Field>).filter {
                    it.name.equals("mService")
                }.get(0).apply {
                    isAccessible = true
                }.get(this)
            val mTileToken =
                (HiddenApiBypass.getInstanceFields(javaClass.superclass) as List<Field>).filter {
                    it.name.equals("mTileToken")
                }.get(0).apply { isAccessible = true }.get(this)
            HiddenApiBypass.getDeclaredMethod(
                mService.javaClass,
                "onStartActivity", IBinder::class.java
            ).apply { isAccessible = true }.invoke(mService, mTileToken)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        VolumeUtils.showVolumeDialog(this)
    }
}