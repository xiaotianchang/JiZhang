package com.hq.jizhang.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import com.hq.jizhang.R
import com.hq.jizhang.activity.MainActivity
import com.hq.jizhang.adapter.DetailAdapter
import com.hq.jizhang.base.BaseApplication
import com.hq.jizhang.base.BaseFragment
import com.hq.jizhang.bean.DetailSqlBean
import com.hq.jizhang.bean.ItemDetailBean
import com.hq.jizhang.cons.CommonConstant
import com.hq.jizhang.holder.RefreshLoadHolder
import com.hq.jizhang.util.DateUtil
import com.hq.jizhang.util.LogUtil
import com.hq.jizhang.util.SpannableStringUtil
import kotlinx.android.synthetic.main.fragment_detail.*
import org.litepal.LitePal
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat

/*
 * @创建者      肖天长
 * @创建时间    2022/05/30 16:31
 * @描述       明细
 *
 */
class DetailFragment : BaseFragment(), BGARefreshLayout.BGARefreshLayoutDelegate {

    private lateinit var refresh: BGARefreshLayout
    private lateinit var timePicker : TimePickerView
    private lateinit var detailAdapter : DetailAdapter
    private lateinit var simpleDateFormat: SimpleDateFormat

    companion object {

        fun createFragment() : DetailFragment {
            return DetailFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        getItemData()
        getMonthMoneyData()
    }


    override fun init(view : View) {
        super.init(view)
        refresh = view.findViewById(R.id.rcy_refresh)
        RefreshLoadHolder(mActivity, refresh, this)
    }

    override fun initView() : View {
        return View.inflate(mActivity , R.layout.fragment_detail , null)
    }

    @SuppressLint("SetTextI18n" , "SimpleDateFormat")
    override fun initData() {
        initTimePicker()
        simpleDateFormat = SimpleDateFormat("yyyyMM")

        fg_detail_view_rcy.getRcy().layoutManager = LinearLayoutManager(mActivity)
        detailAdapter=DetailAdapter(mActivity)
        fg_detail_view_rcy.getRcy().adapter = detailAdapter
        fg_detail_tv_year.text=DateUtil.getYear()+"年"
        fg_detail_tv_month.text=DateUtil.getMonth()+" 月"
        SpannableStringUtil.SpannableStringBuild(fg_detail_tv_month.text.toString()).partTextSize(18 , 0 , 2)
            ?.partTextBold(0 , 2)
            ?.build(fg_detail_tv_month)

        SpannableStringUtil.SpannableStringBuild(fg_detail_view_rcy.getTvEmpty().text.toString())
            .partTextColor(R.color.blue , "该月还没有记账哦，".length , fg_detail_view_rcy.getTvEmpty().text.length)
            ?.build(fg_detail_view_rcy.getTvEmpty())

    }

    override fun initEvent() {
        setOnClickListener(fg_detail_tv_year,fg_detail_tv_month)
        fg_detail_view_rcy.getTvEmpty().setOnClickListener {
            (mActivity as MainActivity).showBookkeeping()
        }
    }

    override fun myOnclick(view : View) {
        when (view) {
            fg_detail_tv_year,fg_detail_tv_month->{
                timePicker.show()
            }
        }
    }

    //获取当前年月
    open fun getYearMonth(){
        val year = fg_detail_tv_year.text.toString().substring(0 , 4)
        val month = fg_detail_tv_month.text.toString().substring(0 , 2)
    }

    //从数据库取数据合并计算条目item数据
    open fun getItemData(){
        val detailDataList = mutableListOf<ItemDetailBean>()
        val findAll = LitePal.findAll(DetailSqlBean::class.java)
        val year = fg_detail_tv_year.text.substring(0 , 4)
        val month = fg_detail_tv_month.text.substring(0 , 2)
        var yearMonth= "$year/$month"

        findAll.forEach {detail->
            if (detailDataList.find { it.date==detail.date }==null){//重复日期
                val itemDetailBean = ItemDetailBean()
                val sameDate = findAll.filter { it.yearMonth == yearMonth&&detail.date == it.date }.toMutableList()
                itemDetailBean.incomeMoney =sameDate.filter { it.type == CommonConstant.MainActivity.TYPE_INCOME }.sumOf { it.money.toBigDecimal() }
                itemDetailBean.disburseMoney = sameDate.filter { it.type == CommonConstant.MainActivity.TYPE_DISBURSE }.sumOf { it.money.toBigDecimal() }
                itemDetailBean.listItemDetailBean=sameDate
                itemDetailBean.date=detail.date
                itemDetailBean.week=detail.week
                itemDetailBean.type=detail.type
                if (sameDate.isNotEmpty())
                    detailDataList.add(itemDetailBean)
            }
            LogUtil.logD("=="+detail.toString())
        }
        detailDataList.forEach {
            LogUtil.logD("================"+it.toString())
        }
        detailAdapter.updateItems(detailDataList)

        //每次刷新滚动到第一条并关闭下来刷新与上拉加载
        fg_detail_view_rcy.getRcy().scrollToPosition(0)
        refresh.endLoadingMore()
        refresh.endRefreshing()

    }


    //从数据库取数据计算每月收入/支出金额
    @SuppressLint("SetTextI18n")
    open fun getMonthMoneyData(){
        val year = fg_detail_tv_year.text.substring(0 , 4)
        val month = fg_detail_tv_month.text.substring(0 , 2)
        var yearMonth= "$year/$month"

        val findAll = LitePal.findAll(DetailSqlBean::class.java)
        val sumOfIncome = findAll.filter { it.yearMonth == yearMonth&&it.type==CommonConstant.MainActivity.TYPE_INCOME }.sumOf { it.money.toBigDecimal() }
        val sumOfDisburse = findAll.filter { it.yearMonth == yearMonth&&it.type==CommonConstant.MainActivity.TYPE_DISBURSE }.sumOf { it.money.toBigDecimal() }
        val decimalFormat = DecimalFormat("0.00")
        fg_detail_tv_income.text=decimalFormat.format(sumOfIncome)
        fg_detail_tv_disburse.text=decimalFormat.format(sumOfDisburse)
        SpannableStringUtil.SpannableStringBuild(fg_detail_tv_income.text.toString()).partTextSize(17,0,fg_detail_tv_income.text.indexOf("."))
            ?.partTextBold(0,fg_detail_tv_income.text.indexOf("."))?.build(fg_detail_tv_income)
        SpannableStringUtil.SpannableStringBuild(fg_detail_tv_disburse.text.toString()).partTextSize(17,0,fg_detail_tv_disburse.text.indexOf("."))
            ?.partTextBold(0,fg_detail_tv_disburse.text.indexOf("."))?.build(fg_detail_tv_disburse)

    }

    //日期控件
    private fun initTimePicker() {
        timePicker = TimePickerBuilder(mActivity) { date , v ->
            val dateToString = DateUtil.dateToString(date,"YYYY年/MM 月")
            fg_detail_tv_year.text=dateToString.substring(0,5)
            fg_detail_tv_month.text=dateToString.substring(6,dateToString.length)
            SpannableStringUtil.SpannableStringBuild(fg_detail_tv_month.text.toString()).partTextSize(19 , 0 , 2)
                ?.partTextBold(0 , 2)
                ?.build(fg_detail_tv_month)

            getItemData()

        }.setTimeSelectChangeListener { Log.i("pvTime" , "onTimeSelectChanged") }
            .setType(booleanArrayOf(true , true , false , false , false , false))
            .setTitleText("选择日期")
            .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
            .addOnCancelClickListener { Log.i("pvTime" , "onCancelClickListener") }.setItemVisibleCount(6) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
            .setLineSpacingMultiplier(2.0f).isAlphaGradient(true).build()
        val mDialog : Dialog = timePicker.dialog
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT , Gravity.BOTTOM)
        params.leftMargin = 0
        params.rightMargin = 0
        timePicker.dialogContainerLayout.layoutParams = params
        val dialogWindow = mDialog.window
        if (dialogWindow != null) {
            dialogWindow.setWindowAnimations(R.style.dialog_showBottom_anim) //修改动画样式
            dialogWindow.setGravity(Gravity.BOTTOM) //改成Bottom,底部显示
            dialogWindow.setDimAmount(0.4f)
        }
    }

    private fun endLoad() {
    }

    @SuppressLint("SetTextI18n")
    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        Thread{
            Thread.sleep(500)
            mActivity.runOnUiThread {
                val year = fg_detail_tv_year.text.substring(0 , 4)
                val month = fg_detail_tv_month.text.substring(0 , 2)
                val parse = simpleDateFormat.parse(year+month)
                val nextMonth = DateUtil.getLastMonth(parse,1)
                fg_detail_tv_year.text=nextMonth.substring(0,4)+"年"
                fg_detail_tv_month.text=nextMonth.substring(4,6)+" 月"
                getItemData()
                getMonthMoneyData()
            }
        }.start()
       // endLoad()
        return true
    }

    @SuppressLint("SetTextI18n")
    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {

        Thread{
            Thread.sleep(500)
            mActivity.runOnUiThread {
                val year = fg_detail_tv_year.text.substring(0 , 4)
                val month = fg_detail_tv_month.text.substring(0 , 2)
                val parse = simpleDateFormat.parse(year+month)
                val lastMonth = DateUtil.getLastMonth(parse)
                fg_detail_tv_year.text=lastMonth.substring(0,4)+"年"
                fg_detail_tv_month.text=lastMonth.substring(4,6)+" 月"
                getItemData()
                getMonthMoneyData()
            }
        }.start()
    }
}
