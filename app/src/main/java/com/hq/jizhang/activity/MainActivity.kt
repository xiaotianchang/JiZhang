package com.hq.jizhang.activity

import android.view.View
import com.hq.jizhang.R
import com.hq.jizhang.adapter.AdapterFragmentPager
import com.hq.jizhang.base.BaseActivity
import com.hq.jizhang.base.BaseApplication
import com.hq.jizhang.fragment.ChatFragment
import com.hq.jizhang.fragment.DetailFragment
import com.hq.jizhang.view.dialog.FullQuestionImgDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var adapterFragmentPager : AdapterFragmentPager
    private lateinit var fullQuestionImgDialog : FullQuestionImgDialog

    override fun initView() : View {
        return View.inflate(this , R.layout.activity_main , null)
    }

    override fun haveCommonTitle() : Boolean {
        return false
    }


    override fun initData() {
        adapterFragmentPager = AdapterFragmentPager(this)
        main_vp.adapter = adapterFragmentPager
        main_vp.offscreenPageLimit = 3
        main_vp.isUserInputEnabled = false
        fullQuestionImgDialog  = FullQuestionImgDialog(this)
    }

    override fun initEvent() {
        setOnClickListener(main_rb_detail , main_rb_bookkeeping , main_rb_chat)

        fullQuestionImgDialog.setOnItemClickListener { position, item ->
            if (main_vp.currentItem==2)
            (adapterFragmentPager.fragments[2] as ChatFragment).getChatData((adapterFragmentPager.fragments[2] as ChatFragment).mType)
        }
    }

    open fun showBookkeeping(){
        if (!fullQuestionImgDialog.isVisible){
            BaseApplication.disburseList.forEach { it.isSelect=false }
            BaseApplication.incomeList.forEach { it.isSelect=false }
            fullQuestionImgDialog.show(supportFragmentManager,"ABC")
        }
    }

    //刷新明细条目条目数据
    open fun refreshData(){
        (adapterFragmentPager.fragments[0] as DetailFragment).getItemData()
        (adapterFragmentPager.fragments[0] as DetailFragment).getMonthMoneyData()
    }

    //获取当前年月
    open fun getYearMonth(){
        (adapterFragmentPager.fragments[0] as DetailFragment).getItemData()
        (adapterFragmentPager.fragments[0] as DetailFragment).getMonthMoneyData()
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
                (adapterFragmentPager.fragments[2] as ChatFragment).getChatData((adapterFragmentPager.fragments[2] as ChatFragment).mType)
            }
        }
    }

}