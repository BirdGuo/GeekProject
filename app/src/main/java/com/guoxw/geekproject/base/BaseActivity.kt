package com.guoxw.geekproject.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.VelocityTracker
import android.view.View
import android.view.Window
import android.widget.Toast
import com.guoxw.geekproject.R
import com.guoxw.geekproject.constatnt.AppConstants.ACCESS_PERMISSION_CODE
import com.guoxw.geekproject.utils.LogUtil
import com.guoxw.geekproject.utils.ToastUtil
import rx.subscriptions.CompositeSubscription
import java.util.*


/**
 * Created by guoxw on 2017/9/5 0005.
 * @auther guoxw
 * @date 2017/9/5 0005
 * @desciption
 * @package com.guoxw.geekproject.base
 */
abstract class BaseActivity : AppCompatActivity() {

    val BTAG: String = BaseActivity::class.java.name

    /**
     * 要申请的权限
     */
    val permissions: Array<String> = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CHANGE_WIFI_STATE)

    var decorView: View? = null

    /**
     * 双击退出标识
     *    true 可以退出
     *    false 无法退出
     */
    var isExit = false

    var mCompositeSubscription: CompositeSubscription? = null

    //用于从左边滑动到右边关闭的变量
    var endX: Int = 0
    var startX: Int = 0
    var deltaX: Int = 0
    var endY: Int = 0
    var startY: Int = 0
    var deltaY: Int = 0

    /**
     * 静态成员
     */
    companion object {
        //管理运行的所有activity
        var mActivities: ArrayList<AppCompatActivity> = ArrayList()
        //单例
        var activity: BaseActivity? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(getLayoutId())

//        mBinding = DataBindingUtil.setContentView(this,getLayoutId())
//        mBinding = createDataBinding(savedInstanceState)
        decorView = window.decorView

        initPermission()

        initData()
        initView()
        initListener()

        synchronized(mActivities) {
            mActivities.add(this)
        }
    }


    /**
     * 绑定页面
     * @return getLayoutId 绑定页面id
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化页面
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 添加监听
     */
    abstract fun initListener()

    override fun onPause() {
        super.onPause()
        activity = null
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        activity = this
    }

    override fun onDestroy() {
        super.onDestroy()

        synchronized(mActivities) {
            mActivities.remove(this)
        }

        if (mCompositeSubscription != null && mCompositeSubscription!!.hasSubscriptions()) {
            mCompositeSubscription!!.unsubscribe()
        }

    }

    /**
     * 打开页面
     *
     * @param activity 需要打开的页面
     * @param bundle 传的值
     */
    fun openActivity(activity: Class<*>, bundle: Bundle?) {
        val intent: Intent = Intent()
        if (bundle != null) {
            intent.putExtra("data", bundle)
        }
        intent.setClass(this, activity)
        startActivity(intent)
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
    }

    /**
     * 关闭所有Activity
     */
    fun finishAll() {
        //复制一份mActivities
        var copy: List<AppCompatActivity> = ArrayList()
        synchronized(mActivities) {
            copy = ArrayList(mActivities)
        }
        //结束所有
        copy.forEach { it.finish() }
        //杀死当前进程
        android.os.Process.killProcess(android.os.Process.myPid())

    }

    /**
     * 关闭所有Activity，除了参数传递的Activity
     * @param except 不需要关闭的activity
     */
    fun finishAll(except: AppCompatActivity) {
        var copy: List<AppCompatActivity> = ArrayList()
        synchronized(mActivities) {
            copy = ArrayList(mActivities)
        }

        //遍历的写法
        copy.asSequence()
                .filter { it !== except }
                .forEach { it.finish() }
    }

    /**
     * 退出应用
     */
    fun exitApp() {
        finishAll()
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    /**
     * 双击退出
     */
    fun exitBy2Click() {
        var tExit: Timer? = null
        if (!isExit) {
            isExit = true // 准备退出
            //吐司提示语
            Toast.makeText(applicationContext, R.string.double_click_exit, Toast.LENGTH_SHORT).show()
            //创建计时器
            tExit = Timer()
            //计时线程
            tExit.schedule(object : TimerTask() {
                override fun run() {
                    // 取消退出
                    isExit = false
                }
            }, 2000) // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            //退出App
            exitApp()
        }
    }

    /**
     * 判断权限
     */
    fun initPermission() {
        //判断权限是否大于6.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions.asSequence()
                    .filter { ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED }
                    .forEach {
                        ActivityCompat.requestPermissions(this, permissions, ACCESS_PERMISSION_CODE)
                    }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_PERMISSION_CODE) {
            //带下标遍历数组
            for ((index, grantResult) in grantResults.withIndex()) {
                //未获得权限
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.toastLong(this, permissions[index].plus("未获得权限"))
                }
            }

        }

    }

}