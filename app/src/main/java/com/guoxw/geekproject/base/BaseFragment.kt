package com.guoxw.geekproject.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.utils.NetworkUtils
import com.guoxw.geekproject.enums.FragmentLifeCycleEvent
import rx.subjects.PublishSubject

/**
 * Created by guoxw on 2017/9/6 0006.
 * @auther guoxw
 * @date 2017/9/6 0006
 * @desciption
 * @package com.guoxw.geekproject.base
 */
abstract class BaseFragment : Fragment() {

    val lifecycleSubject: PublishSubject<FragmentLifeCycleEvent> = PublishSubject.create<FragmentLifeCycleEvent>()

    var mContext: Context? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(getLayoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        lifecycleSubject.onNext(FragmentLifeCycleEvent.CREATE)

        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
        initListener()

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
     * 初始化data
     */
    abstract fun initData()

    /**
     * 添加监听
     */
    abstract fun initListener()

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
        intent.setClass(mContext, activity)
        startActivity(intent)
//        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
    }

    override fun onStart() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.START)
        super.onStart()
    }

    override fun onResume() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.RESUME)
        super.onResume()
    }

    override fun onPause() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.STOP)
        super.onStop()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.DESTROY)
        super.onDestroy()
    }

}