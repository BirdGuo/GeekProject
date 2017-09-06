package com.guoxw.geekproject.gankio.network.retrofit

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonWriter
import com.guoxw.geekproject.utils.LogUtil
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Converter
import java.io.OutputStreamWriter
import java.io.Writer

/**
 * Created by guoxw on 2017/7/7 0007.
 * @auther guoxw
 * @date 2017/7/7 0007
 * @desciption
 * @package com.guoxw.gankio.network.retrofit
 */
class MyJsonRequestBodyConverter<T>(val gson: Gson, val adapter: TypeAdapter<T>) : Converter<T, RequestBody> {

    val TAG = MyJsonRequestBodyConverter::class.java.name.toString()

    val MEDIA_TYPE: MediaType = MediaType.parse("application/json;charset=UTF-8")!!

    override fun convert(value: T): RequestBody {

        LogUtil.i(TAG, value.toString())

        val buffer: Buffer = Buffer()
        //Charsets kotlin 中Charset的包装类
        val writer: Writer = OutputStreamWriter(buffer.outputStream(), Charsets.UTF_8)

        val jsonWriter: JsonWriter = gson.newJsonWriter(writer)
        adapter.write(jsonWriter, value)
        jsonWriter.close()
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString())
    }
}