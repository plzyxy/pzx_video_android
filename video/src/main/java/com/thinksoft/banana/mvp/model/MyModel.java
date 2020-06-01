package com.thinksoft.banana.mvp.model;

import android.content.Context;

import com.thinksoft.banana.mvp.base.BaseAppModel;
import com.thinksoft.banana.net.api.ApiRequestTask;

import java.util.HashMap;

import okhttp3.Callback;


/**
 * @author txf
 * @create 2019/1/22 0022
 * @
 */

public class MyModel extends BaseAppModel {
    @Override
    public void request(Context context, int tag, int sign, HashMap<String, Object> maps, Callback l) {
        ApiRequestTask.my.request(tag, sign, maps, l);
    }
}
