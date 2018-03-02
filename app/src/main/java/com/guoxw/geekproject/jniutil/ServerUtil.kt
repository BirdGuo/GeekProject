package com.guoxw.geekproject.jniutil

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

    external fun nativeStartTcpServer(port: Int)

    external fun nativeStartUdpServer(port: Int)

    external fun nativeStartLocalServer(name: String)
}