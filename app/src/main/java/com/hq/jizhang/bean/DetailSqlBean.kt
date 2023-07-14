package com.hq.jizhang.bean

import android.annotation.SuppressLint
import com.hq.jizhang.util.DateUtil
import org.litepal.crud.LitePalSupport
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

/*
 * @创建者      肖天长
 * @创建时间    2023/7/11 13:15
 * @描述       明細
 *
 */
class DetailSqlBean : LitePalSupport(),Serializable , Comparable<DetailSqlBean> {

    var id:Long=0
    var date=""  //日期年月日
    var week=""  //星期几
    var money="" //金额
    var note="" //备注
    var yearMonth="" //当前年月
    var name:String?="" //操作的哪个名称
    var type="" //支出还是收入

    override fun toString() : String {
        return "DetailSqlBean(id='$id', date='$date', week='$week', money='$money', note='$note', yearMonth='$yearMonth', name=$name, type='$type')"
    }

    //日期比较取出最大的一个
    @SuppressLint("SimpleDateFormat")
    override fun compareTo(other : DetailSqlBean) : Int {
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
        val date = simpleDateFormat.parse(this.date)

        val simpleDateFormat2 = SimpleDateFormat("yyyy/MM/dd")
        val date2 = simpleDateFormat2.parse(other.date)
        return date.compareTo(date2)
    }
}