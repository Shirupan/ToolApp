package com.slaoren.paopai.data

import com.slaoren.common.util.SLog

class Player(name:String) : AI(name) {


    fun checkSelectChupai(selectCards: List<Card>, lastCards: List<Card>?): Boolean {

        if(!lastCards.isNullOrEmpty()){//不是自己第一个出
            if(selectCards.size!=lastCards.size //且数量不一样
                    ||selectCards[0].getExtNum()<=lastCards[0].getExtNum()//比上家出的小，
                    ||checkBi(lastCards[0])!=0){//不是比
                return false
            }else{
                selectCards.forEach {
                    val firstCard = selectCards[0]
                    if (firstCard.num!=it.num){
                        return false
                    }
                }
            }
        }else{//第一个出
            if(selectCards.size in 2..3){//两三张
                val firstCard = selectCards[0]
                selectCards.forEach {
                    if (firstCard.num!=it.num){
                        return false
                    }
                }
            }else if(selectCards.size==4){//四张相同或比
                selectCards.sortedBy { it.num }
                val card1 = selectCards[0].num
                val card2 = selectCards[1].num
                val card3 = selectCards[2].num
                val card4 = selectCards[3].num
                if ((card1!=card2||card1!=card3||card1!=card4)
                        &&!(card2-card1!=1&&card3-card2!=1&&card4-card3!=1)){
                    return false
                }
            }else if (selectCards.isEmpty()||selectCards.size>4){//选择数量不是1-4
                return false
            }
        }

        return true
    }

    fun buyao(lastCards: List<Card>):List<Card>?{
        SLog.d("buyao List:"+aiChupai(lastCards))
        return aiChupai(lastCards)
    }

    //和AI不同的是不从集合中去掉找到的牌
    override fun findCards(cardNum:Int, cardSize:Int):List<Card>?{
        var result = cards.filter {
            SLog.d("findCards:"+cardNum%14)
            it.num == cardNum%14
        } as MutableList

        val size = result.size-cardSize
        return if(size>=0){
            result.subList(0, cardSize)
        }else {
            null
        }
    }

    fun chupai(selectCards: List<Card>){
        selectCards.forEach {
            cards.remove(it)
            numMap[it.getExtNum()] = numMap[it.getExtNum()]!!-1
        }
    }
}