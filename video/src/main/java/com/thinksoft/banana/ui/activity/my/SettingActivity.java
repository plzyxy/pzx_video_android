package com.thinksoft.banana.ui.activity.my;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.UserInfoBean;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;

/**
 * @author txf
 * @create 2019/2/20 0020
 * @设置
 */
public class SettingActivity extends BaseMvpActivity {
    TextView titleTV, phoneTextView;

    UserInfoBean mUserInfoBean;

    public static Intent getIntent(Context context, UserInfoBean bean) {
        Intent i = new Intent(context, SettingActivity.class);
        i.putExtra("data", bean);
        return i;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserInfoBean = (UserInfoBean) getIntent().getSerializableExtra("data");
        init();
    }

    private void init() {
        titleTV = findViewById(R.id.titleTV);
        phoneTextView = findViewById(R.id.phoneTextView);
        setOnClick(R.id.button, R.id.backButton);

        titleTV.setText(getString(R.string.设置));
        phoneTextView.setText(mUserInfoBean.getMobile());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                finish();
                break;
            case R.id.button:
                startActivityForResult(ChangePhoneActivity.getIntent(this, mUserInfoBean), 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case 1:
                mUserInfoBean = (UserInfoBean) data.getSerializableExtra("data");
                phoneTextView.setText(mUserInfoBean.getMobile());
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
