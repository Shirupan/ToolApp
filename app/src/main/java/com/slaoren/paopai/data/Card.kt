package com.slaoren.paopai.data

import com.pan.common.data.BaseData

data class Card(
        val text:String,
        val num:Int,
        val flower:Int, //0黑桃 1红桃 2梅花 3方块
        val index:Int
): BaseData(){
        //返回花色
        fun getFlowerStr():String{
                return when(flower){
                        0->"黑桃"
                        1->"红桃"
                        2->"梅花"
                        3->"方块"
                        else->"黑桃"
                }
        }

        //返回格式 黑桃A
        fun getCardText():String{
                val result = StringBuilder()
                result.append(when(flower){
                        0->"黑桃"
                        1->"红桃"
                        2->"梅花"
                        3->"方块"
                        else->"黑桃"
                })
                result.append(text)
                return result.toString()
        }
}

val listCard = mutableListOf(
        Card("A", 1, 0, 0),
        Card("2", 2, 0, 4),
        Card("3", 3, 0, 8),
        Card("4", 4, 0, 12),
        Card("5", 5, 0, 16),
        Card("6", 6, 0, 20),
        Card("7", 7, 0, 24),
        Card("8", 8, 0, 28),
        Card("9", 9, 0, 32),
        Card("10", 10, 0, 36),
        Card("J", 11, 0, 40),
        Card("Q", 12, 0, 44),
        Card("K", 13, 0, 48),
        Card("A", 1, 1, 1),
        Card("2", 2, 1, 5),
        Card("3", 3, 1, 9),
        Card("4", 4, 1, 13),
        Card("5", 5, 1, 17),
        Card("6", 6, 1, 21),
        Card("7", 7, 1, 25),
        Card("8", 8, 1, 29),
        Card("9", 9, 1, 33),
        Card("10", 10, 1, 37),
        Card("J", 11, 1, 41),
        Card("Q", 12, 1, 45),
        Card("K", 13, 1, 49),
        Card("A", 1, 2, 2),
        Card("2", 2, 2, 6),
        Card("3", 3, 2, 10),
        Card("4", 4, 2, 14),
        Card("5", 5, 2, 18),
        Card("6", 6, 2, 22),
        Card("7", 7, 2, 26),
        Card("8", 8, 2, 30),
        Card("9", 9, 2, 34),
        Card("10", 10, 2, 38),
        Card("J", 11, 2, 42),
        Card("Q", 12, 2, 46),
        Card("K", 13, 2, 50),
        Card("A", 1, 3, 3),
        Card("2", 2, 3, 7),
        Card("3", 3, 3, 11),
        Card("4", 4, 3, 15),
        Card("5", 5, 3, 19),
        Card("6", 6, 3, 23),
        Card("7", 7, 3, 27),
        Card("8", 8, 3, 31),
        Card("9", 9, 3, 35),
        Card("10", 10, 3, 39),
        Card("J", 11, 3, 43),
        Card("Q", 12, 3, 47),
        Card("K", 13, 3,51)
)

val listPaopai = mutableListOf(
        Card("3", 3, 0, 0),
        Card("3", 3, 1, 1),
        Card("3", 3, 2, 2),
        Card("3", 3, 3, 3),
        Card("4", 4, 0, 4),
        Card("4", 4, 1, 5),
        Card("4", 4, 2, 6),
        Card("4", 4, 3, 7),
        Card("5", 5, 0, 8),
        Card("5", 5, 1, 9),
        Card("5", 5, 2, 10),
        Card("5", 5, 3, 11),
        Card("6", 6, 0, 12),
        Card("6", 6, 1, 13),
        Card("6", 6, 2, 14),
        Card("6", 6, 3, 15),
        Card("7", 7, 0, 16),
        Card("7", 7, 1, 17),
        Card("7", 7, 2, 18),
        Card("7", 7, 3, 19),
        Card("8", 8, 0, 20),
        Card("8", 8, 1, 21),
        Card("8", 8, 2, 22),
        Card("8", 8, 3, 23),
        Card("9", 9, 0, 24),
        Card("9", 9, 1, 25),
        Card("9", 9, 2, 26),
        Card("9", 9, 3, 27),
        Card("10", 10, 0, 28),
        Card("10", 10, 1, 29),
        Card("10", 10, 2, 30),
        Card("10", 10, 3, 31),
        Card("J", 11, 0, 32),
        Card("J", 11, 1, 33),
        Card("J", 11, 2, 34),
        Card("J", 11, 3, 35),
        Card("Q", 12, 0, 36),
        Card("Q", 12, 1, 37),
        Card("Q", 12, 2, 38),
        Card("Q", 12, 3, 39),
        Card("K", 13, 0, 40),
        Card("K", 13, 0, 41),
        Card("K", 13, 0, 42),
        Card("K", 13, 0, 43),
        Card("A", 1, 0, 44),
        Card("A", 1, 1, 45),
        Card("A", 1, 2, 46),
        Card("A", 1, 3, 47),
        Card("2", 2, 0, 48),
        Card("2", 2, 1, 49),
        Card("2", 2, 2, 50),
        Card("2", 2, 3, 51)
)