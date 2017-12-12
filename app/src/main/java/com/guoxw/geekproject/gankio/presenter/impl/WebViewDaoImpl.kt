package com.guoxw.geekproject.gankio.presenter.impl

import android.content.Context
import android.media.MediaPlayer
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.guoxw.geekproject.gankio.ui.views.IWebView

/**
 * Created by guoxw on 2017/12/12 0012.
 */
class WebViewDaoImpl(val mContext: Context, val iView: IWebView) {

    fun loadWebVideo(webView: WebView, url: String) {

        webView.webChromeClient = Chrome(iView)
        webView.loadUrl(url)

    }

    class Chrome(val iView: IWebView) : WebChromeClient(), MediaPlayer.OnCompletionListener {

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