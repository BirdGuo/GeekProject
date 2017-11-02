package com.guoxw.geekproject.gankio.ui.activity

import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseActivity
import com.guoxw.geekproject.gankio.GankConfig
import com.guoxw.geekproject.gankio.adapter.DataAdapter
import com.guoxw.geekproject.gankio.bean.BeautyPic
import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.GankDayData
import com.guoxw.geekproject.gankio.bean.GankListData
import com.guoxw.geekproject.gankio.bean.params.GankDayDataParam
import com.guoxw.geekproject.gankio.presenter.GankDataInfoPresenter
import com.guoxw.geekproject.gankio.ui.views.IGankDayDataView
import kotlinx.android.synthetic.main.activity_gank_day_info.*

class GankDayInfoActivity : BaseActivity(), IGankDayDataView {

    //查询日期
    var date: String = ""

    //初始化接口
    val presenter: GankDataInfoPresenter = GankDataInfoPresenter(this)
    //初始化适配器
    var dataAdapter: DataAdapter = DataAdapter(this)

    override fun getLayoutId(): Int = R.layout.activity_gank_day_info

    override fun initView() {

        img_gdi.setImageDrawable(BeautyPic.beauty)
        ViewCompat.setTransitionName(img_gdi, GankConfig.TRANSLATE_GIRL_VIEW)
        //设置recycleview垂直布局
        rv_gdi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //设置适配器
        rv_gdi.adapter = dataAdapter
    }

    override fun initData() {

        //传值
        val meizi: GankData = intent.getSerializableExtra(GankConfig.MEIZI) as GankData
        //获取日期
        val date = meizi.publishedAt.substring(0, 10)
        //设置标题
        ctb_gdi.title = date
        //拆分日期
        val dateSP = date.split("-")
        //获取当日信息
        presenter.initDayData(GankDayDataParam(dateSP[0], dateSP[1], dateSP[2]))

    }

    override fun initListener() {

    }

    override fun onDestroy() {
        super.onDestroy()
        BeautyPic.beauty = null
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
