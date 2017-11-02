package com.guoxw.geekproject.gankio.bean

import android.os.Parcelable
import java.io.Serializable

/**
 * Created by guoxw on 2017/10/31 0031.
 */
data class GankListData(val name: String, val datas: MutableList<GankData>) : Serializable {
}