package com.guoxw.geekproject.screenrecorder

import java.io.IOException

/**
 * @auther guoxw
 * @date 2018/3/13 0013
 * @package com.guoxw.geekproject.screenrecorder
 * @desciption
 */
interface Encoder {

    /**
     * 准备解码
     */
    @Throws(IOException::class)
    fun prepare()

    /**
     * 停止
     */
    fun stop()

    /**
     * 释放
     */
    fun release()

    /**
     * 设置callback
     */
    fun setCallback(callback: Callback)

    interface Callback {
        fun onError(encoder: Encoder, exception: Exception)
    }

}