package com.hq.jizhang.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.hq.jizhang.R
import com.hq.jizhang.base.BaseRcyViewHolder
import com.hq.jizhang.base.BaseRecyclerAdapter
import com.hq.jizhang.bean.TypeBean
import com.umeng.soexample.util.ToastUtil


/*
 * @创建者      肖天长
 * @创建时间    2023/1/17 15:58
 * @描述       收入
 *
 */
class IncomeAdapter : BaseRecyclerAdapter<TypeBean , BaseRcyViewHolder> {

    constructor(baseActivity : Activity) : super(baseActivity)

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : BaseRcyViewHolder {
        return BaseRcyViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_income , parent , false))
    }

    override fun onBindViewHolder(holder : BaseRcyViewHolder , position : Int) {
        mData[position].apply {
            holder.getView<ImageView>(R.id.item_income_iv_logo).setImageResource(logo)
            holder.setText(R.id.item_income_tv_content , title)
            holder.getView<ImageView>(R.id.item_income_iv_logo)
                .setBackgroundResource(if (this.isSelect)R.drawable.shape_blue_oval else R.drawable.shape_gray_oval )
            holder.setOnClickListener(R.id.item_income_ll) { v ->
                mListener?.invoke(position , this)
                mData.forEach {
                    it.isSelect = it.title == this.title
                }
                notifyDataSetChanged()
            }
        }
    }
}