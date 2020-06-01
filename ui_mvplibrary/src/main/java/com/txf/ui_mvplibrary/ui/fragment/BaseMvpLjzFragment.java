package com.txf.ui_mvplibrary.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.txf.ui_mvplibrary.interfaces.IFragment;
import com.txf.ui_mvplibrary.mvp.presenter.BasePresenter;
import com.txf.ui_mvplibrary.mvp.view.BaseView;

/**
 * @author txf
 * @create 2019/2/1 0001
 */
public abstract class BaseMvpLjzFragment<V extends BaseView, P extends BasePresenter<V>>
        extends BaseMvpFragment<V, P> implements IFragment {
    protected boolean isLoad;//是否加载数据
    protected boolean isShow;//是否显示
    protected boolean isViewCreated;//页面视图是否创建完成

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        if (isShow && !isLoad) {
            request();
            isLoad = true;
        }
    }
    protected abstract void request();
    @Override
    public void show() {
        isShow = true;
        if (isViewCreated && !isLoad) {
            request();
            isLoad = true;
        }
    }

    @Override
    public void onDestroyView() {
        isViewCreated = false;
        isShow = false;
        isLoad = false;
        super.onDestroyView();
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void hide() {
        isShow = false;
    }
}
