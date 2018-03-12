package com.guoxw.geekproject.gankio.ui.fragment


import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.View
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseNetFragment
import com.guoxw.geekproject.events.RCVItemClickListener
import com.guoxw.geekproject.gankio.adapter.SpacesItemDecoration
import com.guoxw.geekproject.gankio.adapter.WaterFallAdapter
import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.responses.GankResponse
import com.guoxw.geekproject.gankio.presenter.dao.GankDataDao
import com.guoxw.geekproject.gankio.presenter.impl.GankDataDaoImpl
import com.guoxw.geekproject.gankio.ui.views.IGankDataView
import com.guoxw.geekproject.utils.LogUtil
import com.guoxw.geekproject.utils.RecyclerViewUtil.isSlideToBottom
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.fragment_beauty.*


/**
 * A simple [Fragment] subclass.
 */
class FragmentBeauty : BaseNetFragment<GankResponse<MutableList<GankData>>, GankDataDaoImpl>(),
        RCVItemClickListener, IGankDataView<GankResponse<MutableList<GankData>>>, SwipeRefreshLayout.OnRefreshListener, GankDataDao.View {

    /**
     * 当前页
     */
    var currentPage: Int = 0

    /**
     * 日期列表
     */
    private var dates: MutableList<String> = ArrayList()

    /**
     * 瀑布流适配器
     */
    private var waterFullAdapter: WaterFallAdapter? = null

    var a: StaggeredGridLayoutManager? = null

    /**
     * 数据接口
     */
//    var gankDataPresenter: GankDataPresenter? = null
//    var gankDataDaoImpl: GankDataDaoImpl? = null


    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0x0001 -> {
                    LogUtil.i("GXW", "handler current page;".plus(currentPage))
                    initPage(currentPage)
                }
                0x0002 -> srl_beauty.isRefreshing = false
            }
        }
    }

    override fun initUI() {

        //实例化瀑布流
        waterFullAdapter = WaterFallAdapter(context, this)
        a = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        //解决位置错乱和闪烁
        a!!.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        //设置recycleview瀑布流属性
        rcv_beauty.layoutManager = a

        (rcv_beauty.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
        //recycleview设置adapter
        rcv_beauty.adapter = waterFullAdapter
        //设置item之间的间隔
//        val decoration = SpacesItemDecoration(16)
//        rcv_beauty.addItemDecoration(decoration)
        //上拉刷新控件设置监听
        srl_beauty.setOnRefreshListener(this)

    }

    override fun getContentLayoutId(): Int = R.layout.fragment_beauty

    override fun initData() {
//        gankDataPresenter = GankDataPresenter(this, context)
//        gankDataPresenter!!.initGankHistory()
//        gankDataDaoImpl = GankDataDaoImpl(context,this, this)
//        gankDataDaoImpl!!.fetchGankHistory()

        //这个方法点不出来
        presenter = GankDataDaoImpl(context, this, this)
        presenter!!.fetchGankHistory()
    }

    /**
     * 分页查询新的妹子照片
     * @param currentPage 页码
     */
    private fun initPage(currentPage: Int) {

        LogUtil.i("GXW", "CurrentPage :".plus(currentPage))
        if (dates.size > 0) {
            for (i in ((10 * currentPage))..(((10 * (currentPage + 1)) - 1))) {
                waterFullAdapter!!.dates.add(dates[i])
            }
            waterFullAdapter!!.getRandomHeight(waterFullAdapter!!.dates)

//            handler.sendEmptyMessage(0x0001)
            waterFullAdapter!!.notifyDataSetChanged()
        }
        handler.sendEmptyMessage(0x0002)
    }

    override fun initListener() {
    }


    override fun onItemClickListener(view: View, postion: Int) {
        val bundle = Bundle()
        bundle.putString("date", waterFullAdapter!!.dates[postion])
    }

    override fun onItemLongClickListener(view: View, postion: Int) {
    }

    override fun reflashView(mData: GankResponse<MutableList<GankData>>) {
    }

    override fun getDataFail(error: String) {
        fl_beauty.visibility = View.GONE
        tv_error_msg.visibility = View.VISIBLE
        tv_error_msg.text = error
    }

    override fun getHisSuccess(result: MutableList<String>) {

        fl_beauty.visibility = View.VISIBLE
        tv_error_msg.visibility = View.GONE

        if (dates.isNotEmpty()) {
            dates.clear()
        }
        dates.addAll(result)
//        initPage(currentPage)

        handler.sendEmptyMessage(0x0001)

        Log.i("GXW", "date size".plus(dates.size))

        //滑动监听
        rcv_beauty.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //解决位置错乱和闪烁
                a!!.invalidateSpanAssignments()
                //判断是否到底部
                if (isSlideToBottom(recyclerView)) {
                    //页数+1
                    currentPage++
                    LogUtil.i("GXW", "onScrollStateChanged current page".plus(currentPage))
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
        LogUtil.i("GXW", "下拉刷新")

        presenter!!.fetchGankHistory()

    }

}
