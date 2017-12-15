package com.guoxw.geekproject.map.viewInterfaces

/**
 * @auther guoxw
 * @date 2017/12/14 0014
 * @package com.guoxw.geekproject.map.viewInterfaces
 * @desciption
 */
interface IFileView {

    /**
     * 读取文件成功
     */
    fun readFileToStringSuccess(save: Boolean)

    /**
     * 读取文件失败
     */
    fun readFileToStringFail(error: String)

    /**
     * 读取文件完成
     */
    fun readFileToStringComplete()


}