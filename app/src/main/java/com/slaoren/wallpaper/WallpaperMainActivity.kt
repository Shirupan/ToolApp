package com.slaoren.wallpaper

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.slaoren.R

class WallpaperMainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_wallpaper)
        findViewById<Button>(R.id.to1).setOnClickListener(this)
        findViewById<Button>(R.id.to2).setOnClickListener(this)
        findViewById<Button>(R.id.to3).setOnClickListener(this)
        findViewById<Button>(R.id.to4).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.to1 -> {
                val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                        ComponentName(this, VideoWallPaperService::class.java))
                startActivity(intent)
            }
            R.id.to2 -> {
                val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                        ComponentName(this, GifWallpaperService::class.java))
                startActivity(intent)
            }
            R.id.to3 -> {
//                startActivity(Intent(this, MirroringPicActivity::class.java))
            }
            R.id.to4 -> {
//                startActivity(Intent(this, SplitPicActivity::class.java))
            }

        }
    }
}