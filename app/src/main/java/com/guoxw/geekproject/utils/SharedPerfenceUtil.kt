package com.guoxw.geekproject.utils

import android.content.Context

/**
 * @auther guoxw
 * @date 2017/12/29 0029
 * @package com.guoxw.geekproject.utils
 * @desciption
 */
object SharedPerfenceUtil {

    /**
     * 保存int数据
     * @param mContext
     * @param fileName
     * @param keyName
     * @param value
     */
    fun saveIntValue(mContext: Context, fileName: String, keyName: String, value: Int) {
        val sharedPreferences = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putInt(keyName, value)
        edit.commit()
    }

    /**
     * 获取本地保存int数据
     * @param mContext
     * @param fileName
     * @param keyName
     * @param default
     *
     * @return value
     */
    fun getIntValue(mContext: Context, fileName: String, keyName: String, default: Int): Int {
        val sharedPreferences = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(keyName, default)
    }

}