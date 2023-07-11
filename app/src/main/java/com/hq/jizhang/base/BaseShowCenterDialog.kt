package com.hq.jizhang.base

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.widget.LinearLayout
import com.hq.jizhang.R
import com.hq.jizhang.util.AppUtil
import com.hq.jizhang.util.ResourceUtil

/*
 * @创建者     肖天长
 * @创建时间   2022/12/01  10:50
 * @描述       基类提示框
 *
 */
abstract class BaseShowCenterDialog<T>(var activity: Activity,var theme:Int= R.style.BaseDialogStyle) : Dialog(activity, theme), View.OnClickListener{
    var mType:Int=0
    var mData:T?=null
    var mListener: ((Int, Any?) -> Unit)? = null
    open lateinit var mView: View

    init {
      myInit()
    }

    private fun myInit(){
        mView = initView()
        setContentView(mView, getLayoutParams())
        //  window!!.setWindowAnimations(R.styl.IOSAnimStyle)
        inEvent()
        setCancelable(false)
        window?.setDimAmount(.4f)
        setCanceledOnTouchOutside(false)
    }

    abstract fun inEvent()
    abstract fun initView(): View
    abstract fun refreshHolderView(data: T,type:Int)

    fun setDataAndRefreshHolderView(data: T?, type:Int=0) {
        //接收数据保存到成员变量里面
        mType=type
        data?.let {
            mData = data
            //绑定数据(view+data)
            refreshHolderView(data,type)
        }
    }
/*
    protected inline fun <reified T : MutableList<T>> setDataAndRefreshHolderView(data: T) {
        //绑定数据(view+data)
        refreshHolderView(data)
    }
*/

    fun setOnItemClickListener(listener: (position: Int, data: Any?) -> Unit) {
        this.mListener = listener
    }

    //显示对话框
    open fun showDialog() {
        if ( !activity.isFinishing && !isShowing) {
            show()
        }
    }

    //隐藏对话框
    fun dismissDialog() {
        if ( !activity.isFinishing && isShowing) {
            AppUtil.hideInputs(activity, mView)
            dismiss()
        }
    }

    protected open fun getLayoutParams(size: Float = 0.9f): LinearLayout.LayoutParams {
        val width: Int = (ResourceUtil.getScreenWidth(activity) * size).toInt()
        return LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT)
    }
}
