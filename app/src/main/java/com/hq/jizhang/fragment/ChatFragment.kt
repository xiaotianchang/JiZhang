package com.hq.jizhang.fragment

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hq.jizhang.R
import com.hq.jizhang.base.BaseFragment
import com.hq.jizhang.bean.ChatDataBean
import com.hq.jizhang.bean.DetailSqlBean
import com.hq.jizhang.cons.CommonConstant
import com.hq.jizhang.util.LogUtil
import kotlinx.android.synthetic.main.activity_basetitle.*
import kotlinx.android.synthetic.main.fragment_chat.*
import okhttp3.internal.filterList
import org.litepal.LitePal
import java.math.BigDecimal

/*
 * @创建者      肖天长
 * @创建时间    2022/05/30 16:31
 * @描述       图表
 *
 */
class ChatFragment : BaseFragment() {

    private lateinit var barAdapter : BarAdapter
    private lateinit var fragmentList : MutableList<BaseFragment>
    private lateinit var yearMonthList : MutableList<ChatDataBean>
    open var mType=CommonConstant.MainActivity.TYPE_DISBURSE

    companion object {
        fun createFragment() : ChatFragment {
            return ChatFragment()
        }
    }

    override fun initView() : View {
        return View.inflate(mActivity , R.layout.fragment_chat, null)
    }

    override fun initData() {
        baseTitle_tv_title.text="图表"
        baseTitle_tv_right.text="收入"

        barAdapter = BarAdapter(childFragmentManager)
        getChatData(CommonConstant.MainActivity.TYPE_DISBURSE)
       // fg_chat_vp.offscreenPageLimit = yearMonthList.size

    }

    override fun initEvent() {
        setOnClickListener(baseTitle_tv_right)
    }

    override fun myOnclick(view : View) {
        when (view) {
            baseTitle_tv_right->{
                if (baseTitle_tv_right.text=="收入"){
                    baseTitle_tv_right.text="支出"
                    mType=CommonConstant.MainActivity.TYPE_INCOME
                }else{
                    baseTitle_tv_right.text="收入"
                    mType=CommonConstant.MainActivity.TYPE_DISBURSE
                }
                getChatData(mType)
            }
        }
    }

    open fun getChatData(type:String){
        yearMonthList = mutableListOf()

        val findAll = LitePal.findAll(DetailSqlBean::class.java)
        findAll.forEach {all->
            val filterList = findAll.filterList { this.yearMonth==all.yearMonth && this.type== type}.toMutableList()//过滤出每个月

            if (yearMonthList.find { it.yearMonth==all.yearMonth }==null){
                if (!filterList.isNullOrEmpty()){ //收入或支出有数据才装
                    val chatDataBean = ChatDataBean()
                    chatDataBean.yearMonth=all.yearMonth
                    chatDataBean.sumOfMonth=filterList.sumOf { it.money.toBigDecimal() } //每月支出或收入总消费
                    chatDataBean.listItemDetailBean=filterList

                    yearMonthList.add(chatDataBean)
                }
            }
        }
        yearMonthList.forEach {
            LogUtil.logD("${it.yearMonth}月图表数据"+it.toString())
        }

        fragmentList = mutableListOf()
        yearMonthList.forEach {
            fragmentList.add(ChatItemFragment.createFragment(it,type))
        }

        //改了数据需要刷新
        fg_chat_vp.adapter = barAdapter
        fg_chat_tab.setViewPager(fg_chat_vp)
        barAdapter.notifyDataSetChanged()

    }

    inner class BarAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position : Int) : BaseFragment {
            return fragmentList[position]
        }

        override fun getCount() : Int {
            return fragmentList.size
        }

        override fun getPageTitle(position : Int) : CharSequence {
            return yearMonthList[position].yearMonth
        }
    }

}
