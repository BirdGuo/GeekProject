package com.guoxw.geekproject.utils

import android.content.Context
import java.io.InputStream
import java.lang.ref.WeakReference

/**
 * @auther guoxw
 * @date 2017/12/13 0013
 * @package com.guoxw.geekproject.utils
 * @desciption
 */
object FileUtil {

    private var exReference: WeakReference<Exception>? = null

    /**
     * 读取Assets下文件，并转换为字符串
     * @param mContext
     * @param name
     * @return
     */
    fun assetToString(mContext: Context, name: String): String? {
        val assestToBytes = assestToBytes(mContext, name)
        return String(assestToBytes!!)
    }

    /**
     * 读取assets下name文件返回字节数组
     * @param mContext
     * @param name
     * @return buf 失败返回Null
     */
    private fun assestToBytes(mContext: Context, name: String): ByteArray? {
        var inputStream: InputStream? = null
        try {
            inputStream = mContext.assets.open(name)
            val buf = ByteArray(inputStream.available())
            inputStream.read(buf)
            return buf
        } catch (e: Exception) {
            setLastException(e)
        } finally {
            try {
                inputStream!!.close()
            } catch (e: Exception) {
            }
        }
        return null
    }

    private fun setLastException(e: Exception) {
        exReference = WeakReference(e)
    }

}