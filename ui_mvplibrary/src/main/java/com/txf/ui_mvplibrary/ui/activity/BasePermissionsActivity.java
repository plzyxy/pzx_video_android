package com.txf.ui_mvplibrary.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.txf.ui_mvplibrary.utils.PermissionPageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/1/29 0029
 * 继承{@link BaseActivity}
 * <p>
 * 重写方法 {@link #buildPermissions()} 开始权限请求
 * 请求被允许回调 {@link #requestPermissionsResult()}
 * 请求被拒绝则自动重新请求
 */
public abstract class BasePermissionsActivity
        extends BaseActivity {
    //权限请求标识
    public static final int BASE_PERMISSIONS_ACTIVITY_REQUEST_CODE = 500;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initPermissions();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initPermissions();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != BASE_PERMISSIONS_ACTIVITY_REQUEST_CODE)
            return;
        ArrayList<String> Permit = new ArrayList<>();
        ArrayList<PermissionsResultBean> resultBeans = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {//授权
                Permit.add(permissions[i]);
            } else {//未授权
                PermissionsResultBean bean = new PermissionsResultBean();
                bean.setPermissions(permissions[i]);
                bean.setIsShow(!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]));
                resultBeans.add(bean);
            }
        }
        //权限请求结果
        if (resultBeans.size() == 0) {
            requestPermissionsResult();
        } else {
            repeatRequest(resultBeans);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void repeatRequest(ArrayList<PermissionsResultBean> resultBeans) {
        if (isShow(resultBeans)) {
            //弹出窗口跳转到设置权限页面
            new Builder()
                    .setWith(-1)
                    .setContent("请点击确定前往设置-权限管理-允许权限")
                    .setButton2("取消")
                    .setButton3("确定")
                    .show();
        } else {
            initPermissions();
        }
    }

    @Override
    protected void onInteractionDialog(DialogInterface dialog, int which, int with, Bundle ext) {
        super.onInteractionDialog(dialog, which, with, ext);
        if (with != -1)
            return;
        switch (which) {
            case DialogInterface.BUTTON_NEGATIVE:
                finish();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                PermissionPageUtils permissionPageUtils = new PermissionPageUtils(this);
                permissionPageUtils.jumpPermissionPage();
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PermissionPageUtils.RESULT_CODE:
                initPermissions();
                break;
        }
    }

    private void requestPermission(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getNotPermitPermissions(permissions).length > 0) {
            requestPermissions(permissions, BASE_PERMISSIONS_ACTIVITY_REQUEST_CODE);
        } else {
            requestPermissionsResult();
        }
    }

    protected void initPermissions() {
        if (buildPermissions() != null)
            requestPermission(buildPermissions());
    }

    protected String[] buildPermissions() {
        return null;
    }

    protected void requestPermissionsResult() {

    }

    private String[] getNotPermitPermissions(String[] permissions) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (!checkPermission(permissions[i])) {
                data.add(permissions[i]);
            }
        }
        if (data.size() < 0) {
            return new String[0];
        } else {
            return data.toArray(new String[data.size()]);
        }
    }

    private boolean checkPermission(String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private boolean isShow(ArrayList<PermissionsResultBean> resultBeans) {
        boolean isShow = true;
        for (PermissionsResultBean resultBean : resultBeans) {
            if (!resultBean.getIsShow()) {
                return false;
            }
        }
        return isShow;
    }

    private class PermissionsResultBean {
        String permissions;//权限
        boolean isShow;//是否勾选不在提示

        public String getPermissions() {
            return permissions;
        }

        public void setPermissions(String permissions) {
            this.permissions = permissions;
        }

        public boolean getIsShow() {
            return isShow;
        }

        public void setIsShow(boolean isShow) {
            this.isShow = isShow;
        }
    }
}

