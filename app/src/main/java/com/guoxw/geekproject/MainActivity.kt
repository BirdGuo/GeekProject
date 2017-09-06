package com.guoxw.geekproject

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.guoxw.geekproject.base.BaseActivity
import com.guoxw.geekproject.gankio.ui.fragment.FragmentBeauty
import kotlinx.android.synthetic.main.include_main_center.*


class MainActivity : BaseActivity() {

    var gankFragments: MutableList<Fragment> = ArrayList<Fragment>()
    var beauty: FragmentBeauty = FragmentBeauty()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
//        dl_main.openDrawer(Gravity.LEFT)
        vp_main.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return gankFragments[position]
            }

            override fun getCount(): Int {
                return gankFragments.size
            }
        }
        vp_main.currentItem = 0

    }

    override fun initData() {

        gankFragments.add(beauty)
//        gankFragments.add(beauty)
//        gankFragments.add(beauty)

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
