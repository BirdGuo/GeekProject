package com.guoxw.geekproject.screenrecorder

import android.media.MediaCodec
import android.media.MediaFormat
import android.os.Looper
import android.util.Log
import java.io.IOException
import java.nio.ByteBuffer
import java.util.*

/**
 * @auther guoxw
 * @date 2018/3/13 0013
 * @package com.guoxw.geekproject.screenrecorder
 * @desciption
 */
abstract class BaseEncoder(val mCodecName: String) : Encoder {

    var mCallback: Callback? = null
    var mEncoder: MediaCodec? = null

    companion object {

        abstract class Callback : Encoder.Callback {

            fun onInputBufferAvailable(encoder: BaseEncoder, index: Int) {}

            fun onOutputFormatChanged(encoder: BaseEncoder, format: MediaFormat) {}

            fun onOutputBufferAvailable(encoder: BaseEncoder, index: Int, info: MediaCodec.BufferInfo) {}

        }

    }

    override fun setCallback(callback: Encoder.Callback) {
        if (callback !is Callback) {
            throw IllegalArgumentException()
        }
        this.mmSetCallback(callback)
    }

    fun mmSetCallback(callback: Callback) {
        if (this.mEncoder != null) {
            throw IllegalStateException("mEncoder is not null")
        }
        this.mCallback = callback
    }

    override fun prepare() {

        if (Looper.myLooper() == null || Looper.myLooper() == Looper.getMainLooper()) {
            throw IllegalStateException("should run in a HandlerThread")
        }

        if (mEncoder != null) {
            throw IllegalStateException("prepared!")
        }

        val format: MediaFormat = createMediaFormat()
        var mimeType: String = format.getString(MediaFormat.KEY_MIME)

        val encoder = createEncoder(mimeType)
        try {
            encoder.setCallback(if (this.mCallback == null) null else mCodecCallback)
            encoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
            onEncoderConfigured(encoder)
            encoder.start()

        } catch (e: Exception) {
            Log.e("Encoder", "Configure codec failure!\n  with format$format", e)
            throw e
        }

        mEncoder = encoder
    }

    protected fun onEncoderConfigured(encoder: MediaCodec) {}

    /**
     * 创建一个新的MediaCodec
     * @param type
     */
    @Throws(IOException::class)
    private fun createEncoder(type: String): MediaCodec {

        try {
            if (this.mCodecName != null) {
                return MediaCodec.createByCodecName(mCodecName)
            }
        } catch (e: Exception) {
            Log.w("GXW", "Create MediaCodec by name '".plus(mCodecName).plus("'"))
        }
        return MediaCodec.createEncoderByType(type)
    }

    /**
     * create {@link MediaFormat} for {@link MediaCodec}
     */
    protected abstract fun createMediaFormat(): MediaFormat

    protected fun getEncoder(): MediaCodec {
        return Objects.requireNonNull(mEncoder!!, "doesn't prpare()")
    }

    /**
     * 获取输出流
     * @param index
     */
    fun getOutputBuffer(index: Int): ByteBuffer {
        return getEncoder().getOutputBuffer(index)
    }

    /**
     * 获取输入流
     * @param index
     */
    fun getInputBuffer(index: Int): ByteBuffer {
        return getEncoder().getInputBuffer(index)
    }

    fun queueInputBuffer(index: Int, offset: Int, size: Int, pstTs: Long, flags: Int) {
        getEncoder().queueInputBuffer(index, offset, size, pstTs, flags)
    }

    fun releaseOutputBuffer(index: Int) {
        getEncoder().releaseOutputBuffer(index, false)
    }

    override fun stop() {
        if (mEncoder != null) {
            mEncoder!!.stop()
        }
    }

    override fun release() {
        if (mEncoder != null) {
            mEncoder!!.release()
            mEncoder = null
        }
    }

    private val mCodecCallback: MediaCodec.Callback = object : MediaCodec.Callback() {
        override fun onOutputBufferAvailable(codec: MediaCodec?, index: Int, info: MediaCodec.BufferInfo?) {
            mCallback!!.onOutputBufferAvailable(this@BaseEncoder, index, info!!)
        }

        override fun onInputBufferAvailable(codec: MediaCodec?, index: Int) {
            mCallback!!.onInputBufferAvailable(this@BaseEncoder, index)

        }

        override fun onOutputFormatChanged(codec: MediaCodec?, format: MediaFormat?) {
            mCallback!!.onOutputFormatChanged(this@BaseEncoder, format!!)
        }

        override fun onError(codec: MediaCodec?, e: MediaCodec.CodecException?) {
            mCallback!!.onError(this@BaseEncoder, e!!)
        }
    }

}