package com.guoxw.geekproject.jniutil

import android.content.Context

/**
 * @auther guoxw
 * @date 2018/3/1 0001
 * @package com.guoxw.geekproject.jniutil
 * @desciption
 */
object ServerUtil {
    init {
        System.loadLibrary("native-lib")
    }

    external fun nativeStartTcpServer(mContext: Context, port: Int)

    external fun nativeStartUdpServer(mContext: Context, port: Int)

    external fun nativeStartLocalServer(mContext: Context, name: String)
}