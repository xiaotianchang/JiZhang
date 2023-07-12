package com.hq.jizhang.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hq.jizhang.R
import com.hq.jizhang.base.BaseRcyViewHolder
import com.hq.jizhang.base.BaseRecyclerAdapter
import com.hq.jizhang.bean.ItemDetailBean
import com.hq.jizhang.util.DateUtil


/*
 * @创建者      肖天长
 * @创建时间    2023/1/17 15:58
 * @描述       明细
 *
 */
class DetailAdapter : BaseRecyclerAdapter<ItemDetailBean , BaseRcyViewHolder> {

    constructor(baseActivity : Activity) : super(baseActivity)

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : BaseRcyViewHolder {
        return BaseRcyViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_detail , parent , false))
    }

    override fun onBindViewHolder(holder : BaseRcyViewHolder , position : Int) {
        mData[position].apply {
            holder.setText(
                R.id.item_detail_tv_date , DateUtil.dateToDateText(date.substring(5 , date.length)) + "  " + week)

            holder.setText(
                R.id.item_detail_tv_income , "收入: $incomeMoney    支出: $disburseMoney")
            holder.getView<RecyclerView>(R.id.item_detail_rcy).layoutManager = LinearLayoutManager(mContext)
            holder.getView<RecyclerView>(R.id.item_detail_rcy).adapter = ItemDetailAdapter(mContext , listItemDetailBean)
        }
    }
}