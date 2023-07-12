package com.hq.jizhang.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hq.jizhang.R
import com.hq.jizhang.base.BaseRcyViewHolder
import com.hq.jizhang.base.BaseRecyclerAdapter
import com.hq.jizhang.bean.DetailSqlBean


/*
 * @创建者      肖天长
 * @创建时间    2023/1/17 15:58
 * @描述       明细
 *
 */
class DetailAdapter : BaseRecyclerAdapter<DetailSqlBean , BaseRcyViewHolder> {

    constructor(baseActivity : Activity ) : super(baseActivity )

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : BaseRcyViewHolder {
        return BaseRcyViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_detail , parent , false))
    }

    override fun onBindViewHolder(holder : BaseRcyViewHolder , position : Int) {
        mData.apply {

           /* holder.setText(R.id.item_areaCode_tv_address , this[position].country)
            holder.setOnClickListener(R.id.item_areaCode_cl){ v->
                mListener?.invoke(position, StringUtil.getString(this[position].mobilePrefix))
            }*/
        }
    }
}