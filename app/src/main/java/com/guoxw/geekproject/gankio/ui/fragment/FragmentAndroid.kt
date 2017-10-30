package com.guoxw.geekproject.gankio.ui.fragment


import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseFragment
import com.guoxw.geekproject.gankio.adapter.AndroidAdapter
import com.guoxw.geekproject.gankio.api.GankIOApi
import com.guoxw.geekproject.gankio.api.resetApi.GankIOResetApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_android.*
import kotlinx.android.synthetic.main.fragment_beauty.*


/**
 * A simple [Fragment] subclass.
 */
class FragmentAndroid : BaseFragment() {

    var androidAdapter: AndroidAdapter? = null

    val gankIOApi: GankIOApi = GankIOResetApi

    override fun getLayoutId(): Int = R.layout.fragment_android

    override fun initView() {

        androidAdapter = AndroidAdapter(context)

//        rcv_android.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL)
        rcv_android.adapter = androidAdapter

    }

    override fun initData() {

        gankIOApi.getGankIOData("Android", 10, 1).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe({ res ->
                    if (!res.error) {
                        androidAdapter!!.datas.clear()
                        androidAdapter!!.datas.addAll(res.results)
                        androidAdapter!!.notifyDataSetChanged()
                    }
                })

    }

    override fun initListener() {
    }

}// Required empty public constructor
