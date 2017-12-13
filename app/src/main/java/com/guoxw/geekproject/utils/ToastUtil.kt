package com.guoxw.geekproject.utils

import android.content.Context
import android.widget.Toast

/**
* @auther guoxw
* @date 2017/11/7 0007
* @package ${PACKAGE_NAME}
* @desciption
*/
object ToastUtil {

    fun toastLong(context: Context, content: String) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show()
    }

    fun toastShort(context: Context, content: String) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
    }

}