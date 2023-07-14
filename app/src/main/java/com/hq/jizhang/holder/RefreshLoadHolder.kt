package com.hq.jizhang.holder

import android.content.Context
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout

/**
 * Created by achang on 2018/9/27.
 */

class RefreshLoadHolder : BGANormalRefreshViewHolder {

    /**
     * @param context
     * @param isLoadingMoreEnabled 上拉加载更多是否可用
     */
    constructor(context: Context, isLoadingMoreEnabled: Boolean) : super(
        context,
        isLoadingMoreEnabled
    )

    /**
     * @param context
     * @param
     */
    constructor(
        context: Context,
        mRefreshLayout: BGARefreshLayout,
        bgaRefreshLayoutDelegate: BGARefreshLayout.BGARefreshLayoutDelegate
    ) : super(context, true) {
        mRefreshLayout.setDelegate(bgaRefreshLayoutDelegate)
        setRefreshingText("加载中")
        setPullDownRefreshText("下拉刷新")
        setReleaseRefreshText("释放刷新")
        setLoadingMoreText("加载中")
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(this)
    }//  默认 true      表示可以上拉加载
}
