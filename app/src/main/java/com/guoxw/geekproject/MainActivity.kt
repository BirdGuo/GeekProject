package com.guoxw.geekproject

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.Gravity
import android.view.KeyEvent
import com.amap.api.services.weather.LocalWeatherForecastResult
import com.amap.api.services.weather.LocalWeatherLiveResult
import com.amap.api.services.weather.WeatherSearch
import com.amap.api.services.weather.WeatherSearchQuery
import com.guoxw.geekproject.base.BaseActivity
import com.guoxw.geekproject.calendar.ui.fargment.CalendarFragment
import com.guoxw.geekproject.constatnt.AppConstants
import com.guoxw.geekproject.gankio.ui.fragment.FragmentGank
import com.guoxw.geekproject.jniui.JniUIActivity
import com.guoxw.geekproject.jniutil.HexUtil
import com.guoxw.geekproject.jniutil.JNIUtil
import com.guoxw.geekproject.jniutil.UninstallUtil
import com.guoxw.geekproject.map.LocationTypeMode
import com.guoxw.geekproject.map.bean.MyLocation
import com.guoxw.geekproject.map.factory.LocationFactory
import com.guoxw.geekproject.map.factory.interfaces.MyILocation
import com.guoxw.geekproject.utils.LogUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_main_left.*
import kotlinx.android.synthetic.main.include_title_main.*
import java.util.*


class MainActivity : BaseActivity(), MyILocation {


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
    private val mainFragments: MutableList<Fragment> = ArrayList()

    /**
     * 天气接口
     */
    private var mweathersearch: WeatherSearch? = null

    override fun getLayoutId(): Int = R.layout.activity_main


    override fun initView(savedInstanceState: Bundle?) {

//        LogUtil.i("GXW", "From CPP:".plus(JNIUtil.stringFromJNI()))

        val s: ByteArray = byteArrayOf(0x3c, 0x7c)
        val t = HexUtil.hexEncode(s)

        val b = HexUtil.hexDecode(t)
        val d = Arrays.toString(b)
        JNIUtil.printLogInfo("你好世界！".plus(t))
        JNIUtil.printLogInfo("你好世界！22".plus(d))
        tv_title_menu.text = "首页"

        //fragment操作器
        val beginTransaction = supportFragmentManager.beginTransaction()
        //添加gank页面
        beginTransaction.add(R.id.fl_main_content, mainFragments[0], "main")
        //添加日历页面
        beginTransaction.add(R.id.fl_main_content, mainFragments[1], "cale")
//        beginTransaction.addToBackStack(null)
        //显示gank页面
        beginTransaction.show(mainFragments[0])
        //隐藏日历页面
        beginTransaction.hide(mainFragments[1])
        //提交操作
        beginTransaction.commit()
    }

    override fun initData() {
        val pid1 = android.os.Process.myPid()
        LogUtil.i("GXWPP", "pid1:".plus(pid1))

        LogUtil.i("GXWPP","data/data/".plus(this.packageName))
        UninstallUtil.callUnInstallListener(Build.VERSION.SDK_INT, "data/data/com.guoxw.geekproject-1")
        val pid = android.os.Process.myPid()
        LogUtil.i("GXWPP", "pid2:".plus(pid))

        initLocation()

        //注意顺序
        mainFragments.add(fragmentGank)//添加gank页面
        mainFragments.add(calendarFragment)//添加日历页面
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

        //地图聚合
        fl_theme_map.setOnClickListener {
            openActivity(MapActivity::class.java, Bundle())
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

        //地图测试
        fl_map_test.setOnClickListener {
            openActivity(MapTestActivity::class.java, Bundle())
        }

        //设置
        fl_setting.setOnClickListener {
            openActivity(SettingActivity::class.java, Bundle())
        }

        //设置
        fl_jni.setOnClickListener {
            openActivity(JniUIActivity::class.java, Bundle())
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
                tv_main_temp.text = result.liveResult.temperature.plus("℃")
                tv_main_weather.text = result.liveResult.weather.plus(" | 风力").plus(result.liveResult.windPower).plus("级")
            }

            override fun onWeatherForecastSearched(localWeatherForecastResult: LocalWeatherForecastResult?, rCode: Int) {
            }

        })

    }

    /**
     * 显示要展示的页面
     * @param fragment
     */
    private fun showNewPage(fragment: Fragment) {

        val beginTransaction = supportFragmentManager.beginTransaction()
        hideAllPage(beginTransaction)
        beginTransaction.show(fragment)
        beginTransaction.commit()
        dl_main.closeDrawers()

    }

    /**
     * 隐藏所有fragment
     */
    private fun hideAllPage(transaction: FragmentTransaction) {

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
    private fun initLocation() {

        //初始化定位工厂类
        val create = LocationFactory.create(LocationTypeMode.AMapMode, this, true, this)
        //设置定位参数
        create.initLcationOption(2000)
        //开始定位
        create.startLocation()

    }

    override fun locationSuccess(myLocation: MyLocation) {
        tv_main_city.text = myLocation.city
        initWeatherSearch(myLocation.city)
    }

    override fun locationFail(error: String) {
        LogUtil.i("GXW", "errorInfo:".plus(error))
    }

    /**
     * 查询天气
     * @param city 查询的城市
     */
    private fun initWeatherSearch(city: String) {

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
}
