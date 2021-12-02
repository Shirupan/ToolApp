package com.slaoren.wallpaper

import android.media.MediaPlayer
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.slaoren.R


class VideoWallpaper: WallpaperService() {
    private var mp: MediaPlayer? = null
    private var progress = 0

    //这里就是返回我们自定义的Engine
    override fun onCreateEngine(): Engine? {
        return VideoEngine()
    }

    //自定义Engine
    inner class VideoEngine : Engine() {
        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            //可以设置点击事件
            setTouchEventsEnabled(true)
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            //把视频输出到SurfaceHolder上面
            if (mp != null && mp?.isPlaying()==true) return
            //可以设置SD卡的视频
            mp = MediaPlayer.create(getApplicationContext(), R.raw.test2)
            //这句话并不简单
            mp?.setSurface(holder.surface)
            //重复播放
            mp?.setLooping(true)
            mp?.start()
        }

        //当桌面不可见的时候的处理
        override fun onVisibilityChanged(visible: Boolean) {
            if (visible) {
                if (mp != null) return
                mp = MediaPlayer.create(getApplicationContext(), R.raw.test2)
                mp?.setSurface(surfaceHolder.surface)
                mp?.setLooping(true)
                //获取进度播放
                mp?.seekTo(progress)
                mp?.start()
            } else {
                if (mp != null && mp?.isPlaying()==true) {
                    //保存进度
                    progress = mp?.getCurrentPosition()?:0
                    mp?.stop()
                    mp?.release()
                    mp = null
                }
            }
        }

        override fun onDestroy() {
            if (mp != null) {
                mp?.stop()
                mp?.release()
            }
            super.onDestroy()
        }
    }
}