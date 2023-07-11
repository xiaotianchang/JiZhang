package com.hq.jizhang.util

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.hq.jizhang.base.BaseApplication


/*
 * @创建者     肖天长
 * @创建时间   2020/1/15  11:39
 * @描述      资源工具类
 *
 */ class ResourceUtil {

    companion object {

        /**
         * 得到Resource对象
         */
        val resources : Resources
            get() = BaseApplication.mAppContext.resources

        /**
         * 得到应用程序的包名
         *
         * @return
         */
        val packageName : String
            get() = BaseApplication.mAppContext.packageName

        /**
         * 得到String.xml中的字符
         */
        fun getString(resId : Int) : String {
            return resources.getString(resId)
        }

        /**
         * 得到color.xml中的颜色信息
         */
        fun getColor(resId : Int) : Int {
            return ContextCompat.getColor(BaseApplication.mAppContext , resId)
        }

        /**
         * 安全的执行一个任务
         * 1.当前任务所在线程子线程-->使用消息机制发送到主线程执行
         * 2.当前任务所在线程主线程-->直接执行
         */
        fun postTask(task : Runnable) {
            //得到当前线程的线程id
            val curThreadId = android.os.Process.myTid().toLong()
            val mainThreadId = BaseApplication.instances.getMainThreadIds()
            if (curThreadId == mainThreadId) { //主线程
                task.run()
            } else { //子线程
                BaseApplication.instances.getHandlers().post(task)
            }

            BaseApplication.instances
        }

        /**
         * dp-->px
         *
         * @param dip
         * @return
         */
        inline fun dp2Px(dip : Int) : Float {
            //px和dp倍数关系
            var scale= resources.displayMetrics.density;
            return (dip * scale + .5f)
        }

        /**
         * dp-->px
         *
         * @param dip
         * @return
         */
        inline fun dp2PxInt(dpValue : Int) : Int {
            //px和dp倍数关系
            var scale= resources.displayMetrics.density;
            return  (dpValue * scale + 0.5f).toInt()
        }

        /**
         * px-->dp
         *
         * @param px
         * @return
         */
        fun px2Dip(px : Int) : Int {
            //px/dp = density ①
            //px和dp倍数关系
            val density = resources.displayMetrics.density
            return (px / density + .5f).toInt()
        }

        /**
         * sp转换成px
         */
        public fun sp2px(spValue : Int): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP , spValue.toFloat() , resources.displayMetrics)

        }

        /**
         * px转换成sp
         */
        fun px2sp(pxValue : Int) : Float {
            val fontScale = Resources.getSystem().displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f)
        }

        /**
         * 得到设备屏幕的宽度
         */
        fun getScreenWidth(context : Context) : Int {
            return context.resources.displayMetrics.widthPixels
        }

        /**
         * 得到设备屏幕的高度
         */
        fun getScreenHeight(context : Context) : Int {
            return context.resources.displayMetrics.heightPixels
        }

        /**
         * 得到设备的密度
         */
        fun getScreenDensity(context : Context) : Float {
            return context.resources.displayMetrics.density
        }
    }
}
