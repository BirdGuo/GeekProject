package com.guoxw.geekproject.utils

import android.support.v7.widget.RecyclerView

/**
 * Created by guoxw on 2017/10/31 0031.
 */
object RecyclerViewUtil {

    /**
     * @param recyclerView
     *          列表
     * @return true 在底部；false 不在底部
     */
    fun isSlideToBottom(recyclerView: RecyclerView?): Boolean {
        if (recyclerView == null)
            return false
        var isBottom: Boolean = false
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()) {
            isBottom = true
        } else {
            isBottom = false
        }

        return isBottom
    }

}