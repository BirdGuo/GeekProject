package com.guoxw.geekproject.gankio.ui.fragment


import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseFragment
import com.guoxw.geekproject.events.RCVItemClickListener
import com.guoxw.geekproject.gankio.adapter.AndroidAdapter
import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.params.GankDataParam
import com.guoxw.geekproject.gankio.presenter.GankDataPresenter
import com.guoxw.geekproject.gankio.ui.views.IGankDataView
import com.guoxw.geekproject.utils.RecyclerViewUtil
import kotlinx.android.synthetic.main.fragment_android.*


@SuppressLint("ValidFragment")
/**
 * A simple [Fragment] subclass.
 */
class FragmentAndroid(val type: String) : BaseFragment(), IGankDataView {

    //适配器
    var androidAdapter: AndroidAdapter? = null

    //presenter
    var gankDataPresenter: GankDataPresenter? = null
    //当前页
    var currentPage: Int = 1
    //一页多少数据
    val pageNum: Int = 20
    //类型
//    val type: String = "Android"

    override fun getLayoutId(): Int = R.layout.fragment_android

    override fun initView() {
        androidAdapter = AndroidAdapter(context, object : RCVItemClickListener {
            override fun onItemClickListener(view: View, postion: Int) {

            }

            override fun onItemLongClickListener(view: View, postion: Int) {
            }
        })

        rcv_android.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcv_android.adapter = androidAdapter

    }

    override fun initData() {
        //清除数据
        androidAdapter!!.datas.clear()
        //初始化Presenter
        gankDataPresenter = GankDataPresenter(this, context)
        //获取第一页数据
        gankDataPresenter!!.initGankData(GankDataParam(type, pageNum, currentPage))
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
                    gankDataPresenter!!.initGankData(GankDataParam(type, pageNum, currentPage))
                }
            }
        })

    }

    override fun reflashView(mData: MutableList<GankData>) {
        //添加新数据
        androidAdapter!!.datas.addAll(mData)
        //更新页面
        androidAdapter!!.notifyDataSetChanged()
    }

    override fun getDataFail(error: String) {
    }

    override fun getDataComplete() {
    }

    override fun getHisSuccess(dates: MutableList<String>) {
    }

    override fun getHisFail(error: String) {
    }


}
