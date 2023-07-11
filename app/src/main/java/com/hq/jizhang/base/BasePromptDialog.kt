package com.hq.jizhang.base

import android.app.Activity
import android.app.Dialog
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.hq.jizhang.R
import com.hq.jizhang.util.AppUtil
import kotlinx.android.synthetic.main.dialog_prompt.*

/*
 * @创建者     肖天长
 * @创建时间   2022/12/01  10:50
 * @描述      常用提示框
 *
 */
class BasePromptDialog:Dialog{
    private var mActivity:Activity?=null

    constructor(activity : Activity) : super(activity) {
        initView()
        mActivity=activity
    }
    constructor(activity: Activity,theme : Int) : super(activity,theme) {
        initView()
        mActivity=activity
    }

    private fun initView() {
        setContentView(R.layout.dialog_prompt)
    }

    open fun showDialog() {
        mActivity?.let {
            if (! it.isFinishing && !this.isShowing)
                this.show()
        }
    }

    open fun myDismissDialog() {
        mActivity?.let {
            if (! it.isFinishing && this.isShowing){
                AppUtil.hideInput(it)
                this.dismiss()
            }
        }
    }

    class Builder(var activity: Activity,var theme : Int=R.style.BaseDialogStyle) {
        private var icon: Int? = 0
        private var title: String? = null
        private var content: String? = null
        private var dimAmount: Float = 0.4f
        private var gravity: Int = Gravity.CENTER
        private var titleColor: Int = 0
        private var contentColor: Int = 0
        private var cancelColor: Int = 0
        private var confirmColor: Int = 0
        private var centerAnimations: Int = R.style.IOSAnimStyle
        private var bottomAnimations: Int = R.style.dialog_showBottom_anim
        private var dialogWidth: Int = ViewGroup.LayoutParams.WRAP_CONTENT
        private var dialogHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT
        private var cancelable: Boolean = false
        private var tvCancelText: String? = null
        private var tvConfirmText: String? = null
        private var cancelIsVisibility: Boolean = true
        private var canceledOnTouchOutside: Boolean = false
        private var cancelListener: ((BasePromptDialog) -> Unit)? = null
        private var confirmListener: ((BasePromptDialog) -> Unit)? = null

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }
        fun setIcon(icon: Int): Builder {
            this.icon = icon
            return this
        }
        fun setContent(content: String): Builder {
            this.content = content
            return this
        }
        fun setCancelText(tvCancelText: String): Builder {
            this.tvCancelText = tvCancelText
            return this
        }
        fun setConfirmText(confirmText: String): Builder {
            this.tvConfirmText = confirmText
            return this
        }
        fun setCancelGone(): Builder {
            this.cancelIsVisibility = false
            return this
        }
        fun setCancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }
        fun setCanceledOnTouchOutside(canceledOnTouchOutside: Boolean): Builder {
            this.canceledOnTouchOutside = canceledOnTouchOutside
            return this
        }
        fun setCancelListener(listener: (BasePromptDialog) -> Unit): Builder{
            cancelListener=listener
            return this
        }
        fun setConfirmListener(listener: (BasePromptDialog) -> Unit): Builder{
            confirmListener=listener
            return this
        }
        fun setDimAmount(dimAmount: Float): Builder {
            this.dimAmount=dimAmount
            return this
        }
        fun setGravity(gravity: Int): Builder {
            this.gravity=gravity
            return this
        }
        fun setCenterAnimations(centerAnimations: Int): Builder {
            this.centerAnimations=centerAnimations
            return this
        }
        fun setBottomAnimations(bottomAnimations: Int): Builder {
            this.bottomAnimations=bottomAnimations
            return this
        }
        fun setWidth(width: Int ): Builder {
            this.dialogWidth=width
            return this
        }
        fun setHeight(height: Int ): Builder {
            this.dialogHeight=height
            return this
        }
        fun setTitleColor(color: Int ): Builder {
            this.titleColor=color
            return this
        }
        fun setContentColor(color: Int ): Builder {
            this.contentColor=color
            return this
        }
        fun setCancelColor(color: Int ): Builder {
            this.cancelColor=color
            return this
        }
        fun setConfirmColor(color: Int ): Builder {
            this.confirmColor=color
            return this
        }

        fun create(): BasePromptDialog {
            val dialog = BasePromptDialog(activity,theme)
            if (! TextUtils.isEmpty(title)) {
                dialog.dialog_prompt_tv_title.text = this.title
            } else {
                dialog.dialog_prompt_tv_title.visibility = View.GONE
            }

            dialog.dialog_prompt_tv_content?.text = this.content
            if (icon != 0) {
                //dialog.ivDialogIcon?.setImageResource(this.icon!!)
            }
            dialog.dialog_prompt_tv_confirm.text = tvConfirmText ?: "确认"
            if (cancelIsVisibility) {
                dialog.dialog_prompt_tv_cancel.text = this.tvCancelText ?: "取消"
            } else if (TextUtils.isEmpty(dialog.dialog_prompt_tv_cancel.text)){
                dialog.dialog_prompt_tv_cancel.visibility = View.GONE
                dialog.dialog_prompt_view_cancel.visibility = View.GONE
                dialog.dialog_prompt_tv_confirm.setBackgroundResource(R.drawable.selector_white_bottombtnbg)
            }else {
                dialog.dialog_prompt_tv_cancel.visibility = View.GONE
                dialog.dialog_prompt_view_cancel.visibility = View.GONE
                dialog.dialog_prompt_tv_confirm.setBackgroundResource(R.drawable.selector_white_bottombtnbg)
            }
            if (titleColor!=0)dialog.dialog_prompt_tv_title.setTextColor(ContextCompat.getColor(activity,titleColor))
            if (contentColor!=0)dialog.dialog_prompt_tv_content.setTextColor(ContextCompat.getColor(activity,contentColor))
            if (cancelColor!=0)dialog.dialog_prompt_tv_cancel.setTextColor(ContextCompat.getColor(activity,cancelColor))
            if (confirmColor!=0)dialog.dialog_prompt_tv_confirm.setTextColor(ContextCompat.getColor(activity,confirmColor))
            dialog.setCancelable(cancelable)
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
            dialog.setOnDismissListener {
                AppUtil.hideInput(activity)
                //onDismissListener?.onDismiss(true)
            }
            dialog.window?.apply {
                when(gravity){
                    Gravity.CENTER->{
                        setWindowAnimations(centerAnimations)
                    }
                    Gravity.BOTTOM->{
                         dialogWidth = ViewGroup.LayoutParams.MATCH_PARENT
                        setWindowAnimations(bottomAnimations)
                    }
                }
                setGravity(gravity)
                setDimAmount(dimAmount)
                setLayout(dialogWidth,dialogHeight)
            }
            dialog.dialog_prompt_tv_cancel.setOnClickListener { cancelListener?.invoke(dialog) }
            dialog.dialog_prompt_tv_confirm.setOnClickListener { confirmListener?.invoke(dialog) }
            return dialog
        }

    }
}