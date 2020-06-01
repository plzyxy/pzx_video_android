package com.thinksoft.banana.mvp.base;

import android.content.Context;

import com.txf.ui_mvplibrary.mvp.model.BaseModel;

import java.util.HashMap;

import okhttp3.Callback;

/**
 * @author txf
 * @create 2019/1/29 0029
 * @
 */
public abstract class BaseAppModel extends BaseModel {
    public void request(Context context, int tag, HashMap<String, Object> maps, Callback l) {
        request(context, tag, tag, maps, l);
    }

    /**
     * @param context
     * @param tag     网络请求标识
     * @param sign    网络请求回调标识
     * @param maps    请求参数
     * @param l       请求回调
     */
    public abstract void request(Context context, int tag, int sign, HashMap<String, Object> maps, Callback l);
}
