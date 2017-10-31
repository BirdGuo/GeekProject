package com.guoxw.geekproject.gankio.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseFragment
import com.guoxw.geekproject.events.RCVItemClickListener
import com.guoxw.geekproject.gankio.adapter.WaterFallAdapter
import com.guoxw.geekproject.gankio.api.GankIOApi
import com.guoxw.geekproject.gankio.api.resetApi.GankIOResetApi
import com.guoxw.geekproject.gankio.ui.activity.GankDayInfoActivity
import com.guoxw.geekproject.utils.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_beauty.*


/**
 * A simple [Fragment] subclass.
 */
class FragmentBeauty : BaseFragment(), RCVItemClickListener {

    var currentPage: Int = 0

    var dates: MutableList<String> = ArrayList()

    var waterFullAdapter: WaterFallAdapter? = null
    val gankIOApi: GankIOApi = GankIOResetApi

    override fun getLayoutId(): Int = R.layout.fragment_beauty

    override fun initView() {

        waterFullAdapter = WaterFallAdapter(context, this)
        rcv_beauty.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rcv_beauty.adapter = waterFullAdapter

    }

    override fun initData() {

        initHisDay()

    }

    private fun initHisDay() {

        gankIOApi.getGankHistoryDate().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe({ res ->
                    if (!res.error) {
                        dates.addAll(res.results)
                        initPage(currentPage)
                    } else {

                    }
                }, { e ->
                    LogUtil.e("MainActivity", "error:".plus(e.message).plus("-----").plus(e.cause))
                }, {})
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

//    private fun initIamges(currentPage: Int) {
//        gankIOApi.getGankIOData(gankDataParam).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
//                .subscribe({ res ->
//                    if (!res.error) {
////                        waterFullAdapter!!.mImages.addAll(res.results)
////                        waterFullAdapter!!.getRandomHeight(res.results)
////                        waterFullAdapter!!.notifyDataSetChanged()
//                    } else {
//                    }
//                }, { e -> LogUtil.e("MainActivity", "error:".plus(e.message)) }, {
//
//                })
//
//    }

    override fun initListener() {

        rcv_beauty.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (isSlideToBottom(recyclerView)) {
                    currentPage++
//                    initIamges(currentPage)
                    initPage(currentPage)
                }
            }
        })

    }

    private fun isSlideToBottom(recyclerView: RecyclerView?): Boolean {
        if (recyclerView == null) return false
        return if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()) true else false
    }

    override fun onItemClickListener(view: View, postion: Int) {

        val bundle = Bundle()
        bundle.putString("date", waterFullAdapter!!.dates[postion])
//        openActivity(BeautyActivity::class.java, bundle)
        openActivity(GankDayInfoActivity::class.java,bundle)

    }

    override fun onItemLongClickListener(view: View, postion: Int) {
    }


}// Required empty public constructor
