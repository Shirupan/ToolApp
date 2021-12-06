package com.slaoren.common.mvvm

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

abstract class BaseActivity<T : ViewDataBinding, M : BaseViewModel>:AppCompatActivity() {
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
        ViewModelProviders.of(this)

//        ViewModelProvider.NewInstanceFactory().
    }
}