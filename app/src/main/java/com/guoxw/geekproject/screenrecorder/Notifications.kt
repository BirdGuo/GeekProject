package com.guoxw.geekproject.screenrecorder

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES.O
import android.os.SystemClock
import android.text.format.DateUtils
import com.guoxw.geekproject.R

/**
 * @auther guoxw
 * @date 2018/4/2 0002
 * @package com.guoxw.geekproject.screenrecorder
 * @desciption
 */
class Notifications : ContextWrapper {

    private val id = 0x1fff
    private val CHANNEL_ID: String = "Recording"
    private val CHANNEL_NAME: String = "Screen Recorder Notifications"

    internal val ACTION_STOP = "com.guoxw.geekproject.screenrecorder.action.STOP"

    private var mLastFiredTime: Int = 0
    private var mManager: NotificationManager? = null
    private var mStopAction: Notification.Action? = null
    private var mBuilder: Notification.Builder? = null

    constructor(base: Context?) : super(base) {
        if (Build.VERSION.SDK_INT >= O) {
            createNotificationChannel()
        }
    }

    public fun recording(timeMs: Long) {
        if (SystemClock.elapsedRealtime() - mLastFiredTime < 1000) {
            return
        }
        val notification = getBuilder().setContentText("Length: " + DateUtils.formatElapsedTime(timeMs / 1000))
                .build()
        getNotificationManager().notify(id, notification)
        mLastFiredTime = SystemClock.elapsedRealtime().toInt()
    }

    private fun getBuilder(): Notification.Builder {

        if (mBuilder == null) {
            val builder: Notification.Builder = Notification.Builder(this)
                    .setContentTitle("Recording...")
                    .setOngoing(true)
                    .setLocalOnly(true)
                    .setOnlyAlertOnce(true)
                    .addAction(stopAction())
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_stat_recording)

            if (Build.VERSION.SDK_INT >= O) {
                builder.setChannelId(CHANNEL_ID).setUsesChronometer(true)
            }
            mBuilder = builder
        }
        return mBuilder!!
    }

    @TargetApi(O)
    private fun createNotificationChannel() {
        val channel: NotificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
        channel.setShowBadge(false)
        getNotificationManager().createNotificationChannel(channel)
    }

    private fun stopAction(): Notification.Action {
        if (mStopAction == null) {
            var intent = Intent(ACTION_STOP)
            val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_ONE_SHOT)
            mStopAction = Notification.Action(android.R.drawable.ic_media_pause, "Stop", pendingIntent)
        }
        return mStopAction!!
    }

    fun clear() {
        mLastFiredTime = 0
        mBuilder = null
        mStopAction = null
        getNotificationManager().cancelAll()
    }

    fun getNotificationManager(): NotificationManager {
        if (mManager == null) {
            mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return mManager!!
    }
}