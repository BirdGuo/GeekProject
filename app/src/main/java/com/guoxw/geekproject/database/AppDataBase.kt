package com.guoxw.geekproject.database

import com.raizlabs.android.dbflow.annotation.Database

/**
 * @auther guoxw
 * @date 2017/12/15 0015
 * @package com.guoxw.geekproject.database
 * @desciption
 */
@Database(name = AppDataBase.NAME, version = AppDataBase.VERSION)
object AppDataBase {

    const val NAME = "GeekDataBase"
    const val VERSION = 1


}
