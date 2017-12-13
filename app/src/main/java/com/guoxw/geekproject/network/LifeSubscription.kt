package com.guoxw.geekproject.network

import io.reactivex.disposables.Disposable

/**
 * Created by guoxw on 2017/5/26.
 * @auther guoxw
 * @date 2017/5/26
 * @desciption
 * @package ${PACKAGE_NAME}
 */
interface LifeSubscription {

    /**
     * 绑定订阅管理器
     * @param disposable 订阅
     */
    fun bindCompositeDisposable(disposable: Disposable)

}