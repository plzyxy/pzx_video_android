package com.txf.ui_mvplibrary.mvp.presenter;


import com.txf.ui_mvplibrary.mvp.view.BaseView;

/**
 * @author txf
 * @create 2019/1/29 0029
 * @
 */

public abstract class BasePresenter<V extends BaseView> {
    private V mView;

    public V getView() {
        return mView;
    }

    public void attachView(V v) {
        mView = v;
    }

    public void detachView() {
        mView = null;
    }
}
