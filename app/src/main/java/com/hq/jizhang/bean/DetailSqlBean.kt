package com.hq.jizhang.bean

import org.litepal.crud.LitePalSupport

/*
 * @创建者      肖天长
 * @创建时间    2023/7/11 13:15
 * @描述       明細
 *
 */
class DetailSqlBean : LitePalSupport(){

    var date=""  //日期
    var week=""  //星期几
    var money="" //金额
    var note="" //备注
    var type="" //支出还是记账


    override fun toString() : String {
        return "DetailBean(date='$date', week='$week', money='$money', type='$type')"
    }

}