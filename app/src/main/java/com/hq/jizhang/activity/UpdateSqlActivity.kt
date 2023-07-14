package com.hq.jizhang.activity

import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.flyco.tablayout.listener.OnTabSelectListener
import com.hq.jizhang.R
import com.hq.jizhang.base.BaseActivity
import com.hq.jizhang.base.BaseApplication
import com.hq.jizhang.base.BaseFragment
import com.hq.jizhang.bean.DetailSqlBean
import com.hq.jizhang.cons.CommonConstant
import com.hq.jizhang.fragment.UpdateDisburseFragment
import com.hq.jizhang.fragment.UpdateIncomeFragment
import kotlinx.android.synthetic.main.activity_updatesql.*


class UpdateSqlActivity : BaseActivity() {

    private var mListener : ((Int , Any) -> Unit)? = null
    private lateinit var fragmentList : MutableList<BaseFragment>

    override fun initView() : View {
        return View.inflate(this , R.layout.activity_updatesql , null)
    }

    override fun haveCommonTitle() : Boolean {
        return false
    }

    override fun initData() {
        intent.getSerializableExtra(CommonConstant.MainActivity.UPDATE_DATA)?.apply {
            var detailSqlBean = this as DetailSqlBean
            val titleList = mutableListOf("支出" , "收入")

            val updateDisburseFragment = UpdateDisburseFragment.createFragment(if (detailSqlBean.type==CommonConstant.MainActivity.TYPE_DISBURSE) detailSqlBean else null)
            val updateIncomeFragment = UpdateIncomeFragment.createFragment(if (detailSqlBean.type==CommonConstant.MainActivity.TYPE_INCOME) detailSqlBean else null)
            fragmentList = mutableListOf(updateDisburseFragment , updateIncomeFragment)
            updateSql_vp.offscreenPageLimit = titleList.size
            val barAdapter = BarAdapter(supportFragmentManager , titleList)
            updateSql_vp.adapter = barAdapter
            updateSql_tab.setViewPager(updateSql_vp)

            when (detailSqlBean.type) {
                CommonConstant.MainActivity.TYPE_INCOME   -> {
                    updateSql_tab.currentTab = 1
                }
                CommonConstant.MainActivity.TYPE_DISBURSE -> {
                    updateSql_tab.currentTab = 0
                }
            }
        }
    }

    override fun initEvent() {
        setOnClickListener(updateSql_tv_cancel)
    }

    override fun myOnClick(view : View) {
        when (view) {
            updateSql_tv_cancel -> finishHideInput()
        }
    }

    override fun onStart() {
        super.onStart()
        BaseApplication.disburseList.forEach { it.isSelect = false }
        BaseApplication.incomeList.forEach { it.isSelect = false }
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApplication.disburseList.forEach { it.isSelect = false }
        BaseApplication.incomeList.forEach { it.isSelect = false }
    }

    inner class BarAdapter(fm : FragmentManager , var data : MutableList<String>) : FragmentPagerAdapter(fm) {

        override fun getItem(position : Int) : BaseFragment {
            return fragmentList[position]
        }

        override fun getCount() : Int {
            return data.size
        }

        override fun getPageTitle(position : Int) : CharSequence {
            return data[position]
        }
    }

    fun setOnItemClickListener(listener : (position : Int , item : Any) -> Unit) {
        this.mListener = listener
    }

}