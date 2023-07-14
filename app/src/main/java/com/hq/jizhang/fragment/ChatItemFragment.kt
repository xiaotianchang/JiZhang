package com.hq.jizhang.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hq.jizhang.R
import com.hq.jizhang.adapter.ChatAdapter
import com.hq.jizhang.base.BaseFragment
import com.hq.jizhang.bean.ChatDataBean
import com.hq.jizhang.bean.ItemChatBean
import com.hq.jizhang.cons.CommonConstant
import com.hq.jizhang.util.AppUtil
import com.hq.jizhang.util.LogUtil
import com.hq.jizhang.util.StringUtil
import kotlinx.android.synthetic.main.fragment_chatitem.*
import kotlinx.android.synthetic.main.include_empty.view.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

/*
 * @创建者      肖天长
 * @创建时间    2022/05/30 16:31
 * @描述       图表
 *
 */
class ChatItemFragment(var chatDataBean : ChatDataBean , var type : String) : BaseFragment() {

    private lateinit var chatAdapter : ChatAdapter

    companion object {

        fun createFragment(chatDataBean : ChatDataBean , type : String) : ChatItemFragment {
            return ChatItemFragment(chatDataBean , type)
        }
    }

    override fun initView() : View {
        return View.inflate(mActivity , R.layout.fragment_chatitem , null)
    }

    override fun initData() {
        val emptyView = View.inflate(mActivity , R.layout.include_empty , null)
        emptyView.rcy_tv_to.text = "暂无数据"
        fg_chatItem_rcy.setEmptyView(emptyView)
        fg_chatItem_rcy.layoutManager = LinearLayoutManager(mActivity)
        chatAdapter = ChatAdapter(mActivity)
        fg_chatItem_rcy.adapter = chatAdapter

        getChatData()

    }

    private fun getChatData() { //type支出或者收入
        val max = Collections.max(chatDataBean.listItemDetailBean)
        val day = max.date.substring(max.date.lastIndexOf("/")+1,max.date.length).toBigDecimal()
        val average = chatDataBean.sumOfMonth.divide(day,2 , RoundingMode.HALF_UP)
        LogUtil.logD(day.toString() + "当月日期最大的那个" + max.toString())
        fg_chatItem_average.text = StringUtil.getString("平均值: " + AppUtil.formatToNumber(average))

        if (CommonConstant.MainActivity.TYPE_DISBURSE == type) {
            fg_chatItem_typeMoney.text = StringUtil.getString("总支出: " + AppUtil.formatToNumber(chatDataBean.sumOfMonth))
        } else {
            fg_chatItem_typeMoney.text = StringUtil.getString("总收入: " + AppUtil.formatToNumber(chatDataBean.sumOfMonth))
        }
        var nameTypeMoneyMap = hashMapOf<String , BigDecimal>() //每个月每种类型的金额
        var nameTypeMoneyList = mutableListOf<ItemChatBean>()

        chatDataBean.listItemDetailBean.forEach { yearMonth -> //每种类型种消费
            if (yearMonth.type == type) {
                if (nameTypeMoneyMap.containsKey(yearMonth.name)) {
                    val bigDecimal = nameTypeMoneyMap[yearMonth.name]
                    val sum = bigDecimal?.add(BigDecimal(yearMonth.money))
                    sum?.apply {
                        nameTypeMoneyMap[yearMonth.name + ""] = this
                    }
                } else {
                    nameTypeMoneyMap[yearMonth.name + ""] = BigDecimal(yearMonth.money)
                }
            }
        }
        nameTypeMoneyMap.keys.forEach {
            val percentage = nameTypeMoneyMap[it]?.divide(chatDataBean.sumOfMonth , 2 , RoundingMode.HALF_UP)
                ?.multiply(BigDecimal(100))
            LogUtil.logD("每个月每种类型的金额占比" + nameTypeMoneyMap[it] + "===" + chatDataBean.sumOfMonth + "==" + percentage)
            nameTypeMoneyList.add(ItemChatBean(it , AppUtil.formatToNumber(nameTypeMoneyMap[it]) , type , percentage))
        }
        chatAdapter.updateItems(nameTypeMoneyList)
        LogUtil.logD("每个月每种类型的金额" + nameTypeMoneyList.toString())
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
