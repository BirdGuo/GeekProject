package com.guoxw.geekproject.base

import android.view.LayoutInflater
import com.guoxw.geekproject.R
import kotlinx.android.synthetic.main.activity_toolbar_base.*

/**
 * Created by guoxw on 2017/9/8 0008.
 * @auther guoxw
 * @date 2017/9/8 0008
 * @desciption
 * @package com.guoxw.geekproject.base
 */
abstract class BaseToolbarActivity : BaseActivity() {


    protected abstract fun initUI()
    abstract fun getContentLayoutId(): Int

    override fun getLayoutId(): Int = R.layout.activity_toolbar_base

    override fun initView() {
        val contentView = LayoutInflater.from(this).inflate(getContentLayoutId(), null)
        fl_toolbar_base.addView(contentView)
        setToolBar(tb_toolbar_base, "")
        initUI()
    }


}