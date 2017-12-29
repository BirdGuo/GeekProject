package com.guoxw.geekproject.map.confing

/**
 * @auther guoxw
 * @date 2017/12/29 0029
 * @package com.guoxw.geekproject.map.confing
 * @desciption
 */
object MyMapConfig {

    /**
     * 本地高德地图标识
     */
    val MAP_CONFIG_ALI = 0x0001
    /**
     * 本地百度地图标识
     */
    val MAP_CONFIG_BAIDU = 0x0002
    /**
     * 默认config标识
     */
    val MAP_CONFIG_DEFAULT = 0x001


    /**
     * sharedPreferences文件标识
     */
    val LOCFILENAME = "ChooseMap"
    /**
     * sharedPreferences存储地图keyName
     */
    val LOCMAP = "map"

}