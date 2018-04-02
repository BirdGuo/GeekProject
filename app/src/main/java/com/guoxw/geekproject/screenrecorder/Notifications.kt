package com.guoxw.geekproject.screenrecorder

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Build.VERSION_CODES.O

/**
 * @auther guoxw
 * @date 2018/4/2 0002
 * @package com.guoxw.geekproject.screenrecorder
 * @desciption
 */
class Notifications : ContextWrapper {

    constructor(base: Context?) : super(base) {
        if (Build.VERSION.SDK_INT >= O){

        }
    }
}