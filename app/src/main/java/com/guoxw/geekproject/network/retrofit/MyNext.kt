package com.guoxw.gankio.network.utils

import com.guoxw.gankio.network.Stateful
import io.reactivex.functions.Consumer


/**
 * Created by guoxw on 2017/7/27 0027.
 * @auther guoxw
 * @date 2017/7/27 0027
 * @desciption
 * @package com.guoxw.gankio.network.utils
 */
class MyNext<T> : Consumer<T> {

    private var target: Stateful? = null

    override fun accept(t: T) {



    }

}