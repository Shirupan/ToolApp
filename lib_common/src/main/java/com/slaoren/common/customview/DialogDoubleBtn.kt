package com.slaoren.common.customview

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.slaoren.common.R
import com.slaoren.common.databinding.DialogDoubleBtnBinding

class DialogDoubleBtn(val title:String?=null, val content:String?=null, val btnMsg:String?=null, val listener:Listener?=null): DialogFragment(){

    var binding: DialogDoubleBtnBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_double_btn, container, false)

        binding?.run {
            if (title==null){
                tvTitle.visibility = View.GONE
            }else{
                tvTitle.text = title
            }
            if (context==null){
                tvContext.visibility = View.GONE
            }else{
                tvContext.text = content
            }

            if (btnMsg!=null){
                btnOk.text = btnMsg
            }

            btnOk.setOnClickListener {
                dismiss()
                listener?.ok()
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }

        return binding!!.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(context!!, R.style.dialog)
    }

    override fun onStart() {
        super.onStart()
        dialog?.run {
            setCancelable(true)
            setCanceledOnTouchOutside(true)
            window!!.setGravity(Gravity.CENTER)
            window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    interface Listener{
        fun ok()
    }
}