package com.slaoren.imgedit

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.slaoren.R
import com.slaoren.common.customview.DialogDoubleBtn
import com.slaoren.databinding.ActivityMirroringPicBinding
import com.slaoren.common.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap

/**
 * 图片旋转镜像
 */

class MirroringPicActivity: FragmentActivity(), View.OnClickListener, CoroutineScope by MainScope() {
    var code = 0
    var resultBitmap:Bitmap? = null

    lateinit var mBinding: ActivityMirroringPicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        smToolbar.setToolBarTitle("翻转图片")
//        smToolbar.setToolBarRight("选择图片", R.color.text_33, 14){
//            TakePhotoUtil.pickImage(this, takePhotoHandler?.takePhoto)
//        }
        mBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_mirroring_pic
        )
        mBinding.lifecycleOwner = this

        mBinding.ivPicBg.setOnClickListener(this)
        mBinding.tvLeft.setOnClickListener(this)
        mBinding.tvMirror.setOnClickListener(this)
        mBinding.tvRight.setOnClickListener(this)
        mBinding.btnFinsh.setOnClickListener(this)

        mBinding.ivPic.visibility = View.GONE

    }

    override fun onClick(v: View?) {
        when(v){
            mBinding.ivPicBg -> {
                code = openAlbumAndPick()
            }
            mBinding.tvLeft -> {
                mBinding.ivPic.drawable?.let{
                    setBitmap2Iv(it.toBitmap().rotaing(-90f))
                }
            }
            mBinding.tvMirror -> {
                mBinding.ivPic.drawable?.let{
                    setBitmap2Iv(it.toBitmap().mirror())
                }
            }
            mBinding.tvRight -> {
                mBinding.ivPic.drawable?.let{
                    setBitmap2Iv(it.toBitmap().rotaing(90f))
                }
            }
            mBinding.btnFinsh -> {
                val b: Bitmap? = mBinding.ivPic.drawable?.toBitmap()
                if (b == null) {
                    Toast.makeText(this, "请先选择图片", Toast.LENGTH_SHORT).show()
                    return
                }
                val time = System.currentTimeMillis()
                launch {
                    val file = File(com.slaoren.common.Common.filePath(this@MirroringPicActivity) + "/" + time + ".jpg")
                    val bos = BufferedOutputStream(FileOutputStream(file))
                    mBinding.ivPic.drawToBitmap().compress(Bitmap.CompressFormat.JPEG, 100, bos)
                    bos.close()
                    file.saveImage2Album(this@MirroringPicActivity)
                }

                DialogDoubleBtn("保存成功", "是否前往相册查看？", "查看", object : DialogDoubleBtn.Listener {
                    override fun ok() {
                        openAlbum()
                    }
                }).show(supportFragmentManager, "dialogWatch")

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            code -> if (resultCode == RESULT_OK) {
                resultBitmap = data?.data?.toBitmapBySizeCompress(this)
                setBitmap2Iv(resultBitmap)
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

    override fun onDestroy() {
        super.onDestroy()
        resultBitmap?.recycle()
        resultBitmap = null
    }
}