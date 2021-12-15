package com.slaoren.paopai.vm

import com.slaoren.common.mvvm.BaseViewModel
import com.slaoren.paopai.data.Card
import com.slaoren.paopai.data.Player

class PaopaiVM: BaseViewModel() {
    fun checkLevel0(player: Player, value: Card){
        checkDanzhang(player, value)
        checkLiangzhang(player, value)
        checkSanzhang(player, value)
        checkSizhang(player, value)
        checkBi(player, value)
    }

    fun checkDanzhang(player: Player, value: Card):Card?{
        val num = player.checkDanzhang(value)
        if (num>=0){
            return player.cards.find {
                it.num == num
            }
        }
        return null
    }

    fun checkLiangzhang(player: Player, value: Card){
        player.checkLiangzhang(value)
    }

    fun checkSanzhang(player: Player, value: Card){
        player.checkSanzhang(value)
    }

    fun checkSizhang(player: Player, value: Card){
        player.checkSizhang(value)
    }

    fun checkBi(player: Player, value: Card){
        player.checkBi(value)
    }
}