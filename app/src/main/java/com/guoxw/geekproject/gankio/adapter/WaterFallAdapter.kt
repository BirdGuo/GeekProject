package com.guoxw.geekproject.gankio.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.guoxw.geekproject.R
import com.guoxw.geekproject.events.RCVItemClickListener
import com.guoxw.geekproject.gankio.api.GankIOApi
import com.guoxw.geekproject.gankio.api.resetApi.GankIOResetApi
import com.guoxw.geekproject.gankio.bean.BeautyPic
import com.guoxw.geekproject.gankio.bean.params.GankDayDataParam
import com.guoxw.geekproject.gankio.ui.activity.BeautyActivity
import com.guoxw.geekproject.gankio.ui.activity.GankDayInfoActivity
import com.guoxw.geekproject.utils.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.Serializable


/**
 * Created by guoxw on 2017/9/6 0006.
 * @auther guoxw
 * @date 2017/9/6 0006
 * @desciption
 * @package com.guoxw.geekproject.gankio.adapter
 */
class WaterFallAdapter : RecyclerView.Adapter<WaterFallAdapter.ViewHolder> {

//    var mImages: MutableList<GankData> = ArrayList<GankData>()

    var dates: MutableList<String> = ArrayList<String>()

    var mHeights: MutableList<Int> = ArrayList<Int>()

    var mContext: Context? = null

    var rcvItemClickListener: RCVItemClickListener? = null

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    constructor(mContext: Context?, rcvItemClickListener: RCVItemClickListener?) : super() {
        this.mContext = mContext
        this.rcvItemClickListener = rcvItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_gank_image, parent, false)

        return ViewHolder(view, rcvItemClickListener)
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val layoutParams = holder!!.img_item_gank!!.layoutParams
        layoutParams.height = mHeights[position]
        holder.img_item_gank!!.layoutParams = layoutParams
//        val gankData = mImages[position]

        val date = dates[position]
        val YMD = date.split("-")

        val gankIOApi: GankIOApi = GankIOResetApi

        LogUtil.i("GXW", "date:".plus(date))
        gankIOApi.getGankDayData(GankDayDataParam(YMD[0], YMD[1], YMD[2])).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe({ res ->
                    if (!res.error) {

                        val url = res.results.福利[0].url
                        Glide.with(mContext)
                                .load(url)
                                .into(holder!!.img_item_gank)

                        holder.img_item_gank!!.setOnClickListener {


                            LogUtil.i("GXW", "img_item_gank")
                            val bundle = Bundle()
                            bundle.putString("url", url)
                            val intent = Intent()
                            intent.putExtra("data", bundle)
                            intent.setClass(mContext, BeautyActivity::class.java)
                            mContext!!.startActivity(intent)

                        }

                        if (res.results.休息视频 != null && res.results.休息视频.isNotEmpty()) {
                            holder!!.tv_item_gank!!.text = YMD[1].plus("-").plus(YMD[2]).plus(res.results.休息视频[0].desc)
                        } else {
                            holder!!.tv_item_gank!!.text = YMD[1].plus("-").plus(YMD[2]).plus("今天木有小视频")
                        }
                    }
                })
    }

    fun getRandomHeight(mList: MutableList<String>) {

        for (i in mList.indices) {
            //随机的获取一个范围为200-600直接的高度
            mHeights!!.add((300 + Math.random() * 400).toInt())
        }
    }


    class ViewHolder : RecyclerView.ViewHolder {

        var img_item_gank: ImageView? = null
        var tv_item_gank: TextView? = null
        var itemClickListener: RCVItemClickListener? = null

        var cv_item_gank: CardView? = null

        constructor(itemView: View?, itemClickListener: RCVItemClickListener?) : super(itemView) {
            this.itemClickListener = itemClickListener
            img_item_gank = itemView!!.findViewById<ImageView>(R.id.img_item_gank)
            tv_item_gank = itemView!!.findViewById<TextView>(R.id.tv_item_gank)

            cv_item_gank = itemView!!.findViewById<CardView>(R.id.cv_item_gank)

            tv_item_gank!!.setOnClickListener { view ->
//                itemClickListener!!.onItemClickListener(view, adapterPosition)

                BeautyPic.beauty = img_item_gank!!.drawable

//                ShareElement.shareDrawable = ivMeizi.getDrawable()
                val intent = Intent(view.context, GankDayInfoActivity::class.java)
                intent.putExtra(PanConfig.MEIZI, card.getTag() as Serializable)
//                val optionsCompat = ActivityOptionsCompat
//                        .makeSceneTransitionAnimation(context as Activity, ivMeizi, PanConfig.TRANSLATE_GIRL_VIEW)
//                ActivityCompat.startActivity(context, intent, optionsCompat.toBundle())

            }

            tv_item_gank!!.setOnLongClickListener { view ->
                itemClickListener!!.onItemLongClickListener(view, adapterPosition)
                false
            }
        }


    }


}