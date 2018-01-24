package com.guoxw.geekproject.jniui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.guoxw.geekproject.jniutil.JNIUIUtil
import com.guoxw.geekproject.utils.LogUtil
import kotlinx.android.synthetic.main.activity_jni_ui.*

class JniUIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_jni_ui)

        JNIUIUtil.nativec(this)

        var packageName = JNIUIUtil.getPackageName(this)

       var newpackage = packageName.replace(".", "/")

        LogUtil.i("GXW", "replace :".plus(newpackage))

        btn_jni_sure.setOnClickListener {

            LogUtil.i("GXW", "package name in java".plus(JNIUIUtil.getPackageName(this)))

        }

    }
}
