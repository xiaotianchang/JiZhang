package com.hq.jizhang.view.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.hq.jizhang.R
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
class FullQuestionImgDialog(context: Activity, fragmentManager: FragmentManager) : Dialog(context) {

    private var mListener: ((Int, Any) -> Unit)? = null
    private lateinit var fragmentList:MutableList<BaseFragment>

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_bookkeeping)
        window!!.setWindowAnimations(R.style.dialog_showBottom_anim)
        //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
        window!!.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        //一定要在setContentView之后调用，否则无效
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window!!.setDimAmount(0f)

       // dialog_question_pv.setOnClickListener { dismiss() }
        initData(fragmentManager)
    }

    private fun initData(fragmentManager: FragmentManager) {
        fragmentList= mutableListOf()
        var titleList= mutableListOf("支出","收入")
        fragmentList.add(DisburseFragment.createFragment())
        fragmentList.add(IncomeFragment.createFragment())
        dialog_bookkeeping_vp.offscreenPageLimit=fragmentList.size
        dialog_bookkeeping_vp.adapter = BarAdapter(fragmentManager , titleList)
        dialog_bookkeeping_tab.setViewPager(dialog_bookkeeping_vp)
    }

    inner class BarAdapter (fm: FragmentManager ,  var data: MutableList<String>) : FragmentPagerAdapter(fm){

        override fun getItem(position: Int): BaseFragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return data.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return data[position]
        }
    }

    fun setOnItemClickListener(listener: (position: Int, item: Any) -> Unit) {
        this.mListener = listener
    }

}