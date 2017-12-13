package com.guoxw.geekproject.gankio.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.guoxw.geekproject.R
import com.guoxw.geekproject.gankio.ui.views.IWebView
import com.guoxw.geekproject.utils.StringUtil


/**
* @auther guoxw
* @date 2017/11/7 0007
* @package ${PACKAGE_NAME}
* @desciption
*/
class WebPresenter(val mContext: Context, val iView: IWebView) {

    @SuppressLint("SetJavaScriptEnabled")
            /**
     * 设置wifi默认设置
     */
    fun setWebViewSettings(webView: WebView, url: String) {
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.setAppCacheEnabled(true)
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.setSupportZoom(true)
        webView.webChromeClient = ChromeClient()
        webView.webViewClient = GankClient()
        webView.loadUrl(url)
    }

    /**
     * 刷新
     * @param webView 刷新试图
     */
    fun reflash(webView: WebView) {
        webView.reload()
    }

    /**
     * 复制链接
     * @param url
     */
    fun copyUrl(url: String) {
        StringUtil.copyToClipBoard(mContext, url, mContext.getString(R.string.copy_success))
    }

    /**
     * 在浏览器中打开
     * @param url
     */
    fun openInBrowser(url: String) {

        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val uri = Uri.parse(url)
        intent.data = uri
        if (intent.resolveActivity(mContext.packageManager) != null) {
            mContext.startActivity(intent)
        } else {
            iView.openFailed()
        }

    }

    private inner class ChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            iView.showProgressBar(newProgress)
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            iView.setWebTitle(title)
        }
    }

    private inner class GankClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            if (url != null) view.loadUrl(url)
            return true
        }
    }

}