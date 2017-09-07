package com.guoxw.geekproject

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.guoxw.geekproject.base.BaseActivity
import com.guoxw.geekproject.calendar.ui.fargment.CalendarFragment
import com.guoxw.geekproject.gankio.ui.fragment.FragmentGank
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_main_left.*


class MainActivity : BaseActivity() {

    private val fragmentGank: FragmentGank = FragmentGank()
    private val calendarFragment: CalendarFragment = CalendarFragment()

    private val mainFragments: MutableList<Fragment> = ArrayList<Fragment>()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.fl_main_content, mainFragments[0], "main")
        beginTransaction.add(R.id.fl_main_content, mainFragments[1], "cale")
//        beginTransaction.addToBackStack(null)
        beginTransaction.show(mainFragments[0])
        beginTransaction.hide(mainFragments[1])
        beginTransaction.commit()
    }

    override fun initData() {
        mainFragments.add(calendarFragment)
        mainFragments.add(fragmentGank)
    }

    override fun initListener() {

        fl_theme_main.setOnClickListener {
            showNewPage(mainFragments[0])

        }

        fl_theme_calendar.setOnClickListener {
            showNewPage(mainFragments[1])
        }

    }

    /**
     * 显示要展示的页面
     * @param fragment
     */
    fun showNewPage(fragment: Fragment) {

        val beginTransaction = supportFragmentManager.beginTransaction()
        hideAllPage(beginTransaction)
        beginTransaction.show(fragment)
        beginTransaction.commit()
        dl_main.closeDrawers()

    }

    /**
     * 隐藏所有fragment
     */
    fun hideAllPage(transaction: FragmentTransaction) {

        mainFragments.forEach { fragment ->
            transaction.hide(fragment)
        }

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
