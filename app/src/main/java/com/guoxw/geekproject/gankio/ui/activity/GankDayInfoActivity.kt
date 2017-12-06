package com.guoxw.geekproject.gankio.ui.activity

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.guoxw.gankio.network.LifeSubscription
import com.guoxw.gankio.network.Stateful
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseActivity
import com.guoxw.geekproject.gankio.GankConfig
import com.guoxw.geekproject.gankio.adapter.DataAdapter
import com.guoxw.geekproject.gankio.bean.BeautyPic
import com.guoxw.geekproject.gankio.bean.GankData
import com.guoxw.geekproject.gankio.bean.GankDayData
import com.guoxw.geekproject.gankio.bean.GankListData
import com.guoxw.geekproject.gankio.bean.params.GankDayDataParam
import com.guoxw.geekproject.gankio.presenter.dao.GankDataInfoDao
import com.guoxw.geekproject.gankio.presenter.impl.GankDataInfoDaoImpl
import com.guoxw.geekproject.gankio.ui.views.IGankDayDataView
import com.guoxw.geekproject.utils.LogUtil
import com.guoxw.geekproject.utils.NetWorkUtil
import com.guoxw.geekproject.utils.TipsUtil
import com.guoxw.geekproject.utils.ToastUtil
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_gank_day_info.*

class GankDayInfoActivity : BaseActivity(), IGankDayDataView, GankDataInfoDao.View {

    //查询日期
    var date: String = ""


    //    val presenter: GankDataInfoPresenter = GankDataInfoPresenter(this, this)
    //初始化适配器
    var dataAdapter: DataAdapter = DataAdapter(this)

    //初始化接口
    var gankDataInfoDao: GankDataInfoDaoImpl? = null

    var gankDayData: GankDayData? = null

    /**
     * 接口获取状态
     * 1：未连接 ；2：连接中；3:获取成功；4：获取失败
     */
    var loadState: Int = 1

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

        gankDataInfoDao = GankDataInfoDaoImpl(this, this, this)

        //传值
        val meizi: GankData = intent.getSerializableExtra(GankConfig.MEIZI) as GankData
        //获取日期
        val date = meizi.publishedAt.substring(0, 10)
        //设置标题
        ctb_gdi.title = date
        //拆分日期
        val dateSP = date.split("-")
        //获取当日信息
//        presenter.initDayData(GankDayDataParam(dateSP[0], dateSP[1], dateSP[2]))
        gankDataInfoDao!!.fetchDayData(GankDayDataParam(dateSP[0], dateSP[1], dateSP[2]))

    }

    override fun initListener() {

        fab_gdi.setOnClickListener {//点击播放按钮
            if (loadState == 3 && gankDayData!!.休息视频.isNotEmpty()) {//判断页面是否加载完成是否有视频
                if (NetWorkUtil.isWifiConnect(this)) {//WiFi连接
                    openActivity(WebVideoActivity::class.java, Bundle())
                } else {
                    TipsUtil.showTipWithAction(fab_gdi, "你使用的不是wifi网络，要继续吗？", "继续", View.OnClickListener {
                        openActivity(WebVideoActivity::class.java, Bundle())
                    })
                }
            } else {
                ToastUtil.toastShort(this, "今天木有小视频呢")
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        BeautyPic.beauty = null
    }


    override fun reflashView(mData: GankDayData) {
        Glide.with(this)
                .load(mData.福利[0].url)
                .into(img_gdi)

        gankDayData = mData

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

        loadState = 3

    }

    override fun getDataFail(error: String) {
        loadState = 4
    }

    override fun getDataComplete() {
        LogUtil.i("GXW","------------- getDataComplete -----------")
        spb_gdi.progressiveStop()
    }

}
