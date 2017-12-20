package com.guoxw.geekproject.utils

import android.graphics.Bitmap
import android.view.View

/**
 * @auther guoxw
 * @date 2017/12/20 0020
 * @package com.guoxw.geekproject.utils
 * @desciption
 */
object BitmapUtil {

    /**
     * 把一个view转换成bitmap对象
     *
     * @param view 需要转换的view
     * @return drawingCache 转换成的bitmap
     */
    fun viewToBitmap(view: View): Bitmap {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.buildDrawingCache()
        return view.drawingCache


    }

}