package com.guoxw.geekproject.gankio.ui.activity

import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseToolbarActivity
import com.guoxw.geekproject.gankio.bean.GankDayData
import com.guoxw.geekproject.gankio.bean.GankDayDataParam
import com.guoxw.geekproject.gankio.presenter.GankDataInfoPresenter
import com.guoxw.geekproject.gankio.ui.views.IGankDayDataView
import kotlinx.android.synthetic.main.include_toolbar.*

class GankDayInfoActivity : BaseToolbarActivity(), IGankDayDataView {

    var date: String = ""

    val presenter: GankDataInfoPresenter = GankDataInfoPresenter(this)

    override fun getContentLayoutId(): Int = R.layout.activity_gank_day_info

    override fun initUI() {

        setToolBar(tb_toolbar_base, date)

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



    }

    override fun getDataFail(error: String) {
    }

    override fun getDataComplete() {
    }

}
