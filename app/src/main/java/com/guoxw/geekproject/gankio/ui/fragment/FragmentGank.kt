package com.guoxw.geekproject.gankio.ui.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_gank.*

class FragmentGank : BaseFragment() {

    var gankFragments: MutableList<Fragment> = ArrayList<Fragment>()
    val beauty: FragmentBeauty = FragmentBeauty()

    val andFragment: FragmentAndroid = FragmentAndroid("Android")
    val iosFragment: FragmentAndroid = FragmentAndroid("iOS")

    override fun getLayoutId(): Int = R.layout.fragment_gank

    override fun initView() {
        vp_gank.adapter = object : FragmentPagerAdapter(fragmentManager) {
            override fun getItem(position: Int): Fragment {
                return gankFragments[position]
            }

            override fun getCount(): Int {
                return gankFragments.size
            }
        }
        vp_gank.currentItem = 0
    }

    override fun initData() {
        gankFragments.add(beauty)
        gankFragments.add(andFragment)
        gankFragments.add(iosFragment)
        vp_gank.adapter.notifyDataSetChanged()
    }

    override fun initListener() {
    }

}// Required empty public constructor
