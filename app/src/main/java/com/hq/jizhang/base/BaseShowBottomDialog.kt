package com.hq.jizhang.base

import android.app.Activity
import android.app.Dialog
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.hq.jizhang.R
import com.hq.jizhang.util.AppUtil

/**
 *
 */
abstract class BaseShowBottomDialog(context: Activity) {
    protected var mContext: Activity = context
    private val mDialog by lazy { Dialog(mContext, R.style.BaseDialogStyle) }
    protected var mListener: ((Int, Any) -> Unit)? = null
    private var mView: View? = null

    init {
        init()
    }

    private fun init() {
        mView = initView()
        mView?.apply {
            mDialog.setContentView(this)
            mDialog.window?.apply {
                setGravity(Gravity.BOTTOM)
                setWindowAnimations(R.style.dialog_showBottom_anim)
                setDimAmount(setMyDimAmount())
                mDialog.setCancelable(true)
                mDialog.setCanceledOnTouchOutside(true)
                mDialog.setOnDismissListener {
                    AppUtil.hideInput(mContext)
                }
                setLayout(setWidth(), setHeight())
                val wl = attributes
                //  wl.x = 0
                wl.y = 0
//                wl.width = ViewGroup.LayoutParams.MATCH_PARENT
//                wl.height = ViewGroup.LayoutParams.WRAP_CONTENT
                mDialog.onWindowAttributesChanged(wl) // 设置布局参数
            }
            inEvent(this)
        }
    }

    protected abstract fun inEvent(view: View)
    protected abstract fun initView(): View?
    protected open fun setWidth(width: Int = ViewGroup.LayoutParams.MATCH_PARENT): Int {
        return width
    }

    protected open fun setHeight(height: Int = ViewGroup.LayoutParams.WRAP_CONTENT): Int {
        return height
    }

    fun setOnItemClickListener(listener: (position: Int, data: Any) -> Unit) {
        this.mListener = listener
    }

    protected open fun setMyDimAmount(dimAmount: Float = 0.4f): Float {
        return dimAmount
    }

    //显示对话框
    fun showDialog() {
        if (!mContext.isFinishing && !mDialog.isShowing)
            mDialog.show()
    }

    //隐藏对话框
    fun dismissDialog() {
        if (!mContext.isFinishing && mDialog.isShowing) {
            AppUtil.hideInput(mContext)
            mDialog.dismiss()
        }
    }

    fun cancelDialog() {
        if (!mContext.isFinishing && mDialog.isShowing)
            mDialog.cancel()
    }

    fun setCancelable(Cancelable: Boolean) {
        mDialog.setCancelable(Cancelable)
    }

    fun setCancelOnTouchOutside(CancelOnTouchOutside: Boolean) {
        mDialog.setCanceledOnTouchOutside(CancelOnTouchOutside)
    }
}
