package com.guoxw.geekproject.jniutil

import android.content.Context
import android.os.Bundle

/**
 * @auther guoxw
 * @date 2018/1/22 0022
 * @package com.guoxw.geekproject.jniutil
 * @desciption
 */
object JNIUIUtil {

    init {
        System.loadLibrary("native-lib")
    }

    external fun nativec(mContext: Context)

}