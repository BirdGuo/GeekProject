package com.guoxw.geekproject.gankio.ui.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_gank.*

class FragmentGank : BaseFragment() {

    /**
     * fragment列表
     */
    var gankFragments: MutableList<Fragment> = ArrayList<Fragment>()

    /**
     * 妹纸页面
     */
    val beauty: FragmentBeauty = FragmentBeauty()
    /**
     * Android页面
     */
    val andFragment: FragmentAndroid = FragmentAndroid("Android")
    /**
     * iOS页面
     */
    val iosFragment: FragmentAndroid = FragmentAndroid("iOS")

    override fun getLayoutId(): Int = R.layout.fragment_gank

    override fun initView() {

        vp_gank.adapter = object : FragmentPagerAdapter(fragmentManager) {

            override fun getPageTitle(position: Int): CharSequence {
                /**
                 * 设置每页的标题
                 * @param position
                 * @return title
                 */
                var title: CharSequence = ""
                when (position) {
                    0 -> title = "妹汁"
                    1 -> title = "Android"
                    2 -> title = "iOS"
                }
                return title
            }

            override fun getItem(position: Int): Fragment {
                return gankFragments[position]
            }

            override fun getCount(): Int {
                return gankFragments.size
            }
        }

        /**
         * 这样设置之后adapter会把tab中的所有view都清除，然后将adapter中的titile加上
         */
        tbl_gank.setupWithViewPager(vp_gank, true)
        //当前页
        vp_gank.currentItem = 0
    }

    override fun initData() {
        gankFragments.add(beauty)//添加妹纸页面
        gankFragments.add(andFragment)//添加Android页面
        gankFragments.add(iosFragment)//添加iOS页面
        vp_gank.adapter.notifyDataSetChanged()//更新主页面
    }

    override fun initListener() {
    }

}// Required empty public constructor
