package com.guoxw.geekproject.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.guoxw.geekproject.R
import com.guoxw.geekproject.gankio.presenter.WebPresenter
import com.guoxw.geekproject.gankio.ui.views.IWebView
import kotlinx.android.synthetic.main.activity_base_web.*

class BaseWebActivity : AppCompatActivity(), IWebView {

    /**
     * 数据层接口
     */
    private var webPresenter: WebPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_web)

        //获得访问链接
        val data: Bundle = intent.getBundleExtra("data")
        val url: String = data.getString("url")

        //初始化接口
        webPresenter = WebPresenter(this, this)
        //设置webview
        webPresenter!!.setWebViewSettings(web_base, url)
    }

    override fun showProgressBar(progress: Int) {
    }

    override fun setWebTitle(title: String) {
    }

    override fun openFailed() {
    }


}
