package com.guoxw.geekproject.screenrecorder

import android.media.MediaCodecInfo

/**
 * @auther guoxw
 * @date 2018/3/13 0013
 * @package com.guoxw.geekproject.screenrecorder
 * @desciption
 */
class VideoEncodeConfig {

    var width: Int
    val height: Int
    val bitrate: Int
    val codecProfileLevel: MediaCodecInfo.CodecProfileLevel
    val mimeType: String
    val codecName: String
    val iframeInterval: Int
    val framerate: Int

}