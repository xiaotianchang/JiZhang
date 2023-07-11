package com.hq.jizhang.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by achang on 2018/11/27.
 */

class EmptyRecyclerView : RecyclerView {
    private var emptyView: View? = null

    private val observer = object : AdapterDataObserver() {
       override fun onChanged() {
            checkIfEmpty()
        }

        override   fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }

        override  fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override  fun setAdapter( adapter:Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.let {
         it.unregisterAdapterDataObserver(observer)
        }
        super.setAdapter(adapter)
        adapter?.let {
           it.registerAdapterDataObserver(observer)
        }
        checkIfEmpty()
    }
    //设置没有内容时，提示用户的空布局
    fun setEmptyView(emptyView: View) {
        this.emptyView = emptyView
        checkIfEmpty()
    }

    private fun checkIfEmpty() {
        if (emptyView != null && adapter != null) {
            val emptyViewVisible = adapter !!.itemCount == 0
            emptyView!!.visibility = if (emptyViewVisible) VISIBLE else GONE
            visibility = if (emptyViewVisible) GONE else VISIBLE
        }
    }
}
