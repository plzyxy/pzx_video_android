package com.thinksoft.banana.ui.activity.start;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.CommonStartModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.view.InputView;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;

import java.util.HashMap;

/**
 * @author txf
 * @create 2019/2/16
 * 注册
 */
public class RegisterActivity
        extends BaseMvpActivity<CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View {
    InputView phoneInputView, passInputView, codeInputView, inviteInputView;
    TextView titleText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarTransparent();
        setContract(this, new CommonPresenter(this, new CommonStartModel()));
        initView();
        initListener();
        initData();
    }

    @Override
    public void httpOnSuccess(int tag, JsonElement data, String message) {
        switch (tag) {
            case ApiRequestTask.start.TAG_SEND_CODE_REGISTER:
                ToastUtils.show(getString(R.string.发送成功));
                break;
            case ApiRequestTask.start.TAG_REGISTER:
                ToastUtils.show(getString(R.string.注册成功));
                finish();
                break;
        }
    }

    @Override
    public void httpOnError(int tag, int error, String message) {
        switch (tag) {
            case ApiRequestTask.start.TAG_SEND_CODE_REGISTER:
                codeInputView.stopCode();
                break;

        }
    }

    private void initData() {
        titleText.setText(R.string.注册);
    }

    private void initListener() {
        setOnClick(R.id.backButton, R.id.confirmButton);
    }

    private void initView() {
        titleText = findViewById(R.id.titleText);
        phoneInputView = findViewById(R.id.phoneInputView);
        codeInputView = findViewById(R.id.codeInputView);
        passInputView = findViewById(R.id.passInputView);
        inviteInputView = findViewById(R.id.inviteInputView);

        phoneInputView.setInputType(Constant.TYPE_PHONE);
        codeInputView.setInputType(Constant.TYPE_CODE);
        passInputView.setInputType(Constant.TYPE_PASSWORD);
        inviteInputView.setInputType(Constant.TYPE_INVITE);
    }

    @Override
    public void onInteractionView(int action, Bundle bundle) {
        super.onInteractionView(action, bundle);
        switch (action) {
            case Constant.ACTION_GET_CODE:
                if (StringTools.isNull(phoneInputView.getInputString())) {
                    ToastUtils.show(getString(R.string.请输入手机号码));
                    return;
                }
                if (!phoneInputView.isMobile(phoneInputView.getInputString())) {
                    ToastUtils.show(getString(R.string.手机号码格式不对));
                    return;
                }
                codeInputView.startCode();
                HashMap<String, Object> mags = new HashMap<>();
                mags.put("mobile", phoneInputView.getInputString());
                getPresenter().getData(ApiRequestTask.start.TAG_SEND_CODE_REGISTER, mags);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                finish();
                break;
            case R.id.confirmButton:
                if (StringTools.isNull(phoneInputView.getInputString())) {
                    ToastUtils.show(getString(R.string.请输入手机号码));
                    return;
                }
                if (!phoneInputView.isMobile(phoneInputView.getInputString())) {
                    ToastUtils.show(getString(R.string.手机号码格式不对));
                    return;
                }
                if (StringTools.isNull(codeInputView.getInputString())) {
                    ToastUtils.show(getString(R.string.请输入验证码));
                    return;
                }
                if (StringTools.isNull(passInputView.getInputString())) {
                    ToastUtils.show(getString(R.string.请输入密码));
                    return;
                }
                HashMap<String, Object> mags = new HashMap<>();
                mags.put("mobile", phoneInputView.getInputString());
                mags.put("code", codeInputView.getInputString());
                mags.put("password", passInputView.getInputString());
                if (!StringTools.isNull(inviteInputView.getInputString())) {
                    mags.put("invite_code", inviteInputView.getInputString());
                }
                getPresenter().getData(ApiRequestTask.start.TAG_REGISTER, mags);
                break;
        }
    }


}
