package com.guoxw.geekproject.calendar.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.guoxw.geekproject.R
import com.guoxw.geekproject.calendar.bean.MyCalendar

/**
 * Created by guoxw on 2017/9/7 0007.
 * @auther guoxw
 * @date 2017/9/7 0007
 * @desciption
 * @package com.guoxw.geekproject.calendar.ui.adapter
 */
class CalGoodAdapter : RecyclerView.Adapter<CalGoodAdapter.ViewHolder> {

    var calendars: MutableList<MyCalendar> = ArrayList<MyCalendar>()
    var mContext: Context? = null

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_cal_good, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val myCalendar = calendars[position]
        holder!!.tv_item_good_title!!.text = myCalendar.title
        holder!!.tv_item_good_content!!.text = myCalendar.content
    }

    override fun getItemCount(): Int {
        return calendars.size
    }


    class ViewHolder : RecyclerView.ViewHolder {

        var tv_item_good_title: TextView? = null
        var tv_item_good_content: TextView? = null

        constructor(itemView: View?) : super(itemView) {
            tv_item_good_title = itemView!!.findViewById(R.id.tv_item_good_title) as TextView
            tv_item_good_content = itemView!!.findViewById(R.id.tv_item_good_content) as TextView
        }
    }


}