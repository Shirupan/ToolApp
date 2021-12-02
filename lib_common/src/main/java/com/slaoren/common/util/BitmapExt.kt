package com.slaoren.common.util

import android.graphics.*
import android.media.FaceDetector
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/*
获取人脸
widthTimes 宽度增加两眼距离的倍数
heightTimes 高度增加两眼距离的倍数
isRotate 是否旋转到脸摆正
*/
fun Bitmap.getFace(widthTimes: Int? = 1, heightTimes: Int? = 1, isRotate: Boolean? = false):Bitmap?{
    var left = 0
    var top = 0
    var right = 0
    var bot = 0
    //人脸检测
    val bm = this
    val bitmap565 = bm.copy(Bitmap.Config.RGB_565, true)
    val detector = FaceDetector(bitmap565.width, bitmap565.height, 10)
    val faces = arrayOfNulls<FaceDetector.Face>(10)
    val resultNum = detector.findFaces(bitmap565, faces)
    if (resultNum == 1) {
        var mMidPoint = PointF()
        faces[0]?.getMidPoint(mMidPoint) //两眼中心点
        faces[0]?.confidence()
        val mEyesDistance = faces[0]?.eyesDistance()  //两眼眼间距离
        val resultWidth = mEyesDistance!! / 2 + mEyesDistance*(widthTimes?:1) //用于确定图片宽度（宽为2*resultWidth）
        val resultHeight = mEyesDistance * 2 / 3 + mEyesDistance*(heightTimes?:1) //用于确定图片高度（高为2*resultHeight）


        var width = bm.width
        var height = bm.height
        left = if (mMidPoint.x - resultWidth < 0) {//判断左边超出图片设置为图片左边界
            0
        } else {
            (mMidPoint.x - resultWidth).toInt()
        }
        right = if (mMidPoint.x + resultWidth > width) {//判断右边超出图片设置为图片右边界
            width
        } else {
            (mMidPoint.x + resultWidth).toInt()
        }
        top = if (mMidPoint.y - resultHeight < 0) {//判断上边是否超出图片设置为图片上边界
            0
        } else {
            (mMidPoint.y - resultHeight).toInt()
        }
        bot = if (mMidPoint.y + resultHeight > height) {//判断下边是否超出图片设置为图片下边界
            height
        } else {
            (mMidPoint.y + resultHeight).toInt()
        }
    }
    try {
        var matrix = Matrix()
        if (isRotate == true){
            //根据y坐标计算角度
            matrix.preRotate(30f)
        }
        return Bitmap.createBitmap(bm, left, top, right - left, bot - top, matrix, false)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

//图像更改纯色背景，color传getColor
fun Bitmap.drawBg4Bitmap(color: Int): Bitmap {
    val paint = Paint()
    paint.color = color

    val bitmap = Bitmap.createBitmap(this.width, this.height, this.config)
    val canvas = Canvas(bitmap)
    canvas.drawRect(0f, 0f, this.width.toFloat(), this.height.toFloat(), paint)
    canvas.drawBitmap(this, 0f, 0f, paint)
    return bitmap
}

// 图像更改渐变背景，color传getColor
fun Bitmap.drawBg4BitmapGradient(colorStart: Int, colorEnd: Int): Bitmap {
    val paint = Paint()

    //设置线性渐变，点(x0,y0)向(x1,y1)渐变
    var linearGradient = LinearGradient(
        0f,
        0f,
        0f,
        this.height.toFloat(),
        colorStart,
        colorEnd,
        Shader.TileMode.CLAMP
    )
    //设置着色器
    paint.shader = linearGradient
    paint.strokeWidth = this.width.toFloat()
    val resultBitmap = Bitmap.createBitmap(this.width, this.height, this.config)
    //设置画布
    val canvas = Canvas(resultBitmap)
    //绘制渐变矩形当背景
    canvas.drawRect(0f, 0f, this.width.toFloat(), this.height.toFloat(), paint)
    //绘制图像
    canvas.drawBitmap(this, 0f, 0f, paint)
    return resultBitmap
}

fun Bitmap.xxx(width: Int, height: Int): Bitmap {
    val paint = Paint()
    val bitmap = Bitmap.createBitmap(width, height, this.config)

    val resizeBmp = Bitmap.createBitmap(this, 0, 0, 100, 200)

    val canvas = Canvas(bitmap)
    canvas.drawBitmap(resizeBmp, 0f, 0f, paint)
    canvas.drawBitmap(resizeBmp, resizeBmp.width.toFloat(), 0f, paint)
    canvas.drawBitmap(resizeBmp, resizeBmp.width.toFloat() * 2, 0f, paint)
    canvas.drawBitmap(resizeBmp, 0f, resizeBmp.height.toFloat(), paint)
    resizeBmp.recycle()
    return bitmap
}

/**
 * 图片切割成x列y行
 * @param x x轴切割
 * @param y y轴切割
 * @return
 */
fun Bitmap.split(x: Int, y: Int): List<Bitmap>? {
    val pieces = mutableListOf<Bitmap>()
    val width = this.width
    val height = this.height
    val pieceWidth = width / x
    val pieceHeight = height / y
    for (i in 0 until y) {
        for (j in 0 until x) {
            val xValue = j * pieceWidth
            val yValue = i * pieceHeight
            val image = Bitmap.createBitmap(
                this, xValue, yValue,
                pieceWidth, pieceHeight
            )
            pieces.add(image)
        }
    }
    return pieces
}

/**
* 获取旋转后的图片资源
* @param angle旋转角度
* @return Bitmap
*/
fun Bitmap.rotaing(angle: Float): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(this, 0, 0, this.width, this.height, matrix, true)
}

/**
* 获取镜像后的图片资源
*/
fun Bitmap.mirror(): Bitmap? {
    val matrix = Matrix()
    matrix.postScale(-1f, 1f)
    return Bitmap.createBitmap(this, 0, 0, this.width, this.height, matrix, true)
}

/**
 * 调整图片的色相，饱和度，亮度
 * @param srcBitmap
 * @param rotate
 * @param saturation
 * @param scale
 * @return
 */
fun Bitmap.beautyImage(contrast: Float, saturation: Float, scale: Float): Bitmap? {

//    //调整色相, 0~180颜色旋转？
//    val rotateMatrix = ColorMatrix()
//    rotateMatrix.setRotate(0, rotate)//R
//    rotateMatrix.setRotate(1, rotate)//G
//    rotateMatrix.setRotate(2, rotate)//B


    val cMatrix = ColorMatrix()
    /* 在亮度不变的情况下，提高对比度必定要降低亮度 */
    var regulateBright = (1 - contrast) * 128
    cMatrix.set(
        floatArrayOf(
            contrast, 0f, 0f, 0f, regulateBright, 0f,
            contrast, 0f, 0f, regulateBright, 0f, 0f,
            contrast, 0f, regulateBright, 0f, 0f, 0f, 1f, 0f
        )
    )


    //调整色彩饱和度，1是正常，增大变鲜艳，减小变暗淡，0会变黑白
    val saturationMatrix = ColorMatrix()
    saturationMatrix.setSaturation(saturation)

    //调整亮度，1是正常，增大变亮，减小变暗，0会变黑
    val scaleMatrix = ColorMatrix()
    scaleMatrix.setScale(scale, scale, scale, 1f)

    //叠加效果
    val colorMatrix = ColorMatrix()
//    colorMatrix.postConcat(rotateMatrix)
    colorMatrix.postConcat(cMatrix)
    colorMatrix.postConcat(saturationMatrix)
    colorMatrix.postConcat(scaleMatrix)

    //创建一个大小相同的空白Bitmap
    val dstBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    //载入Canvas,Paint
    val canvas = Canvas(dstBitmap)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
    //绘图
    canvas.drawBitmap(this, 0f, 0f, paint)
    return dstBitmap
}

fun Bitmap.writeToFile(path:String) {
    val file = File(path)
    val bos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, bos)
    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(file)
        fos.write(bos.toByteArray())
        bos.flush()
        fos.flush()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        if (fos != null) try {
            fos.close()
            if (bos != null) bos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}