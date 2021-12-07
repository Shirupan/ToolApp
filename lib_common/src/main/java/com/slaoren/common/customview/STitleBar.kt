package com.slaoren.common.customview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.slaoren.common.R
import com.slaoren.common.util.SizeUtil

/**
 * 2019/5/9
 * 建议在布局设置文字颜色，调用时设置文字和点击事件
 * 建议在xml中统一设置，或者在代码中统一设置。在代码中设置将覆盖xml设置。
 */
class STitleBar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private var imgLeft: ImageView? = null
    private var imgRight: ImageView? = null
    private var tvLeft: TextView? = null
    private var tvCenter: TextView? = null
    private var tvRight: TextView? = null
    private val defTextColor = Color.BLACK
    private val defTextSize = 54 //获取xml设置的值会自动转为px
    private fun setAttrs(context: Context, attrs: AttributeSet) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.STitleBar)
        tvLeft?.text = attributes.getString(R.styleable.STitleBar_left_text)
        tvLeft?.setTextColor(attributes.getColor(R.styleable.STitleBar_left_color, defTextColor))
        if (attributes.getBoolean(R.styleable.STitleBar_left_visible, true)) {
            setLeftTextVisible(VISIBLE)
        } else {
            setLeftTextVisible(GONE)
        }
        imgLeft?.setImageResource(attributes.getResourceId(R.styleable.STitleBar_img_left_src, 0))
        if (attributes.getBoolean(R.styleable.STitleBar_img_left_visible, true)) {
            setLeftImgVisible(VISIBLE)
        } else {
            setLeftImgVisible(GONE)
        }
        tvCenter?.text = attributes.getString(R.styleable.STitleBar_title_text)
        tvCenter?.setTextColor(attributes.getColor(R.styleable.STitleBar_title_color, defTextColor))
        if (attributes.getBoolean(R.styleable.STitleBar_title_visible, true)) {
            setTitleVisible(VISIBLE)
        } else {
            setTitleVisible(GONE)
        }
        tvRight?.text = attributes.getString(R.styleable.STitleBar_right_text)
        tvRight?.setTextColor(attributes.getColor(R.styleable.STitleBar_right_color, defTextColor))
        if (attributes.getBoolean(R.styleable.STitleBar_right_visible, true)) {
            setRightTextVisible(VISIBLE)
        } else {
            setRightTextVisible(GONE)
        }
        imgRight?.setImageResource(attributes.getResourceId(R.styleable.STitleBar_img_right_src, 0))
        if (attributes.getBoolean(R.styleable.STitleBar_img_right_visible, true)) {
            setRightImgVisible(VISIBLE)
        } else {
            setRightImgVisible(GONE)
        }
        setTextSize(SizeUtil.px2Sp(context, attributes.getDimension(R.styleable.STitleBar_text_size, defTextSize.toFloat())))//获取xml设置的值会自动转为px
        attributes.recycle()
    }

    private fun bindId() {
        imgLeft = findViewById(R.id.img_title_left)
        tvLeft = findViewById(R.id.tv_title_left)
        tvCenter = findViewById(R.id.tv_title_center)
        tvRight = findViewById(R.id.tv_title_right)
        imgRight = findViewById(R.id.img_title_right)
    }

    fun setBg(colorId: Int) {
        setBackgroundColor(ContextCompat.getColor(context, colorId))
    }

    fun setTextColor(colorId: Int) {
        tvLeft?.setTextColor(ContextCompat.getColor(context, colorId))
        tvCenter?.setTextColor(ContextCompat.getColor(context, colorId))
        tvRight?.setTextColor(ContextCompat.getColor(context, colorId))
    }

    fun setTextSize(size: Float) {
        tvLeft?.textSize = size
        tvCenter?.textSize = size
        tvRight?.textSize = size
    }

    fun setLeftImg(id: Int, onClickListener: OnClickListener?) {
        imgLeft?.setImageResource(id)
        imgLeft?.setOnClickListener(onClickListener)
    }

    fun setLeftImg(drawable: Drawable?, onClickListener: OnClickListener?) {
        imgLeft?.setImageDrawable(drawable)
        imgLeft?.setOnClickListener(onClickListener)
    }

    fun setLeftImgVisible(visible: Int) {
        imgLeft?.visibility = visible
    }

    fun setLeftText(textId: Int, onClickListener: OnClickListener?) {
        tvLeft?.setText(textId)
        tvLeft?.setOnClickListener(onClickListener)
    }

    fun setLeftText(text: String?, onClickListener: OnClickListener?) {
        tvLeft?.text = text
        tvLeft?.setOnClickListener(onClickListener)
    }

    fun setLeftTextVisible(visible: Int) {
        tvLeft?.visibility = visible
    }

    fun setTitle(textId: Int) {
        tvCenter?.setText(textId)
    }

    fun setTitle(text: String?) {
        tvCenter?.text = text
    }

    fun setTitleVisible(visible: Int) {
        tvCenter?.visibility = visible
    }

    fun setRightText(textId: Int, onClickListener: OnClickListener?) {
        tvRight?.setText(textId)
        tvRight?.setOnClickListener(onClickListener)
    }

    fun setRightText(text: String?, onClickListener: OnClickListener?) {
        tvRight?.text = text
        tvRight?.setOnClickListener(onClickListener)
    }

    fun setRightTextVisible(visible: Int) {
        tvRight?.visibility = visible
    }

    fun setRightImg(id: Int, onClickListener: OnClickListener?) {
        imgRight?.setImageResource(id)
        imgRight?.setOnClickListener(onClickListener)
    }

    fun setRightImg(drawable: Drawable?, onClickListener: OnClickListener?) {
        imgRight?.setImageDrawable(drawable)
        imgRight?.setOnClickListener(onClickListener)
    }

    fun setRightImgVisible(visible: Int) {
        imgRight?.visibility = visible
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_stitlebar, this, true)
        bindId()
        setAttrs(context, attrs)
    }
}