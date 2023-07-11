package com.hq.jizhang.fragment

import android.view.View
import com.hq.jizhang.R
import com.hq.jizhang.base.BaseFragment
import com.hq.jizhang.util.SpannableStringUtil
import kotlinx.android.synthetic.main.fragment_detail.*

/*
 * @创建者      肖天长
 * @创建时间    2022/05/30 16:31
 * @描述       明细
 *
 */
class DetailFragment() : BaseFragment() {

    companion object {
        fun createFragment() : DetailFragment {
            return DetailFragment()
        }
    }

    override fun initView() : View {
        return View.inflate(mActivity , R.layout.fragment_detail, null)
    }

    override fun initData() {
        SpannableStringUtil.SpannableStringBuild(fg_detail_tv_month.text.toString()).partTextSize(18,0,2)?.build(fg_detail_tv_month)
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
