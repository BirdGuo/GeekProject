package com.guoxw.geekproject.jniutil

/**
 * @auther guoxw
 * @date 2018/2/27 0027
 * @package com.guoxw.geekproject.jniutil
 * @desciption
 */
object TcpIPUtil {

    init {
        System.loadLibrary("native-lib")
    }

    external fun startConnect()

}