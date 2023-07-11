package com.hq.jizhang.fragment

import android.view.View
import com.hq.jizhang.R
import com.hq.jizhang.base.BaseFragment
import kotlinx.android.synthetic.main.activity_basetitle.*

/*
 * @创建者      肖天长
 * @创建时间    2022/05/30 16:31
 * @描述       图表
 *
 */
class ChatFragment : BaseFragment() {

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
