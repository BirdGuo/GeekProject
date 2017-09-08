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

    fun onItemClickListener(view: View, postion: Int)

    fun onItemLongClickListener(view: View, postion: Int)

}