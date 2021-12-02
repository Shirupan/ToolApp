package com.slaoren.imgedit

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.pan.common.Common
import com.slaoren.R
import com.pan.common.customview.DialogDoubleBtn
import com.slaoren.databinding.ActivityChangePicBinding
import com.pan.common.listener.SimpleOnSeekBarChangeListener
import com.pan.common.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import androidx.core.graphics.drawable.toBitmap


/**
 * 调整图片对比度、亮度、饱和度
 */

class ChangePicActivity: FragmentActivity(), View.OnClickListener, CoroutineScope by MainScope() {
    var rawBitmap:Bitmap?=null
    var code = 0
    var contrast = 1f
    var brightness = 1f
    var saturation = 1f
    lateinit var mBinding:ActivityChangePicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        smToolbar.setToolBarTitle("图片增强")
//        smToolbar.setToolBarRight("选择图片", R.color.text_33, 14){
//            TakePhotoUtil.pickImage(this, takePhotoHandler?.takePhoto)
//        }

        mBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_change_pic
        )
        mBinding.lifecycleOwner = this

        mBinding.ivPicBg.setOnClickListener(this)
        mBinding.btnFinsh.setOnClickListener(this)

        mBinding.ivPic.visibility = View.GONE

        setSeekBar()
    }

    private fun setSeekBar(){
        mBinding.seekContrast.setOnSeekBarChangeListener(object:SimpleOnSeekBarChangeListener(){
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                super.onProgressChanged(seekBar, progress, fromUser)
                contrast = (progress+50)/100f//0.5~1.5
                mBinding.ivPic.setImageBitmap(rawBitmap?.beautyImage(contrast, saturation, brightness))
            }
        })
        mBinding.seekBrightness.setOnSeekBarChangeListener((object:SimpleOnSeekBarChangeListener(){
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                super.onProgressChanged(seekBar, progress, fromUser)
                brightness = progress/50f//0~1
                mBinding.ivPic.setImageBitmap(rawBitmap?.beautyImage(contrast, saturation, brightness))
            }
        }))
        mBinding.seekSaturation.setOnSeekBarChangeListener((object: SimpleOnSeekBarChangeListener(){
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                super.onProgressChanged(seekBar, progress, fromUser)
                saturation = progress/50f//0~1
                mBinding.ivPic.setImageBitmap(rawBitmap?.beautyImage(contrast, saturation, brightness))
            }
        }))
    }

    override fun onClick(v: View?) {
        when(v){
            mBinding.ivPicBg -> {
                code = openAlbumAndPick()
            }

            mBinding.btnFinsh -> {
                val b: Bitmap? = mBinding.ivPic.drawable?.toBitmap()
                if (b == null) {
                    Toast.makeText(this@ChangePicActivity, "请先选择图片", Toast.LENGTH_SHORT).show()
                    return
                }
                val time = System.currentTimeMillis()
                launch {
                    val file = File( com.pan.common.Common.filePath(this@ChangePicActivity)+ "/" + time + ".jpg")
                    val bos = BufferedOutputStream(FileOutputStream(file))
                    b?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                    bos.close()
                    file.saveImage2Album(this@ChangePicActivity)
                }
                DialogDoubleBtn("保存成功", "是否前往相册查看？", "查看", object : DialogDoubleBtn.Listener {
                    override fun ok() {
                        openAlbum()
                    }
                }).show(supportFragmentManager, "dialogWatch")

            }
        }
    }

    fun setBitmap2Iv(bitmap: Bitmap?){
        if (bitmap==null){
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show()
            return
        }
        mBinding.ivPic.let { iv ->
            iv.visibility = View.VISIBLE

            val bitmapWh = (bitmap.width.toFloat() / bitmap.height)
            val ivPicBgWh = (mBinding.ivPicBg.width.toFloat() / mBinding.ivPicBg.height)
            SLog.d("test", "bitmap w:"+bitmapWh)
            SLog.d("test", "ivPicBg w:"+ivPicBgWh)
            if ( bitmapWh > ivPicBgWh ) {//用宽高比确认高度撑满后宽度是否会超出背景
                val lp = iv.layoutParams as ConstraintLayout.LayoutParams
                lp.width = 0
                lp.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                iv.layoutParams = lp
                iv.scaleType = ImageView.ScaleType.CENTER_CROP
                iv.adjustViewBounds = true
            } else {
                val lp = iv.layoutParams as ConstraintLayout.LayoutParams
                lp.width = (mBinding.ivPicBg.height.toFloat() / bitmap.height * bitmap.width).toInt()//宽度按高度扩大比例来确认
                lp.height = mBinding.ivPicBg.height
                iv.layoutParams = lp
                iv.scaleType = ImageView.ScaleType.FIT_CENTER
                iv.adjustViewBounds = false
            }

            SLog.d("test", "iv w:"+iv.width+", h:"+iv.height)
            iv.setImageBitmap(bitmap)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
                code -> if (resultCode == RESULT_OK) {
                    rawBitmap = data?.data?.toBitmapBySizeCompress(this)
                    Log.d("test", "bitmap w:"+rawBitmap?.width+", h"+rawBitmap?.height)
                    setBitmap2Iv(rawBitmap)
//                    mBinding.ivPic.setImageURI(data?.data)
            }
        }
    }
}