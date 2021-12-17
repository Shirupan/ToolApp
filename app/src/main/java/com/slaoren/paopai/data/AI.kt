package com.slaoren.paopai.data

import com.slaoren.common.util.SLog

open class AI(var name:String):IAIPaoPai{

    var cards= mutableListOf<Card>()
    var numMap= mutableMapOf<Int, Int>()
    var score=0
    var liangZhang=mutableListOf<Card>()
    var sanZhang=mutableListOf<Card>()
    var siZhang=mutableListOf<Card>()
    var wuZhang=mutableListOf<Card>()

    override fun getCardSize():Int{
        return cards.size
    }

    override fun showCard():String{
        var result = StringBuilder()
        cards.forEach {
            result.append(it.getCardText())
            result.append("、")
        }
        if (result.length>1)result.deleteCharAt(result.lastIndex)
//        SLog.d("showCard():"+result)
        return result.toString()
    }

    override fun addCard(card: Card){
        cards.add(card)
    }

    override fun sort(){
        SLog.d("cards b:"+cards)
        cards.sortBy {
            it.index
        }
        SLog.d("cards a:"+cards)
    }

    override fun checkCark(){
        var current:Card? = null
        var card1:Int = 0
        var card2:Int = 0
        var card3:Int = 0
        var card4:Int = 0
        var card5:Int = 0
        var card6:Int = 0
        var card7:Int = 0
        var card8:Int = 0
        var card9:Int = 0
        var card10:Int = 0
        var card11:Int = 0
        var card12:Int = 0
        var card13:Int = 0
        if (cards.size<=1) return//只有一张


        cards.forEachIndexed { index, card ->
//            if (current==null){
//                current = card
//            }else{
//                if (current?.num == card.num){
//
//                }
//            }


//            if(index<cards.lastIndex-4 && card.num==cards[index+1].num==cards[index+1].num){
//
//            }

            when(card.num){
                1->card1++
                2->card2++
                3->card3++
                4->card4++
                5->card5++
                6->card6++
                7->card7++
                8->card8++
                9->card9++
                10->card10++
                11->card11++
                12->card12++
                13->card13++
            }

            numMap.put(3,card3)
            numMap.put(4,card4)
            numMap.put(5,card5)
            numMap.put(6,card6)
            numMap.put(7,card7)
            numMap.put(8,card8)
            numMap.put(9,card9)
            numMap.put(10,card10)
            numMap.put(11,card11)
            numMap.put(12,card12)
            numMap.put(13,card13)
            numMap.put(15,card1)//A
            numMap.put(16,card2)//2
        }
    }

    //是否有大与目标的,返回Card.num
    override fun checkDanzhang(value:Card):Int{
        numMap.forEach{
            SLog.d("checkDanzhang: "+it.key+":"+it.value+","+value.getExtNum())
            if(it.key>value.getExtNum()&&it.value>0){
                return it.key
            }
        }
        return 0
    }

    override fun checkLiangzhang(value:Card):Int{
        numMap.forEach{
            if(it.value>1&&it.key>value.getExtNum()){
                return it.key
            }
        }
        return 0
    }

    override fun checkSanzhang(value:Card):Int{
        numMap.forEach{
            if(it.value>2&&it.key>value.getExtNum()){
                return it.key
            }
        }
        return 0
    }

    override fun checkSizhang(value:Card):Int{
        numMap.forEach{
            if(it.value==4&&it.key>value.getExtNum()){
                return it.key
            }
        }
        return 0
    }

    override fun checkBi(value:Card):Int{
        var index = 0
        if (++index>value.getExtNum()
                &&index<10
                &&numMap[index]!=null&&numMap[index]!!>0
                &&numMap[index+1]!=null&&numMap[index+1]!!>0
                &&numMap[index+2]!=null&&numMap[index+2]!!>0
                &&numMap[index+3]!=null&&numMap[index+3]!!>0){
            return index
        }
        return 0
    }

    override fun aiChupai(value:List<Card>?):List<Card>?{
        SLog.d("chupai:"+value?.size)

        if (!value.isNullOrEmpty()){
            val card0 = value[0]
            val result = when(value.size){
                1->checkDanzhang(card0)
                2->checkLiangzhang(card0)
                3->checkSanzhang(card0)
                4->{
                    if (card0.num==value[1].num){
                        checkSizhang(card0)
                    }else{
                        val startCard = checkBi(card0)
                        return if (startCard!=0){
                            findBi(startCard)
                        }else{
                            null
                        }
                    }
                }
                else->0
            }
            SLog.d("chupai result:"+result)
            return findCards(result, value.size)
        }else{
            return firstTimeChuPai()
        }
    }

    override fun findCards(cardNum:Int, cardSize:Int):List<Card>?{
        var result = cards.filter {
            SLog.d("findCards:"+cardNum%14)
            it.num == cardNum%14
        } as MutableList

        val size = result.size-cardSize
        return if(size>=0){
            //去掉合集中的元素
            numMap[cardNum] = size
            result.subList(0, cardSize).onEach {
                cards.remove(it)
            }
        }else {
            null
        }
    }

    override fun firstTimeChuPai(): List<Card>? {
        numMap.forEach{
            if (it.value!=0){
                SLog.d("firstTimeChuPai it.value:"+it.value)
                return findCards(it.key, it.value)
            }
        }
        return null
    }

    override fun findBi(cardSmallestNum: Int): List<Card>? {
        var result = mutableListOf<Card>()

        for (i in cardSmallestNum..cardSmallestNum+4){
            cards.find {
                it.num == i
            }?.let {c->
                result.add(c)
            }
        }
        return if (result.size==4) result else null
    }
}