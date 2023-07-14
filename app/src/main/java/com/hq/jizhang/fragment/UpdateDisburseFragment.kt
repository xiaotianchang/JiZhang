package com.hq.jizhang.fragment

import android.app.Dialog
import android.inputmethodservice.Keyboard
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import com.gyf.immersionbar.ImmersionBar
import com.hq.jizhang.R
import com.hq.jizhang.activity.UpdateSqlActivity
import com.hq.jizhang.view.JokerKeyBoradHelper
import com.hq.jizhang.adapter.IncomeAdapter
import com.hq.jizhang.base.BaseActivity
import com.hq.jizhang.base.BaseApplication
import com.hq.jizhang.base.BaseFragment
import com.hq.jizhang.bean.DetailSqlBean
import com.hq.jizhang.cons.CommonConstant
import com.hq.jizhang.util.DateUtil
import com.hq.jizhang.util.LogUtil
import com.hq.jizhang.util.StringUtil
import com.hq.jizhang.view.dialog.FullQuestionImgDialog
import kotlinx.android.synthetic.main.fragment_income.*

/*
 * @创建者      肖天长
 * @创建时间    2022/05/30 16:31
 * @描述       修改支出
 *
 */
class UpdateDisburseFragment(var detailSqlBean:DetailSqlBean?) : BaseFragment() {

    private lateinit var incomeAdapter:IncomeAdapter
    private lateinit var mKey : Keyboard.Key
    private lateinit var helper : JokerKeyBoradHelper
    private lateinit var timePicker : TimePickerView

    companion object {
        fun createFragment(detailSqlBean:DetailSqlBean?) : UpdateDisburseFragment {
            return UpdateDisburseFragment(detailSqlBean)
        }
    }

    override fun initView() : View {
        return View.inflate(mActivity , R.layout.fragment_income , null)
    }

    override fun initData() {
        initKey()
        initTimePicker()

        fg_income_rcy.layoutManager= GridLayoutManager(mActivity , 4)
        incomeAdapter = IncomeAdapter(mActivity)
        fg_income_rcy.adapter=incomeAdapter
        incomeAdapter.updateItems(BaseApplication.disburseList)

        detailSqlBean?.apply {
            fg_income_et_note.setText(note)
            fg_income_et_money.setText(money)
            incomeAdapter.getData().find { it.title==name }?.apply { isSelect=true }
            incomeAdapter.notifyDataSetChanged()
            //  fg_income_ll_board.visibility=View.VISIBLE
            fg_income_scl.visibility=View.VISIBLE
            fg_income_board.visibility=View.VISIBLE
        }
    }

    override fun initEvent() {
        setOnClickListener()
        incomeAdapter.setOnItemClickListener { position , item ->
           /* if (fg_income_ll_board.visibility == View.GONE) {
                fg_income_ll_board.visibility = View.VISIBLE
            } */
            fg_income_scl.visibility=View.VISIBLE
            if (fg_income_board.visibility == View.GONE) {
                fg_income_board.visibility = View.VISIBLE
            }
        }

        ImmersionBar.with(this) // 默认状态栏字体颜色为黑色
            .statusBarDarkFont(true) // 指定导航栏背景颜色
            .navigationBarColor(R.color.white) // 状态栏字体和导航栏内容自动变色，必须指定状态栏颜色和导航栏颜色才可以自动变色
            .autoDarkModeEnable(true , 0.2f)
            .keyboardEnable(true).setOnKeyboardListener { isPopup , keyboardHeight ->
                if (isPopup){
                    fg_income_board.visibility = View.GONE
                }else{
                    fg_income_board.visibility = View.VISIBLE
                }
            }.init()
    }

    override fun myOnclick(view : View) {
        when (view) {
        }
    }

    //日期控件
    private fun initTimePicker() {
        timePicker = TimePickerBuilder(mActivity) { date , v ->
            val dateToString = DateUtil.dateToString(date,"YYYY/MM/dd")
            mKey.label = dateToString
            // 这里调用了系统日历，需要调用view的postInvalidate进行重绘
            helper.keyBoradView.postInvalidate()
        }.setTimeSelectChangeListener { Log.i("pvTime" , "onTimeSelectChanged") }
            .setType(booleanArrayOf(true , true , true , false , false , false))
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

    private fun initKey() {
        //初始化KeyboardView
        helper = JokerKeyBoradHelper(mActivity , fg_income_board)
        // 软键盘捆绑etInput
        helper.setEditText(fg_income_et_money)
        helper.setCallBack(object : JokerKeyBoradHelper.KeyboardCallBack {
            override fun keyCall(code : Int , content : String) {
                //                if (!content.isEmpty() && !content.startsWith("+") && !content.startsWith("-")) {
                //                    if (content.contains("+") || content.contains("-")) {
                //                        //回调键盘监听，根据回调的code值进行处理
                //                        if (code == 43 || code == 45) {
                //                            Keyboard.Key key = helper.getKey(-4);
                //                            key.label = "=";
                //                        }
                //                    } else {
                //                        Keyboard.Key key = helper.getKey(-4);
                //                        key.label = "完成";
                //                    }
                //                }
                if (! content.isEmpty()) {
                    if (code == 43 || code == 45) {
                        val key : Keyboard.Key = helper.getKey(- 4)
                        key.label = "="
                        LogUtil.logD("=coed " + code.toString() + "  " + key.label)
                    }
                    if (code == - 4) {
                        val key : Keyboard.Key = helper.getKey(- 4)
                        key.label = "完成"
                        LogUtil.logD("=--coed " + code.toString() + "   " + key.label)
                    }
                }
            }

            override fun doneCallback() {
                val key : Keyboard.Key = helper.getKey(- 100000)
                LogUtil.logD("最终内容" + fg_income_et_note.text.toString() + "\n" + fg_income_et_money.text.toString())
                detailSqlBean?.apply {
                    if (StringUtil.getString(key.label)=="今天"){
                        val replace = DateUtil.curDate.replace("-" , "/")
                        date = replace
                        yearMonth = DateUtil.yearMonth
                    }else{
                        date = StringUtil.getString(key.label)
                        yearMonth = date.substring(0,7)
                    }
                    week = date.let { DateUtil.getWeekByDateStr(it) }
                    money = fg_income_et_money.text.toString()
                    note = fg_income_et_note.text.toString()
                    type = CommonConstant.MainActivity.TYPE_DISBURSE
                    name = incomeAdapter.getData().find { it.isSelect }?.title
                    val update = update(id)
                    LogUtil.logD("update"+update+"======"+detailSqlBean?.toString())
                    (mActivity as UpdateSqlActivity).finishHideInput()
                }
            }

            override fun dateCallback(key : Keyboard.Key) {
                mKey = key
                timePicker.show()
            }
        })
    }
}
