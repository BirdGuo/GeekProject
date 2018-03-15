package com.guoxw.geekproject.screenrecorder

import android.media.*
import android.media.MediaCodec.INFO_OUTPUT_FORMAT_CHANGED
import android.os.*
import android.os.Build.VERSION_CODES.N
import android.util.Log
import com.guoxw.geekproject.utils.LogUtil
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @auther guoxw
 * @date 2018/3/15 0015
 * @package com.guoxw.geekproject.screenrecorder
 * @desciption
 */
class MicRecorder : Encoder {

    val TAG: String = "MicRecorder"
    val VERBOSE: Boolean = false

    var mEncoder: AudioEncoder
    var mRecordThread: HandlerThread
    var mMic: AudioRecord? = null//access in mRecordThread only
    var mSampleRate: Int
    var mChannelConfig: Int
    var mFormat: Int = AudioFormat.ENCODING_PCM_16BIT
    var mRecordHandler: RecordHandler? = null

    var mForceStop: AtomicBoolean = AtomicBoolean(false)
    var mCallback: BaseEncoder.Callback? = null
    var mChannelsSampleRate: Int

    var mCallbackDelegate: CallbackDelegate? = null

    private val MSG_PREPARE = 0
    private val MSG_FEED_INPUT = 1
    private val MSG_DRAIN_OUTPUT = 2
    private val MSG_RELEASE_OUTPUT = 3
    private val MSG_STOP = 4
    private val MSG_RELEASE = 5


    constructor(config: AudioEncodeConfig) {
        this.mEncoder = AudioEncoder(config)
        this.mSampleRate = config.sampleRate
        mChannelsSampleRate = mSampleRate * config.channelCount
        if (VERBOSE) LogUtil.i(TAG, "in bitrate ".plus(mChannelsSampleRate * 16 /* PCM_16BIT*/))
        this.mChannelConfig = if (config.channelCount == 2) AudioFormat.CHANNEL_IN_STEREO else AudioFormat.CHANNEL_IN_MONO
        this.mRecordThread = HandlerThread(TAG)
    }

    override fun prepare() {
        val myLooper = Objects.requireNonNull(Looper.myLooper(), "Should prepare in HandlerThread")
        mCallbackDelegate = CallbackDelegate(myLooper, mCallback!!)
        mRecordThread.start()
        mRecordHandler = RecordHandler(mRecordThread.looper)
    }

    override fun stop() {

        mCallbackDelegate!!.removeCallbacksAndMessages(null)
        mForceStop.set(true)
        mRecordHandler?.sendEmptyMessage(MSG_STOP)

    }

    override fun release() {
        mRecordHandler?.sendEmptyMessage(MSG_RELEASE)
        mRecordThread.quitSafely()
    }

    override fun setCallback(callback: Encoder.Callback) {

        this.mCallback = callback as BaseEncoder.Callback
    }

    inner class RecordHandler(looper: Looper) : Handler(looper) {

        val mCachedInfos: LinkedList<MediaCodec.BufferInfo> = LinkedList()
        val mMuxingOutputBufferIndices: LinkedList<Int> = LinkedList()
        val mPollRate: Int = 2048_000 / mSampleRate


        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                MSG_PREPARE -> {
                }
                MSG_FEED_INPUT -> {
                }
                MSG_DRAIN_OUTPUT -> {
                }
                MSG_RELEASE_OUTPUT -> {
                }
                MSG_STOP -> {
                }
                MSG_RELEASE -> {
                }
            }
        }

        fun offerOutput() {
            while (!mForceStop.get()) {
                var info: MediaCodec.BufferInfo? = mCachedInfos.poll()
                if (info == null) {
                    info = MediaCodec.BufferInfo()
                }
                val index = mEncoder.mEncoder!!.dequeueOutputBuffer(info, 1)
                if (VERBOSE) LogUtil.d(TAG, "audio encoder returned output buffer index=" + index)
                if (index == INFO_OUTPUT_FORMAT_CHANGED) {
                    mCallbackDelegate.onOutputFormatChanged(mEncoder, mEncoder.mEncoder!!.outputFormat)
                }
                if (index < 0) {
                    info.set(0, 0, 0, 0)
                    mCachedInfos.offer(info)
                    break
                }
                mMuxingOutputBufferIndices.offer(index)
                mCallbackDelegate.onOutputBufferAvailable(mEncoder, index, info)

            }
        }

        fun pollInput(): Int {
            return mEncoder.mEncoder!!.dequeueInputBuffer(0)
        }

        fun pollInputIfNeed() {
            if (mMuxingOutputBufferIndices.size <= 1 && !mForceStop.get()) {
                removeMessages(MSG_FEED_INPUT)
            }
        }

    }

    inner class CallbackDelegate : Handler {

        private val mCallback: BaseEncoder.Callback

        constructor(looper: Looper?, mCallback: BaseEncoder.Callback) : super(looper) {
            this.mCallback = mCallback
        }

        fun onError(encoder: Encoder, exception: Exception) {
            Message.obtain(this, {
                mCallback?.onError(encoder, exception)
            }).sendToTarget()
        }

        fun onOutputFormatChanged(encoder: BaseEncoder, format: MediaFormat) {
            Message.obtain(this, {
                mCallback?.onOutputFormatChanged(encoder, format)
            }).sendToTarget()
        }

        fun onOutputBufferAvailable(encoder: BaseEncoder, index: Int, info: MediaCodec.BufferInfo) {
            Message.obtain(this, {
                mCallback?.onOutputBufferAvailable(encoder, index, info)
            }).sendToTarget()
        }
    }

    fun createAudioRecord(sampleRateInHz: Int, channelConfig: Int, audioFormat: Int): AudioRecord? {

        val minBytes: Int = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat)
        if (minBytes < 0) {
            LogUtil.e(TAG, String.format(Locale.CHINA, "Bad arguments: getMinBufferSize(%d, %d, %d)",
                    sampleRateInHz, channelConfig, audioFormat))
            return null
        }
        val record = AudioRecord(MediaRecorder.AudioSource.MIC, sampleRateInHz, channelConfig, audioFormat, minBytes * 2)
        if (record.state == AudioRecord.STATE_UNINITIALIZED) {
            LogUtil.e(TAG, String.format(Locale.US, "Bad arguments to new AudioRecord %d, %d, %d",
                    sampleRateInHz, channelConfig, audioFormat))
            return null
        }
        if (VERBOSE) {
            LogUtil.i(TAG, "created AudioRecord $record, MinBufferSize= $minBytes")
            if (Build.VERSION.SDK_INT >= N) {
                LogUtil.d(TAG, " size in frame " + record.bufferSizeInFrames)
            }
        }
        return record
    }

}