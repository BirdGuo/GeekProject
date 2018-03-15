package com.guoxw.geekproject.screenrecorder

import android.media.MediaCodecInfo
import android.media.MediaFormat
import java.util.*

/**
 * @auther guoxw
 * @date 2018/3/13 0013
 * @package com.guoxw.geekproject.screenrecorder
 * @desciption
 */
class VideoEncodeConfig {

    var width: Int
    var height: Int
    var bitrate: Int
    var codecProfileLevel: MediaCodecInfo.CodecProfileLevel
    var mimeType: String
    var codecName: String
    var iframeInterval: Int
    var framerate: Int


    constructor(width: Int, height: Int, bitrate: Int, codecProfileLevel: MediaCodecInfo.CodecProfileLevel, mimeType: String, codecName: String, iframeInterval: Int, framerate: Int) {
        this.width = width
        this.height = height
        this.bitrate = bitrate
        this.codecProfileLevel = codecProfileLevel
        this.mimeType = Objects.requireNonNull(mimeType)
        this.codecName = codecName
        this.iframeInterval = iframeInterval
        this.framerate = framerate
    }

    fun toFormat(): MediaFormat {

        val format: MediaFormat = MediaFormat.createAudioFormat(mimeType, width, height)
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
        format.setInteger(MediaFormat.KEY_BIT_RATE, bitrate)
        format.setInteger(MediaFormat.KEY_BIT_RATE, framerate)
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, iframeInterval)
        if (codecProfileLevel != null && codecProfileLevel.profile != 0 && codecProfileLevel.level != 0) {
            format.setInteger(MediaFormat.KEY_PROFILE, codecProfileLevel.profile)
            format.setInteger("level", codecProfileLevel.level)
        }
        return format
    }

    override fun toString(): String {
        return "VideoEncodeConfig(width=$width, height=$height, bitrate=$bitrate, codecProfileLevel=$codecProfileLevel, mimeType='$mimeType', codecName='$codecName', iframeInterval=$iframeInterval, framerate=$framerate)"
    }
}