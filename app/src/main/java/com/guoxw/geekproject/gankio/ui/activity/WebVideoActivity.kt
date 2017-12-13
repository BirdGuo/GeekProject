package com.guoxw.geekproject.gankio.ui.activity

import android.os.Bundle
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseToolbarActivity
import com.guoxw.geekproject.gankio.presenter.impl.WebViewDaoImpl
import com.guoxw.geekproject.gankio.ui.views.IWebView
import com.guoxw.geekproject.utils.LogUtil
import kotlinx.android.synthetic.main.activity_web_video.*
import kotlinx.android.synthetic.main.include_toolbar.*

class WebVideoActivity : BaseToolbarActivity(), IWebView {

    private var videoUrl: String = ""

    override fun showProgressBar(progress: Int) {

        LogUtil.i("GXW", "progress:".plus(progress))

    }

    override fun setWebTitle(title: String) {
        setToolBar(tb_toolbar_base, title)
    }

    override fun openFailed() {
    }

    override fun initUI(savedInstanceState: Bundle?) {
        setToolBar(tb_toolbar_base, "视频")
//        npb_video.progress = 50
//        web_video.init()
//        web_video.init()
        val webViewDaoImpl = WebViewDaoImpl(this, this)
        webViewDaoImpl.loadWebVideo(web_video, videoUrl)

    }

    override fun getContentLayoutId(): Int = R.layout.activity_web_video

    override fun initData() {
        val data = intent.getBundleExtra("data")
        videoUrl = data.getString("url")
    }

    override fun initListener() {
    }

    override fun onResume() {
        super.onResume()
        web_video.resumeTimers()
        web_video.onResume()
    }

    override fun onPause() {
        super.onPause()
        web_video.onPause()
        web_video.pauseTimers()
    }

    override fun onDestroy() {
        super.onDestroy()
        web_video.webViewClient = null
        web_video.webChromeClient = null
        web_video.destroy()
    }

}
