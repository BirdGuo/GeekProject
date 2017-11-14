package com.guoxw.geekproject

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.Gravity
import android.view.KeyEvent
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.services.weather.LocalWeatherForecastResult
import com.amap.api.services.weather.LocalWeatherLiveResult
import com.amap.api.services.weather.WeatherSearch
import com.amap.api.services.weather.WeatherSearchQuery
import com.guoxw.geekproject.base.BaseActivity
import com.guoxw.geekproject.calendar.ui.fargment.CalendarFragment
import com.guoxw.geekproject.constatnt.AppConstants
import com.guoxw.geekproject.gankio.ui.fragment.FragmentGank
import com.guoxw.geekproject.utils.LogUtil
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_main_left.*
import kotlinx.android.synthetic.main.include_title_main.*
import java.util.*


class MainActivity : BaseActivity(), AMapLocationListener {

    /**
     * Gank页面
     */
    private val fragmentGank: FragmentGank = FragmentGank()

    /**
     * 程序猿老黄历页面
     */
    private val calendarFragment: CalendarFragment = CalendarFragment()

    /**
     * 页面列表
     */
    private val mainFragments: MutableList<Fragment> = ArrayList<Fragment>()

    /**
     * 定位终端
     */
    private var mlocationClient: AMapLocationClient? = null

    /**
     * 定位参数
     */
    private val mlocationOption: AMapLocationClientOption = AMapLocationClientOption()

    /**
     * 天气接口
     */
    var mweathersearch: WeatherSearch? = null

    override fun getLayoutId(): Int = R.layout.activity_main


    override fun initView() {

        tv_title_menu.text = "首页"

        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.fl_main_content, mainFragments[0], "main")
        beginTransaction.add(R.id.fl_main_content, mainFragments[1], "cale")
//        beginTransaction.addToBackStack(null)
        beginTransaction.show(mainFragments[0])
        beginTransaction.hide(mainFragments[1])
        beginTransaction.commit()
    }

    override fun initData() {

        initLocation()

        //注意顺序
        mainFragments.add(fragmentGank)
        mainFragments.add(calendarFragment)
    }

    override fun initListener() {

        //主页
        fl_theme_main.setOnClickListener {
            showNewPage(mainFragments[0])
            tv_title_menu.text = "首页"
        }

        //程序猿老黄历
        fl_theme_calendar.setOnClickListener {
            showNewPage(mainFragments[1])
            tv_title_menu.text = "程序猿老黄历"
        }

        //退出
        fl_exit_app.setOnClickListener {
            exitApp()
        }

        //反馈
        fl_feedback.setOnClickListener {
            openActivity(FeedbackActivity::class.java, Bundle())
        }

        //关于
        fl_about_us.setOnClickListener {
            openActivity(AboutActivity::class.java, Bundle())
        }

        //设置
        fl_setting.setOnClickListener {
            openActivity(SettingActivity::class.java, Bundle())
        }

        //menu
        fl_title_main_menu.setOnClickListener {
            dl_main.openDrawer(Gravity.LEFT)
        }

        mweathersearch = WeatherSearch(this)

        //天气接口回调监听
        mweathersearch!!.setOnWeatherSearchListener(object : WeatherSearch.OnWeatherSearchListener {
            override fun onWeatherLiveSearched(result: LocalWeatherLiveResult?, rCode: Int) {
                tv_main_city.text = result!!.liveResult.city
                tv_main_temp.text = result!!.liveResult.temperature.plus("℃")
                tv_main_weather.text = result!!.liveResult.weather.plus(" | 风力").plus(result!!.liveResult.windPower).plus("级")
            }

            override fun onWeatherForecastSearched(localWeatherForecastResult: LocalWeatherForecastResult?, rCode: Int) {
            }

        })

    }

    /**
     * 显示要展示的页面
     * @param fragment
     */
    fun showNewPage(fragment: Fragment) {

        val beginTransaction = supportFragmentManager.beginTransaction()
        hideAllPage(beginTransaction)
        beginTransaction.show(fragment)
        beginTransaction.commit()
        dl_main.closeDrawers()

    }

    /**
     * 隐藏所有fragment
     */
    fun hideAllPage(transaction: FragmentTransaction) {

        mainFragments.forEach { fragment ->
            transaction.hide(fragment)
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click()
        }
        return false
    }

    /**
     * 初始化定位
     */
    fun initLocation() {

        //实例化定位终端
        mlocationClient = AMapLocationClient(this)
        //设置定位监听
        mlocationClient!!.setLocationListener(this)
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mlocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
        // 设置定位间隔
        mlocationOption.interval = 2000
        //需要位置信息
        mlocationOption.isNeedAddress = true
        //设置单次定位
        mlocationOption.isOnceLocation = true
        //设置定位参数
        mlocationClient!!.setLocationOption(mlocationOption)
        //启动定位
        mlocationClient!!.startLocation()

    }

    override fun onLocationChanged(aMapLocation: AMapLocation?) {
        LogUtil.i("GXW", "-------------onLocationChanged-----1-----")

        if (aMapLocation!!.errorCode == 0) {
            LogUtil.i("GXW", "-------------onLocationChanged---2-------")
            aMapLocation.city//获取定位城市
            tv_main_city.text = aMapLocation.city

            initWeatherSearch(aMapLocation.city)

        } else {
            LogUtil.e("GXW", "errorCode:".plus(aMapLocation.errorCode).plus("  errorInfo:").plus(aMapLocation.errorInfo))
        }

    }

    /**
     * 查询天气
     * @param city 查询的城市
     */
    fun initWeatherSearch(city: String) {

        //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
        val mquery = WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE)

        //一次只能查实时数据或者预报数据
        mweathersearch!!.query = mquery
        mweathersearch!!.searchWeatherAsyn() //异步搜索

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == AppConstants.ACCESS_PERMISSION_CODE) {
            initLocation()
        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
