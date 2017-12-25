package com.guoxw.geekproject.map.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*


/**
 * @auther guoxw
 * @date 2017/12/22 0022
 * @package com.guoxw.geekproject.map.utils
 * @desciption
 */
object MapUtil {

    fun addMarker(paramAMap: AMap, paramLatLng: LatLng, paramBitmapDescriptor: BitmapDescriptor, paramString1: String, paramString2: String): Marker {
        val localMarkerOptions = MarkerOptions()
        localMarkerOptions.position(paramLatLng)
        localMarkerOptions.anchor(0.5f, 0.5f)
        localMarkerOptions.icon(paramBitmapDescriptor)
        localMarkerOptions.draggable(false)
        localMarkerOptions.title(paramString1)
        localMarkerOptions.snippet(paramString2)
        return paramAMap.addMarker(localMarkerOptions)
    }

    private fun animateCamera(paramAMap: AMap, paramDouble1: Double?, paramDouble2: Double?, paramFloat: Float, paramInt: Int) {
        paramAMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(LatLng(paramDouble1!!.toDouble(), paramDouble2!!.toDouble()), paramFloat, 0.0f, 0.0f)), paramInt.toLong(), null)
    }

    fun getViewBitmap(paramView: View): Bitmap {
        //        paramView.measure(View.MeasureSpec.makeMeasureSpec(0,0), View.MeasureSpec.makeMeasureSpec(0,0));
        paramView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        paramView.layout(0, 0, paramView.measuredWidth, paramView.measuredHeight)
        paramView.buildDrawingCache()
        return paramView.drawingCache
    }

    fun moveCamera(paramAMap: AMap, paramDouble1: Double?, paramDouble2: Double?, paramFloat: Float) {
        paramAMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(LatLng(paramDouble1!!.toDouble(), paramDouble2!!.toDouble()), paramFloat, 0.0f, 0.0f)))
    }

    fun moveToCurrentPosition(paramAMap: AMap?) {
        if (paramAMap != null && paramAMap.myLocation != null)
            animateCamera(paramAMap, java.lang.Double.valueOf(paramAMap.myLocation.latitude), java.lang.Double.valueOf(paramAMap.myLocation.longitude), 12.0f, 500)
    }

    fun moveToCurrentPositionWithZoom(paramAMap: AMap?, paramFloat: Float) {
        if (paramAMap != null && paramAMap.myLocation != null)
            animateCamera(paramAMap, java.lang.Double.valueOf(paramAMap.myLocation.latitude), java.lang.Double.valueOf(paramAMap.myLocation.longitude), paramFloat, 500)
    }

    fun moveToPosition(paramAMap: AMap, paramDouble1: Double?, paramDouble2: Double?, paramFloat: Float) {
        animateCamera(paramAMap, paramDouble1, paramDouble2, paramFloat, 500)
    }

    fun moveToPosition(paramAMap: AMap, paramDouble1: Double?, paramDouble2: Double?, paramFloat: Float, paramInt: Int) {
        animateCamera(paramAMap, paramDouble1, paramDouble2, paramFloat, paramInt)
    }

//    public Drawable getDrawAble(int clusterNum, Context context, int clusterRadius) {
//        int radius = ScreenUtil.dp2px(context, clusterRadius);
//        if (clusterNum == 1) {
//            return context.getResources().getDrawable(
//                    R.mipmap.ic_charge_point);
//        } else if (clusterNum < 5) {
//            BitmapDrawable drawable = new BitmapDrawable(drawCircle(radius,
//                    Color.argb(159, 210, 154, 6)));
//            return drawable;
//        } else if (clusterNum < 10) {
//            BitmapDrawable drawable = new BitmapDrawable(drawCircle(radius,
//                    Color.argb(199, 217, 114, 0)));
//            return drawable;
//        } else {
//            BitmapDrawable drawable = new BitmapDrawable(drawCircle(radius,
//                    Color.argb(235, 215, 66, 2)));
//            return drawable;
//        }
//    }

    fun drawCircle(radius: Int, color: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(radius * 2, radius * 2,
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val rectF = RectF(0f, 0f, (radius * 2).toFloat(), (radius * 2).toFloat())
        paint.color = color
        canvas.drawArc(rectF, 0F, 360F, true, paint)
        return bitmap
    }


}