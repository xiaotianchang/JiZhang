package com.umeng.soexample.interfaces

import android.view.View


interface ClickAction : View.OnClickListener {
    override fun onClick(v: View) {
        // 默认不实现，让子类实现
    }

    fun setOnClickListener(vararg views: View) {
        for (view in views) {
            view.setOnClickListener(this)
        }
    }
}