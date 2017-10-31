package com.guoxw.geekproject.gankio.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by guoxw on 2017/10/31 0031.
 */
data class GankListData(val name: String, val datas: MutableList<GankData>) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.createTypedArrayList(GankData.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeTypedList(datas)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GankListData> = object : Parcelable.Creator<GankListData> {
            override fun createFromParcel(source: Parcel): GankListData = GankListData(source)
            override fun newArray(size: Int): Array<GankListData?> = arrayOfNulls(size)
        }
    }
}