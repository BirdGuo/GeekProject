package com.guoxw.geekproject.map.api

import android.content.Context
import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.google.gson.Gson
import com.guoxw.geekproject.map.bean.MyStations
import com.guoxw.geekproject.map.bean.MyStations_Table
import com.guoxw.geekproject.map.bean.Station
import com.guoxw.geekproject.map.utils.CluterUtil
import com.guoxw.geekproject.utils.FileUtil
import com.raizlabs.android.dbflow.kotlinextensions.*
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_map.*


/**
 * @auther guoxw
 * @date 2017/12/14 0014
 * @package com.guoxw.geekproject.map.api
 * @desciption
 */
object MapResetApi : MapApi {
    override fun addStationsToMap(markerOptionsListAll: MutableList<MarkerOptions>, list: MutableList<Station>,
                                  mContext: Context, amap: AMap, clickedMarker: Marker): Observable<MutableList<Station>> {

        return Observable.create { t ->

            try {
                //如果放在进行点的绘制固然不会卡主线程，但是由于异步的操作下可能会对List造成同时访问，产生异常
                //放到这会卡顿主线程，但是不会产生同时访问的异常
                //通过synchronized锁定List,不能同时修改
                synchronized(markerOptionsListAll, {

                    markerOptionsListAll.clear()
                    list.forEach {
                        //遍历所有station
//                      addStationToMap(it)

                        //把筛选出来的数据转换为marker参数
                        val markerOptions: MarkerOptions = MarkerOptions()
                        markerOptions.position(LatLng(it.lat, it.lon))//设置位置
                        markerOptions.title(it.address)//设置标题
                        markerOptionsListAll.add(markerOptions)

                    }
                    //聚合
                    CluterUtil.resetMarks(mContext, amap,markerOptionsListAll, clickedMarker)
                })

                t.onNext(list)
            } catch (e: Exception) {
                t.onError(e)
            } finally {
                t.onComplete()
            }

        }

    }

    override fun selectStationAllFromDB(): Observable<MutableList<Station>> {

        return Observable.create { t ->
            val list = select.from(Station::class.java).list
            if (list.isNotEmpty()) {
                t.onNext(list)
            } else {
                t.onError(Throwable("no station in db!"))
            }
            t.onComplete()
        }
    }

    override fun readStationsFromAsset(mContext: Context, name: String): Observable<String> {
        return Observable.create { t ->
            //读取文件
            val assetToString = FileUtil.assetToString(mContext, name)
            val gson = Gson()
            val myStations = gson.fromJson(assetToString, MyStations::class.java)

//            val query = SQLite.select(com.raizlabs.android.dbflow.sql.language.Method.max(MyStations_Table.version)).from(MyStations::class).querySingle()
//            val version = query!!.getInt(0)

            /**
             * 升序排列后的版本号
             */
            val stationFromLoc = (select from MyStations::class).groupBy(MyStations_Table.version).queryList()//按照升序

            if (stationFromLoc.isNotEmpty()) {//不为空
                if (myStations.version > stationFromLoc[stationFromLoc.lastIndex].version) {//新版本大于本地版本
                    //删除数据
                    deleteStationsAll()
                    //保存到数据库
                    saveStations(myStations)
                }
            } else {//为空
                //保存到数据库
                saveStations(myStations)
            }

            if (assetToString != null) {
                //传到订阅者
                t.onNext(assetToString)
            } else {
                //无内容
                t.onError(Throwable("File content is null"))
            }
            //完成获取
            t.onComplete()
        }
    }


    override fun saveStationsToDB() {
    }

    /**
     * 保存到数据库
     * @param myStations 定位数据
     */
    private fun saveStations(myStations: MyStations) {
        myStations.save()
        // myStations.stations.save()
        myStations.stations.asSequence()
                .forEach {
                    it.save()
                }
    }

    /**
     * 删除定位数据
     */
    private fun deleteStationsAll() {
        //不加where就是全部删除
        delete(Station::class).execute()
    }

}