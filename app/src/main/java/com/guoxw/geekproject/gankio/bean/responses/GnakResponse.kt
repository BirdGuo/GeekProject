package com.guoxw.geekproject.gankio.data.responses

/**
 * Created by guoxw on 2017/5/25.
 * @auther guoxw
 * @date 2017/5/25
 * @desciption
 * @package ${PACKAGE_NAME}
 */
class GankResponse<T>(val error: Boolean, val results: T) {
    override fun toString(): String {
        return "GankResponse(error=$error, result=$results)"
    }
}