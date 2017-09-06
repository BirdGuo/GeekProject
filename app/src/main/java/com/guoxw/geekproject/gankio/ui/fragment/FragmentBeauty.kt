package com.guoxw.geekproject.gankio.ui.fragment


import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseFragment
import com.guoxw.geekproject.gankio.adapter.WaterFallAdapter
import com.guoxw.geekproject.gankio.api.GankIOApi
import com.guoxw.geekproject.gankio.api.resetApi.GankIOResetApi
import com.guoxw.geekproject.utils.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_beauty.*


/**
 * A simple [Fragment] subclass.
 */
class FragmentBeauty : BaseFragment() {

    var waterFullAdapter: WaterFallAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_beauty

    override fun initView() {

//        mRecyclerView.setLayoutManager(new
//                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        mAdapter = new WaterFallAdapter(mActivity);
//        mRecyclerView.setAdapter(mAdapter);
        waterFullAdapter = WaterFallAdapter(context)
        rcv_beauty.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rcv_beauty.adapter = waterFullAdapter

    }

    override fun initData() {

        val gankIOApi: GankIOApi = GankIOResetApi
        gankIOApi.getGankIOData("福利", 10, 1).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe({ res ->
                    if (!res.error) {
                        LogUtil.i("GXW", "-------------1------------")
                        waterFullAdapter!!.mImages = res.results
                        waterFullAdapter!!.getRandomHeight(res.results)
                        waterFullAdapter!!.notifyDataSetChanged()
                    } else {
                        LogUtil.i("GXW", "-------------2------------")
                    }
                }, { e -> LogUtil.e("MainActivity", "error:".plus(e.message)) }, {

                })

    }

    override fun initListener() {
    }


}// Required empty public constructor
