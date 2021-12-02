package com.slaoren.paopai.data

import com.slaoren.common.util.SLog

class Player {
    var cards= mutableListOf<Card>()
    var score=0

    fun showCard():String{
        SLog.d("showCard()")
        var result = StringBuilder()
        cards.forEach {
            result.append(it.getCardText())
            result.append("、")
        }
        if (result.length>1)result.deleteCharAt(result.lastIndex)
        SLog.d("showCard():"+result)
        return result.toString()
    }

    fun addCard(card: Card){
        cards.add(card)
    }

    fun sort(){
        SLog.d("cards b:"+cards)
        cards.sortBy {
            it.index
        }
        SLog.d("cards a:"+cards)
    }
}