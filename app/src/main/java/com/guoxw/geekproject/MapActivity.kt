package com.guoxw.geekproject

import android.os.Bundle
import com.guoxw.geekproject.base.BaseToolbarActivity
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.include_toolbar.*

class MapActivity : BaseToolbarActivity() {

    override fun getContentLayoutId(): Int = R.layout.activity_map

    override fun initUI(savedInstanceState: Bundle?) {
        amap_map.onCreate(savedInstanceState)
        setToolBar(tb_toolbar_base, "地图")



    }

    override fun initData() {

    }

    override fun initListener() {
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        amap_map.onDestroy()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        amap_map.onPause()
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        amap_map.onResume()
    }

    override fun onRestart() {
        super.onRestart()
    }

}
