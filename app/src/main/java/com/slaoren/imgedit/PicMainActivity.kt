package com.slaoren.imgedit

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.slaoren.R

class PicMainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_pic)
        findViewById<Button>(R.id.to1).setOnClickListener(this)
        findViewById<Button>(R.id.to2).setOnClickListener(this)
        findViewById<Button>(R.id.to3).setOnClickListener(this)
        findViewById<Button>(R.id.to4).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.to1 -> {
                startActivity(Intent(this, BlackAndWhitePicActivity::class.java))
            }
            R.id.to2 -> {
                startActivity(Intent(this, ChangePicActivity::class.java))
            }
            R.id.to3 -> {
                startActivity(Intent(this, MirroringPicActivity::class.java))
            }
            R.id.to4 -> {
                startActivity(Intent(this, SplitPicActivity::class.java))
            }

        }
    }
}