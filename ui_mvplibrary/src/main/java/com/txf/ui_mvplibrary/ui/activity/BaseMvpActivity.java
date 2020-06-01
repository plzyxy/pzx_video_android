package com.txf.ui_mvplibrary.ui.activity;

import com.txf.ui_mvplibrary.mvp.presenter.BasePresenter;
import com.txf.ui_mvplibrary.mvp.view.BaseView;


/**
 * @author txf
 * @create 2019/1/29 0029
 * 继承{@link BasePermissionsActivity}
 *
 * 1.暴露设置P, V方法{@link #setContract(BaseView, BasePresenter)}
 */
public abstract class BaseMvpActivity<V extends BaseView, P extends BasePresenter<V>>
        extends BasePermissionsActivity {
    private P presenter;
    private V view;

    protected void setContract(V v, P p) {
        presenter = p;
        view = v;
        presenter.attachView(view);
    }

    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
