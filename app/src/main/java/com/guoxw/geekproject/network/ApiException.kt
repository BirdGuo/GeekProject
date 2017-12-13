package com.guoxw.geekproject.network

/**
* @auther guoxw
* @date 2017/11/13 0013
* @package ${PACKAGE_NAME}
* @desciption
*/
open class ApiException : RuntimeException {

    companion object {

        /**
         * 错误详情
         */
        private var detailMessage: String = ""

        /**
         * 用户不存在
         */
        val USER_NOT_EXIST: Int = 0x0001
        /**
         * 密码错误
         */
        val WRONG_PASSWORD: Int = 0x0002

        /**
         * 无数据
         */
        private val NO_DATA: Int = 0x0003

        /**
         * 无网络
         */
        private val NO_NETWORK: Int = 0x0004

        /**
         * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
         * 需要根据错误码对错误信息进行一个转换，在显示给用户
         *
         * @param code
         *
         * @return
         */
        private fun getApiExceptionMessage(code: Int): String {

            detailMessage = "未知错误"
            when (code) {
                NO_DATA -> detailMessage = "禽兽，妹子拒绝了你的请求！"
                NO_NETWORK -> detailMessage = "混蛋，没有网还想看妹子！！！"
            }
            return detailMessage
        }
    }

    constructor(message: String?) : super(message)

    constructor(resultCode: Int) : super(getApiExceptionMessage(resultCode))

}