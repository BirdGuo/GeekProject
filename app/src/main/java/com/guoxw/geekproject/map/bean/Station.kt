package com.guoxw.geekproject.map.bean

import com.guoxw.geekproject.database.AppDataBase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import java.io.Serializable

/**
 * @auther guoxw
 * @date 2017/12/15 0015
 * @package com.guoxw.geekproject.map.bean
 * @desciption
 */
@Table(database = AppDataBase::class, name = "Station")
data class Station(@PrimaryKey var id: Int = 0, @Column var lat: Double = 0.0, @Column var lon: Double = 0.0, @Column var address: String = "") : Serializable