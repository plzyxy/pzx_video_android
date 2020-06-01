package com.thinksoft.banana.mvp.base;

import com.google.gson.JsonElement;
import com.txf.ui_mvplibrary.mvp.view.BaseView;

/**
 * @author txf
 * @create 2019/1/29 0029
 * @
 */
public interface BaseAppView extends BaseView {
    /**
     * 网络请求成功
     *
     * @param sign     回调标识
     * @param data    请求结果
     * @param message 文字描述
     */
    void httpOnSuccess(int sign, JsonElement data, String message);

    /**
     * 网络请求失败
     *
     * @param sign     回调标识
     * @param error   错误code
     * @param message 文字描述
     */
    void httpOnError(int sign, int error, String message);
}
