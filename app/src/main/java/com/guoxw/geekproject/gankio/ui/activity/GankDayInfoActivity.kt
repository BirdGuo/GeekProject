package com.guoxw.geekproject.gankio.ui.activity

import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseActivity
import com.guoxw.geekproject.gankio.adapter.DataAdapter
import com.guoxw.geekproject.gankio.bean.GankDayData
import com.guoxw.geekproject.gankio.bean.GankListData
import com.guoxw.geekproject.gankio.bean.params.GankDayDataParam
import com.guoxw.geekproject.gankio.presenter.GankDataInfoPresenter
import com.guoxw.geekproject.gankio.ui.views.IGankDayDataView
import kotlinx.android.synthetic.main.activity_gank_day_info.*

class GankDayInfoActivity : BaseActivity(), IGankDayDataView {


    var date: String = ""

    val presenter: GankDataInfoPresenter = GankDataInfoPresenter(this)

    var dataAdapter: DataAdapter = DataAdapter(this)

    override fun getLayoutId(): Int = R.layout.activity_gank_day_info

    override fun initView() {

        rv_gdi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_gdi.adapter = dataAdapter
    }

    override fun initData() {

        val bundleExtra = intent.getBundleExtra("data")
        date = bundleExtra.getString("date")

        ctb_gdi.title = date

        val dateSP = date.split("-")

        presenter.initDayData(GankDayDataParam(dateSP[0], dateSP[1], dateSP[2]))

    }

    override fun initListener() {

    }

    override fun reflashView(mData: GankDayData) {

        Glide.with(this)
                .load(mData.福利[0].url)
                .into(img_gdi)

        val gankListDatas: MutableList<GankListData> = ArrayList<GankListData>()

        if (mData.Android != null)
            gankListDatas.add(GankListData("Android", mData.Android))
        if (mData.iOS != null)
            gankListDatas.add(GankListData("iOS", mData.iOS))
        if (mData.前端 != null)
            gankListDatas.add(GankListData("前端", mData.前端))
        if (mData.拓展资源 != null)
            gankListDatas.add(GankListData("拓展资源", mData.拓展资源))

        dataAdapter.dataList.clear()
        dataAdapter.dataList.addAll(gankListDatas)
        dataAdapter.notifyDataSetChanged()

    }

    override fun getDataFail(error: String) {
    }

    override fun getDataComplete() {
    }

}
