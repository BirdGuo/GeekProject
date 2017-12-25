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
* @auther guoxw
* @date 2017/10/30 0030
* @package ${PACKAGE_NAME}
* @desciption
*/
class AndroidAdapter : RecyclerView.Adapter<AndroidAdapter.ViewHolder> {

    val datas: MutableList<GankData> = ArrayList()
    var mContext: Context? = null

    private var rcvItemClickListener: RCVItemClickListener? = null

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


    /**
     * init 是这样用的 当默认构造参数是使用init初始化
     */
    class ViewHolder(itemView: View?, private var itemClickListener: RCVItemClickListener?) : RecyclerView.ViewHolder(itemView) {

        var tv_android_name: TextView? = null

        private var lin_item_android: LinearLayout? = null

        init {
            tv_android_name = itemView!!.findViewById(R.id.tv_android_name)
            lin_item_android = itemView.findViewById(R.id.lin_item_android)
            lin_item_android!!.setOnClickListener { view ->
                itemClickListener!!.onItemClickListener(view, adapterPosition)
            }
        }

    }

}