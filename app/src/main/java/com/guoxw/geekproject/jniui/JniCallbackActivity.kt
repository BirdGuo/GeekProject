package com.guoxw.geekproject.jniui

import android.os.Bundle
import android.support.annotation.Keep
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseToolbarActivity
import com.guoxw.geekproject.jniutil.JNICallbackUtil
import kotlinx.android.synthetic.main.activity_jni_callback.*
import kotlinx.android.synthetic.main.include_toolbar.*

class JniCallbackActivity : BaseToolbarActivity() {

    private var hour: Int = 0
    private var minute: Int = 0
    private var second: Int = 0

    override fun getContentLayoutId(): Int = R.layout.activity_jni_callback

    override fun initUI(savedInstanceState: Bundle?) {

        setToolBar(tb_toolbar_base, "计时器")

    }

    override fun initData() {

    }

    override fun initListener() {
    }

    override fun onResume() {
        super.onResume()
        JNICallbackUtil.startTicks()
        tv_hello_jnicallback.text = JNICallbackUtil.stringFromJNI()
    }

    override fun onPause() {
        super.onPause()
        JNICallbackUtil.stopTicks()
    }

    @Keep
    private fun updateTimer() {
        //加一秒
        ++second
        if (second >= 60) {
            //加一分钟
            ++minute
            //减60秒
            second -= 60
            if (minute >= 60) {
                //加一小时
                ++hour
                //减60分钟
                minute -= 60
            }
        }
        runOnUiThread {
            var tick: String = "".plus(hour).plus(":").plus(minute).plus(":").plus(second)
            tv_jnicallback_clock.text = tick
        }
    }

}
