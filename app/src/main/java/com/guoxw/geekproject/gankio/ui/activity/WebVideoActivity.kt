package com.guoxw.geekproject.gankio.ui.activity

import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseToolbarActivity
import kotlinx.android.synthetic.main.include_toolbar.*

class WebVideoActivity : BaseToolbarActivity() {

    override fun initUI() {
        setToolBar(tb_toolbar_base, "视频")
//        npb_video.progress = 50

    }

    override fun getContentLayoutId(): Int = R.layout.activity_web_video

    override fun initData() {
    }

    override fun initListener() {
    }

}
