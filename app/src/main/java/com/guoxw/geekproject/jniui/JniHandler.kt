package com.guoxw.geekproject.jniui

import android.os.Build
import android.support.annotation.Keep
import android.util.Log

/**
 * @auther guoxw
 * @date 2018/1/8 0008
 * @package com.guoxw.geekproject.jniui
 * @desciption
 */
object JniHandler {

    /*
     * Return Java memory info
     */
    @Keep
    open fun getRuntimeMemorySize(): Long {
        return Runtime.getRuntime().freeMemory()
    }

    /*
     * Print out status to logcat
     */
    @Keep
    private fun updateStatus(msg: String) {
        if (msg.toLowerCase().contains("error")) {
            Log.e("JniHandler", "Native Err: " + msg)
        } else {
            Log.i("JniHandler", "Native Msg: " + msg)
        }
    }

    @Keep
    open fun getBuildVersion(): String {
        return Build.VERSION.RELEASE
    }
}
