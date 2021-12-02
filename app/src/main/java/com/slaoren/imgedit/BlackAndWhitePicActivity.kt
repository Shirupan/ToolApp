package com.slaoren.imgedit

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.pan.common.Common
import com.slaoren.R
import com.pan.common.customview.DialogDoubleBtn
import com.slaoren.databinding.ActivityBlackAndWhitePicBinding
import com.pan.common.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.*


/**
 * 图片变黑白
 */

class BlackAndWhitePicActivity: FragmentActivity(), View.OnClickListener, CoroutineScope by MainScope(){
    var code = 0
    lateinit var mBinding:ActivityBlackAndWhitePicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_black_and_white_pic
        )
        mBinding.lifecycleOwner = this

        mBinding.ivPic.visibility = View.GONE
        mBinding.ivPicBg.setOnClickListener(this)
        mBinding.btnFinsh.setOnClickListener(this)

        val cm = ColorMatrix()
        cm.setSaturation(0f) // 设置饱和度:0为纯黑白，饱和度为0；1为饱和度为100，即原图；
        val mGrayColorFilter = ColorMatrixColorFilter(cm)
        mBinding.ivPic.colorFilter = mGrayColorFilter

    }

    override fun onClick(v: View?) {
        when(v){
            mBinding.ivPicBg -> {
                code = openAlbumAndPick()
            }

            mBinding.btnFinsh -> {
                val b: Bitmap? = mBinding.ivPic.drawable?.toBitmap()
                if (b == null) {
                    Toast.makeText(this@BlackAndWhitePicActivity, "请先选择图片", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                val time = System.currentTimeMillis()
                launch {
                    val file =
                        File(com.pan.common.Common.filePath(this@BlackAndWhitePicActivity) + "/" + time + ".jpg")
                    val bos = BufferedOutputStream(FileOutputStream(file))
                    mBinding.ivPic.drawToBitmap().compress(Bitmap.CompressFormat.JPEG, 100, bos)
                    bos.close()
                    file.saveImage2Album(this@BlackAndWhitePicActivity)
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
                val bitmap = data?.data?.toBitmapBySizeCompress(this)
                setBitmap2Iv(bitmap)

//                mBinding.ivPic.visibility=View.VISIBLE
//                mBinding.ivPic.setImageBitmap(bitmap)
//                    mBinding.ivPic.setImageURI(data?.data)

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

}