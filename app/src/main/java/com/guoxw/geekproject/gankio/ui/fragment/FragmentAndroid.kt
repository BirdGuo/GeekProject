package com.guoxw.geekproject.gankio.ui.fragment


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.guoxw.gankio.network.LifeSubscription
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseNetFragment
import com.guoxw.geekproject.base.BaseWebActivity
import com.guoxw.geekproject.events.RCVItemClickListener
import com.guoxw.geekproject.gankio.adapter.AndroidAdapter
import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.params.GankDataParam
import com.guoxw.geekproject.gankio.data.responses.GankResponse
import com.guoxw.geekproject.gankio.presenter.dao.GankDataDao
import com.guoxw.geekproject.gankio.presenter.impl.GankDataDaoImpl
import com.guoxw.geekproject.utils.LogUtil
import com.guoxw.geekproject.utils.RecyclerViewUtil
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_android.*
import kotlinx.android.synthetic.main.fragment_base.*


@SuppressLint("ValidFragment")
/**
 * A simple [Fragment] subclass.
 */
class FragmentAndroid(val type: String) : BaseNetFragment(), GankDataDao.view, SwipeRefreshLayout.OnRefreshListener, LifeSubscription {


    //适配器
    var androidAdapter: AndroidAdapter? = null

    //presenter
//    var gankDataPresenter: GankDataPresenter? = null

    var gankDataDao: GankDataDaoImpl? = null

    //当前页
    var currentPage: Int = 1
    //一页多少数据
    val pageNum: Int = 20
    //类型
//    val type: String = "Android"

    override fun initUI() {

        swipe_refresh_layout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.BLACK)
        swipe_refresh_layout.setOnRefreshListener(this)

        androidAdapter = AndroidAdapter(context, object : RCVItemClickListener {
            override fun onItemClickListener(view: View, postion: Int) {

                val bundle = Bundle()
                bundle.putString("url", androidAdapter!!.datas[postion].url)
                openActivity(BaseWebActivity::class.java, bundle)

            }

            override fun onItemLongClickListener(view: View, postion: Int) {
            }
        })

        rcv_android.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcv_android.adapter = androidAdapter
    }

    override fun getContentLayoutId(): Int = R.layout.fragment_android

    override fun initData() {
        //清除数据
        androidAdapter!!.datas.clear()
        //初始化Presenter
//        gankDataPresenter = GankDataPresenter(this, context)
        gankDataDao = GankDataDaoImpl(this)
        //获取第一页数据
        gankDataDao!!.fetchGankData(GankDataParam(type, pageNum, currentPage))
//        gankDataPresenter!!.initGankData(GankDataParam(type, pageNum, currentPage))
    }

    override fun initListener() {

        //添加滑动监听
        rcv_android.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //判断是否滑动到了底部
                if (RecyclerViewUtil.isSlideToBottom(recyclerView)) {
                    //页数+1
                    currentPage++
                    //查询新数据
//                    gankDataPresenter!!.initGankData(GankDataParam(type, pageNum, currentPage))
                }
            }
        })

    }

//    override fun reflashView(mData: MutableList<GankData>) {
//
//        tv_error_msg.visibility = View.GONE
//        fl_android.visibility = View.VISIBLE
//
//        if (currentPage == 1)
//            androidAdapter!!.datas.clear()
//        //添加新数据
//        androidAdapter!!.datas.addAll(mData)
//        //更新页面
//        androidAdapter!!.notifyDataSetChanged()
//    }

    override fun reflashView(mData: GankResponse<MutableList<GankData>>) {
        tv_error_msg.visibility = View.GONE
        fl_android.visibility = View.VISIBLE

        if (currentPage == 1)
            androidAdapter!!.datas.clear()
        //添加新数据
        androidAdapter!!.datas.addAll(mData.results)
        //更新页面
        androidAdapter!!.notifyDataSetChanged()
    }

    override fun getDataFail(error: String) {

        tv_error_msg.visibility = View.VISIBLE
        fl_android.visibility = View.GONE

        tv_error_msg.text = error
    }

    override fun getDataComplete() {
        //
        swipe_refresh_layout.isRefreshing = false
    }

    override fun getHisSuccess(dates: MutableList<String>) {
    }

    override fun getHisFail(error: String) {
    }

    override fun onRefresh() {
        //刷新首页
        currentPage = 1
        //重新请求数据
//        gankDataPresenter!!.initGankData(GankDataParam(type, pageNum, currentPage))
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun bindCompositeDisposable(disposable: Disposable) {
        LogUtil.i("GXW", "-----------bindCompositeDisposable----------")
    }

}
