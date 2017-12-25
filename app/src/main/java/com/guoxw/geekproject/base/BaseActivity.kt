package com.guoxw.geekproject.base

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.Toast
import com.guoxw.geekproject.network.LifeSubscription
import com.guoxw.geekproject.network.Stateful
import com.guoxw.geekproject.R
import com.guoxw.geekproject.constatnt.AppConstants.ACCESS_PERMISSION_CODE
import com.guoxw.geekproject.enums.ActivityLifeCycleEvent
import com.guoxw.geekproject.utils.ToastUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription
import java.util.*


/**
 * Created by guoxw on 2017/9/5 0005.
 * @auther guoxw
 * @date 2017/9/5 0005
 * @desciption
 * @package com.guoxw.geekproject.base
 */
abstract class BaseActivity : AppCompatActivity(), LifeSubscription, Stateful {

    /**
     * 生命周期状态接收者
     *
     * PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者。
     * 需要注意的是，PublishSubject可能会一创建完成就立刻开始发射数据（除非你可以阻止它发生），
     * 因此这里有一个风险：在Subject被创建后到有观察者订阅它之前这个时间段内，
     * 一个或多个数据可能会丢失。如果要确保来自原始Observable的所有数据都被分发，
     * 你需要这样做：或者使用Create创建那个Observable,
     * 以便手动给它引入"冷"Observable的行为（当所有观察者都已经订阅时才开始发射数据），
     * 或者改用ReplaySubject。
     */
    private val lifecycleSubject: PublishSubject<ActivityLifeCycleEvent> = PublishSubject.create<ActivityLifeCycleEvent>()

    /**
     * 要申请的权限
     */
    private val permissions: Array<String> = arrayOf(Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CHANGE_WIFI_STATE)

    private var decorView: View? = null

    /**
     * 双击退出标识
     *    true 可以退出
     *    false 无法退出
     */
    var isExit = false

    var mCompositeSubscription: CompositeSubscription? = null
    /**
     * 线程
     */
    private var compositeDisposable: CompositeDisposable? = null

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
        @SuppressLint("StaticFieldLeak")
//单例
        var activity: BaseActivity? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE)

        super.onCreate(savedInstanceState)
        //去除原有的Titile
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //装载布局
        setContentView(getLayoutId())
        decorView = window.decorView

        //初始化权限
        initPermission()

        initData()
        initView(savedInstanceState)
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
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 添加监听
     */
    abstract fun initListener()

    override fun onPause() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE)
        super.onPause()
        activity = null
    }

    override fun onStart() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.START)
        super.onStart()
    }

    override fun onResume() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.RESUME)
        super.onResume()
        activity = this
    }

    override fun onStop() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.STOP)
        super.onStop()
        if (compositeDisposable != null && compositeDisposable!!.size() > 0) {
            compositeDisposable!!.dispose()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY)
        synchronized(mActivities) {
            mActivities.remove(this)
        }

        if (compositeDisposable != null && compositeDisposable!!.size() > 0) {
            compositeDisposable!!.dispose()
        }

    }

    /**
     * 打开页面
     *
     * @param activity 需要打开的页面
     * @param bundle 传的值
     */
    fun openActivity(activity: Class<*>, bundle: Bundle?) {
        val intent = Intent()
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
    private fun finishAll() {
        //复制一份mActivities
        var copy: List<AppCompatActivity> = ArrayList()
        synchronized(mActivities) {
            copy = ArrayList(mActivities)
        }
        //结束所有
        copy.forEach { it.finish() }
        //杀死当前进程
//        android.os.Process.killProcess(android.os.Process.myPid())

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
        //这句话是否需要
//        android.os.Process.killProcess(android.os.Process.myPid())
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
    private fun initPermission() {
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


    override fun bindCompositeDisposable(disposable: Disposable) {
//        if (compositeDisposable == null) {
//            compositeDisposable = CompositeDisposable()
//        }
//        compositeDisposable!!.add(disposable)
    }

    override fun setState(state: Int) {
    }
}