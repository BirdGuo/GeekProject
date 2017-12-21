package com.guoxw.geekproject

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.amap.api.maps.AMap
import com.amap.api.maps.model.*
import com.guoxw.geekproject.base.BaseToolbarActivity
import com.guoxw.geekproject.map.bean.Station
import com.guoxw.geekproject.map.presenter.daoimpl.MapDaoImpl
import com.guoxw.geekproject.map.utils.CluterUtil
import com.guoxw.geekproject.map.viewInterfaces.IFileView
import com.guoxw.geekproject.map.viewInterfaces.IMapView
import com.guoxw.geekproject.utils.DistancesUtil
import com.guoxw.geekproject.utils.LogUtil
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.rx2.kotlinextensions.list
import com.raizlabs.android.dbflow.rx2.kotlinextensions.rx
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.include_toolbar.*
import java.util.*
import java.util.concurrent.TimeUnit

class MapActivity : BaseToolbarActivity(), IMapView, IFileView, AMap.OnCameraChangeListener {


    var lastLoc: LatLng = LatLng(0.0, 0.0)

    /**
     * 视图内的marker参数
     */
//    val markerOptionsListInView: MutableList<MarkerOptions> = ArrayList()

    /**
     * 所有marker参数
     */
    val markerOptionsListAll: MutableList<MarkerOptions> = ArrayList()

    /**
     * 地图上所有marker
     */
    val markers: MutableList<Marker> = ArrayList()

    /**
     * 点击的marker
     */
    var clickedMarker: Marker? = null

    /**
     * marker绘制线程
     */
    var create: Observable<MutableList<Station>>? = null

    var handler: Handler = object : Handler() {

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg!!.what) {

                0x0001 -> refreshMapMarkers()

            }
        }

    }

    override fun getContentLayoutId(): Int = R.layout.activity_map

    override fun initUI(savedInstanceState: Bundle?) {
        amap_map.onCreate(savedInstanceState)
        setToolBar(tb_toolbar_base, "地图")

        initLocation()

        val mapDaoImpl = MapDaoImpl(this, this)
        mapDaoImpl.readStationsFromAsset(this, "json_station.txt")

    }

    /**
     * 地图定位初始化
     */
    private fun initLocation() {
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。
        //（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        var myLocationStyle = MyLocationStyle()
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW)//只定位一次。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW)//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE)//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE)//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
//以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER)//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER)//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER)//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.interval(2000)//设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        //设置定位蓝点style
        amap_map.map.myLocationStyle = myLocationStyle
        //设置默认定位按钮是否显示，非必需设置。
        amap_map.map.uiSettings.isMyLocationButtonEnabled = false
        amap_map.map.uiSettings.isZoomControlsEnabled = false
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        amap_map.map.isMyLocationEnabled = true

    }

    override fun initData() {

        selectStations()

    }

    override fun initListener() {

        //定位监听
        amap_map.map.setOnMyLocationChangeListener { location ->
            //从location对象中获取经纬度信息，地址描述信息，建议拿到位置之后调用逆地理编码接口获取（获取地址描述数据章节有介绍）

            var newLoc = LatLng(location.latitude, location.longitude)
            if (lastLoc.latitude != 0.0 && lastLoc.longitude != 0.0) {

                val distance = DistancesUtil.getDistance(lastLoc, newLoc)
                //判断前后定位距离 单位km
                if (distance > 0.5) {//前后定位超过500米
                    //重新搜索
                    //一种写法
                    //val stations = SQLite.select().from(Station::class.java).where(Station_Table.id.eq(1)).queryList()
                    //val stations = (select from Station::class where Station_Table.id.eq(1)).list

//                    (select from Station::class).list
                    LogUtil.i("GXW", "------------- setOnMyLocationChangeListener ------------")
//                    selectStations()
                    handler.sendEmptyMessage(0x0001)

                } else {//未超过
                    //不做处理

                }

            } else {//第一次定位
                //搜索附件
//                selectStations()
                handler.sendEmptyMessage(0x0001)
            }
            lastLoc = LatLng(location.latitude, location.longitude)
        }
        //地图视图变化监听
        amap_map.map.setOnCameraChangeListener(this)
        //marker点击监听
        amap_map.map.setOnMarkerClickListener { marker ->
            clickedMarker = marker
            false
        }
        //地图加载成功监听事件
        amap_map.map.setOnMapLoadedListener {

        }

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

    override fun readFileToStringSuccess(save: Boolean) {
        LogUtil.i("GXW", "readFileToStringSuccess:".plus(save))
    }

    override fun readFileToStringFail(error: String) {
        Log.i("GXW", "-------------readFileToStringFail-----------:".plus(error))
    }

    override fun readFileToStringComplete() {
    }

    override fun addStations() {
    }

    /**
     * 视图变化完成
     */
    override fun onCameraChangeFinish(cameraPosition: CameraPosition?) {
//        selectStations()
        handler.sendEmptyMessage(0x0001)
    }

    /**
     * 视图变化中
     */
    override fun onCameraChange(cameraPosition: CameraPosition?) {

    }

    /**
     * 添加单个点到地图上
     * @param station 单个点
     */
    private fun addStationToMap(station: Station) {
        LogUtil.i("GXW", "it".plus(station.address))
        val markerOptions = MarkerOptions()
        markerOptions.position(LatLng(station.lat, station.lon))//设置坐标
                .title(station.address)//设置标题
                .snippet("DefaultMarker")//
                .draggable(false).isFlat = false
        amap_map.map.addMarker(markerOptions)
    }

    /**
     * 异步添加点集合到地图上
     * @param list 点集合
     */
    private fun addStationsListAsycn(list: MutableList<Station>) {

        create = Observable.create<MutableList<Station>> { t ->
            try {
                //如果放在进行点的绘制固然不会卡主线程，但是由于异步的操作下可能会对List造成同时访问，产生异常
                //放到这会卡顿主线程，但是不会产生同时访问的异常
                //通过synchronized锁定List,不能同时修改
                synchronized(markerOptionsListAll, {

                    markerOptionsListAll.clear()
                    list.forEach {
                        //遍历所有station
//                    addStationToMap(it)

                        //把筛选出来的数据转换为marker参数
                        val markerOptions: MarkerOptions = MarkerOptions()
                        markerOptions.position(LatLng(it.lat, it.lon))//设置位置
                        markerOptions.title(it.address)//设置标题
                        markerOptionsListAll.add(markerOptions)

                    }
                    //聚合
                    CluterUtil.resetMarks(this, amap_map.map,
                            markerOptionsListAll, markers, clickedMarker)

                })

                t.onNext(list)
            } catch (e: Exception) {
                t.onError(e)
            } finally {
                t.onComplete()
            }
        }

    }

    /**
     * 从数据库中筛选点
     */
    private fun selectStations() {
        select.from(Station::class.java).rx().list { list ->
            addStationsListAsycn(list)
        }
    }

    /**
     * 刷新地图点
     */
    private fun refreshMapMarkers() {
        create!!.throttleLast(5, TimeUnit.SECONDS)//防止重复 在每次事件触发后的一定时间间隔内丢弃旧的事件。常用作去抖动过滤
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe({ list ->
                    LogUtil.i("GXW", "list size:".plus(list.size))
                }, { error ->
                    LogUtil.e("GXW", error.message!!)
                }, {
                    //完成
                    LogUtil.i("GXW", "-------complete------")
                })
    }

}
