package com.guoxw.geekproject.gankio.network.retrofit

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.guoxw.geekproject.utils.LogUtil
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException


/**
 * Created by guoxw on 2017/7/7 0007.
 * @auther guoxw
 * @date 2017/7/7 0007
 * @desciption
 * @package com.guoxw.gankio.network.retrofit
 */
class MyJsonResponseBodyConverter<T>(val gson: Gson, val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

    val TAG = MyJsonResponseBodyConverter::class.java.name.toString()

    override fun convert(value: ResponseBody?): T {


        val result = value!!.string()
        LogUtil.i(TAG, "result:".plus(result))
        return adapter.fromJson(result)

    }

    // 解析geo中的数据
    @Throws(IOException::class)
    fun readDoubleArray(jsReader: JsonReader) {
        jsReader.beginArray()
        while (jsReader.hasNext()) {
            LogUtil.i("TAG", "readDoubleArray:" + jsReader.nextString())
        }
        jsReader.endArray()
    }
}