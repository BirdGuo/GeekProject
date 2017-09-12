package com.guoxw.geekproject.base

import android.graphics.Color
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.animation.DecelerateInterpolator
import com.guoxw.geekproject.R
import kotlinx.android.synthetic.main.activity_toolbar_base.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * Created by guoxw on 2017/9/8 0008.
 * @auther guoxw
 * @date 2017/9/8 0008
 * @desciption
 * @package com.guoxw.geekproject.base
 */
abstract class BaseToolbarActivity : BaseActivity() {

    protected var isToolBarHiding = false

    protected abstract fun initUI()
    abstract fun getContentLayoutId(): Int

    override fun getLayoutId(): Int = R.layout.activity_toolbar_base

    override fun initView() {
        val contentView = LayoutInflater.from(this).inflate(getContentLayoutId(), null)
        fl_toolbar_base.addView(contentView)
        setToolBar(tb_toolbar_base, "")
        initUI()
    }

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

    protected fun hideOrShowToolBar() {

        (supportActionBar as AppBarLayout)
                .animate()
                .translationY((if (isToolBarHiding) 0 else -supportActionBar!!.height).toFloat())
                .setInterpolator(DecelerateInterpolator(2f))
                .start()
        isToolBarHiding = !isToolBarHiding
    }

}