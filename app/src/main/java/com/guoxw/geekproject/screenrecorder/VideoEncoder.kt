package com.guoxw.geekproject.screenrecorder

import android.media.MediaCodec
import android.media.MediaFormat
import android.view.Surface
import com.guoxw.geekproject.utils.LogUtil

/**
 * @auther guoxw
 * @date 2018/4/2 0002
 * @package com.guoxw.geekproject.screenrecorder
 * @desciption
 */
class VideoEncoder(val mConfig: VideoEncodeConfig) : BaseEncoder(mConfig.codecName) {

    private val VERBOSE = false

    var mSurface: Surface? = null

    override fun onEncoderConfigured(encoder: MediaCodec) {
        mSurface = encoder.createInputSurface()
        LogUtil.i("GXW", "VideoEncoder create input surface: " + mSurface)
    }

    override fun createMediaFormat(): MediaFormat {
        return mConfig.toFormat()
    }


    override fun release() {
        if (mSurface != null) {
            mSurface!!.release()
            mSurface = null
        }
        super.release()
    }
}