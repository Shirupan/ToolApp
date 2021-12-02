package com.pan.common.listener

import androidx.viewpager.widget.ViewPager.OnPageChangeListener

abstract class SimpleOnPagerChangeListener : OnPageChangeListener {
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}
}