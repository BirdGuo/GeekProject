package com.guoxw.geekproject.jniutil

/**
 * @auther guoxw
 * @date 2018/1/3 0003
 * @package com.guoxw.geekproject.jniutil
 * @desciption
 */
object HexUtil {

    init {
        System.loadLibrary("native-lib")
    }

    /**
     * 十六进制编码
     * @param string
     * @return array
     */
    external fun hexDecode(string: String): ByteArray

    /**
     * 十六进制解码
     * @param array
     * @return string
     */
    external fun hexEncode(array: ByteArray): String
}