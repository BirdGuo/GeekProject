package com.guoxw.geekproject.screenrecorder

import android.hardware.display.DisplayManager
import android.media.MediaCodec
import android.media.MediaFormat
import android.media.projection.MediaProjection
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import com.guoxw.geekproject.utils.LogUtil
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicBoolean
import android.media.MediaMuxer
import java.io.IOException
import android.hardware.display.VirtualDisplay


class ScreenRecorder {

    val TAG = ScreenRecorder::class.java.name
    private val INVALID_INDEX = -1

    private var mWidth: Int
    private var mHeight: Int
    private var mDpi: Int
    private var mDstPath: String
    private var mp: MediaProjection
    private var mVideoEncoder: VideoEncoder
    private var mAudioEncoder: MicRecorder?

    private var mForceQuit = AtomicBoolean(false)
    private var mIsRunning: AtomicBoolean = AtomicBoolean(false)

    private var mWorker: HandlerThread? = null
    private var mHandler: CallbackHander? = null

    lateinit var mCallback: Callback

    private val mVirtualDisplay: VirtualDisplay? = null

    private val mProjectionCallback: MediaProjection.Callback = object : MediaProjection.Callback() {
        override fun onStop() {
            super.onStop()
        }
    }

    private var mVideoTrackIndex = INVALID_INDEX

    private var mAudioTrackIndex = INVALID_INDEX
    private var mVideoOutputFormat: MediaFormat? = null
    private var mAudioOutputFormat: MediaFormat? = null

    private var mMuxer: MediaMuxer? = null
    private val mMuxerStarted = false

    private val MSG_START = 0
    private val MSG_STOP = 1
    private val MSG_ERROR = 2
    private val STOP_WITH_EOS = 1

    constructor(video: VideoEncodeConfig, audio: AudioEncodeConfig, dpi: Int,
                mp: MediaProjection, dsPath: String) {
        this.mWidth = video.width
        this.mHeight = video.height
        this.mDpi = dpi
        this.mp = mp
        this.mDstPath = dsPath
        this.mVideoEncoder = VideoEncoder(video)
        this.mAudioEncoder = if (audio == null) null else MicRecorder(audio)
    }

    fun quit() {
        mForceQuit.set(true)
        if (!mIsRunning.get()) {
            release()
        } else {
            signalStop(false)
        }
    }

    public fun start() {
        if (mWorker != null) throw IllegalStateException()
        mWorker = HandlerThread(TAG)
        mWorker!!.start()
        mHandler = CallbackHander(mWorker!!.looper)
        mHandler!!.sendEmptyMessage(MSG_START)
    }

    fun getSavedPath(): String {
        return mDstPath
    }

    private fun release() {

    }

    private fun signalStop(stopWithEOS: Boolean) {
        val msg: Message = Message.obtain(mHandler, MSG_STOP, if (stopWithEOS) STOP_WITH_EOS else 0, 0)
        mHandler!!.sendMessageAtFrontOfQueue(msg)
    }

    interface Callback {
        fun onStop(error: Throwable)

        fun onStart()

        fun onRecording(presentationTimeUs: Long)
    }

    inner class CallbackHander(looper: Looper?) : Handler(looper) {

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg!!.what) {
                MSG_START -> {
                    try {
                        record()
                        if (mCallback != null) {
                            mCallback.onStart()
                        }
                    } catch (e: Exception) {
                        msg.obj = e
                    }
                }
                MSG_STOP, MSG_ERROR -> {
                    stopEncoders()
                    if (msg.arg1 != STOP_WITH_EOS) signalEndOfStream()
                    if (mCallback != null) mCallback.onStop(msg.obj as Throwable)
                    release()
                }
            }
        }
    }

    private fun record() {
        if (mIsRunning.get() || mForceQuit.get()) throw IllegalStateException()

        if (mp == null) throw IllegalStateException("maybe release")

        mIsRunning.set(true)
        mp.registerCallback(mProjectionCallback, mHandler)

        //mp4格式
        mMuxer = MediaMuxer(mDstPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)

        try {
            prepareVideoEncoder()
            prepareAudioEncoder()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        mp.createVirtualDisplay("$TAG-display", mWidth, mHeight, mDpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC, mVideoEncoder.getInputSurface(),
                null, null)

        LogUtil.i(TAG, "create virtual display: ${mVirtualDisplay!!.display}")

    }

    private fun muxVideo(index: Int, buffer: MediaCodec.BufferInfo) {
        if (!mIsRunning.get()) {
            LogUtil.w(TAG, "muxVideo:Already stopped!")
            return
        }

        if (!mMuxerStarted || mVideoTrackIndex == INVALID_INDEX) {

        }
    }

    private fun stopEncoders() {}

    private fun signalEndOfStream() {
        val eos: MediaCodec.BufferInfo = MediaCodec.BufferInfo()
        var buffer: ByteBuffer = ByteBuffer.allocate(0)
        eos.set(0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
        LogUtil.i(TAG, "Signal EOS to muxer ")
        if (mVideoTrackIndex != INVALID_INDEX) {
            writeSampleData(mVideoTrackIndex, eos, buffer)
        }

        if (mAudioTrackIndex != INVALID_INDEX) {
            writeSampleData(mAudioTrackIndex, eos, buffer)
        }

        mVideoTrackIndex = INVALID_INDEX
        mAudioTrackIndex = INVALID_INDEX

    }

    private fun writeSampleData(track: Int, buffer: MediaCodec.BufferInfo, encodedData: ByteBuffer) {

    }

    @Throws(IOException::class)
    private fun prepareVideoEncoder() {


    }

    @Throws(IOException::class)
    private fun prepareAudioEncoder() {
    }

}