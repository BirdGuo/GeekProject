package com.guoxw.geekproject.gankio.ui.fragment


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseNetFragment
import com.guoxw.geekproject.base.BaseWebActivity
import com.guoxw.geekproject.events.RCVItemClickListener
import com.guoxw.geekproject.gankio.adapter.AndroidAdapter
import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.params.GankDataParam
import com.guoxw.geekproject.gankio.bean.responses.GankResponse
import com.guoxw.geekproject.gankio.presenter.dao.GankDataDao
import com.guoxw.geekproject.gankio.presenter.impl.GankDataDaoImpl
import com.guoxw.geekproject.utils.RecyclerViewUtil
import kotlinx.android.synthetic.main.fragment_android.*
import kotlinx.android.synthetic.main.fragment_base.*


@SuppressLint("ValidFragment")
/**
 * A simple [Fragment] subclass.
 */
class FragmentAndroid(val type: String) : BaseNetFragment<GankResponse<MutableList<GankData>>, GankDataDaoImpl>(),
        GankDataDao.View, SwipeRefreshLayout.OnRefreshListener {


    //适配器
    var androidAdapter: AndroidAdapter? = null

    //Presenter
//    var gankDataPresenter: GankDataPresenter? = null

//    var gankDataDao: GankDataDaoImpl? = null

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
//        gankDataDao = GankDataDaoImpl(context,this, this)
        //获取第一页数据
//        gankDataDao!!.fetchGankData(GankDataParam(type, pageNum, currentPage))
        presenter =  GankDataDaoImpl(context,this, this)
        Log.i("GXW","initData android")
        presenter!!.fetchGankData(GankDataParam(type, pageNum, currentPage))
//        gankDataPresenter!!.initGankData(GankDataParam(type, pageNum, currentPage))
    }

    override fun initListener() {

        //添加滑动监听
        rcv_android.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                /**
                 * RecyclerView被滚动时调用的回调方法。 这会是
                 *在滚动完成后调用。
                 * <p>
                 *如果布局后可见项目范围发生变化，则此回调也将被调用
                 *计算。 在这种情况下，dx和dy将会是0。
                 *
                 * @param recyclerView 滚动的RecyclerView。
                 * @param dx 水平滚动的数量。
                 * @param dy 垂直滚动的数量。
                 */
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
//                    gankDataDao!!.fetchGankData(GankDataParam(type, pageNum, currentPage))
                    Log.i("GXW","onScrollStateChanged android")
                    presenter!!.fetchGankData(GankDataParam(type, pageNum, currentPage))
                }
            }
        })

    }

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
    }


}
