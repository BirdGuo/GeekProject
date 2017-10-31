package com.guoxw.geekproject.gankio.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.guoxw.geekproject.R
import com.guoxw.geekproject.events.RCVItemClickListener
import com.guoxw.geekproject.gankio.bean.GankData

/**
 * Created by guoxw on 2017/10/30 0030.
 */
class AndroidAdapter : RecyclerView.Adapter<AndroidAdapter.ViewHolder> {

    val datas: MutableList<GankData> = ArrayList<GankData>()
    var mContext: Context? = null

    var rcvItemClickListener: RCVItemClickListener? = null

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    constructor(mContext: Context?, rcvItemClickListener: RCVItemClickListener?) : super() {
        this.mContext = mContext
        this.rcvItemClickListener = rcvItemClickListener
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        //绑定页面数据
        holder!!.tv_android_name!!.text = datas[position].desc

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        //绑定页面
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_gank_android, parent, false)
        //返回ViewHolder
        return AndroidAdapter.ViewHolder(view, rcvItemClickListener)
    }

    override fun getItemCount(): Int {
        //数据长度
        return datas.size
    }


    class ViewHolder : RecyclerView.ViewHolder {

        var tv_android_name: TextView? = null
        var itemClickListener: RCVItemClickListener? = null

        var lin_item_android: LinearLayout? = null

        constructor(itemView: View?, itemClickListener: RCVItemClickListener?) : super(itemView) {
            tv_android_name = itemView!!.findViewById<TextView>(R.id.tv_android_name)
            lin_item_android = itemView!!.findViewById<LinearLayout>(R.id.lin_item_android)
            this.itemClickListener = itemClickListener
            lin_item_android!!.setOnClickListener { view ->
                itemClickListener!!.onItemClickListener(view, adapterPosition)
            }
        }
    }

}