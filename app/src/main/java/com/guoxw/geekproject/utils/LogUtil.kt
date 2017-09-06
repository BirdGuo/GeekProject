package com.guoxw.geekproject.utils

import android.util.Log
import com.guoxw.geekproject.BuildConfig

/**
 * Created by guoxw on 2017/9/5 0005.
 * @auther guoxw
 * @date 2017/9/5 0005
 * @desciption
 * @package com.guoxw.geekproject.utils
 */
object LogUtil {
    val isShow = true//是否显示标识 true：显示；false 不显示
    //作为log打印和不打印的标志
    val TAG = BuildConfig.LOG_DEBUG
//    private static final boolean TAG= true;

    fun d(tag: String, msg: String) {
        if (TAG) {
            Log.d(tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (TAG) {
            Log.i(tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (TAG) {
            Log.e(tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (TAG) {
            Log.w(tag, msg)
        }
    }

    fun v(tag: String, msg: String) {
        if (TAG) {
            Log.v(tag, msg)
        }
    }
}