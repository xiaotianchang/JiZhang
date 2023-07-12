package com.hq.jizhang.activity

import android.view.View
import com.hq.jizhang.R
import com.hq.jizhang.adapter.AdapterFragmentPager
import com.hq.jizhang.base.BaseActivity
import com.hq.jizhang.bean.DetailSqlBean
import com.hq.jizhang.bean.ItemDetailBean
import com.hq.jizhang.util.LogUtil
import com.hq.jizhang.view.dialog.FullQuestionImgDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.litepal.LitePal

class MainActivity : BaseActivity() {

    private lateinit var fullQuestionImgDialog : FullQuestionImgDialog

    override fun initView() : View {
        return View.inflate(this , R.layout.activity_main , null)
    }

    override fun haveCommonTitle() : Boolean {
        return false
    }

    override fun onStart() {
        super.onStart()
        val mutableListOf = mutableListOf<ItemDetailBean>()
        val findAll = LitePal.findAll(DetailSqlBean::class.java)

        findAll.forEach {detail->
            val itemDetailBean = ItemDetailBean()
            val sameDate = findAll.filter { detail.date == it.date }.toMutableList()
            itemDetailBean.listItemDetailBean=sameDate
            itemDetailBean.date=detail.date
            itemDetailBean.week=detail.week
            itemDetailBean.type=detail.type
            mutableListOf.add(itemDetailBean)

            LogUtil.logD("=="+detail.toString())
        }

        mutableListOf.forEach {
            LogUtil.logD("================"+it.toString())
        }
    }

    override fun initData() {
        main_vp.adapter = AdapterFragmentPager(this)
        main_vp.offscreenPageLimit = 3
        main_vp.isUserInputEnabled = false
        fullQuestionImgDialog  = FullQuestionImgDialog()
    }

    override fun initEvent() {
        setOnClickListener(main_rb_detail , main_rb_bookkeeping , main_rb_chat)
    }

    open fun showBookkeeping(){
        if (!fullQuestionImgDialog.isVisible)
        fullQuestionImgDialog.show(supportFragmentManager,"ABC")
    }

    override fun myOnClick(view : View) {
        when (view) {
            main_rb_detail -> {
                main_vp.currentItem=0
            }
            main_rb_bookkeeping -> {
                showBookkeeping()
            }
            main_rb_chat -> {
                main_vp.currentItem=2
            }
        }
    }

}