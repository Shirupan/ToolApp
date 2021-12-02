package com.slaoren.view

import android.app.ActivityManager
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.slaoren.R

/**
 * 防劫持提醒
 * 如果在输入密码时被劫持跳转到其他app界面，android并没有有效的放置劫持的方案
 * 只有在界面切换到后台提醒用户
 */
class HijackActivity: FragmentActivity() {

    override fun onPause() {
        super.onPause()
        hijack()
    }

    /**
     * 防劫持
     */
    private fun hijack() {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        //getRunningTasks会返回一个List，List的大小等于传入的参数。
        //get(0)可获得List中的第一个元素，即栈顶的task
        val info = activityManager.getRunningTasks(1)?.get(0)
        //得到当前栈顶的包名
        val shortClassName = info?.topActivity?.packageName //类名
        if (shortClassName != packageName) {
            Toast.makeText(applicationContext, getString(R.string.app_name)+"进入后台运行", Toast.LENGTH_SHORT).show()
        }
    }
}