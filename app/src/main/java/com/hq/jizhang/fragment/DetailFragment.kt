package com.hq.jizhang.fragment

import android.app.Dialog
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import com.hq.jizhang.R
import com.hq.jizhang.activity.MainActivity
import com.hq.jizhang.adapter.DetailAdapter
import com.hq.jizhang.base.BaseFragment
import com.hq.jizhang.util.DateUtil
import com.hq.jizhang.util.SpannableStringUtil
import kotlinx.android.synthetic.main.fragment_detail.*

/*
 * @创建者      肖天长
 * @创建时间    2022/05/30 16:31
 * @描述       明细
 *
 */
class DetailFragment() : BaseFragment() {

    private lateinit var timePicker : TimePickerView
    private lateinit var detailAdapter : DetailAdapter

    companion object {

        fun createFragment() : DetailFragment {
            return DetailFragment()
        }
    }

    override fun initView() : View {
        return View.inflate(mActivity , R.layout.fragment_detail , null)
    }

    override fun initData() {
        initTimePicker()
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

    //日期控件
    private fun initTimePicker() {
        timePicker = TimePickerBuilder(mActivity) { date , v ->
            val dateToString = DateUtil.dateToString(date,"YYYY年/MM 月")
            fg_detail_tv_year.text=dateToString.substring(0,5)
            fg_detail_tv_month.text=dateToString.substring(6,dateToString.length)
            SpannableStringUtil.SpannableStringBuild(fg_detail_tv_month.text.toString()).partTextSize(18 , 0 , 2)
                ?.partTextBold(0 , 2)
                ?.build(fg_detail_tv_month)

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
}
