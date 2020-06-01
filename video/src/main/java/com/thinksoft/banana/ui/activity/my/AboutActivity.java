package com.thinksoft.banana.ui.activity.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.ui_mvplibrary.ui.activity.BaseActivity;
import com.txf.ui_mvplibrary.ui.activity.BaseWebActivity;

/**
 * @author txf
 * @create 2019/2/20 0020
 * @
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView titleTV = findViewById(R.id.titleTV);
        titleTV.setText(getString(R.string.关于我们));
        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);

        tv1.setText("版本名称: " + getAppVersionName());
        tv2.setText("版本号: " + getAppVersionCode() + "");
    }

    private String getAppVersionName() {
        PackageManager packageManager = getPackageManager();
        try {
            return packageManager.getPackageInfo(getContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    private int getAppVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            return packageManager.getPackageInfo(getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
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
