package com.guoxw.geekproject.base

/**
* @auther guoxw
* @date 2017/12/6 0006
* @package ${PACKAGE_NAME}
* @desciption
*/
interface BaseBaseView {

    /**
     * 获取获取数据失败
     * @param error 错误提示语
     */
    fun getDataFail(error: String)

    /**
     * 获取数据完成
     */
    fun getDataComplete()

}