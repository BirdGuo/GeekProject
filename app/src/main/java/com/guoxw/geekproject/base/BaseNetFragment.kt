package com.guoxw.geekproject.base

import android.view.LayoutInflater
import com.guoxw.geekproject.R.id.fl_fragment_base

/**
 * Created by guoxw on 2017/11/3 0003.
 */
abstract class BaseNetFragment<R, T : BasePresenter<R, BaseView<R>>> : BaseFragment<R, T>() {

    /**
     * 初始化页面
     */
    protected abstract fun initUI()

    /**
     * 设置布局
     * @return getContentLayoutId 绑定页面id
     */
    abstract fun getContentLayoutId(): Int

    override fun getLayoutId(): Int = R.layout.fragment_base

    override fun initView() {
        //查找页面
        val contentView = LayoutInflater.from(context).inflate(getContentLayoutId(), null)
        //往Fragment中添加页面
        fl_fragment_base.addView(contentView)
        //初始化页面
        initUI()
    }

}