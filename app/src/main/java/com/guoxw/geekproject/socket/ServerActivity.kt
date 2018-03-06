package com.guoxw.geekproject.socket

import com.guoxw.geekproject.R
import com.guoxw.geekproject.jniutil.ServerUtil
import com.guoxw.geekproject.utils.NetWorkUtil
import kotlinx.android.synthetic.main.activity_server.*
import kotlinx.android.synthetic.main.include_toolbar.*

class ServerActivity : BaseSocketActivity() {

    override fun initSocketUI() {
        setToolBar(tb_toolbar_base, "服务端")

        tv_server_ip.text = NetWorkUtil.getIPAddress(this)
    }

    override fun getSocketUILayout(): Int = R.layout.activity_server

    override fun initData() {
    }

    override fun initListener() {

        btn_server_start.setOnClickListener {

            Thread(Runnable {
                try {
                    ServerUtil.nativeStartTcpServer(et_server_port.text.toString().toInt())
                } catch (e: Exception) {
                }
            }).start()


        }
    }
}
