package com.hq.jizhang.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.hq.jizhang.R
import com.hq.jizhang.activity.MainActivity
import com.hq.jizhang.base.BaseFragment
import com.hq.jizhang.fragment.DisburseFragment
import com.hq.jizhang.fragment.IncomeFragment
import kotlinx.android.synthetic.main.dialog_bookkeeping.*

/*
 * @创建者      肖天长
 * @创建时间    2022/3/25 11:36
 * @描述       全屏dialog
 *
 */
class FullQuestionImgDialog(var activity: MainActivity) : DialogFragment() {

    private var mListener : ((Int , Any) -> Unit)? = null
    private lateinit var fragmentList : MutableList<BaseFragment>

    override fun onCreateView(inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?) : View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(R.layout.dialog_bookkeeping , container , false)
    }

    override fun onActivityCreated(savedInstanceState : Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initWindow()
    }

    private fun initWindow() {
        //初始化window相关表现
        val window = dialog?.window
        /*  //设置window宽高(单位px)
          window?.attributes?.width = 700
          //window?.attributes?.height = 350
          //设置window位置
          window?.attributes?.gravity = Gravity.CENTER//居中*/
        window?.setWindowAnimations(R.style.dialog_showBottom_anim)
        //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
         window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        //一定要在setContentView之后调用，否则无效
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT)
        window?.setDimAmount(0f)
        initData()
        initEvent()
    }

    private fun initEvent() {
        dialog_bookkeeping_tv_cancel.setOnClickListener { dismiss() }
    }


    private fun initData() {
        val titleList = mutableListOf("支出" , "收入")
        fragmentList = mutableListOf(DisburseFragment.createFragment(this) , IncomeFragment.createFragment(this))
        dialog_bookkeeping_vp.offscreenPageLimit = titleList.size
        dialog_bookkeeping_vp.adapter = BarAdapter(childFragmentManager , titleList)
        dialog_bookkeeping_tab.setViewPager(dialog_bookkeeping_vp)
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