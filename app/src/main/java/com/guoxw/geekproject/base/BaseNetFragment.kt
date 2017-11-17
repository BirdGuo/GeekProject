package com.guoxw.geekproject.base

import android.view.LayoutInflater
import com.guoxw.gankio.network.LifeSubscription
import com.guoxw.geekproject.R
import com.guoxw.geekproject.utils.LogUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_base.*

/**
 * Created by guoxw on 2017/11/3 0003.
 */
abstract class BaseNetFragment<D, T : BasePresenter<D, BaseView<D>>> : BaseFragment(), LifeSubscription {

    var presenter: T? = null

    /**
     * 线程
     */
    var compositeDisposable: CompositeDisposable? = null

    /**
     * 初始化页面
     */
    protected abstract fun initUI()

    /**
     * 设置布局
     * @return getContentLayoutId 绑定页面id
     */
    abstract fun getContentLayoutId(): Int

    override fun getLayoutId(): Int = R.layout.fragment_base

    override fun initView() {
        //查找页面
        val contentView = LayoutInflater.from(context).inflate(getContentLayoutId(), null)
        //往Fragment中添加页面
        fl_fragment_base.addView(contentView)
        //初始化页面
        initUI()
    }

    override fun onStop() {
        super.onStop()
        if (compositeDisposable != null && compositeDisposable!!.size() > 0) {
            LogUtil.i("GXW", "--------onStop-----1----")
            compositeDisposable!!.dispose()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        if (compositeDisposable != null && compositeDisposable!!.size() > 0) {
            LogUtil.i("GXW", "--------onDetach-----1----")
            compositeDisposable!!.dispose()
        }

//        if (basePresenter!=null){
//            basePresenter!!.detachView()
//        }

    }


    override fun bindCompositeDisposable(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable!!.add(disposable)
    }


}