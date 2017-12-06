package com.guoxw.geekproject.utils

import android.support.design.widget.Snackbar
import android.view.View

/**
 * 用于显示提示信息
 * Created by panl on 15/12/26.
 */
object TipsUtil {

    fun showTipWithAction(view: View, tipText: String, actionText: String, listener: View.OnClickListener) {
        Snackbar.make(view, tipText, Snackbar.LENGTH_INDEFINITE).setAction(actionText, listener).show()
    }

    fun showTipWithAction(view: View, tipText: String, actionText: String, listener: View.OnClickListener, duration: Int) {
        Snackbar.make(view, tipText, duration).setAction(actionText, listener).show()
    }

    fun showSnackTip(view: View, tipText: String) {
        Snackbar.make(view, tipText, Snackbar.LENGTH_SHORT).show()
    }
}
