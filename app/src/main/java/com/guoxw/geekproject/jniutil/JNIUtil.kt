package com.guoxw.geekproject.jniutil

/**
 * @auther guoxw
 * @date 2018/1/2 0002
 * @package com.guoxw.geekproject.jniutil
 * @desciption
 */
object JNIUtil {

    init {
        System.loadLibrary("native-lib")
    }

    external fun stringFromJNI(): String

    external fun countFromJNI(): Int

}