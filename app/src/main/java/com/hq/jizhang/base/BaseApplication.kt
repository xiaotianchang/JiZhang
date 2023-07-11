package com.hq.jizhang.base

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Handler

/*
 * @创建者     肖天长
 * @创建时间   2020/12/15  20:43
 * @描述      应用程序类
 *
 */
open class BaseApplication : Application() {

    /**
     * 得到主线程的handler
     */
    val handler by lazy { Handler() }

    /**
     * 得到主线程的id
     */
    private val mainThreadId by lazy { android.os.Process.myTid().toLong() }

    override fun onCreate() {
        super.onCreate()
        instances = this
        mAppContext = this

    }


    /**
     * 得到主线程的handler
     */
    fun getHandlers(): Handler {
        return handler
    }

    /**
     * 得到主线程的id
     */
    fun getMainThreadIds(): Long {
        return mainThreadId
    }

    companion object {
        var isMemorialDay : Boolean?=false
       // var debug = BuildConfig.DEBUG
        var isLogin: Boolean = false
        lateinit var instances: BaseApplication
        lateinit var mAppContext: Context
        var custId: String = ""
    }
}
