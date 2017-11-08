package com.guoxw.geekproject.gankio.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseNetFragment
import com.guoxw.geekproject.events.RCVItemClickListener
import com.guoxw.geekproject.gankio.adapter.WaterFallAdapter
import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.presenter.GankDataPresenter
import com.guoxw.geekproject.gankio.ui.views.IGankDataView
import com.guoxw.geekproject.utils.LogUtil
import com.guoxw.geekproject.utils.RecyclerViewUtil.isSlideToBottom
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.fragment_beauty.*


/**
 * A simple [Fragment] subclass.
 */
class FragmentBeauty : BaseNetFragment(), RCVItemClickListener, IGankDataView, SwipeRefreshLayout.OnRefreshListener {

    /**
     * 当前页
     */
    var currentPage: Int = 0

    /**
     * 日期列表
     */
    var dates: MutableList<String> = ArrayList()

    /**
     * 瀑布流适配器
     */
    var waterFullAdapter: WaterFallAdapter? = null

    /**
     * 数据接口
     */
    var gankDataPresenter: GankDataPresenter? = null

    override fun initUI() {

        //实例化瀑布流
        waterFullAdapter = WaterFallAdapter(context, this)
        //设置recycleview瀑布流属性
        rcv_beauty.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        //recycleview设置adapter
        rcv_beauty.adapter = waterFullAdapter
        //上拉刷新控件设置监听
        srl_beauty.setOnRefreshListener(this)

    }

    override fun getContentLayoutId(): Int = R.layout.fragment_beauty

    override fun initData() {
        gankDataPresenter = GankDataPresenter(this, context)
        gankDataPresenter!!.initGankHistory()
    }

    private fun initPage(currentPage: Int) {

        for (i in ((10 * currentPage))..(((10 * (currentPage + 1)) - 1))) {
            waterFullAdapter!!.dates.add(dates[i])
        }

        waterFullAdapter!!.getRandomHeight(waterFullAdapter!!.dates)
        waterFullAdapter!!.notifyDataSetChanged()

    }

    override fun initListener() {
    }


    override fun onItemClickListener(view: View, postion: Int) {
        val bundle = Bundle()
        bundle.putString("date", waterFullAdapter!!.dates[postion])
    }

    override fun onItemLongClickListener(view: View, postion: Int) {
    }

    override fun reflashView(mData: MutableList<GankData>) {
    }

    override fun getDataFail(error: String) {
//        LogUtil.e("GXW", "error:".plus(error))

        fl_beauty.visibility = View.GONE
        tv_error_msg.visibility = View.VISIBLE
        tv_error_msg.text = error

    }

    override fun getHisSuccess(result: MutableList<String>) {
        fl_beauty.visibility = View.VISIBLE
        tv_error_msg.visibility = View.GONE

        dates.addAll(result)
        initPage(currentPage)

        //滑动监听
        rcv_beauty.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //判断是否到底部
                if (isSlideToBottom(recyclerView)) {
                    //页数+1
                    currentPage++
                    initPage(currentPage)
                }
            }
        })
    }

    override fun getDataComplete() {
    }

    override fun getHisFail(error: String) {
    }

    override fun onRefresh() {


    }

}// Required empty public constructor
