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
     * @param mContext 上下文
     * @param fileName 文件名
     * @param keyName 存储变量名
     * @param value 存储变量值
     */
    fun saveIntValue(mContext: Context, fileName: String, keyName: String, value: Int) {
        val sharedPreferences = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putInt(keyName, value)
        edit.commit()
    }

    /**
     * 获取本地保存int数据
     * @param mContext 上下文
     * @param fileName 文件名
     * @param keyName 存储变量名
     * @param default 默认值
     *
     * @return value
     */
    fun getIntValue(mContext: Context, fileName: String, keyName: String, default: Int): Int {
        val sharedPreferences = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(keyName, default)
    }

}