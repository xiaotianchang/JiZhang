package com.hq.jizhang.util

import android.app.Activity
import java.util.*

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
class AppManagerUtil {

    companion object {

        private val activityStack: ArrayList<Activity> by lazy { ArrayList<Activity>() }

        /**
         * 获取堆栈中最后一个压入的
         */
        val lastActivity: Activity?
            get() = if (activityStack.size > 0) { activityStack[activityStack.size - 1] } else null

        /**
         * 添加Activity到堆栈
         */
        fun addActivity(activity: Activity): ArrayList<Activity> {
            activityStack.add(activity)
            return activityStack
        }

        /**
         * 结束当前Activity
         */
        fun finishActivity(activity: Activity?) {
            activity?.let {
                activityStack.remove(it)
            }
        }

        /**
         * 结束指定类名的Activity
         */
        fun finishActivity(cls: Class<*>) {
            activityStack.find { it.javaClass == cls }?.apply {
                activityStack.remove(this)
                finish()
            }
        }

        /**
         * 结束所有Activity
         */
        fun finishAllActivity(localClassName: String = "") {
            activityStack.forEach { activity ->
                if (activity.localClassName != localClassName)
                    activity.finish()
            }
            activityStack.clear()
        }

        /**
         * 获取activity数量
         */
        fun activityStackSize(): Int {
            return activityStack.size
        }

        /**
         * 获取所有Activity
         */
        fun getAllActivity():ArrayList<Activity> {
            return activityStack
        }
    }
}