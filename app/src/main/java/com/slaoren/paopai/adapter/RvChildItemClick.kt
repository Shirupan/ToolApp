package com.slaoren.paopai.adapter

import android.view.View

interface RvChildItemClick {
    fun onClick(view: View, data:Any, position:Int)
}