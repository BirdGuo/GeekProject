package com.guoxw.geekproject.screenrecorder

import android.media.MediaFormat

/**
 * @auther guoxw
 * @date 2018/3/14 0014
 * @package com.guoxw.geekproject.screenrecorder
 * @desciption
 */
class AudioEncoder : BaseEncoder {
    var mConfig: AudioEncodeConfig

    constructor(mConfig: AudioEncodeConfig) : super(mConfig.codecName) {
        this.mConfig = mConfig
    }

    override fun createMediaFormat(): MediaFormat {
        return mConfig.toFormat()
    }
}