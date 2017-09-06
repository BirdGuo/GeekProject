package com.guoxw.geekproject.network

import com.guoxw.geekproject.BuildConfig
import com.guoxw.geekproject.gankio.network.retrofit.MyJsonConverterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Created by guoxw on 2017/9/5 0005.
 * @auther guoxw
 * @date 2017/9/5 0005
 * @desciption
 * @package com.guoxw.geekproject.network
 */
object ApiClient {
    //基本接口构造
    val retrofit: Retrofit = Retrofit.Builder().baseUrl(BuildConfig.GankIO_URL)
            //自定义Gson转换器
            .addConverterFactory(MyJsonConverterFactory.create())
            //默认
//            .addConverterFactory(GsonConverterFactory.create())
            //rxjava适配器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            //设置超时
            .client(OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                    //添加头还有拦截器
                    .addInterceptor({ chain ->
                        val request: Request = chain.request().newBuilder()
////                                .addHeader("Content-Type", "application/json;charset=UTF-8")
//////                                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
//                                .addHeader("Accept-Encoding", "gzip, deflate, sdch")
//                                .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
//                                .addHeader("Connection", "keep-alive")
                                .build()
                        chain.proceed(request)
                    }).build())
            .build()

}