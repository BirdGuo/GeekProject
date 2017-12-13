package com.guoxw.geekproject.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
* @auther guoxw
* @date 2017/11/7 0007
* @package ${PACKAGE_NAME}
* @desciption
*/
object StringUtil {

    /**
     * 复制到剪切板
     *
     * @param context
     * @param text 内容
     * @param success 提示成功
     */
    fun copyToClipBoard(context: Context, text: String, success: String) {
        val clipData: ClipData = ClipData.newPlainText("girl_copy", text)
        val manager: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        manager.primaryClip = clipData
        ToastUtil.toastShort(context, success)
    }

}