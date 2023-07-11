package com.hq.jizhang.adapter

import android.util.SparseArray
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hq.jizhang.base.BaseFragment
import com.hq.jizhang.fragment.BookkeepingFragment
import com.hq.jizhang.fragment.ChatFragment
import com.hq.jizhang.fragment.DetailFragment

/*
 * @创建者      肖天长
 * @创建时间    2023/7/10 20:22
 * @描述       adapter
 *
 */
class AdapterFragmentPager (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments: SparseArray<BaseFragment> = SparseArray()

    init {
        fragments.put(PAGE_HOME, DetailFragment.createFragment())
        fragments.put(PAGE_FIND, BookkeepingFragment.createFragment())
        fragments.put(PAGE_INDICATOR, ChatFragment.createFragment())
    }

    override fun createFragment(position: Int): BaseFragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size()
    }

    companion object {

        const val PAGE_HOME = 0

        const val PAGE_FIND = 1

        const val PAGE_INDICATOR = 2

        const val PAGE_OTHERS = 3

    }
}
