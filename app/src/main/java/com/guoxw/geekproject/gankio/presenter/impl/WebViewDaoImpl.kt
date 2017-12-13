package com.guoxw.geekproject.gankio.presenter.impl

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.guoxw.geekproject.gankio.ui.views.IWebView

/**
* @auther guoxw
* @date 2017/12/12 0012
* @package ${PACKAGE_NAME}
* @desciption
*/
class WebViewDaoImpl(val mContext: Context, val iView: IWebView) {

    @SuppressLint("SetJavaScriptEnabled")
    fun loadWebVideo(webView: WebView, url: String) {

        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.setAppCacheEnabled(true)
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.setSupportZoom(true)
        webView.loadUrl(url)

        webView.webChromeClient = Chrome()
        webView.webViewClient = GankClient()

    }
//
    private inner class GankClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            if (url != null) view.loadUrl(url)
            return true
        }
    }

    /**
     * 内部类的时候用加上inner
     */
    private inner class Chrome : WebChromeClient(), MediaPlayer.OnCompletionListener {

        override fun onCompletion(mp: MediaPlayer?) {

            if (mp != null) {
                if (mp.isPlaying) mp.stop()
                mp.reset()
                mp.release()
            }

        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            iView.showProgressBar(newProgress)
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            iView.setWebTitle(title!!)
        }

    }

}