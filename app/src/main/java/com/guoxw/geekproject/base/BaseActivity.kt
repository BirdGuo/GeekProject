package com.guoxw.geekproject.base

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.VelocityTracker
import android.view.View
import android.view.Window
import com.guoxw.geekproject.R
import rx.subscriptions.CompositeSubscription
import java.util.*

/**
 * Created by guoxw on 2017/9/5 0005.
 * @auther guoxw
 * @date 2017/9/5 0005
 * @desciption
 * @package com.guoxw.geekproject.base
 */
/**
 * Created by guoxw on 2017/5/24.
 * @auther guoxw
 * @date 2017/5/24
 * @desciption
 * @package ${PACKAGE_NAME}
 */
abstract class BaseActivity : AppCompatActivity() {

    val BTAG: String = BaseActivity::class.java.name

    var isClose: Boolean = true
    var decorView: View? = null
    var mVelocityTracker: VelocityTracker = VelocityTracker.obtain()

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

        initData()
        initView()
        initListener()

        synchronized(mActivities) {
            mActivities.add(this)
        }
    }


    //绑定页面
    abstract fun getLayoutId(): Int

    //初始化页面
    abstract fun initView()

    //初始化data
    abstract fun initData()

    //监听
    abstract fun initListener()

    /**
     * 设置title
     * 子类可以直接用
     *
     * @param toolbar
     * @param title
     */
    protected fun setToolBar(toolbar: Toolbar, title: String) {
        //设置标题
        toolbar.title = title
        setSupportActionBar(toolbar)
        //标题颜色
        toolbar.setTitleTextColor(Color.WHITE)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)//显示返回按钮
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        //返回按钮监听事件
        toolbar.setNavigationOnClickListener {
            //返回
            onBackPressed()
        }
    }

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
     *
     */
    fun finishAll(except: AppCompatActivity) {
        var copy: List<AppCompatActivity> = ArrayList()
        synchronized(mActivities) {
            copy = ArrayList(mActivities)
        }

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



}