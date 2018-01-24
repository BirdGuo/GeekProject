package com.guoxw.geekproject.jniui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.guoxw.geekproject.jniutil.JNIUIUtil
import kotlinx.android.synthetic.main.activity_jni_ui.*

import android.view.View

class JniUIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_jni_ui)

        JNIUIUtil.nativec(this)

        btn_jni_sure.setOnClickListener {


        }

    }
}
