package com.thinksoft.banana.mvp.base;

import com.txf.ui_mvplibrary.mvp.presenter.BasePresenter;
import com.txf.ui_mvplibrary.mvp.view.BaseView;

import java.util.HashMap;

/**
 * @author txf
 * @create 2019/1/29 0029
 * @
 */

public abstract class BaseAppPresenter<V extends BaseView> extends BasePresenter<V> {

    public void getData(int tag) {
        getData(tag, new HashMap<String, Object>(), true);
    }

    public void getData(int tag, HashMap<String, Object> maps) {
        getData(tag, maps, true);
    }

    public void getData(int tag, int sign, HashMap<String, Object> maps) {
        getData(tag, sign, maps, true);
    }


    public void getData(int tag, HashMap<String, Object> maps, boolean isShowLoading) {
        getData(tag, tag, maps, isShowLoading);
    }

    public void getData(int tag, boolean isShowLoading) {
        getData(tag, tag, new HashMap<String, Object>(), isShowLoading);
    }

    /**
     * @param tag           请求标识
     * @param sign          回调标识
     * @param maps          请求参数
     * @param isShowLoading 是否显示加载
     */
    public abstract void getData(int tag, int sign, HashMap<String, Object> maps, boolean isShowLoading);
}
