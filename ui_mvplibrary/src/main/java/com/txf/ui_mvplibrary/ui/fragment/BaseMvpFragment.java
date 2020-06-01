package com.txf.ui_mvplibrary.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;

import com.txf.ui_mvplibrary.mvp.presenter.BasePresenter;
import com.txf.ui_mvplibrary.mvp.view.BaseView;

/**
 * @author txf
 * @create 2019/2/1 0001
 * 1.暴露设置P, V方法{@link #setContract(BaseView, BasePresenter)}} 必须在{@link #onViewCreated(View, Bundle)}}之前调用
 */
public abstract class BaseMvpFragment<V extends BaseView, P extends BasePresenter<V>> extends BaseFragment {
    private P mPresenter;
    private V mView;

    protected void setContract(V v, P p) {
        mPresenter = p;
        mView = v;
        mPresenter.attachView(mView);
    }

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void onResume() {
        //防止放入ViewPager中  onDestroy解除绑定
        if (mPresenter != null && mView != null && mPresenter.getView() == null)
            mPresenter.attachView(mView);
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
    }
}
