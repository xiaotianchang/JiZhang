package com.hq.jizhang.activity

import android.content.Intent
import android.os.Handler
import android.view.View
import com.hq.jizhang.R
import com.hq.jizhang.base.BaseActivity
import org.jetbrains.anko.startActivity

class StartActivity : BaseActivity() {

    override fun haveCommonTitle() : Boolean {
        return false
    }

    override fun initView() : View {
        return View.inflate(this , R.layout.activity_start , null)
    }

    override fun initData() {
        Handler(mainLooper).postDelayed({
            startActivity<MainActivity>()
            finish()
        } , 2000)
    }

    override fun initEvent() {
    }

    override fun myOnClick(view : View) {
    }
}