package com.guoxw.gankio.network.utils

import io.reactivex.functions.Consumer


/**
 * Created by guoxw on 2017/7/27 0027.
 * @auther guoxw
 * @date 2017/7/27 0027
 * @desciption
 * @package com.guoxw.gankio.network.utils
 */
interface MyConsumer<T> : Consumer<T> {

    override fun accept(t: T) {
    }
}