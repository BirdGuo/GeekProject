package com.guoxw.geekproject.jniutil

/**
 * @auther guoxw
 * @date 2018/1/2 0002
 * @package com.guoxw.geekproject.jniutil
 * @desciption
 */
object JNIUtil {

    init {
        //这边添加的libname是CMakeLists中add_library中设置的库的名称
        System.loadLibrary("native-lib")
//        System.loadLibrary("myJniLib")
    }

    external fun stringFromJNI(): String

    external fun countFromJNI(): Int

    external fun getCLanguageString(): String

    external fun calAAndB(a: Int, b: Int): Int

    /**
     * 输出info类型的Log
     * @param content 输出内容
     */
    external fun printLogInfo(content: String)

    /**
     * 输出debug类型的Log
     * @param content 输出内容
     */
    external fun printLogDebug(content: String)

    /**
     * 输出warn类型的Log
     * @param content 输出内容
     */
    external fun printLogWarn(content: String)

    /**
     * 输出error类型的Log
     * @param content 输出内容
     */
    external fun printLogError(content: String)


}