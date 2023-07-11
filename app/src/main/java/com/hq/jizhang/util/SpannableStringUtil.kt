package com.hq.jizhang.util

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.hq.jizhang.base.BaseApplication

/*
 * @创建者     肖天长 
 * @创建时间   2020/11/12  9:50
 * @描述      文字不同颜色、不同大小、粗体等工具
 *
 */class SpannableStringUtil {
    class SpannableStringBuild(var text: String) {
        private var spannedString: SpannableString = SpannableString(text)

        fun partTextColor(color: Int, start: Int, end: Int): SpannableStringBuild? {
            if (start<0||start>spannedString.length||end>spannedString.length||end<=0)return null
            val color= ContextCompat.getColor(BaseApplication.mAppContext,color)
            val foregroundColorSpan = ForegroundColorSpan(color)
            spannedString.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            return this
        }
        fun partTextSize(size: Int,start: Int, end: Int): SpannableStringBuild? {
            if (start<0||start>spannedString.length||end>spannedString.length||end<=0)return null
            spannedString.setSpan(AbsoluteSizeSpan(size, true), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            return this
        }
        fun partTextBold(start: Int, end: Int): SpannableStringBuild? {
            if (start<0||start>spannedString.length||end>spannedString.length||end<=0)return null
            spannedString.setSpan(StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            return this
        }
        fun partTextNoBold(start: Int, end: Int): SpannableStringBuild? {
            if (start<0||start>spannedString.length||end>spannedString.length||end<=0)return null
            spannedString.setSpan(StyleSpan(Typeface.NORMAL), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            return this
        }
        fun setPan(pan: ClickableSpan,start: Int,end: Int): SpannableStringBuild?{
            if (start<0||start>spannedString.length||end>spannedString.length||end<=0)return null
            spannedString.setSpan(pan,start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            return this
        }
        fun setMiddleLine(pan: StrikethroughSpan,start: Int,end: Int): SpannableStringBuild?{
            if (start<0||start>spannedString.length||end>spannedString.length||end<=0)return null
            spannedString.setSpan(pan,start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            return this
        }
        fun setLeadingMarginSpan(length: Int=ResourceUtil.dp2Px(20).toInt(),rest: Int=0): SpannableStringBuild?{
            if (spannedString.isEmpty())return null
            val leadingMarginSpan: LeadingMarginSpan = LeadingMarginSpan.Standard(length, rest) //length缩进多少，0仅首行缩进
            spannedString.setSpan(leadingMarginSpan, 0, spannedString.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            return this
        }
        fun build(textView: TextView){
            textView.text=spannedString
        }

    }
}