package com.hq.jizhang.bean

import org.litepal.crud.LitePalSupport
import java.math.BigDecimal

/*
 * @创建者      肖天长
 * @创建时间    2023/7/11 13:15
 * @描述       明細
 *
 */
class ChatDataBean{

    var yearMonth=""  //年月
    var sumOfMonth:BigDecimal= BigDecimal(0)  //每月支出或收入总消费
    var listItemDetailBean= mutableListOf<DetailSqlBean>()  //

    override fun toString() : String {
        return "ChatDataBean(yearMonth='$yearMonth',sumOfMonth='$sumOfMonth', listItemDetailBean=$listItemDetailBean)"
    }

}