
package com.slaoren.paopai.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.slaoren.paopai.data.Card
import com.slaoren.R
import com.slaoren.common.mvvm.BaseActivity
import com.slaoren.databinding.ActivityPaopaiBinding
import com.slaoren.paopai.adapter.CardQuickAdapter
import com.slaoren.paopai.data.AI
import com.slaoren.paopai.data.Player
import com.slaoren.paopai.data.listPaopai
import com.slaoren.paopai.vm.PaopaiVM
import kotlinx.coroutines.*


/**
 * 跑牌
 * 2021.11.29
 */

class PaopaiActivity: BaseActivity<ActivityPaopaiBinding, PaopaiVM>(), View.OnClickListener, CoroutineScope by MainScope(){
    var result = StringBuilder()//记录结果
    var totleCard = mutableListOf<Card>()
    var play1 = Player("p1")
    var play2 = AI("p2")
    var play3 = AI("p3")
    var play4 = AI("p4")
    var currentPlayer:AI? = null


    var STATE_STOP = 0
    var STATE_PAUSE = 1
    var STATE_PLAYING = 2
    var state = STATE_STOP

    var current: List<Card>?=null

    override fun getLayoutId(): Int = R.layout.activity_paopai
    var p1Adapter = lazy { CardQuickAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mBinding.btnStart.setOnClickListener(this)
        mBinding.btnChupai.setOnClickListener(this)
        mBinding.btnBuYao.setOnClickListener(this)

        initPlayerRv()
    }

    override fun onClick(v: View?) {
        when(v){
            mBinding.btnStart -> {
                state = STATE_PLAYING
                dealCard()
                v.visibility = View.GONE
            }
            mBinding.btnChupai -> {
                chupai()
            }
            mBinding.btnBuYao -> {
                buyao()
            }

        }
    }

    fun initPlayerRv(){
        mBinding.rvPlayer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mBinding.rvPlayer.adapter = p1Adapter.value

        p1Adapter.value.setOnItemClickListener(object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                (p1Adapter.value.data[position]).let {
                    it.isSelected = !it.isSelected
                    p1Adapter.value.setData(position, it)
                }
            }
        })
//        p1Adapter.value.setOnItemclick(object :RvChildItemClick{
//            override fun onClick(view: View, data: Any, position: Int) {
////                (data as Card).let {
////                    data.isSelected = !data.isSelected
////                    p1Adapter.value.notifyItemChanged(position)
////                }
////                chupai(data as Card, position)
//            }
//        })
    }

    fun aiChupai(ai: AI, list:List<Card>?):List<Card>?{
        val result = ai.aiChupai(list)
        addMsg(ai.name+":")

        if (result==null){
            addMsg("不要\n")
            return list
        } else{
            result.forEach {
                addMsg(it.getCardText())
                addMsg(" ")
            }
            addMsg("\n")
        }
        return result
    }

    fun chupai(){
        var indexs = mutableListOf<Card>()
        addMsg(play1.name+":")
        p1Adapter.value.data.forEachIndexed { index, card ->
            if (card.isSelected){
                indexs.add(card)
                addMsg(card.getCardText()+" ")
            }
        }
        addMsg("\n")
        tvShowBottom()

        indexs.reverse()

//        if(play1.checkChupai(indexs, current)){
            indexs.forEach {
                play1.cards.remove(it)
            }
            p1Adapter.value.notifyDataSetChanged()
//        }

        mBinding.btnChupai.isEnabled = false

        current = indexs
        current = aiChupai(play2, current)?:current
        current = aiChupai(play3, current)?:current
        current = aiChupai(play4, current)?:current

        mBinding.btnChupai.isEnabled = true
    }

    fun buyao(){
        current = null
        current = aiChupai(play2, current)?:current
        current = aiChupai(play3, current)?:current
        current = aiChupai(play4, current)?:current
    }

    fun tvShowBottom(){
        launch {
            delay(50)
            mBinding.nsv.smoothScrollBy(0, mBinding.tvResult.measuredHeight+200)
        }
    }

    fun dealCard(){
        totleCard.addAll(listPaopai)
        for (i in 1 .. totleCard.size){
                    val card = totleCard.random()
//            SLog.d(i.toString()+","+card.getCardText())
//            result.append(card.toString()+","+card.getCardText())
//            result.append("\n")
                    when(i%4){
                        0->{
                            play1.addCard(card)
                        }
                        1->play2.addCard(card)
                        2->play3.addCard(card)
                        3->play4.addCard(card)
                    }

                    totleCard.remove(card)

        }
        play2.checkCark()
        play3.checkCark()
        play4.checkCark()
        result.append("Player1:")
        result.append("\n")
        result.append(play1.showCard())
        result.append("\n")
        result.append("\n")
        result.append("Player2:")
        result.append("\n")
        result.append(play2.showCard())
        result.append("\n")
        result.append("\n")
        result.append("Player3:")
        result.append("\n")
        result.append(play3.showCard())
        result.append("\n")
        result.append("\n")
        result.append("Player4:")
        result.append("\n")
        result.append(play4.showCard())
        result.append("\n")
        result.append("\n")
        mBinding.tvResult.text = result.toString()
        tvShowBottom()

        play1.sort()

        p1Adapter.value.setNewData(play1.cards)
        p1Adapter.value.notifyDataSetChanged()

    }

    fun addMsg(msg:String){
        result.append(msg)
        mBinding.tvResult.text = result.toString()
    }


}