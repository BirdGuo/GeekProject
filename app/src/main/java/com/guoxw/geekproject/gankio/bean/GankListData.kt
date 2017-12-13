package com.guoxw.geekproject.gankio.bean

import java.io.Serializable

/**
* @auther guoxw
* @date 2017/10/31 0031
* @package ${PACKAGE_NAME}
* @desciption
*/
data class GankListData(val name: String, val datas: MutableList<GankData>) : Serializable