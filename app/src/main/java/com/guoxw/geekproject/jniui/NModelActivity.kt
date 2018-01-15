package com.guoxw.geekproject.jniui

import android.os.Bundle
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseToolbarActivity
import kotlinx.android.synthetic.main.include_toolbar.*

class NModelActivity : BaseToolbarActivity() {

    override fun initUI(savedInstanceState: Bundle?) {
        setToolBar(tb_toolbar_base, "NNAPI_DEMO")
    }

    override fun getContentLayoutId(): Int = R.layout.activity_nmodel

    override fun initData() {

    }

    override fun initListener() {
    }

}
