package com.hq.jizhang.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hq.jizhang.R
import com.hq.jizhang.base.BaseApplication
import com.hq.jizhang.base.BaseRcyViewHolder
import com.hq.jizhang.base.BaseRecyclerAdapter
import com.hq.jizhang.bean.ItemChatBean
import com.hq.jizhang.bean.ItemDetailBean
import com.hq.jizhang.cons.CommonConstant
import com.hq.jizhang.util.AppUtil
import com.hq.jizhang.util.DateUtil


/*
 * @创建者      肖天长
 * @创建时间    2023/1/17 15:58
 * @描述       明细
 *
 */
class ChatAdapter : BaseRecyclerAdapter<ItemChatBean , BaseRcyViewHolder> {

    constructor(baseActivity : Activity) : super(baseActivity)

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : BaseRcyViewHolder {
        return BaseRcyViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_chat , parent , false))
    }

    override fun onBindViewHolder(holder : BaseRcyViewHolder , position : Int) {
        mData[position].apply {
            holder.setText(R.id.item_chat_tv_name , "$name  $percentage%")
            holder.setText(R.id.item_chat_tv_money , money)

            if (type== CommonConstant.MainActivity.TYPE_INCOME){
                BaseApplication.incomeList.find { it.title==name }?.logo?.let {
                    holder.getView<ImageView>(R.id.item_chat_iv_logo).setImageResource(it)
                }
            }else{
                BaseApplication.disburseList.find { it.title==name }?.logo?.let {
                    holder.getView<ImageView>(R.id.item_chat_iv_logo).setImageResource(it)
                }
            }


        }
    }

}