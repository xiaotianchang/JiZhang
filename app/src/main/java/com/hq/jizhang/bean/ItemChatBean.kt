package com.hq.jizhang.bean

import java.math.BigDecimal

/*
 * @创建者      肖天长
 * @创建时间    2023/7/11 13:15
 * @描述       明細
 *
 */
data class ItemChatBean(
    var name : String = "" ,
    var money : String = "" ,
    var type : String = "" ,
    var percentage : BigDecimal? =null //占总数比
 )