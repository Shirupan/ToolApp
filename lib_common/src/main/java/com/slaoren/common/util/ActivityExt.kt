package com.slaoren.common.util

import android.app.Activity
import android.content.Intent
import android.os.Build

/**
 * 选取相册图片返回请求码
 */
fun Activity.openAlbumAndPick():Int {
    val code = 1001
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
        startActivityForResult(
                Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                code
        )
    } else {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, code)
    }
    return code
}

fun Activity.openAlbum() {
    val intent = Intent()
    intent.action = Intent.ACTION_MAIN
    intent.addCategory(Intent.CATEGORY_APP_GALLERY)
    this.startActivity(intent)
}