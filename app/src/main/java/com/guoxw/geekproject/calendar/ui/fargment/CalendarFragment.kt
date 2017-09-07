package com.guoxw.geekproject.calendar.ui.fargment


import android.support.v4.app.Fragment
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseFragment
import com.guoxw.geekproject.calendar.utils.CalendarUtil
import com.guoxw.geekproject.utils.TimeUtil
import kotlinx.android.synthetic.main.fragment_calendar.*


/**
 * A simple [Fragment] subclass.
 */
class CalendarFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_calendar

    override fun initView() {

        val calendarUtil = CalendarUtil()

        tv_cal_date.text = "今天是".plus(TimeUtil.getNowDateZH()).plus(" ").plus(TimeUtil.getWeekStr(TimeUtil.getNowDate()))
        tv_cal_direction.text = calendarUtil.initDirection()
        tv_cal_drink.text = calendarUtil.initDrinks()
        tv_cal_girl.text = "".plus(calendarUtil.getGirlValue())




    }

    override fun initData() {

    }

    override fun initListener() {

    }

}// Required empty public constructor
