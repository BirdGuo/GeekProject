package com.guoxw.geekproject.jniutil

/**
 * @auther guoxw
 * @date 2018/3/1 0001
 * @package com.guoxw.geekproject.jniutil
 * @desciption
 */
object ClientUtil {

    init {
        System.loadLibrary("native-lib")
    }

    /**
     *
     * @param ip
     * @param port
     * @param message
     * @throws Exception
     */
    @Throws(Exception::class)
    external fun nativeStartTcpClient(ip: String, port: Int, message: String)

    /**
     *
     * @param ip
     * @param port
     * @param message
     * @throws Exception
     */
    @Throws(Exception::class)
    external fun nativeStartUdpClient(ip: String, port: Int, message: String)
}