package com.guoxw.geekproject

import android.os.Bundle
import android.view.View
import com.guoxw.geekproject.base.BaseToolbarActivity
import com.guoxw.geekproject.map.factory.IMapManager
import com.guoxw.geekproject.map.factory.MapFactory
import kotlinx.android.synthetic.main.activity_map_test.*
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.layout_amap.*
import kotlinx.android.synthetic.main.layout_bmap.*

class MapTestActivity : BaseToolbarActivity() {

    var iMapFactory: IMapManager? = null

    override fun initUI(savedInstanceState: Bundle?) {

        setToolBar(tb_toolbar_base, "地图测试")

        include_map_ali.visibility = View.GONE
        include_map_baidu.visibility = View.VISIBLE

        iMapFactory = MapFactory.create(this, layout_map_baidu)
        iMapFactory!!.mapCreate(savedInstanceState)

    }

    override fun getContentLayoutId(): Int = R.layout.activity_map_test

    override fun initData() {
    }

    override fun initListener() {
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
