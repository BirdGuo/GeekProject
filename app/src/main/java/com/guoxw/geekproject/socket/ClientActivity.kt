package com.guoxw.geekproject.socket

import com.guoxw.geekproject.R
import com.guoxw.geekproject.jniutil.ClientUtil
import kotlinx.android.synthetic.main.activity_client.*
import kotlinx.android.synthetic.main.include_toolbar.*

class ClientActivity : BaseSocketActivity() {

    override fun getSocketUILayout(): Int = R.layout.activity_client

    override fun initSocketUI() {

        setToolBar(tb_toolbar_base, "客户端")

    }

    override fun initData() {
    }

    override fun initListener() {
        btn_client_sure.setOnClickListener { view ->
            if (et_client_ip.text.isNotEmpty() && et_client_port.text.isNotEmpty() && et_client_message.text.isNotEmpty()) {

                Thread(Runnable {

                    try {
                        ClientUtil.nativeStartTcpClient(this,et_client_ip.text.toString(), et_client_port.text.toString().toInt(), et_client_message.text.toString())
                    } catch (e: Exception) {
                        logMessage(e.message!!)
                    }

                }).start()

            }
        }
    }


}