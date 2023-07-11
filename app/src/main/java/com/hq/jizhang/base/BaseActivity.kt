package com.hq.jizhang.base

import android.content.BroadcastReceiver
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.gyf.immersionbar.ImmersionBar
import com.hq.jizhang.R
import com.hq.jizhang.util.AppManagerUtil
import com.hq.jizhang.util.AppUtil
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.umeng.soexample.interfaces.ClickAction
import com.umeng.soexample.interfaces.KeyboardAction
import kotlinx.android.synthetic.main.activity_baselayout.*
import kotlinx.android.synthetic.main.activity_basetitle.*
import org.jetbrains.anko.toast

/*
 * @创建者     肖天长
 * @创建时间   2020/1/15  10:50
 * @描述      自定义基类
 *
 */
abstract class BaseActivity : RxAppCompatActivity(), ClickAction, KeyboardAction{
    var language: String = ""
    var lastScreenOff = false
    private var lastBackground = false
    private var lastClickTime: Long = 0
    public var liveTime = 5 //后台存活时间/分钟
    private val continuousClickTime = 1500 //两次点击间隔不能少于1500ms
    private var deviceKeyReceiver: BroadcastReceiver? = null
    private var tempView: View? = null
    lateinit var mBaseTitleIvLeft: ImageView
    lateinit var mBaseTitleTvLeft: TextView
    lateinit var mBaseTitleTvTitle: TextView
    lateinit var mBaseTitleTvRight: TextView
    lateinit var mBaseLayoutFl: FrameLayout
    lateinit var mBaseTitleRl: RelativeLayout
    private lateinit var mBaseTitleIvRight: ImageView


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        AppManagerUtil.addActivity(this)
        if (isScreenPortrait())
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)//设置强制竖屏
        val inflate = View.inflate(this , R.layout.activity_baselayout , null)
        setContentView(inflate)
        mBaseTitleIvLeft = baseTitle_iv_left
        mBaseTitleTvLeft = baseTitle_tv_left
        mBaseTitleTvRight = baseTitle_tv_right
        mBaseTitleIvRight = baseTitle_iv_right
        mBaseTitleTvTitle = baseTitle_tv_title
        mBaseTitleRl = baseTitle_rl as RelativeLayout
        mBaseLayoutFl = baseLayout_fl
        mBaseLayoutFl.addView(initView())
        mBaseTitleIvLeft.setOnClickListener(this)
        mBaseTitleTvRight.setOnClickListener(this)
        mBaseTitleRl.visibility = if (haveCommonTitle()) View.VISIBLE else View.GONE
        if (isImmersionStatusBar()) createStatusBarConfig().init()

        initData()
        initEvent()

    }


    protected abstract fun initView(): View

    protected abstract fun initData()

    protected abstract fun initEvent()

    protected open fun haveCommonTitle(): Boolean = true //默认所有标题样式一样

    protected open fun isScreenPortrait(): Boolean = true //是否竖屏展示

    protected open fun isImmersionStatusBar(): Boolean = true //是否要沉浸式状态栏


    /**
     * 初始化沉浸式状态栏
     */
    protected open fun createStatusBarConfig() : ImmersionBar {
        return ImmersionBar.with(this) // 默认状态栏字体颜色为黑色
            .statusBarDarkFont(isStatusBarDarkFont()) // 指定导航栏背景颜色
            .navigationBarColor(R.color.white) // 状态栏字体和导航栏内容自动变色，必须指定状态栏颜色和导航栏颜色才可以自动变色
            .autoDarkModeEnable(true , 0.2f)
            .keyboardEnable(true)
    }

    /**
     * 状态栏字体深色模式
     */
    protected open fun isStatusBarDarkFont() : Boolean {
        return true
    }

    protected fun finishHideInput() {
        AppUtil.hideInput(this)
        finish()
    }

    /**
     * 和 setContentView 对应的方法
     */
    open fun getContentView() : ViewGroup? {
        return findViewById(Window.ID_ANDROID_CONTENT)
    }

    /**
     * 初始化软键盘
     */
    protected open fun initSoftKeyboard() {
        // 点击外部隐藏软键盘，提升用户体验
        getContentView()?.setOnClickListener{
            // 隐藏软键，避免内存泄漏
            hideKeyboard(currentFocus)
        }
    }

    fun showToast(message : String?) {
        runOnUiThread {
            toast(message + "")
        }
    }

    override fun onClick(view : View) {
        if (tempView == view && System.currentTimeMillis() - lastClickTime < continuousClickTime) {
            return
        }
        lastClickTime = System.currentTimeMillis()
        tempView = view
        when (view.id) {
            R.id.baseTitle_iv_left -> {
                AppUtil.hideInput(this)
                finish()
            }
            R.id.baseTitle_tv_right -> myOnClick(view)
        }
        myOnClick(view)
    }

    protected abstract fun myOnClick(view : View)
    override fun onDestroy() {
        tempView = null
        AppManagerUtil.finishActivity(this)
        // mImmersionBar.destroy()  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        super.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        lastBackground = AppUtil.isApplicationInBackground(this)
    }

    override fun onStart() {
        super.onStart()
        lastBackground = false
    }
}
