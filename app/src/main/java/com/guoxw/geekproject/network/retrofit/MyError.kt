package com.guoxw.geekproject.network.retrofit

import com.guoxw.geekproject.network.ApiThrowable
import java.util.function.Consumer

/**
 * Created by guoxw on 2017/11/20 0020.
 */
class MyError : Consumer<ApiThrowable> {

    override fun accept(t: ApiThrowable) {

    }
}