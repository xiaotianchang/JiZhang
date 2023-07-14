package com.hq.jizhang.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hq.jizhang.R
import com.hq.jizhang.activity.MainActivity
import com.hq.jizhang.base.BaseRcyViewHolder
import com.hq.jizhang.base.BaseRecyclerAdapter
import com.hq.jizhang.bean.ItemDetailBean
import com.hq.jizhang.util.AppUtil
import com.hq.jizhang.util.DateUtil
import com.hq.jizhang.util.LogUtil
import com.hq.jizhang.util.SpannableStringUtil
import kotlinx.android.synthetic.main.fragment_detail.*


/*
 * @创建者      肖天长
 * @创建时间    2023/1/17 15:58
 * @描述       明细
 *
 */
class DetailAdapter : BaseRecyclerAdapter<ItemDetailBean , BaseRcyViewHolder> {

    constructor(baseActivity : Activity) : super(baseActivity)

    private val VIEW_TYPE_DATA = 200
    private var VIEW_TYPE_EMPTY = 100

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : BaseRcyViewHolder {
        return if (viewType == VIEW_TYPE_EMPTY) {
            BaseRcyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.include_empty , parent , false))
        } else {
            BaseRcyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_detail , parent , false))
        }
        /*  return BaseRcyViewHolder(
              LayoutInflater.from(mContext).inflate(R.layout.item_detail , parent , false))*/
    }

    override fun onBindViewHolder(holder : BaseRcyViewHolder , position : Int) {
        if (getItemViewType(position) == VIEW_TYPE_DATA) {
            mData[position].apply {
                holder.setText(
                    R.id.item_detail_tv_date , DateUtil.dateToDateText(date.substring(5 , date.length)) + "  " + week)

                holder.setText(
                    R.id.item_detail_tv_income , "收入: ${AppUtil.formatToNumber(incomeMoney)}    支出: ${AppUtil.formatToNumber(disburseMoney)}")
                holder.getView<RecyclerView>(R.id.item_detail_rcy).layoutManager = LinearLayoutManager(mContext)
                holder.getView<RecyclerView>(R.id.item_detail_rcy).adapter = ItemDetailAdapter(
                    mContext , listItemDetailBean)
                /*  if(position==mData.size-1){
                      holder.getView<View>(R.id.item_detail_view).visibility=View.VISIBLE
                  }else holder.getView<View>(R.id.item_detail_view).visibility=View.GONE*/
            }
        }else{
            holder.getView<TextView>(R.id.rcy_tv_to).setOnClickListener {
                (mContext as MainActivity).showBookkeeping()
            }
            SpannableStringUtil.SpannableStringBuild( holder.getView<TextView>(R.id.rcy_tv_to).text.toString())
                .partTextColor(R.color.blue , "该月还没有记账哦，".length ,  holder.getView<TextView>(R.id.rcy_tv_to).text.length)
                ?.build( holder.getView(R.id.rcy_tv_to))

        }
    }

    override fun getItemCount() : Int {
        return if (mData.isEmpty()) {
            // 返回 1 来表示空数据项
            1
        } else {
            mData.size
        }
    }

    override fun getItemViewType(position : Int) : Int {
        return if (mData.isEmpty()) {
            // 返回一个特殊的 ViewType 来表示空数据项
            VIEW_TYPE_EMPTY
        } else {
            VIEW_TYPE_DATA
        }
    }

}