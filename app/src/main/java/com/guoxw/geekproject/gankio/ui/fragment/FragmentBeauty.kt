package com.guoxw.geekproject.gankio.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
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
class FragmentBeauty : BaseNetFragment(), RCVItemClickListener, IGankDataView {

    override fun initUI() {
        waterFullAdapter = WaterFallAdapter(context, this)
        rcv_beauty.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rcv_beauty.adapter = waterFullAdapter
    }

    override fun getContentLayoutId(): Int =R.layout.fragment_beauty

    var currentPage: Int = 0

    var dates: MutableList<String> = ArrayList()

    var waterFullAdapter: WaterFallAdapter? = null

    var gankDataPresenter: GankDataPresenter ?= null

    override fun initData() {
        gankDataPresenter = GankDataPresenter(this,context)
        gankDataPresenter!!.initGankHistory()
    }

    private fun initPage(currentPage: Int) {

        for (i in ((10 * currentPage))..(((10 * (currentPage + 1)) - 1))) {
            LogUtil.i("GXW", "".plus(i).plus("waterFullAdapter.dates").plus(dates[i]))
            waterFullAdapter!!.dates.add(dates[i])
        }
        LogUtil.i("GXW", "waterFullAdapter.dates sie:".plus(waterFullAdapter!!.dates.size))

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

        rcv_beauty.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (isSlideToBottom(recyclerView)) {
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
}// Required empty public constructor
