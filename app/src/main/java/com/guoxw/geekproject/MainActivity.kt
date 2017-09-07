package com.guoxw.geekproject

import com.guoxw.geekproject.base.BaseActivity
import com.guoxw.geekproject.gankio.ui.fragment.FragmentGank


class MainActivity : BaseActivity() {

    private var fragmentGank: FragmentGank = FragmentGank()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        /* val beginTransaction = fragmentManager.beginTransaction()
         beginTransaction.replace(R.id.fl_main_content, fragmentGank as Fragment)
         beginTransaction.commit()*/

        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.fl_main_content, fragmentGank)
        beginTransaction.commit()


    }

    override fun initData() {

    }

    override fun initListener() {
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
