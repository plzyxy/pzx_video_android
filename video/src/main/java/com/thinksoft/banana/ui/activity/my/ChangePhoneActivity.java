package com.thinksoft.banana.ui.activity.my;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.UserInfoBean;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.MyModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.thinksoft.banana.ui.view.InputView;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;

import java.util.HashMap;

import static com.thinksoft.banana.app.Constant.TYPE_CODE;
import static com.thinksoft.banana.app.Constant.TYPE_PHONE;

/**
 * @author txf
 * @create 2019/2/21 0021
 * @修改手机号
 */
public class ChangePhoneActivity
        extends BaseMvpActivity<CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View {
    View backButton;
    TextView titleTV, phoneTextView;
    InputView codeInputView, phoneInputView;

    UserInfoBean mUserInfoBean;

    public static Intent getIntent(Context context, UserInfoBean bean) {
        Intent i = new Intent(context, ChangePhoneActivity.class);
        i.putExtra("data", bean);
        return i;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_changephone;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(this, new MyModel()));
        mUserInfoBean = (UserInfoBean) getIntent().getSerializableExtra("data");
        initView();
        initListener();
        initData();
    }

    private void initData() {
        titleTV.setText(getString(R.string.修改手机号));
        phoneTextView.setText(mUserInfoBean.getMobile());
        codeInputView.setInputType(TYPE_CODE);
        codeInputView.getLineView().setVisibility(View.GONE);
        phoneInputView.setInputType(TYPE_PHONE);
        phoneInputView.getLineView().setVisibility(View.GONE);
        phoneInputView.getIconView().setVisibility(View.GONE);
        phoneInputView.getEditText().setHint(getString(R.string.输入新的手机号码));
    }

    private void initListener() {
        setOnClick(R.id.backButton, R.id.confirmButton);
    }

    private void initView() {
        backButton = findViewById(R.id.backButton);
        titleTV = findViewById(R.id.titleTV);
        phoneTextView = findViewById(R.id.phoneTextView);

        codeInputView = findViewById(R.id.codeInputView);
        phoneInputView = findViewById(R.id.phoneInputView);
    }

    @Override
    public void onInteractionView(int action, Bundle bundle) {
        super.onInteractionView(action, bundle);
        switch (action) {
            case Constant.ACTION_GET_CODE:
                if (StringTools.isNull(phoneTextView.getText().toString())) {
                    ToastUtils.show(getString(R.string.请输入手机号码));
                    return;
                }
                if (!phoneInputView.isMobile(phoneTextView.getText().toString())) {
                    ToastUtils.show(getString(R.string.手机号码格式不对));
                    return;
                }
                codeInputView.startCode();
                HashMap<String, Object> mags = new HashMap<>();
                mags.put("mobile", phoneTextView.getText().toString());
                getPresenter().getData(ApiRequestTask.my.TAG_SEND_CODE_NEW_MOBILE, mags);
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
                //确认
                if (StringTools.isNull(codeInputView.getEditText().getText().toString())) {
                    ToastUtils.show(getString(R.string.请输入验证码));
                    return;
                }
                if (StringTools.isNull(phoneInputView.getEditText().getText().toString())) {
                    ToastUtils.show(getString(R.string.请输入新的手机号码));
                    return;
                }
                if (phoneInputView.isMobile(phoneInputView.getEditText().getText().toString())) {
                    ToastUtils.show(getString(R.string.手机号码格式不对));
                    return;
                }
                HashMap<String, Object> mags = new HashMap<>();
                mags.put("mobile", phoneInputView.getInputString());
                mags.put("code", codeInputView.getInputString());
//                mags.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
                getPresenter().getData(ApiRequestTask.my.TAG_MODIFY_MOBILE, mags);
                break;
        }
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.my.TAG_SEND_CODE_NEW_MOBILE:
                ToastUtils.show(getString(R.string.发送成功));
                break;
            case ApiRequestTask.my.TAG_MODIFY_MOBILE:
                //修改成功


                break;
        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {
        switch (sign) {
            case ApiRequestTask.my.TAG_SEND_CODE_NEW_MOBILE:
                codeInputView.stopCode();
                break;
        }
    }
    @Override
    public void logInvalid() {
        super.logInvalid();
        UserInfoManage.getInstance().cleanUserInfo();
        new Builder()
                .setWith(1024)
                .setButton3(getString(R.string.确认))
                .setContent(getString(R.string.登录过期请重新登录))
                .setCancelable(false)
                .setDialogListener(new DialogListener(null, 0) {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            //登录过期
                            startActivity(new Intent(getContext(), LoginActivity.class));
                            AppManager.getInstance().retainAcitivity(LoginActivity.class);
                        }
                    }
                })
                .show();
    }
}
