package com.guoxw.geekproject.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by guoxw on 2017/9/6 0006.
 * @auther guoxw
 * @date 2017/9/6 0006
 * @desciption
 * @package com.guoxw.geekproject.base
 */
abstract class BaseFragment : Fragment() {

    var mContext: Context? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        initData()
        initView()
        initListener()

        return inflater!!.inflate(getLayoutId(), container, false)
    }

    //绑定页面
    abstract fun getLayoutId(): Int

    //初始化页面
    abstract fun initView()

    //初始化data
    abstract fun initData()

    //监听
    abstract fun initListener()


}