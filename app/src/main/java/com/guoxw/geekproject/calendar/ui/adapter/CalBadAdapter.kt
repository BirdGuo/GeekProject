package com.guoxw.geekproject.calendar.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.guoxw.geekproject.R

/**
 * Created by guoxw on 2017/9/7 0007.
 * @auther guoxw
 * @date 2017/9/7 0007
 * @desciption
 * @package com.guoxw.geekproject.calendar.ui.adapter
 */
class CalBadAdapter(var mContext: Context?) : RecyclerView.Adapter<CalBadAdapter.ViewHolder>() {

    var calendars: MutableList<Map<String, String>> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_cal_good, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val myCalendar = calendars[position]
        holder!!.tv_item_good_title!!.text = myCalendar["name"]
        holder.tv_item_good_content!!.text = myCalendar["bad"]
    }

    override fun getItemCount(): Int {
        return calendars.size
    }


    class ViewHolder//            tv_item_good_content = itemView!!.findViewById(R.id.tv_item_good_content) as TextView 过时了
    //使用
    (itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var tv_item_good_title: TextView? = null
        var tv_item_good_content: TextView? = null

        init {
            tv_item_good_title = itemView!!.findViewById(R.id.tv_item_good_title)
            tv_item_good_content = itemView.findViewById(R.id.tv_item_good_content)
        }

    }


}