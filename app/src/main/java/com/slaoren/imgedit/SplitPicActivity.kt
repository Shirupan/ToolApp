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
import com.slaoren.databinding.ActivityCutPicBinding
import com.slaoren.common.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import androidx.core.graphics.drawable.toBitmap

/**
 * 图片切割成n*n大小的多张小图片
 */

class SplitPicActivity: FragmentActivity(), View.OnClickListener, CoroutineScope by MainScope() {
    var x = 3//分割后的列数
    var y = 3//分割后的行数
    var code = 0
    var resultBitmap:Bitmap? = null
    lateinit var mBinding: ActivityCutPicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        smToolbar.setToolBarTitle("宫格切图")
//        smToolbar.setToolBarRight("选择图片", R.color.text_33, 14){
//            TakePhotoUtil.pickImage(this, takePhotoHandler?.takePhoto)
//        }
        mBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_cut_pic
        )
        mBinding.lifecycleOwner = this

        mBinding.ivPicBg.setOnClickListener(this)
        mBinding.ivCutTo9.setOnClickListener(this)
        mBinding.ivCutTo6.setOnClickListener(this)
        mBinding.ivCutTo4.setOnClickListener(this)
        mBinding.ivCutTo2.setOnClickListener(this)
        mBinding.ivCutTo3.setOnClickListener(this)
        mBinding.btnFinsh.setOnClickListener(this)

        hideAllLine()
        mBinding.ivPic.visibility = View.GONE

    }

    fun clearSelect(){
        mBinding.ivCutTo9.isSelected = false
        mBinding.ivCutTo6.isSelected = false
        mBinding.ivCutTo4.isSelected = false
        mBinding.ivCutTo2.isSelected = false
        mBinding.ivCutTo3.isSelected = false
    }

    fun hideAllLine(){
        mBinding.lineH1.visibility = View.GONE
        mBinding.lineH2.visibility = View.GONE
        mBinding.lineH3.visibility = View.GONE
        mBinding.lineV1.visibility = View.GONE
        mBinding.lineV2.visibility = View.GONE
        mBinding.lineV3.visibility = View.GONE
    }

    fun showLine9(){
        mBinding.ivCutTo9.isSelected = true
        mBinding.lineH1.visibility = View.VISIBLE
        mBinding.lineH3.visibility = View.VISIBLE
        mBinding.lineV1.visibility = View.VISIBLE
        mBinding.lineV3.visibility = View.VISIBLE
    }
    fun showLine6(){
        mBinding.ivCutTo6.isSelected = true
        mBinding.lineH2.visibility = View.VISIBLE
        mBinding.lineV1.visibility = View.VISIBLE
        mBinding.lineV3.visibility = View.VISIBLE
    }
    fun showLine4(){
        mBinding.ivCutTo4.isSelected = true
        mBinding.lineH2.visibility = View.VISIBLE
        mBinding.lineV2.visibility = View.VISIBLE
    }
    fun showLine3(){
        mBinding.ivCutTo3.isSelected = true
        mBinding.lineV1.visibility = View.VISIBLE
        mBinding.lineV3.visibility = View.VISIBLE
    }
    fun showLine2(){
        mBinding.ivCutTo2.isSelected = true
        mBinding.lineV2.visibility = View.VISIBLE
    }

    override fun onClick(v: View?) {
        when(v){
            mBinding.ivPicBg->{
                code = openAlbumAndPick()
            }
            mBinding.ivCutTo9->{
                x=3
                y=3
                hideAllLine()
                clearSelect()
                showLine9()
            }
            mBinding.ivCutTo6->{
                x=3
                y=2
                hideAllLine()
                clearSelect()
                showLine6()
            }
            mBinding.ivCutTo4->{
                x=2
                y=2
                hideAllLine()
                clearSelect()
                showLine4()
            }
            mBinding.ivCutTo3->{
                x=3
                y=1
                hideAllLine()
                clearSelect()
                showLine3()
            }
            mBinding.ivCutTo2->{
                x=2
                y=1
                hideAllLine()
                clearSelect()
                showLine2()
            }
            mBinding.btnFinsh->{
                val b:Bitmap? = mBinding.ivPic.drawable?.toBitmap()
                if (b==null){
                    Toast.makeText(this, "请先选择图片", Toast.LENGTH_SHORT).show()
                    return
                }
                val time = System.currentTimeMillis()
                val list = b.split(x, y)
                b.split(x, y)?.forEachIndexed { index, imagePiece ->
                    launch {
                        val file = File(com.slaoren.common.Common.filePath(this@SplitPicActivity) + "/" + time+index+".jpg")
                        val bos = BufferedOutputStream(FileOutputStream(file))
                        imagePiece.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                        bos.close()
                        file.saveImage2Album(this@SplitPicActivity)
                    }
                }
                if (!list.isNullOrEmpty()){
                    DialogDoubleBtn("切割成功", "是否前往相册查看？", "查看", object :DialogDoubleBtn.Listener{
                        override fun ok() {
                            openAlbum()
                        }
                    }).show(supportFragmentManager, "dialogWatch")
                }
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