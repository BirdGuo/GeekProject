package com.guoxw.geekproject

import android.os.Bundle
import com.guoxw.geekproject.base.BaseToolbarActivity
import com.guoxw.geekproject.map.confing.MyMapConfig
import com.guoxw.geekproject.utils.SharedPerfenceUtil
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.include_toolbar.*

class SettingActivity : BaseToolbarActivity() {

    private var intValue = 0x0001

    override fun getContentLayoutId(): Int = R.layout.activity_setting

    override fun initUI(savedInstanceState: Bundle?) {
        setToolBar(tb_toolbar_base, "设置")

        intValue = SharedPerfenceUtil.getIntValue(this, MyMapConfig.LOCFILENAME, MyMapConfig.LOCMAP, MyMapConfig.MAP_CONFIG_ALI)
        when (intValue) {
            MyMapConfig.MAP_CONFIG_ALI -> tv_setting_map.text = "高德地图"
            MyMapConfig.MAP_CONFIG_BAIDU -> tv_setting_map.text = "百度地图"
        }

    }

    override fun initData() {

    }

    override fun initListener() {
        lin_setting_map.setOnClickListener {

            when (intValue) {
                MyMapConfig.MAP_CONFIG_ALI -> {
                    intValue = MyMapConfig.MAP_CONFIG_BAIDU
                    tv_setting_map.text = "百度地图"
                }
                MyMapConfig.MAP_CONFIG_BAIDU -> {
                    intValue = MyMapConfig.MAP_CONFIG_ALI
                    tv_setting_map.text = "高德地图"
                }
            }

            SharedPerfenceUtil.saveIntValue(this, MyMapConfig.LOCFILENAME, MyMapConfig.LOCMAP, intValue)
        }
    }

}
