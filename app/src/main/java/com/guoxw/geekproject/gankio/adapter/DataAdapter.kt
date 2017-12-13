package com.guoxw.geekproject.gankio.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.guoxw.geekproject.R
import com.guoxw.geekproject.events.RCVItemClickListener
import com.guoxw.geekproject.gankio.bean.GankListData

/**
* @auther guoxw
* @date 2017/10/31 0031
* @package ${PACKAGE_NAME}
* @desciption
*/
class DataAdapter(val mContext: Context) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

//    val datas: MutableList<MutableList<GankData>> = ArrayList<MutableList<GankData>>()

    val dataList: MutableList<GankListData> = ArrayList()

//    var rcvItemClickListener: RCVItemClickListener? = null

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        //绑定页面
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_gank_data, parent, false)
        //返回ViewHolder
        return DataAdapter.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        val data = dataList[position]
        holder!!.tv_data_title!!.text = data.name

        val contentAdapter = AndroidAdapter(mContext, object : RCVItemClickListener {
            override fun onItemClickListener(view: View, postion: Int) {
            }

            override fun onItemLongClickListener(view: View, postion: Int) {
            }
        })

        contentAdapter.datas.addAll(data.datas)
        holder.rcv_item_data!!.adapter = contentAdapter
        holder.rcv_item_data!!.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)


    }

    class ViewHolder//            lin_item_android = itemView!!.findViewById<LinearLayout>(R.id.lin_item_android)
    (itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tv_data_title: TextView? = null
        private var itemClickListener: RCVItemClickListener? = null

        var rcv_item_data: RecyclerView? = null

        init {
            tv_data_title = itemView!!.findViewById(R.id.tv_data_title)
            rcv_item_data = itemView.findViewById(R.id.rcv_item_data)
            this.itemClickListener = itemClickListener
        }

    }

}