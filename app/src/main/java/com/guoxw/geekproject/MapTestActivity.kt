package com.guoxw.geekproject

import android.os.Bundle
import android.view.View
import com.guoxw.geekproject.base.BaseToolbarActivity
import com.guoxw.geekproject.map.confing.MyMapConfig
import com.guoxw.geekproject.map.factory.IMapManager
import com.guoxw.geekproject.map.factory.MapFactory
import com.guoxw.geekproject.utils.SharedPerfenceUtil
import kotlinx.android.synthetic.main.activity_map_test.*
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.layout_amap.*
import kotlinx.android.synthetic.main.layout_bmap.*

class MapTestActivity : BaseToolbarActivity() {

    var iMapFactory: IMapManager? = null

    override fun initUI(savedInstanceState: Bundle?) {

        val intValue = SharedPerfenceUtil.getIntValue(this, MyMapConfig.LOCFILENAME, MyMapConfig.LOCMAP, MyMapConfig.MAP_CONFIG_ALI)

        var title = "高德地图"
        when (intValue) {
            MyMapConfig.MAP_CONFIG_ALI -> {
                title = "高德地图"
                include_map_ali.visibility = View.VISIBLE
                include_map_baidu.visibility = View.GONE
                iMapFactory = MapFactory.create(this, layout_map_ali)
            }
            MyMapConfig.MAP_CONFIG_BAIDU -> {
                title = "百度地图"
                include_map_ali.visibility = View.GONE
                include_map_baidu.visibility = View.VISIBLE
                iMapFactory = MapFactory.create(this, layout_map_baidu)
            }
        }
        setToolBar(tb_toolbar_base, "地图测试")
        iMapFactory!!.mapCreate(savedInstanceState)

    }

    override fun getContentLayoutId(): Int = R.layout.activity_map_test

    override fun initData() {


    }

    override fun initListener() {

    }

    fun changeMapToAli() {
        iMapFactory = MapFactory.create(this, layout_map_ali)

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        iMapFactory!!.mapDestroy()
    }

    override fun onPause() {
        super.onPause()
        iMapFactory!!.mapPause()
    }

    override fun onResume() {
        super.onResume()
        iMapFactory!!.mapResume()
    }

}
