package com.slaoren.paopai.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.slaoren.R
import com.slaoren.common.util.SLog
import com.slaoren.paopai.data.Card

class CardQuickAdapter:BaseQuickAdapter<Card, BaseViewHolder>(R.layout.item_card){

    override fun convert(holder: BaseViewHolder, item: Card) {
        holder.setGone(R.id.ivSpace, !item.isSelected)
//        holder.setVisible(R.id.tv, !item.isSelected)
        holder.setText(R.id.tv, item.getCardText())
//        holder.itemView.setOnClickListener {
//            item.let {d->
//                d.isSelected = !d.isSelected
////                notifyItemChanged()
//            }
//        }
    }
}
