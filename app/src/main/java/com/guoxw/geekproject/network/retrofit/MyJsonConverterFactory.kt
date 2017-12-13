package com.guoxw.geekproject.network.retrofit

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by guoxw on 2017/7/7 0007.
 * @auther guoxw
 * @date 2017/7/7 0007
 * @desciption
 * @package com.guoxw.gankio.network
 */
class MyJsonConverterFactory : Converter.Factory() {

    private val gson: Gson = Gson()

    /**
     * 部分是静态方法的情况 : 将方法用 companion object { } 包裹即可
     */
    companion object {
        fun create(): MyJsonConverterFactory {
            return MyJsonConverterFactory()
        }
    }

    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {

        val adapter: TypeAdapter<*> = gson.getAdapter(TypeToken.get(type))
        return MyJsonResponseBodyConverter(gson, adapter)
//        return super.responseBodyConverter(type, annotations, retrofit)
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<out Annotation>?, methodAnnotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody>? {

        val adapter: TypeAdapter<*> = gson.getAdapter(TypeToken.get(type))
        return MyJsonRequestBodyConverter(gson, adapter)
//        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }

    override fun stringConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, String>? {
        return super.stringConverter(type, annotations, retrofit)
    }
}