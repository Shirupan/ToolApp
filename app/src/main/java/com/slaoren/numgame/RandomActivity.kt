package com.slaoren.numgame

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.slaoren.R
import com.slaoren.databinding.ActivityRandomBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope


/**
 * 生成随机数
 */

class RandomActivity: FragmentActivity(), View.OnClickListener, CoroutineScope by MainScope(){
    var result = StringBuilder()
    lateinit var mBinding:ActivityRandomBinding
    var printTimes = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_random
        )
        mBinding.lifecycleOwner = this

        mBinding.btnOk.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            mBinding.btnOk -> {
                if (mBinding.etStart.text.toString().isNullOrEmpty()){
                    Toast.makeText(this, "请先输入生成范围", Toast.LENGTH_SHORT).show()
                    mBinding.etStart.requestFocus()
                    return
                }
                if (mBinding.etEnd.text.toString().isNullOrEmpty()){
                    Toast.makeText(this, "请先输入生成范围", Toast.LENGTH_SHORT).show()
                    mBinding.etEnd.requestFocus()
                    return
                }
                val start = mBinding.etStart.text.toString().toLong()
                val end = mBinding.etEnd.text.toString().toLong()
                var times = mBinding.etTimes.text.toString().toInt()
//                val r = (1 .. 10).random()
                if (times<1) {
                    Toast.makeText(this, "至少生成一个", Toast.LENGTH_SHORT).show()
                    times = 1
                    mBinding.etTimes.setText(times.toString())
                }
                result.append("第${printTimes++}次生成结果:\n")
                for (i in 1 .. times){
                    val r = if (start<end) (start .. end).random() else (end .. start).random()
                    result.append(r.toString())
                    result.append("\n")
                }
                result.append("\n")
                mBinding.tvResult.text = result
                tvShowBottom()
            }

        }
    }

    fun tvShowBottom(){
        mBinding.nsv.scrollTo(0, mBinding.tvResult.measuredHeight)
    }

}