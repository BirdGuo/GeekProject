package com.guoxw.geekproject.gankio.ui.activity

import com.bumptech.glide.Glide
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseActivity
import com.guoxw.geekproject.gankio.bean.GankDayData
import com.guoxw.geekproject.gankio.bean.GankDayDataParam
import com.guoxw.geekproject.gankio.presenter.GankDataInfoPresenter
import com.guoxw.geekproject.gankio.ui.views.IGankDayDataView
import kotlinx.android.synthetic.main.activity_gank_day_info.*

class GankDayInfoActivity : BaseActivity(), IGankDayDataView {


    var date: String = ""

    val presenter: GankDataInfoPresenter = GankDataInfoPresenter(this)

    override fun getLayoutId(): Int = R.layout.activity_gank_day_info

    override fun initView() {
    }

    override fun initData() {

        val bundleExtra = intent.getBundleExtra("data")
        date = bundleExtra.getString("date")

        val dateSP = date.split("-")

        presenter.initDayData(GankDayDataParam(dateSP[0], dateSP[1], dateSP[2]))

    }

    override fun initListener() {
    }

    override fun reflashView(mData: GankDayData) {

        Glide.with(this)
                .load(mData.福利[0].url)
                .into(img_gdi)
    }

    override fun getDataFail(error: String) {
    }

    override fun getDataComplete() {
    }

}
