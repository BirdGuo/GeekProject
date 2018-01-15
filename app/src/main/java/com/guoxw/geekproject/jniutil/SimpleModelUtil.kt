package com.guoxw.geekproject.jniutil

import android.content.res.AssetManager

/**
 * @auther guoxw
 * @date 2018/1/11 0011
 * @package com.guoxw.geekproject.jniutil
 * @desciption
 */
object SimpleModelUtil {

    init {
        System.loadLibrary("native-lib")
    }

    external fun initModel(assestManager: AssetManager, assetName: String): Long

    external fun startCompute(modelHandle: Long, input1: Float, input2: Float): Float

    external fun destoryModel(modelHandle: Long)

}