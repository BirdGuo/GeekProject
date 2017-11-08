package com.guoxw.geekproject.events

import android.view.View

/**
 * Created by guoxw on 2017/9/8 0008.
 * @auther guoxw
 * @date 2017/9/8 0008
 * @desciption
 * @package com.guoxw.geekproject.events
 */
interface RCVItemClickListener {

    /**
     * recycleview内容点击事件
     * @param view 父布局
     * @param postion 序号
     */
    fun onItemClickListener(view: View, postion: Int)

    /**
     * recycleview内容长按点击事件
     * @param view 父布局
     * @param postion 序号
     */
    fun onItemLongClickListener(view: View, postion: Int)

}