package com.hq.jizhang.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.TextureView
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.hq.jizhang.R
import com.hq.jizhang.activity.UpdateSqlActivity
import com.hq.jizhang.base.BaseApplication
import com.hq.jizhang.base.BaseRcyViewHolder
import com.hq.jizhang.base.BaseRecyclerAdapter
import com.hq.jizhang.bean.DetailSqlBean
import com.hq.jizhang.cons.CommonConstant
import com.hq.jizhang.util.AppUtil
import com.hq.jizhang.util.StringUtil
import org.jetbrains.anko.startActivity


/*
 * @创建者      肖天长
 * @创建时间    2023/1/17 15:58
 * @描述       明细
 *
 */
class ItemDetailAdapter : BaseRecyclerAdapter<DetailSqlBean , BaseRcyViewHolder> {

    constructor(baseActivity : Activity , mutableList : MutableList<DetailSqlBean>) : super(baseActivity , mutableList)

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : BaseRcyViewHolder {
        return BaseRcyViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_detail_item , parent , false))
    }

    override fun onBindViewHolder(holder : BaseRcyViewHolder , position : Int) {
        mData[position].apply {
            holder.setText(R.id.item_detail_tv_type , if (!StringUtil.isEmpty(note)) note else name)

            if (type==CommonConstant.MainActivity.TYPE_INCOME){
                BaseApplication.incomeList.find { it.title==name }?.logo?.let {
                    AppUtil.setCompoundDrawables(
                        it , holder.getView(R.id.item_detail_tv_type),CommonConstant.ORIENTATION_LEFT)
                }
            }else{
                BaseApplication.disburseList.find { it.title==name }?.logo?.let {
                    AppUtil.setCompoundDrawables(
                        it , holder.getView(R.id.item_detail_tv_type),CommonConstant.ORIENTATION_LEFT)
                }
            }

            if (type == CommonConstant.MainActivity.TYPE_INCOME) {
                holder.setText(R.id.item_detail_tv_money , "+$money")
            } else holder . setText (R.id.item_detail_tv_money , "-$money")

            holder.getView<LinearLayout>(R.id.item_detailItem_ll).setOnClickListener {
                mContext.startActivity<UpdateSqlActivity>(CommonConstant.MainActivity.UPDATE_DATA to this)
            }
        }
    }
}