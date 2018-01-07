package com.guoxw.geekproject.jniutil

/**
 * @auther guoxw
 * @date 2018/1/5 0005
 * @package com.guoxw.geekproject.jniutil
 * @desciption
 */
object JNICallbackUtil {

    init {
        System.loadLibrary("native-lib")
    }

    /**
     * 获取文字
     */
    external fun stringFromJNI(): String

    /**
     * 开始计时
     */
    external fun startTicks()

    /**
     * 结束计时
     */
    external fun stopTicks()

}