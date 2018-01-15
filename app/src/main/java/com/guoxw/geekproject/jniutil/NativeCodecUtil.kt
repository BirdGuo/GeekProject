package com.guoxw.geekproject.jniutil

import android.content.res.AssetManager
import android.view.Surface

/**
 * @auther guoxw
 * @date 2018/1/15 0015
 * @package com.guoxw.geekproject.jniutil
 * @desciption
 */
object NativeCodecUtil {

    init {
        System.loadLibrary("native-lib")
    }

    external fun createEngine()

    external fun createStreamingMediaPlayer(assetManager: AssetManager, fileName: String): Boolean

    external fun setPlayingStreamingMediaPlayer(isPlaying: Boolean)

    external fun shutdown()

    external fun setSurface(surface: Surface)

    external fun rewindStreamingMediaPlayer()

}