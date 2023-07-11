package com.hq.jizhang.base

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class BaseRecyclerAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH> {
    protected var mContext: Activity
    protected var inflater: LayoutInflater
    protected lateinit var mData: MutableList<T>
    protected var mListener: ((Int, Any) -> Unit)? = null

    constructor(context: Activity) : this(context, null) {
        mContext = context
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    constructor(context: Activity, data: MutableList<T>?) {
        this.mContext = context
        mData = data ?: mutableListOf<T>()
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    constructor(context: Activity, data: Array<T>) {
        this.mContext = context
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        Collections.addAll(mData, *data)
    }

    fun setOnItemClickListener(listener: (position: Int, item: Any) -> Unit) {
        this.mListener = listener
    }

    override fun getItemCount(): Int {
        return   mData.size
    }

    /**
     * 更新数据，替换原有数据
     */
    fun updateItems(list: MutableList<T>?) {
        mData = list ?: mutableListOf<T>()
        notifyDataSetChanged()
    }

    /**
     * 头部插入一条数据
     */
    fun addItem(item: T) {
        mData.add(0, item)
        notifyItemInserted(0)
    }

    /**
     * 任意位置插入一条数据
     */
    fun addItem(item: T, position: Int) {
        var position = position
        position = Math.min(position, mData.size)
        mData.add(position, item)
        notifyItemInserted(position)
    }

    /**
     * 在列表尾添加一串数据
     */
    fun addItems(items: List<T>?) {
        items?.apply {
            val start = mData.size
            mData.addAll(items)
            notifyItemRangeChanged(start, items.size)
        }
    }

    /**
     * 移除一条数据
     */
    fun removeItem(position: Int) {
        if (position > mData.size - 1) {
            return
        }
        mData.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * 更新一条数据
     * 注：需更改数据源
     */
    fun updateItem(position: Int) {
        if (position > mData.size - 1) {
            return
        }
        notifyItemChanged(position/*,mData.get(position)*/)
    }


    /**
     * 清除所有数据
     */
    fun removeAllItems() {
        mData.clear()
        notifyDataSetChanged()
    }

    /**
     * 获取所有数据
     */
    fun getData():MutableList<T> {
       return mData
    }
}