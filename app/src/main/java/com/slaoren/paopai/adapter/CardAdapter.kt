package com.slaoren.paopai.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.slaoren.R
import com.slaoren.common.util.SLog
import com.slaoren.paopai.data.Card

class CardAdapter:RecyclerView.Adapter<CardViewHolder>(){
    var datas:MutableList<Card>?=null
    var childItemClick:RvChildItemClick?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent,false))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val data = datas?.get(position)
        holder.tv?.text = data?.getCardText()
        SLog.d("isSelected:"+position+","+data?.isSelected)
        holder.ivSpace?.visibility = if(data?.isSelected==false) View.GONE else View.VISIBLE
        holder.itemView.setOnClickListener {
            SLog.d("position:"+position)
            SLog.d("isSelected:"+data.toString())
            data?.let {d->
                d.isSelected = !d.isSelected
                SLog.d("isSelected:"+d.isSelected)
                holder.ivSpace?.visibility = if(d.isSelected) View.VISIBLE else View.GONE

//                notifyItemChanged(position)
//                childItemClick?.onClick(holder.itemView, d, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return datas?.size?:0
    }

    fun setOnItemclick(rvChildItemClick:RvChildItemClick){
        childItemClick = rvChildItemClick
    }
}

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var ivSpace:ImageView?=null
    var tv:TextView?=null

    init {
        ivSpace = itemView.findViewById(R.id.ivSpace)
        tv = itemView.findViewById(R.id.tv)
    }


}