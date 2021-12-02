package com.pan.common.util

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.net.URLConnection

fun File.saveVideo2Album(context:Context):Boolean{
    val fileNameMap = URLConnection.getFileNameMap()
    val mimeType = fileNameMap.getContentTypeFor(this.name)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val fileName = this.name
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
        val contentResolver = context.contentResolver
        val uri = contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
                ?: return false
        try {
            val out = contentResolver.openOutputStream(uri)
            val fis = FileInputStream(this)
            FileUtils.copy(fis, out!!)
            fis.close()
            out.close()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    } else {
        MediaScannerConnection.scanFile(context, arrayOf(this.path), arrayOf(mimeType)) { path: String?, uri: Uri? -> }
        true
    }
}

fun File.saveImage2Album(context:Context):Boolean{
    val fileNameMap = URLConnection.getFileNameMap()
    val mimeType = fileNameMap.getContentTypeFor(this.name)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val fileName = this.name
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
        val contentResolver = context.contentResolver
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                ?: return false
        try {
            val out = contentResolver.openOutputStream(uri)
            val fis = FileInputStream(this)
            FileUtils.copy(fis, out!!)
            fis.close()
            out.close()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    } else {
        MediaScannerConnection.scanFile(context, arrayOf(this.path), arrayOf(mimeType)) { path: String?, uri: Uri? -> }
        true
    }
}