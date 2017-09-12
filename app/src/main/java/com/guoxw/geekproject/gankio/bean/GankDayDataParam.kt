package com.guoxw.geekproject.gankio.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by guoxw on 2017/9/12 0012.
 * @auther guoxw
 * @date 2017/9/12 0012
 * @desciption
 * @package com.guoxw.geekproject.gankio.bean
 */
data class GankDayDataParam(val year: String, val month: String, val date: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(year)
        writeString(month)
        writeString(date)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GankDayDataParam> = object : Parcelable.Creator<GankDayDataParam> {
            override fun createFromParcel(source: Parcel): GankDayDataParam = GankDayDataParam(source)
            override fun newArray(size: Int): Array<GankDayDataParam?> = arrayOfNulls(size)
        }
    }
}