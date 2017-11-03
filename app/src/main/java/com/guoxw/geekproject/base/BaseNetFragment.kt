package com.guoxw.geekproject.base

import android.view.LayoutInflater
import com.guoxw.geekproject.R
import kotlinx.android.synthetic.main.fragment_base.*

/**
 * Created by guoxw on 2017/11/3 0003.
 */
abstract class BaseNetFragment : BaseFragment() {

    protected abstract fun initUI()
    abstract fun getContentLayoutId(): Int

    override fun getLayoutId(): Int = R.layout.fragment_base

    override fun initView() {
        val contentView = LayoutInflater.from(context).inflate(getContentLayoutId(), null)
        fl_fragment_base.addView(contentView)
        initUI()
    }

}