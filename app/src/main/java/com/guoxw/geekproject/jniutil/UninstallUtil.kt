package com.guoxw.geekproject.jniutil

/**
 * @auther guoxw
 * @date 2018/1/4 0004
 * @package com.guoxw.geekproject.jniutil
 * @desciption
 */
object UninstallUtil {

    init {
        System.loadLibrary("native-lib")
    }

    external fun callUnInstallListener(versionSDK: Int, path: String)

}