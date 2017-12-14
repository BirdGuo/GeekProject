package com.guoxw.geekproject.map.presenter.daoimpl

import android.content.Context
import com.guoxw.geekproject.map.presenter.dao.MapDao
import com.guoxw.geekproject.map.viewInterfaces.IFileView
import com.guoxw.geekproject.map.viewInterfaces.IMapView

/**
 * @auther guoxw
 * @date 2017/12/14 0014
 * @package com.guoxw.geekproject.map.presenter.daoimpl
 * @desciption
 */
class MapDaoImpl(viewFile: IFileView, viewMap: IMapView) : MapDao.Presenter {


    override fun readStationsFromAsset(mContext: Context, name: String) {



    }

    override fun addStationsToMap() {
    }

    override fun saveStationsToDB() {
    }

}