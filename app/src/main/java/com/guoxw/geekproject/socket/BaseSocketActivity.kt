package com.guoxw.geekproject.socket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseToolbarActivity
import kotlinx.android.synthetic.main.activity_base_socket.*
import kotlinx.android.synthetic.main.activity_toolbar_base.*

abstract class BaseSocketActivity : BaseToolbarActivity() {

    override fun initUI(savedInstanceState: Bundle?) {
        //找寻视图
        val contentView = LayoutInflater.from(this).inflate(getSocketUILayout(), null)
        //添加到Fragment中
        fl_toolbar_base.addView(contentView)

        initSocketUI()

    }

    override fun getContentLayoutId(): Int = R.layout.activity_base_socket

    protected fun logMessage(message: String) {
        Log.i("GXW","=======1==========")
        runOnUiThread { logMessageDirect(message) }
    }

    protected fun logMessageDirect(message: String) {
        Log.i("GXW","=======2=========="+message)
        tv_socket_log.append(message)
        tv_socket_log.append("\n")
        sc_socket.fullScroll(View.FOCUS_DOWN)
        Log.i("GXW","=======3==========")
    }

    abstract fun initSocketUI()

    abstract fun getSocketUILayout(): Int

}
