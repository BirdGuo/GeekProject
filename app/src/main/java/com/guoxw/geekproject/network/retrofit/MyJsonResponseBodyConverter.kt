package com.guoxw.geekproject.network.retrofit

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Converter


/**
 * Created by guoxw on 2017/7/7 0007.
 * @auther guoxw
 * @date 2017/7/7 0007
 * @desciption
 * @package com.guoxw.gankio.network.retrofit
 */
class MyJsonResponseBodyConverter<T>(val gson: Gson, val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody?): T {
        val result = value!!.string()
        return adapter.fromJson(result)
    }

}