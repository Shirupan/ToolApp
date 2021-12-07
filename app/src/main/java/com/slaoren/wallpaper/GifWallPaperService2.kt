package com.slaoren.wallpaper

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Rect
import android.graphics.drawable.AnimatedImageDrawable
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import java.io.File

class GifWallPaperService2: WallpaperService() {
    override fun onCreateEngine(): Engine {
        return GifWallPaperEngine()
    }

    private inner class GifWallPaperEngine:Engine(){
        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            val filter = IntentFilter()
            filter.addAction(ACTION)
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)

//            val bt = ImageDecoder.decodeDrawable(ImageDecoder.createSource(File(gifPath)))
            val bt = ImageDecoder.decodeDrawable(ImageDecoder.createSource(File(gifPath)))

            val canvas = holder.lockCanvas()
//            val rect = Rect(0, 0, canvas.width, canvas.height)
//            canvas.drawBitmap(bt, null, rect, null)
            (bt as AnimatedImageDrawable).let {
                it.draw(canvas)
                it.start()
            }
            holder.unlockCanvasAndPost(canvas)
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)

        }
    }

    companion object {
        val ACTION = "action"

        var gifPath = ""

        fun startWallpaper(context: Context, path: String) {
            gifPath = path

            val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, ComponentName(context, GifWallPaperService2::class.java))
            context.startActivity(intent)
        }

    }
}