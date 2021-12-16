package com.slaoren.paopai.data

import com.slaoren.common.util.SLog

interface IAIPaoPai {

    fun getCardSize():Int

    fun showCard():String

    fun addCard(card: Card)

    fun sort()

    fun checkCark()

    fun checkDanzhang(value:Card):Int

    fun checkLiangzhang(value:Card):Int

    fun checkSanzhang(value:Card):Int

    fun checkSizhang(value:Card):Int

    fun checkBi(value:Card):Int

    fun aiChupai(value:List<Card>?):List<Card>?

    fun findCards(cardNum:Int, times:Int):List<Card>?

    fun firstTimeChuPai():List<Card>?

    fun findBi(cardSmallestNum:Int):List<Card>?
}