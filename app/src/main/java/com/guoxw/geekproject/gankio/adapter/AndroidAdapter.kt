package com.guoxw.geekproject.gankio.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.guoxw.geekproject.R
import com.guoxw.geekproject.gankio.bean.GankData

/**
 * Created by guoxw on 2017/10/30 0030.
 */
class AndroidAdapter : RecyclerView.Adapter<AndroidAdapter.ViewHolder> {

    val datas: MutableList<GankData> = ArrayList<GankData>()
    var mContext: Context? = null

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder!!.tv_android_name!!.text = datas[position].desc

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_gank_android, parent, false)

        return AndroidAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return datas.size
    }


    class ViewHolder : RecyclerView.ViewHolder {

        var tv_android_name: TextView? = null

        constructor(itemView: View?) : super(itemView) {
            tv_android_name = itemView!!.findViewById<TextView>(R.id.tv_android_name)
        }
    }

}