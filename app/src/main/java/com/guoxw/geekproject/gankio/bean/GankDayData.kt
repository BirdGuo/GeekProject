package com.guoxw.geekproject.gankio.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by guoxw on 2017/9/8 0008.
 * @auther guoxw
 * @date 2017/9/8 0008
 * @desciption
 * @package com.guoxw.geekproject.gankio.bean
 */
data class GankDayData(
        var Android: MutableList<GankData>,
        var iOS: MutableList<GankData>,
        var 休息视频: MutableList<GankData>,
        var 前端: MutableList<GankData>,
        var 福利: MutableList<GankData>) : Parcelable {
    constructor(source: Parcel) : this(
            source.createTypedArrayList(GankData.CREATOR),
            source.createTypedArrayList(GankData.CREATOR),
            source.createTypedArrayList(GankData.CREATOR),
            source.createTypedArrayList(GankData.CREATOR),
            source.createTypedArrayList(GankData.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(Android)
        writeTypedList(iOS)
        writeTypedList(休息视频)
        writeTypedList(前端)
        writeTypedList(福利)
    }

    override fun toString(): String {
        return "GankDayData(Android=$Android, iOS=$iOS, 休息视频=$休息视频, 前端=$前端, 福利=$福利)"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GankDayData> = object : Parcelable.Creator<GankDayData> {
            override fun createFromParcel(source: Parcel): GankDayData = GankDayData(source)
            override fun newArray(size: Int): Array<GankDayData?> = arrayOfNulls(size)
        }
    }


}