package com.guoxw.geekproject

import android.support.multidex.MultiDexApplication
import com.blankj.utilcode.utils.Utils

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
        //不然无法使用工具类
        Utils.init(this)
    }

}