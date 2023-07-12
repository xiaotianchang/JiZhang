package com.hq.jizhang.base

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Handler
import com.hq.jizhang.R
import com.hq.jizhang.bean.TypeBean
import org.litepal.LitePal

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

        LitePal.initialize(this)


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
       // var debug = BuildConfig.DEBUG
        var isLogin: Boolean = false
        lateinit var instances: BaseApplication
        lateinit var mAppContext: Context

        val incomeList = mutableListOf(TypeBean("工资" , R.mipmap.home_notice),TypeBean("理财" , R.mipmap.home_notice)
        ,TypeBean("礼金" , R.mipmap.home_notice),TypeBean("其他" , R.mipmap.home_notice),TypeBean("兼职" , R.mipmap.home_notice))

        val disburseList = mutableListOf(TypeBean("日用",R.mipmap.home_notice),TypeBean("餐饮",R.mipmap.home_notice),
            TypeBean("购物",R.mipmap.home_notice),TypeBean("交通",R.mipmap.home_notice),TypeBean("娱乐",R.mipmap.home_notice),
            TypeBean("通讯",R.mipmap.home_notice),TypeBean("服饰",R.mipmap.home_notice),
            TypeBean("美容",R.mipmap.home_notice), TypeBean("住房",R.mipmap.home_notice),
            TypeBean("居家",R.mipmap.home_notice), TypeBean("孩子",R.mipmap.home_notice),
            TypeBean("长辈",R.mipmap.home_notice), TypeBean("社交",R.mipmap.home_notice),
            TypeBean("旅行",R.mipmap.home_notice), TypeBean("烟酒",R.mipmap.home_notice),
            TypeBean("数码",R.mipmap.home_notice), TypeBean("汽车",R.mipmap.home_notice),
            TypeBean("医疗",R.mipmap.home_notice), TypeBean("书籍",R.mipmap.home_notice),
            TypeBean("学习",R.mipmap.home_notice), TypeBean("宠物",R.mipmap.home_notice),
            TypeBean("礼金",R.mipmap.home_notice), TypeBean("礼物",R.mipmap.home_notice),
            TypeBean("办公",R.mipmap.home_notice), TypeBean("维修",R.mipmap.home_notice),
            TypeBean("捐赠",R.mipmap.home_notice), TypeBean("彩票",R.mipmap.home_notice),
            TypeBean("亲友",R.mipmap.home_notice), TypeBean("快递",R.mipmap.home_notice),
        )
    }
}
