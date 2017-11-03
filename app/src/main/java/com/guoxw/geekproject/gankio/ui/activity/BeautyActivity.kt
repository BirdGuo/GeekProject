package com.guoxw.geekproject.gankio.ui.activity

import android.support.v4.view.ViewCompat
import com.bumptech.glide.Glide
import com.guoxw.geekproject.R
import com.guoxw.geekproject.base.BaseToolbarActivity
import com.guoxw.geekproject.gankio.GankConfig
import com.guoxw.geekproject.gankio.bean.BeautyPic
import com.guoxw.geekproject.gankio.bean.GankData
import kotlinx.android.synthetic.main.activity_beauty.*
import kotlinx.android.synthetic.main.activity_gank_day_info.*
import kotlinx.android.synthetic.main.include_toolbar.*

class BeautyActivity : BaseToolbarActivity() {

    var url: String? = ""

    override fun initUI() {
        setToolBar(tb_toolbar_base, "福利")
        //设置打开方式
        ViewCompat.setTransitionName(img_beauty, GankConfig.TRANSLATE_GIRL_VIEW)
        img_beauty.setImageDrawable(BeautyPic.beauty)
        Glide.with(this)
                .load(url)
                .into(img_beauty)
    }

    override fun getContentLayoutId(): Int = R.layout.activity_beauty

    override fun initData() {

        //传值
        val meizi: GankData = intent.getSerializableExtra(GankConfig.MEIZI) as GankData
        url = meizi.url

    }

    override fun initListener() {

        img_beauty.setOnClickListener {

            hideOrShowToolBar()
//            when (tb_toolbar_base.visibility) {
//                View.GONE -> {
//                    tb_toolbar_base.visibility = View.VISIBLE
//                }
//                View.VISIBLE ->{
//                    tb_toolbar_base.visibility = View.GONE
//                }
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BeautyPic.beauty = null
    }

}
