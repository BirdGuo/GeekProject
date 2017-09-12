package com.guoxw.gankio.network

import io.reactivex.disposables.Disposable

/**
* Created by guoxw on 2017/5/26.
* @auther guoxw
* @date 2017/5/26
* @desciption
* @package ${PACKAGE_NAME}
*/
interface LifeSubscription {

    fun bindCompositeDisposable(disposable: Disposable)

}