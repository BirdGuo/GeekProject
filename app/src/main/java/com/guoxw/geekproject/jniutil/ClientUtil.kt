package com.guoxw.geekproject.jniutil

import android.content.Context

/**
 * @auther guoxw
 * @date 2018/3/1 0001
 * @package com.guoxw.geekproject.jniutil
 * @desciption
 */
object ClientUtil {

    init {
        System.loadLibrary("native-lib")
    }

    /**
     *
     * @param ip
     * @param port
     * @param message
     * @throws Exception
     */
    @Throws(Exception::class)
    external fun nativeStartTcpClient(mContext: Context, ip: String, port: Int, message: String)

    /**
     *
     * @param ip
     * @param port
     * @param message
     * @throws Exception
     */
    @Throws(Exception::class)
    external fun nativeStartUdpClient(mContext: Context, ip: String, port: Int, message: String)
}