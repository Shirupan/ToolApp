package com.slaoren.wallpaper

import android.app.WallpaperManager
import android.content.*
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder


class ImageWallPaperService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return ImageEngine()
    }

    private inner class ImageEngine : Engine(){

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            val filter = IntentFilter()
            filter.addAction(ACTION)
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            val bt = BitmapFactory.decodeFile(imgPath)
            val canvas = holder.lockCanvas()
            val rect = Rect(0, 0, canvas.width, canvas.height)
            canvas.drawBitmap(bt, null, rect, null)
            holder.unlockCanvasAndPost(canvas)
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)

        }

    }

    companion object {
        val ACTION = "action"

        var imgPath = ""

        fun startWallpaper(context: Context, path: String) {
            imgPath = path

            val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, ComponentName(context, ImageWallPaperService::class.java))
            context.startActivity(intent)
        }

    }
}