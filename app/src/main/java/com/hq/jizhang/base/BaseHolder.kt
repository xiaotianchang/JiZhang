package com.hq.jizhang.base

import android.app.Activity
import android.view.View


/**
 * 创建者     肖天长
 * 创建时间   2016/1/26 09:37
 * 描述	     1.提供视图
 * 描述	     2.接收数据
 * 描述	     3.绑定数据
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-26 09:50:14 +0800 (星期二, 26 一月 2016) $
 * 更新描述   ${TODO}
 */
abstract class BaseHolder<HOMEBEANTYPE>(var mActivity : Activity) : View.OnClickListener {
    var mHolderView: View?=null//持有的视图,所能提供的视图,根视图
    var mData: HOMEBEANTYPE?=null
    private var mTempView: View? = null
    private var mListener: ((String) -> Unit)? = null

    //全局定义
    private var lastClickTime: Long = 0

    // 两次点击间隔不能少于1000ms
    private val delayTime = 500
    init {
        mHolderView = initHolderView()
        mHolderView?.let {
            initAction(it)
        }
    }

    fun setDataAndRefreshHolderView(data: HOMEBEANTYPE) {
        //接收数据保存到成员变量里面
        data?.let {
            mData = it
            mHolderView.apply {
                refreshHolderView(it)
            }
        }
    }

    override fun onClick(view: View) {
        if (mTempView == view && System.currentTimeMillis() - lastClickTime < delayTime) {
            return
        }
        lastClickTime = System.currentTimeMillis();
        mTempView = view
        myOnClick(view)
    }

    fun setOnClickListener(listener: (String) -> Unit) {
        this.mListener = listener
    }

    abstract fun initHolderView(): View
    abstract fun initAction(view: View)

    protected abstract fun myOnClick(view: View)

    abstract fun refreshHolderView(data: HOMEBEANTYPE)

}
