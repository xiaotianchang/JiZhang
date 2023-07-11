package com.umeng.soexample.util

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.hq.jizhang.R
import com.hq.jizhang.base.BaseApplication

/**
 * @Description 通用Toast提示
 * @Author 一花一世界
 */
class ToastUtil private constructor(toast: Toast?) {
    private val internalToast: Toast?

    @JvmOverloads
    fun show(cancelCurrent: Boolean = true) {
        if (cancelCurrent && globalBoast != null) {
            globalBoast!!.cancel()
        }
        internalToast?.show()
        globalBoast = this
    }

    private fun cancel() {
        internalToast?.cancel()
    }

    companion object {
        @Volatile
        private var globalBoast: ToastUtil? = null
        private const val posY = 300
        val context: Context
            get() = BaseApplication.mAppContext

        fun showText(text: CharSequence?) {
            makeText(context, text, Toast.LENGTH_SHORT)
                .show()
        }

        fun showText(text: CharSequence?, duration: Int) {
            makeText(context, text, duration).show()
        }

        @Throws(NotFoundException::class)
        fun showText(resId: Int) {
            makeText(
                context,
                resId,
                Toast.LENGTH_SHORT
            ).show()
        }

        @Throws(NotFoundException::class)
        fun showText(resId: Int, duration: Int) {
            makeText(context, resId, duration).show()
        }

        fun showView(view: View?) {
            makeView(context, view, Toast.LENGTH_SHORT)
                .show()
        }

        fun showView(view: View?, duration: Int,gravity: Int=Gravity.CENTER) {
            makeView(context, view, duration,gravity).show()
        }

        private fun makeText(
            context: Context?,
            text: CharSequence?,
            duration: Int
        ): ToastUtil {
            return ToastUtil(innerCreate(context, text, duration))
        }

        @Throws(NotFoundException::class)
        fun makeText(context: Context?, resId: Int, duration: Int): ToastUtil {
            return ToastUtil(innerCreate(context, resId, duration))
        }

        private fun makeView(
            context: Context?,
            vContent: View?,
            duration: Int,
            gravity: Int=Gravity.CENTER
        ): ToastUtil {
            return ToastUtil(innerCreate(context, vContent, duration,gravity))
        }

        private fun innerCreate(
            context: Context?,
            info: CharSequence?,
            duration: Int
        ): Toast? {
            var returnValue: Toast? = null
            if (isContext()) {
                val inflater = LayoutInflater.from(context)
                val vContent: View = inflater.inflate(R.layout.view_toast, null)
                val mTvContent =
                    vContent.findViewById<View>(R.id.tv_content) as TextView
                mTvContent.text = info
                mTvContent.bringToFront()
                returnValue = Toast(context)
               // returnValue.setGravity(gravity, 60, posY)
                returnValue.duration = duration
                returnValue.view = vContent
            }
            return returnValue
        }

        private fun innerCreate(
            context: Context?,
            resId: Int,
            duration: Int
        ): Toast? {
            var returnValue: Toast? = null
            if (isContext()) {
                returnValue = innerCreate(
                    context,
                    context!!.resources.getString(resId),
                    duration
                )
            }
            return returnValue
        }

        private fun innerCreate(
            context: Context?,
            vContent: View?,
            duration: Int,
            gravity: Int=Gravity.CENTER,
            ): Toast? {
            var returnValue: Toast? = null
            if (isContext() && vContent != null) {
                returnValue = Toast(context)
                returnValue.setGravity(gravity, 0, 0)
                returnValue.duration = duration
                returnValue.view = vContent
            }
            return returnValue
        }

        private fun isContext(): Boolean {
            return context != null
        }

        fun showText(ErrorCodeMessage: Unit) {

        }
    }

    init {
        if (toast == null) {
            throw NullPointerException("Toast requires a non-null parameter.")
        }
        internalToast = toast
    }
}