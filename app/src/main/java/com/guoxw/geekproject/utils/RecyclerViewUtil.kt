package com.guoxw.geekproject.utils

import android.support.v7.widget.RecyclerView

/**
* @auther guoxw
* @date 2017/10/31 0031
* @package ${PACKAGE_NAME}
* @desciption
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
        var isBottom = false
        isBottom = recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()

        return isBottom
    }

}