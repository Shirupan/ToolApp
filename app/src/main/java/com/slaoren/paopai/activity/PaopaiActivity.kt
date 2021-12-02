package com.slaoren.paopai.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.slaoren.paopai.data.Card
import com.slaoren.R
import com.slaoren.databinding.ActivityPaopaiBinding
import com.slaoren.paopai.adapter.CardAdapter
import com.slaoren.paopai.adapter.RvChildItemClick
import com.slaoren.paopai.data.Player
import com.slaoren.paopai.data.listPaopai
import kotlinx.coroutines.*


/**
 * 跑牌
 * 2021.11.29
 */

class PaopaiActivity: FragmentActivity(), View.OnClickListener, CoroutineScope by MainScope(){
    var result = StringBuilder()//记录结果
    var totleCard = mutableListOf<Card>()
    var play1 = Player()
    var play2 = Player()
    var play3 = Player()
    var play4 = Player()

    var STATE_STOP = 0
    var STATE_PAUSE = 1
    var STATE_PLAYING = 2
    var state = STATE_STOP

    lateinit var mBinding:ActivityPaopaiBinding
    var p1Adapter = lazy { CardAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_paopai
        )
        mBinding.lifecycleOwner = this

        mBinding.btnStart.setOnClickListener(this)

        initPlayerRv()
    }

    override fun onClick(v: View?) {
        when(v){
            mBinding.btnStart -> {
                state = STATE_PLAYING
                dealCard()
                v.visibility = View.GONE
            }

        }
    }

    fun initPlayerRv(){
        mBinding.rvPlayer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mBinding.rvPlayer.adapter = p1Adapter.value
        p1Adapter.value.setOnItemclick(object :RvChildItemClick{
            override fun onClick(view: View, data: Any, position: Int) {
                play1.cards.removeAt(position)
                p1Adapter.value.notifyDataSetChanged()
            }

        })
    }

    fun tvShowBottom(){
        mBinding.nsv.scrollTo(0, mBinding.tvResult.measuredHeight+200)
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

        p1Adapter.value.datas = play1.cards
        p1Adapter.value.notifyDataSetChanged()

    }
}