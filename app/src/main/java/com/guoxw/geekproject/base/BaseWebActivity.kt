package com.guoxw.geekproject.base

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.guoxw.geekproject.R
import com.guoxw.geekproject.gankio.presenter.WebPresenter
import com.guoxw.geekproject.gankio.ui.views.IWebView
import kotlinx.android.synthetic.main.activity_base_web.*

class BaseWebActivity : AppCompatActivity(), IWebView {

    var webPresenter: WebPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_web)

        val data: Bundle = intent.getBundleExtra("data")
        val url: String = data.getString("url")

        webPresenter = WebPresenter(this, this)

        webPresenter!!.setWebViewSettings(web_base, url)
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun showProgressBar(progress: Int) {
    }

    override fun setWebTitle(title: String) {
    }

    override fun openFailed() {
    }


}
