package com.guoxw.geekproject

import android.support.multidex.MultiDexApplication
import com.baidu.mapapi.SDKInitializer
import com.blankj.utilcode.utils.Utils
import com.guoxw.geekproject.database.AppDataBase
import com.raizlabs.android.dbflow.config.DatabaseConfig
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager

/**
 * Created by guoxw on 2017/10/30 0030.
 * @auther guoxw
 * @date 2017/10/30 0030
 * @desciption
 * @package com.guoxw.geekproject
 */
class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        //初始化工具类不然无法使用工具类
        Utils.init(this)

        //初始化数据库
        FlowManager.init(FlowConfig.builder(this)
                .addDatabaseConfig(DatabaseConfig.builder(AppDataBase::class.java).build())
                .build())

        SDKInitializer.initialize(this)
    }

}