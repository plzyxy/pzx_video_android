package com.thinksoft.banana.ui.activity.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.CommonStartModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.MainActivity;
import com.thinksoft.banana.ui.view.InputView;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;

import java.util.HashMap;

/**
 * @author txf
 * @create 2019/2/15 0015
 * @登录
 */
public class LoginActivity
        extends BaseMvpActivity<CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View {
    InputView phoneInputView, passInputView;
    TextView confirmIconView;
    int tag;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getIntent().getIntExtra("tag", -1);
        statusBarTransparent();
        setContract(this, new CommonPresenter(this, new CommonStartModel()));
        initView();
        initListener();
    }

    private void initListener() {
        setOnClick(R.id.registerButton, R.id.modifyPassButton, R.id.loginButton, R.id.confirmButton, R.id.xyButton);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.loginButton:
                if (StringTools.isNull(phoneInputView.getInputString())) {
                    ToastUtils.show(getString(R.string.请输入手机号码));
                    return;
                }
                if (!phoneInputView.isMobile(phoneInputView.getInputString())) {
                    ToastUtils.show(getString(R.string.手机号码格式不对));
                    return;
                }
                if (StringTools.isNull(passInputView.getInputString())) {
                    ToastUtils.show(getString(R.string.请输入密码));
                    return;
                }
                if (!confirmIconView.isSelected()) {
                    ToastUtils.show(getString(R.string.请阅读并同意用户协议));
                    return;
                }
                HashMap<String, Object> mags = new HashMap<>();
                mags.put("mobile", phoneInputView.getInputString());
                mags.put("password", passInputView.getInputString());
                getPresenter().getData(ApiRequestTask.start.TAG_LOGIN, mags);
                break;
            case R.id.registerButton:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.modifyPassButton:
                startActivity(new Intent(this, ModifyPassActivity.class));
                break;
            case R.id.confirmButton://同意用户协议
                if (confirmIconView.isSelected()) {
                    confirmIconView.setSelected(false);
                    confirmIconView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.icon_sel_flase), null, null, null);
                } else {
                    confirmIconView.setSelected(true);
                    confirmIconView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.icon_sel_true), null, null, null);
                }
                break;
            case R.id.xyButton://跳转到协议
                startActivity(new Intent(this, AgreementActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (tag == 1) {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("tag", tag);
            startActivity(i);
            finish();
        }
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void initView() {
        phoneInputView = findViewById(R.id.phoneInputView);
        passInputView = findViewById(R.id.passInputView);
        phoneInputView.setInputType(Constant.TYPE_PHONE);
        passInputView.setInputType(Constant.TYPE_PASSWORD);
        confirmIconView = findViewById(R.id.confirmIconView);
    }

    @Override
    public void httpOnSuccess(int tag, JsonElement data, String message) {
        switch (tag) {
            case ApiRequestTask.start.TAG_LOGIN:
                startMainActivity();
                break;
        }
    }

    @Override
    public void httpOnError(int tag, int error, String message) {
        switch (tag) {
            case ApiRequestTask.start.TAG_LOGIN:

                break;
        }
    }
}
