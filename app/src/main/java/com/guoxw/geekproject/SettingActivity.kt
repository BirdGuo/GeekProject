package com.guoxw.geekproject

import android.os.Bundle
import com.guoxw.geekproject.base.BaseToolbarActivity
import kotlinx.android.synthetic.main.include_toolbar.*

class SettingActivity : BaseToolbarActivity() {

    override fun getContentLayoutId(): Int = R.layout.activity_setting

    override fun initUI(savedInstanceState: Bundle?) {
        setToolBar(tb_toolbar_base, "设置")
    }

    override fun initData() {

    }

    override fun initListener() {
    }

}
