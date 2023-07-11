package com.hq.jizhang.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hq.jizhang.base.BaseActivity
import com.trello.rxlifecycle2.components.support.RxFragment
import com.umeng.soexample.interfaces.ClickAction
import com.hq.jizhang.util.SpUtils
import org.jetbrains.anko.toast

/**
 * 创 建 人:  chang
 * 创建日期:  ${TATE}  16:43
 * 描    述:  ${TODO}
 */
abstract class BaseFragment : RxFragment(), ClickAction {
    private var lastClickTime: Long = 0
    private val continuousClickTime = 1500 //两次点击间隔不能少于1500ms
    private var  tempView: View? = null
    protected lateinit var mActivity: BaseActivity
    var language: String = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initEvent()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = initView()
        init(view)
        return view
    }

    protected abstract fun initView(): View
    protected open fun init(view: View) {
    }

    abstract fun initData()
    protected abstract fun initEvent()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as BaseActivity
    }

    override fun onClick(view: View) {
        if (tempView == view && System.currentTimeMillis() - lastClickTime < continuousClickTime) {
            return
        }
        lastClickTime = System.currentTimeMillis()
        tempView = view
        myOnclick(view)
    }


    fun showToast(message: String?) {
        mActivity.runOnUiThread {
            mActivity.toast(message + "")
        }
    }

    protected fun startActivityForResult(
        classActivity: Class<*>,
        requestCode: Int,
        intent: Intent? = Intent(mActivity, classActivity)
    ) {
        startActivityForResult(intent, requestCode)
    }

    protected abstract fun myOnclick(view: View)

}
