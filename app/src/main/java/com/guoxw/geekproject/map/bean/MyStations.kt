package com.guoxw.geekproject.map.bean

import com.guoxw.geekproject.database.AppDataBase
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table

/**
 * @auther guoxw
 * @date 2017/12/15 0015
 * @package com.guoxw.geekproject.map.bean
 * @desciption DBFlow 不支持List的存储
 */
@Table(database = AppDataBase::class, name = "MyStations")
data class MyStations(var stations: ArrayList<Any> = arrayListOf(), @PrimaryKey var version: Int = 1)