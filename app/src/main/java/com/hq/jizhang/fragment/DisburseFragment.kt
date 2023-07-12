package com.hq.jizhang.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.hq.jizhang.R
import com.hq.jizhang.adapter.IncomeAdapter
import com.hq.jizhang.base.BaseFragment
import com.hq.jizhang.bean.TypeBean
import com.hq.jizhang.view.dialog.FullQuestionImgDialog
import kotlinx.android.synthetic.main.fragment_income.*

/*
 * @创建者      肖天长
 * @创建时间    2022/05/30 16:31
 * @描述       支出
 *
 */
class DisburseFragment() : BaseFragment() {

    companion object {
        fun createFragment(fullQuestionImgDialog : FullQuestionImgDialog) : DisburseFragment {
            return DisburseFragment()
        }
    }

    override fun initView() : View {
        return View.inflate(mActivity , R.layout.fragment_income, null)
    }

    override fun initData() {
        val incomeList = mutableListOf<TypeBean>()
        incomeList.add(TypeBean("日用",R.mipmap.home_notice))
        incomeList.add(TypeBean("餐饮",R.mipmap.home_notice))
        incomeList.add(TypeBean("购物",R.mipmap.home_notice))
        incomeList.add(TypeBean("交通",R.mipmap.home_notice))
        incomeList.add(TypeBean("娱乐",R.mipmap.home_notice))
        incomeList.add(TypeBean("通讯",R.mipmap.home_notice))
        incomeList.add(TypeBean("服饰",R.mipmap.home_notice))
        incomeList.add(TypeBean("美容",R.mipmap.home_notice))
        incomeList.add(TypeBean("住房",R.mipmap.home_notice))
        incomeList.add(TypeBean("居家",R.mipmap.home_notice))
        incomeList.add(TypeBean("孩子",R.mipmap.home_notice))
        incomeList.add(TypeBean("长辈",R.mipmap.home_notice))
        incomeList.add(TypeBean("社交",R.mipmap.home_notice))
        incomeList.add(TypeBean("旅行",R.mipmap.home_notice))
        incomeList.add(TypeBean("烟酒",R.mipmap.home_notice))
        incomeList.add(TypeBean("数码",R.mipmap.home_notice))
        incomeList.add(TypeBean("汽车",R.mipmap.home_notice))
        incomeList.add(TypeBean("医疗",R.mipmap.home_notice))
        incomeList.add(TypeBean("书籍",R.mipmap.home_notice))
        incomeList.add(TypeBean("学习",R.mipmap.home_notice))
        incomeList.add(TypeBean("宠物",R.mipmap.home_notice))
        incomeList.add(TypeBean("礼金",R.mipmap.home_notice))
        incomeList.add(TypeBean("礼物",R.mipmap.home_notice))
        incomeList.add(TypeBean("办公",R.mipmap.home_notice))
        incomeList.add(TypeBean("维修",R.mipmap.home_notice))
        incomeList.add(TypeBean("捐赠",R.mipmap.home_notice))
        incomeList.add(TypeBean("彩票",R.mipmap.home_notice))
        incomeList.add(TypeBean("亲友",R.mipmap.home_notice))
        incomeList.add(TypeBean("快递",R.mipmap.home_notice))
        fg_income_rcy.layoutManager= GridLayoutManager(mActivity,4)
        val incomeAdapter = IncomeAdapter(mActivity)
        fg_income_rcy.adapter=incomeAdapter
        incomeAdapter.updateItems(incomeList)
    }

    override fun initEvent() {
        setOnClickListener()
    }

    override fun myOnclick(view : View) {
        when (view) {
           /* fg_notice_tv_transferMoney->{
                mActivity.startActivity<NoticeListActivity>(MainConstant.NoticeActivity.NOTICE to MainConstant.NoticeActivity.NOTICE_NOTICE,
                    MainConstant.NoticeActivity.NOTICE_TITLE to getString(R.string.transferMoney))
            }*/
        }
    }
}
