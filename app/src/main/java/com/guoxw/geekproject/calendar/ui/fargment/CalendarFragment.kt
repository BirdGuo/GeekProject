package com.guoxw.geekproject.calendar.ui.fargment


import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseFragment
import com.guoxw.geekproject.calendar.ui.adapter.CalBadAdapter
import com.guoxw.geekproject.calendar.ui.adapter.CalGoodAdapter
import com.guoxw.geekproject.calendar.utils.CalendarUtil
import com.guoxw.geekproject.utils.TimeUtil
import kotlinx.android.synthetic.main.fragment_calendar.*


@Suppress("UNUSED_EXPRESSION")
/**
 * A simple [Fragment] subclass.
 */
class CalendarFragment : BaseFragment() {

    var adapterGood: CalGoodAdapter? = null
    var adapterBad: CalBadAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_calendar

    override fun initView() {

        val calendarUtil = CalendarUtil()

        tv_cal_date.text = "今天是".plus(TimeUtil.getNowDateZH()).plus(" ").plus(TimeUtil.getWeekStr(TimeUtil.getNowDate()))
        tv_cal_direction.text = calendarUtil.initDirection()
        tv_cal_drink.text = calendarUtil.initDrinks()
        tv_cal_girl.text = "".plus(calendarUtil.getGirlValue())

        adapterGood = CalGoodAdapter(context)
        adapterGood!!.calendars = calendarUtil.goodList

        adapterBad = CalBadAdapter(context)

        adapterBad!!.calendars = calendarUtil.badList

        rcv_cal_good.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcv_cal_good.adapter = adapterGood

        rcv_cal_bad.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcv_cal_bad.adapter = adapterBad


    }

    override fun initData() {

    }

    override fun initListener() {

    }

}// Required empty public constructor
