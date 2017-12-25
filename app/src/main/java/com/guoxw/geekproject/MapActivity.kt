package com.guoxw.geekproject

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.amap.api.maps.AMap
import com.amap.api.maps.model.*
import com.guoxw.geekproject.base.BaseToolbarActivity
import com.guoxw.geekproject.map.bean.Station
import com.guoxw.geekproject.map.presenter.daoimpl.MapDaoImpl
import com.guoxw.geekproject.map.utils.MapUtil
import com.guoxw.geekproject.map.viewInterfaces.IFileView
import com.guoxw.geekproject.map.viewInterfaces.IMapView
import com.guoxw.geekproject.utils.DistancesUtil
import com.guoxw.geekproject.utils.LogUtil
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.include_toolbar.*
import java.lang.ref.WeakReference

class MapActivity : BaseToolbarActivity(), IMapView, IFileView, AMap.OnCameraChangeListener {


    private var lastLoc: LatLng = LatLng(0.0, 0.0)

    /**
     * 视图内的marker参数
     */
//    val markerOptionsListInView: MutableList<MarkerOptions> = ArrayList()

    /**
     * 所有marker参数
     */
    private val markerOptionsListAll: MutableList<MarkerOptions> = ArrayList()

    private var stations: MutableList<Station> = ArrayList()

    /**
     * 地图上所有marker
     */
    private val markers: MutableList<Marker> = ArrayList()

    /**
     * 点击的marker
     */
    private var clickedMarker: Marker? = null

    /**
     * marker绘制线程
     */
    var create: Observable<MutableList<Station>>? = null

    /**
     * 地图操作接口
     */
    private var mapDaoImpl: MapDaoImpl? = null

    /**
     * 线程操作handler
     */
    private var handler: MyHandler? = null

    /**
     * 地图缩放比例
     */
    private var newZoom = 12.0f

    override fun getContentLayoutId(): Int = R.layout.activity_map

    override fun initUI(savedInstanceState: Bundle?) {
        amap_map.onCreate(savedInstanceState)
        setToolBar(tb_toolbar_base, "地图")

        handler = MyHandler(this)

        initLocation()

    }

    /**
     * 地图定位初始化
     */
    private fun initLocation() {
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。
        //（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        val myLocationStyle = MyLocationStyle()
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW)//只定位一次。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW)//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE)//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE)//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
//以下三种模式从5.1.0版本开始提供
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER)//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
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
        mapDaoImpl = MapDaoImpl(this, this)
        mapDaoImpl!!.readStationsFromAsset(this, "json_station.txt")
    }

    override fun initListener() {

        //定位监听
        amap_map.map.setOnMyLocationChangeListener { location ->
            //从location对象中获取经纬度信息，地址描述信息，建议拿到位置之后调用逆地理编码接口获取（获取地址描述数据章节有介绍）

            val newLoc = LatLng(location.latitude, location.longitude)
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
                    handler!!.sendEmptyMessage(0x0001)

                } else {//未超过
                    //不做处理

                }

            } else {//第一次定位
                //搜索附件
//                selectStations()
                handler!!.sendEmptyMessage(0x0001)
            }
            lastLoc = LatLng(location.latitude, location.longitude)
        }
        //地图视图变化监听
        amap_map.map.setOnCameraChangeListener(this)
        //marker点击监听
        amap_map.map.setOnMarkerClickListener { marker ->

            when {
                marker.title == null -> //其他
                    newZoom = amap_map.map.maxZoomLevel
                marker.title == "-1" -> {//聚合点
                    if (newZoom > 15.0F) {
                        newZoom = 15.0F
                    } else if (newZoom > 13.0F && newZoom <= 15.0F) {
                        newZoom += 16.0F - newZoom
                    } else if ((newZoom >= 10.0F) && (newZoom < 13.0F)) {
                        newZoom += 14.0F - newZoom
                    } else if ((newZoom >= 7.0F) && (newZoom < 10.0F)) {
                        newZoom += 10.0F - newZoom
                    } else if ((newZoom >= 5.0F) && (newZoom < 7.0F)) {
                        newZoom += 7.0F - newZoom
                    } else if (newZoom < 5.0F) {
                        newZoom += 5.0F - newZoom
                    }
                    clickedMarker = marker
                }
                else -> //单个点
                    newZoom = amap_map.map.maxZoomLevel
            }

            MapUtil.moveToPosition(amap_map.map, marker.position.latitude,
                    marker.position.longitude, newZoom)

            true
        }
        //地图加载成功监听事件
        amap_map.map.setOnMapLoadedListener {

        }

    }

    /*--------------------------------- 生命周期 --------------------------------------*/

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        amap_map.onDestroy()
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

    /*--------------------------------- 接口回调 --------------------------------------*/

    override fun readFileToStringSuccess(save: Boolean) {
        selectStations()
    }

    override fun readFileToStringFail(error: String) {
    }

    override fun readFileToStringComplete() {
    }

    override fun selectStationFromDBSuccess(stations: MutableList<Station>) {
        this.stations = stations
        handler!!.sendEmptyMessage(0x0001)
    }

    override fun selectStationFromDBFail(error: String) {
    }

    override fun addStationsSuccess() {
    }

    override fun addStationsFail(error: String) {
    }

    /*--------------------------------- 地图回调 --------------------------------------*/

    /**
     * 视图变化完成
     */
    override fun onCameraChangeFinish(cameraPosition: CameraPosition?) {
        handler!!.sendEmptyMessage(0x0001)
    }

    /**
     * 视图变化中
     */
    override fun onCameraChange(cameraPosition: CameraPosition?) {

    }

    /*--------------------------------- 内部方法 --------------------------------------*/

    /**
     * 从数据库中筛选点
     */
    private fun selectStations() {
        mapDaoImpl!!.selectStationAllFromDB()
    }

    /**
     * 刷新地图点
     */
    private fun refreshMapMarkers() {
        if (stations.isNotEmpty())
            mapDaoImpl!!.addStationsToMap(markerOptionsListAll, stations, this, amap_map.map, markers, clickedMarker)
    }

    /**
     * @SuppressLint("HandlerLeak")
     *
     * 原因：Handler在Android中用于消息的发送与异步处理，常常在Activity中作为一个匿名内部类来定义，
     * 此时Handler会隐式地持有一个外部类对象（通常是一个Activity）的引用。当Activity已经被用户关闭时，
     * 由于Handler持有Activity的引用造成Activity无法被GC回收，这样容易造成内存泄露。
     *
     * 正确的做法是将其定义成一个静态内部类（此时不会持有外部类对象的引用），在构造方法中传入Activity并对Activity对象增加一个弱引用，
     * 这样Activity被用户关闭之后，即便异步消息还未处理完毕，Activity也能够被GC回收，从而避免了内存泄露。
     *
     * 内部类
     * 1.kotlin默认的内部类是静态内部类，不能持有外部类的状态（属性、方法等）
     * 2.给内部类加上inner关键词之后，就会变成非静态内部类，可以访问外部类的属性和方法
     * 3.非静态内部类想访问外部类的属性，可以使用 this@外部类名.外部类属性名 的形式访问
     * 4.非静态内部类可以访问到外部静态内部类的方法和属性，静态内部类访问不到外部所有的属性和方法
     *
     */
    class MyHandler(val activity: MapActivity) : Handler() {

        private var reference: WeakReference<Activity>? = null

        init {
            reference = WeakReference(activity)
        }

        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                0x0001 -> activity.refreshMapMarkers()
            }
        }

    }

}
