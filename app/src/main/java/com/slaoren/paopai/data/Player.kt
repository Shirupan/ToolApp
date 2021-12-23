package com.slaoren.paopai.data

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
}