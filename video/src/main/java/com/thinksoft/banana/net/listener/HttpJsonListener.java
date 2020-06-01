package com.thinksoft.banana.net.listener;

import com.google.gson.JsonElement;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.UserBean;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.net.response.AppBaseResponse;
import com.txf.net_okhttp3library.listener.BaseListener;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.tools.StringTools;


/**
 * @title 请求结果回调
 */
public class HttpJsonListener extends BaseListener<JsonElement> {
    @Override
    public void onSuccess(int sign, int code, String message, String body) {
        String strDecode = body;
        AppBaseResponse result = JsonTools.fromJson(strDecode, AppBaseResponse.class);
        if (result == null) {
            onError(sign, -7, "服务器数据异常");
            return;
        }
        if (result.getCode() == 1) {
            if (sign == ApiRequestTask.start.TAG_LOGIN) {
                //登录成功 保存token
                UserBean bean = new UserBean();
                bean.setToken(result.getToken());
                UserInfoManage.getInstance().setUserInfo(bean);
            }
            onSuccess(sign, result.getData(), result.getMsg());
        } else if (result.getCode() == -1 && StringTools.isNull(result.getToken())) {
            onError(sign, -2, "请先登录");
        } else {
            onError(sign, result.getCode(), result.getMsg());
        }
    }

    public void onSuccess(int sign, JsonElement data, String message) {

    }

    @Override
    public void onError(int sign, int error, String message) {

    }

}
