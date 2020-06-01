package com.thinksoft.banana.ui.activity.my;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.UserInfoBean;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.MyModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;

import java.util.HashMap;

/**
 * @author txf
 * @create 2019/2/21 0021
 * @
 */
public class ChangeNameActivity
        extends BaseMvpActivity<CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View {
    TextView titleTV, rightTV;
    EditText editText;
    View backButton;

    UserInfoBean mUserInfoBean;

    public static Intent getIntent(Context context, UserInfoBean bean) {
        Intent i = new Intent(context, ChangeNameActivity.class);
        i.putExtra("data", bean);
        return i;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_changename;
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
        titleTV.setText(getString(R.string.修改昵称));
        rightTV.setText(getString(R.string.保存));
        rightTV.setTextColor(0xff12BC1A);
    }

    private void initListener() {
        setOnClick(R.id.backButton, R.id.rightTV);
    }

    private void initView() {
        backButton = findViewById(R.id.backButton);
        titleTV = findViewById(R.id.titleTV);
        rightTV = findViewById(R.id.rightTV);
        editText = findViewById(R.id.editText);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                finish();
                break;
            case R.id.rightTV://保存昵称
                if (StringTools.isNull(editText.getText().toString()) ||
                        mUserInfoBean.getNickname().equals(editText.getText().toString())) {
                    ToastUtils.show(getString(R.string.请输入新的昵称));
                    return;
                }
                HashMap<String, Object> maps = new HashMap<>();
                maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
                maps.put("nickname", editText.getText().toString());
                getPresenter().getData(ApiRequestTask.my.TAG_MODIFY_USER_INFO, maps);
                break;
        }
    }

    private void notifyResult() {
        Intent i = new Intent();
        i.putExtra("data", mUserInfoBean);
        setResult(Activity.RESULT_OK, i);
    }
    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.my.TAG_MODIFY_USER_INFO:
                mUserInfoBean.setNickname(editText.getText().toString());
                notifyResult();
                ToastUtils.show(getString(R.string.修改成功));
                finish();
                break;
        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {

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
