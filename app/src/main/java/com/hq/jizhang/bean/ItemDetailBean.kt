package com.hq.jizhang.bean

import org.litepal.crud.LitePalSupport
import java.math.BigDecimal

/*
 * @创建者      肖天长
 * @创建时间    2023/7/11 13:15
 * @描述       明細
 *
 */
class ItemDetailBean{

    var date=""  //日期
    var week=""  //星期几
    var type="" //收入/支出
    var disburseMoney=BigDecimal(0) //支出金额
    var incomeMoney=BigDecimal(0) //收入金额
    var listItemDetailBean= mutableListOf<DetailSqlBean>()
    override fun toString() : String {
        return "ItemDetailBean(date='$date', week='$week', type='$type', disburseMoney='$disburseMoney', incomeMoney='$incomeMoney', listItemDetailBean=$listItemDetailBean)"
    }

}