package com.slaoren.wallpaper

import android.app.WallpaperManager
import android.content.*
import android.media.MediaPlayer
import android.service.wallpaper.WallpaperService
import android.text.TextUtils
import android.view.SurfaceHolder
import java.io.IOException


class VideoWallPaperService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return VideoEngine()
    }

    private inner class VideoEngine : Engine(), MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
        private var mPlayer: MediaPlayer? = null
        private var isPapered = false
        private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                val action = intent.getIntExtra(BROADCAST_SET_VIDEO_PARAM, -1)
                val volume = intent.getFloatExtra(ACTION_SETVOLUME, -1f)
                when (action) {
                    ACTION_SET_VIDEO -> {
                        setVideo(videoPath)
                    }
                }
                mPlayer?.setVolume(volume, volume)
            }
        }


        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            val filter = IntentFilter()
            filter.addAction(ACTION)
            registerReceiver(mReceiver, filter)
        }

        override fun onDestroy() {
            super.onDestroy()
            unregisterReceiver(mReceiver)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            if (isPapered) {
                if (visible) {
                    mPlayer?.start()
                } else {
                    mPlayer?.pause()
                }
            }
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            mPlayer = MediaPlayer()
            setVideo(videoPath)
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
            if (mPlayer?.isPlaying==true) {
                mPlayer?.stop()
            }
            mPlayer?.release()
            mPlayer = null
        }

        override fun onPrepared(mp: MediaPlayer) {
            isPapered = true
            mp.start()
        }

        override fun onCompletion(mp: MediaPlayer) {
            closeWallpaper(applicationContext)
        }

        override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
            closeWallpaper(applicationContext)
            return true
        }

        private fun setVideo(videoPath: String?) {
            if (TextUtils.isEmpty(videoPath)) {
                closeWallpaper(applicationContext)
                throw IllegalArgumentException("video path is null")
            }
            if (mPlayer != null) {
                mPlayer?.reset()
                isPapered = false
                try {
                    mPlayer?.setOnPreparedListener(this)
                    mPlayer?.setOnCompletionListener(this)
                    mPlayer?.setOnErrorListener(this)
                    mPlayer?.isLooping = true
                    mPlayer?.setVolume(volume, volume)
                    //                  mPlayer.setDisplay(getSurfaceHolder());
                    mPlayer?.setSurface(surfaceHolder.surface)
                    mPlayer?.setDataSource(videoPath)
                    mPlayer?.prepareAsync()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        private const val SERVICE_NAME = "com.slaoren.wallpaper.VideoWallPaperService"

        val BROADCAST_SET_VIDEO_PARAM = "broadcast_set_video_param"
        val ACTION_SETVOLUME = "action_setvolume"
        val ACTION_SET_VIDEO = 2
        val ACTION = "action"

        var videoPath = ""
        var volume = 0f

        fun startWallPaper(context: Context, videoPath: String) {
            val info = WallpaperManager.getInstance(context).wallpaperInfo
            if (info != null && SERVICE_NAME == info.serviceName) {
                changeVideo(context, videoPath)
            } else {
                startNewWallpaper(context, videoPath)
            }
        }

        fun closeWallpaper(context: Context?) {
            try {
                WallpaperManager.getInstance(context).clear()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        private fun startNewWallpaper(context: Context, path: String) {
            saveVideoPath(path)

            val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, ComponentName(context, VideoWallPaperService::class.java))
            context.startActivity(intent)
        }

        private fun changeVideo(context: Context, path: String) {
            saveVideoPath(path)
            val intent = Intent()
            intent.action = ACTION
            intent.putExtra(BROADCAST_SET_VIDEO_PARAM, ACTION_SET_VIDEO)
            context.sendBroadcast(intent)
        }

        fun setVolume(context: Context, _volume: Float) {
            volume = _volume
            val intent = Intent()
            intent.action = ACTION
            intent.putExtra(ACTION_SETVOLUME, volume)
            context.sendBroadcast(intent)
        }

        private fun saveVideoPath(path: String) {
            videoPath = path
        }
    }
}