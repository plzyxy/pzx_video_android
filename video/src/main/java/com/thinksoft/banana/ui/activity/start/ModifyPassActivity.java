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
 * @create 2019/2/15 0015
 * @
 */
public class ModifyPassActivity
        extends BaseMvpActivity<CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View {
    InputView phoneInputView, passInputView, codeInputView;
    TextView titleText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modifypass;
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

    private void initData() {
        titleText.setText(R.string.忘记密码);
    }

    private void initListener() {
        setOnClick(R.id.backButton, R.id.confirmButton);
    }

    private void initView() {
        titleText = findViewById(R.id.titleText);
        phoneInputView = findViewById(R.id.phoneInputView);
        codeInputView = findViewById(R.id.codeInputView);
        passInputView = findViewById(R.id.passInputView);

        phoneInputView.setInputType(Constant.TYPE_PHONE);
        codeInputView.setInputType(Constant.TYPE_CODE);
        passInputView.setInputType(Constant.TYPE_PASSWORD);
    }

    @Override
    public void httpOnSuccess(int tag, JsonElement data, String message) {
        switch (tag) {
            case ApiRequestTask.start.TAG_SEND_CODE_NEW_PASSWORD:
                ToastUtils.show(getString(R.string.发送成功));
                break;
            case ApiRequestTask.start.TAG_NEW_PASSWORD:
                ToastUtils.show(getString(R.string.修改成功));
                finish();
                break;
        }
    }

    @Override
    public void httpOnError(int tag, int error, String message) {
        switch (tag) {
            case ApiRequestTask.start.TAG_SEND_CODE_NEW_PASSWORD:
                codeInputView.stopCode();
                break;

        }
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
                getPresenter().getData(ApiRequestTask.start.TAG_SEND_CODE_NEW_PASSWORD, mags);
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
                getPresenter().getData(ApiRequestTask.start.TAG_NEW_PASSWORD, mags);
                break;
        }
    }

}
