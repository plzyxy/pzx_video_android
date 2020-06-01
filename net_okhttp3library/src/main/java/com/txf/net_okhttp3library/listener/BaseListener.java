package com.txf.net_okhttp3library.listener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import okhttp3.Call;
import okhttp3.Callback;


/**
 * @title 请求结果回调
 */
public abstract class BaseListener<T> implements Callback {
    private static final String KEY_TAG = "KEY_TAG";
    private static final String KEY_ERROR = "KEY_ERROR";
    private static final String KEY_BODY = "KEY_BODY";
    private static final String KEY_MESSAGE = "KEY_MESSAGE";
    private static final String KEY_CODE = "KEY_CODE";

    private static final int WHAT_ERROR = -1;
    private static final int WHAT_SUCCESS = 1;
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data;
            switch (msg.what) {
                case WHAT_ERROR:
                    data = msg.getData();
                    int error = data.getInt(KEY_ERROR);
                    onError(data.getInt(KEY_TAG), error, getMesg(error));
                    break;
                case WHAT_SUCCESS:
                    data = msg.getData();
                    onSuccess(data.getInt(KEY_TAG), data.getInt(KEY_CODE), data.getString(KEY_MESSAGE), data.getString(KEY_BODY));
                    break;
            }

        }
    };

    protected int getError(IOException e) {
        Throwable throwable = e.getCause();
        int error;
        if (throwable == null) {
            error = -100;
        } else if (throwable instanceof UnknownHostException) {
            error = -200;
        } else if (throwable instanceof SocketTimeoutException) {
            error = -300;
        } else if (throwable instanceof ConnectException) {
            error = -400;
        } else if (throwable instanceof UnknownServiceException) {
            error = -500;
        } else {
            error = -600;
        }
        return error;
    }

    protected String getMesg(int error) {
        String mesg;
        if (error == -100) {
            mesg = "请求失败";
        } else if (error == -200) {
            mesg = "请检查网络连接";
        } else if (error == -300) {
            mesg = "请求超时";
        } else if (error == -400) {
            mesg = "服务器连接失败，请稍后重试";
        } else if (error == -500) {
            mesg = "未知的服务器错误";
        } else {
            mesg = "服务器错误，请稍后重试";
        }
        return mesg;
    }

    /**
     * 请求失败
     */
    @Override
    public void onFailure(Call call, IOException e) {
        Message msg = new Message();
        msg.what = WHAT_ERROR;
        Bundle data = new Bundle();
        data.putInt(KEY_TAG, (Integer) call.request().tag());
        data.putInt(KEY_ERROR, getError(e));
        msg.setData(data);
        mHandler.sendMessage(msg);
    }

    /**
     * 请求成功
     */
    @Override
    public void onResponse(Call call, okhttp3.Response response) throws IOException {
        Message msg = new Message();
        msg.what = WHAT_SUCCESS;

        Bundle data = new Bundle();
        data.putInt(KEY_TAG, (Integer) call.request().tag());
        data.putInt(KEY_CODE, response.code());
        data.putString(KEY_MESSAGE, response.message());
        data.putString(KEY_BODY, response.body().string());

        msg.setData(data);
        mHandler.sendMessage(msg);
    }

    public abstract void onSuccess(int sign, int code, String message, String body);

    public abstract void onError(int sign, int error, String message);
}
