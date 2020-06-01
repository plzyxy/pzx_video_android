package com.thinksoft.banana.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.mvp.base.BaseAppModel;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.net.listener.HttpJsonListener;
import com.thinksoft.banana.ui.activity.MainActivity;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.interfaces.ExtrListener;
import com.txf.ui_mvplibrary.ui.activity.BaseActivity;

import java.util.HashMap;


/**
 * @author txf
 * @create 2019/1/22 0022
 * @
 */

public class CommonPresenter extends CommonContract.Presenter {

    private Context mContext;
    private BaseAppModel model;
    private ExtrListener mExtrListener;

    public CommonPresenter(Context context, BaseAppModel model) {
        this.mContext = context;
        if (context instanceof ExtrListener) {
            this.mExtrListener = (ExtrListener) context;
        }
        this.model = model;
    }

    public CommonPresenter(Context context, ExtrListener loading, BaseAppModel model) {
        this.mContext = context;
        this.mExtrListener = loading;
        this.model = model;
    }

    public void showLoading(boolean isShowLoading) {
        if (isShowLoading && mExtrListener != null) {
            mExtrListener.showILoading();
        }
    }

    public void hideLoading(boolean isShowLoading) {
        if (isShowLoading && mExtrListener != null) {
            mExtrListener.hideILoading();
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        mExtrListener = null;
    }

    public void logInvalid(String text) {
        if (mExtrListener != null) {

            UserInfoManage.getInstance().cleanUserInfo();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(text);
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //登录过期
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                    AppManager.getInstance().retainAcitivity(LoginActivity.class);
                }
            });
            builder.setCancelable(false);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public void logInvalid2(String text) {
        if (mExtrListener != null) {
            //登录过期
            Intent i = new Intent(mContext, LoginActivity.class);
//            i.putExtra("tag", 1);
            mContext.startActivity(i);
            AppManager.getInstance().retainAcitivity(LoginActivity.class);
        }
    }

    public void httpOnSuccess(int sign, JsonElement data, String message) {
        if (getView() != null) {
            getView().httpOnSuccess(sign, data, message);
        }
    }

    public void httpOnError(int sign, int error, String message) {
        if (getView() != null) {
            getView().httpOnError(sign, error, message);
        }
    }

    @Override
    public void getData(int tag, int sign, HashMap<String, Object> maps, final boolean isShowLoading) {
        showLoading(isShowLoading);
        model.request(mContext, tag, sign, maps, new HttpJsonListener() {
            @Override
            public void onSuccess(int sign, JsonElement data, String message) {
                super.onSuccess(sign, data, message);
                hideLoading(isShowLoading);
                httpOnSuccess(sign, data, message);
            }

            @Override
            public void onError(int sign, int error, String message) {
                super.onError(sign, error, message);
                hideLoading(isShowLoading);
                if (error == -1) {
                    //登录失效
                    logInvalid("登录过期请重新登录");
                } else if (error == -2) {
                    //未登录
                    logInvalid2(message);
                } else {
                    ToastUtils.show(message);
                    httpOnError(sign, error, message);
                }
            }
        });
    }
}
