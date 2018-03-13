package com.guoxw.geekproject.screenrecorder

import android.media.MediaFormat
import java.io.Serializable

/**
 * @auther guoxw
 * @date 2018/3/13 0013
 * @package com.guoxw.geekproject.screenrecorder
 * @desciption
 */
class AudioEncodeConfig(val codecName: String, val mimeType: String, val bitRate: Int,
                        val sampleRate: Int, val channelCount: Int, val profile: Int) : Serializable {

    fun toFormat(): MediaFormat {

        val format = MediaFormat.createAudioFormat(mimeType, sampleRate, channelCount)
        format.setInteger(MediaFormat.KEY_AAC_PROFILE, profile)
        format.setInteger(MediaFormat.KEY_BIT_RATE, bitRate)
        return format
    }

    override fun toString(): String {
        return "AudioEncodeConfig(codecName='$codecName', mimeType='$mimeType', bitRate=$bitRate, sampleRate=$sampleRate, channelCount=$channelCount, profile=$profile)"
    }
}