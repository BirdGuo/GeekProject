package com.guoxw.geekproject

import com.guoxw.geekproject.base.BaseToolbarActivity
import kotlinx.android.synthetic.main.include_toolbar.*

class FeedbackActivity : BaseToolbarActivity() {

    override fun getContentLayoutId(): Int = R.layout.activity_feedback

    override fun initUI() {
        setToolBar(tb_toolbar_base, "意见反馈")
    }

    override fun initData() {
    }

    override fun initListener() {
    }

}
