package com.hq.jizhang.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.hq.jizhang.R
import kotlinx.android.synthetic.main.include_refreshlayout_recy.view.*

/*
 * @创建者     肖天长
 * @创建时间   2020/11/3  17:29
 * @描述
 *
 */
class CustomRcyLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.include_refreshlayout_recy, this, true);
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRcyLayout, 0, 0)
        var backGround = typedArray.getDrawable(R.styleable.CustomRcyLayout_android_background)
        var hasMore = typedArray.getBoolean(R.styleable.CustomRcyLayout_hasMore,true)
        var hasReFresh = typedArray.getBoolean(R.styleable.CustomRcyLayout_hasReFresh,true)
        rcy_view.setEmptyView(rcy_ll_noData)
        background=backGround
/*        rcy_refresh.setPullDownRefreshEnable(hasMore)
        rcy_refresh.setDelegate(null)*/
        typedArray.recycle()
    }

    fun getRcy(): RecyclerView {
        return rcy_view
    }

    fun getRefreshLayout(): BGARefreshLayout {
        return rcy_refresh
    }

    fun getTvEmpty(): TextView {
        return rcy_tv_to
    }

}