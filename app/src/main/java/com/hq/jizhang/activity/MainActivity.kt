package com.hq.jizhang.activity

import android.view.View
import com.hq.jizhang.R
import com.hq.jizhang.adapter.AdapterFragmentPager
import com.hq.jizhang.base.BaseActivity
import com.hq.jizhang.view.dialog.FullQuestionImgDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var fullQuestionImgDialog : FullQuestionImgDialog
    override fun initView() : View {
        return View.inflate(this , R.layout.activity_main , null)
    }

    override fun haveCommonTitle() : Boolean {
        return false
    }

    override fun initData() {
        main_vp.adapter = AdapterFragmentPager(this)
        main_vp.offscreenPageLimit = 3
        main_vp.isUserInputEnabled = false
        fullQuestionImgDialog  = FullQuestionImgDialog(this , supportFragmentManager)
    }

    override fun initEvent() {
        setOnClickListener(main_rb_detail , main_rb_bookkeeping , main_rb_chat)
    }

    override fun myOnClick(view : View) {
        when (view) {
            main_rb_detail -> {
                main_vp.currentItem=0
            }
            main_rb_bookkeeping -> {
                fullQuestionImgDialog.show()
            }
            main_rb_chat -> {
                main_vp.currentItem=2
            }
        }
    }

}