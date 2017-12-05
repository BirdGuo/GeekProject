package com.guoxw.geekproject.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Base64
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebSettings
import android.webkit.WebViewClient
import java.io.InputStream


/**
 * Created by guoxw on 2017/12/5 0005.
 *
 * 修改自darkeet的FLoveVideoView
 * @link https://github.com/drakeet/Meizhi/blob/master/app%2Fsrc%2Fmain%2Fjava%2Fme%2Fdrakeet%2Fmeizhi%2Fwidget%2FLoveVideoView.java
 */
class LoveVideoView : WebView {

    var mContext: Context? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.mContext = context
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, privateBrowsing: Boolean) : super(context, attrs, defStyleAttr, privateBrowsing)

    fun init() {

        webViewClient = LoveClient(this, mContext!!)
        val webSettings = settings
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.databaseEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.saveFormData = false
        webSettings.setAppCacheEnabled(true)
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.loadWithOverviewMode = false
        webSettings.useWideViewPort = true
    }


    class LoveClient(val loveVideoView: LoveVideoView, val context: Context) : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view!!.loadUrl(request!!.url.toString())
            }
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            if (url!!.contains("www.vmovier.com")) {
                loveVideoView.injectCSS("vmovier.css")
            } else if (url.contains("video.weibo.com")) {
                loveVideoView.injectCSS("weibo.css")
            } else if (url.contains("m.miaopai.com")) {
                loveVideoView.injectCSS("miaopai.css")
            }
        }
    }

    fun injectCSS(fileName: String) {
        val inputStream: InputStream = context.assets.open(fileName)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()
        val encoded: String = Base64.encodeToString(buffer, Base64.NO_WRAP)
        loadUrl("javascript:(function() {" +
                "var parent = document.getElementsByTagName('head').item(0);" +
                "var style = document.createElement('style');" +
                "style.type = 'text/css';" +
                // Tell the browser to BASE64-decode the string into your script !!!
                "style.innerHTML = window.atob('" + encoded + "');" +
                "parent.appendChild(style)" +
                "})()")
    }

}