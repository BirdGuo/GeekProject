package com.guoxw.geekproject

import android.os.Bundle
import com.guoxw.geekproject.base.BaseToolbarActivity
import kotlinx.android.synthetic.main.include_toolbar.*

class AboutActivity : BaseToolbarActivity() {

    override fun getContentLayoutId(): Int = R.layout.activity_about

    override fun initUI(savedInstanceState: Bundle?) {
        setToolBar(tb_toolbar_base, "关于")
    }

    override fun initData() {
    }

    override fun initListener() {
    }

}
