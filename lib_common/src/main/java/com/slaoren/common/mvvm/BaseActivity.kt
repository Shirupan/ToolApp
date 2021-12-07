package com.slaoren.common.mvvm

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<T : ViewDataBinding, M:BaseViewModel>:AppCompatActivity() {
    lateinit var mBinding:T
    var mViewModel:M ?=null

    abstract fun getLayoutId():Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(
            this,
            getLayoutId()
        )
        mBinding.lifecycleOwner = this

        setVM()

    }

    private fun setVM(){
        val type = this.javaClass.genericSuperclass //获取泛型
        if (type is ParameterizedType) {
            val targetType = type.actualTypeArguments[1]//获取第二个参数
            if (targetType is Class<*>) {
                mViewModel = ViewModelProvider(this).get(targetType as Class<M>)
            }
        }
    }
}