package com.thinksoft.banana.ui.activity.my;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.donkingliang.imageselector.OnImgSelectorListener;
import com.donkingliang.imageselector.utils.ImageSelectorManage;
import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.HttpImgBean;
import com.thinksoft.banana.entity.bean.StringBean;
import com.thinksoft.banana.entity.bean.UserInfoBean;
import com.thinksoft.banana.entity.event_bean.MyEventBean;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.MyModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.thinksoft.banana.ui.view.window.BottomListWindow;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/19 0019
 * @个人资料
 */
public class PersonDataActivity
        extends BaseMvpActivity<CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnWindowListener, OnImgSelectorListener {
    TextView titleTV, valueTextView1, valueTextView2;
    BottomListWindow mCameraWindow, mSexWindow;
    ImageSelectorManage mImageSelectorManage;
    UserInfoBean mUserInfoBean;

    public static Intent getIntent(Context context, UserInfoBean bean) {
        Intent i = new Intent(context, PersonDataActivity.class);
        i.putExtra("data", bean);
        return i;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_persondata;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(this, new MyModel()));
        mUserInfoBean = (UserInfoBean) getIntent().getSerializableExtra("data");
        initView();
        initData();
    }


    private void initView() {
        titleTV = findViewById(R.id.titleTV);
        valueTextView1 = findViewById(R.id.valueTextView1);
        valueTextView2 = findViewById(R.id.valueTextView2);
        setOnClick(R.id.backButton, R.id.button1, R.id.button2, R.id.button3);
    }

    private void initData() {
        mImageSelectorManage = new ImageSelectorManage(this, this);
        titleTV.setText(getString(R.string.我的资料));

        setName(mUserInfoBean.getNickname());
        setSex(mUserInfoBean.getSex());
    }

    private void setName(String name) {
        valueTextView1.setText(name);
    }

    private void setSex(int sex) {
        valueTextView2.setText(sex == 2 ? getString(R.string.女) : getString(R.string.男));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                finish();
                break;
            case R.id.button1://更换头像
                if (mCameraWindow == null) {
                    mCameraWindow = new BottomListWindow(this);
                    mCameraWindow.setOnBackListener(this, 0);
                    List<StringBean> datas = new ArrayList<>();
                    datas.add(new StringBean(0, getString(R.string.拍照)));
                    datas.add(new StringBean(1, getString(R.string.从相册中选择)));
                    mCameraWindow.setData(datas);
                }
                mCameraWindow.showPopupWindow();
                break;
            case R.id.button2://修改昵称
                startActivityForResult(ChangeNameActivity.getIntent(this, mUserInfoBean), 0);
                break;
            case R.id.button3://修改性别
                if (mSexWindow == null) {
                    mSexWindow = new BottomListWindow(this);
                    mSexWindow.setOnBackListener(this, 1);
                    List<StringBean> datas = new ArrayList<>();
                    datas.add(new StringBean(1, getString(R.string.男)));
                    datas.add(new StringBean(2, getString(R.string.女)));
                    mSexWindow.setData(datas);
                }
                mSexWindow.showPopupWindow();
                break;
        }
    }

    @Override
    public void onInteractionWindow(int action, int tag, Bundle ext) {
        StringBean bean;
        switch (tag) {
            case 0://修改头像
                bean = BundleUtils.getSerializable(ext);
                if (bean.getId() == 0) {
                    mImageSelectorManage.openPhoto(); //打开相机
                } else if (bean.getId() == 1) {
                    mImageSelectorManage.openPhotoAlbum(); //打开相册
                }
                break;
            case 1://修改性别
                bean = BundleUtils.getSerializable(ext);
                if (bean.getId() == mUserInfoBean.getSex()) {
                    //防止无修改
                    return;
                }
                HashMap<String, Object> maps = new HashMap<>();
                maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
                maps.put("sex", bean.getId());
                getPresenter().getData(ApiRequestTask.my.TAG_MODIFY_USER_INFO, 0, maps);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageSelectorManage.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 0:
                    mUserInfoBean = (UserInfoBean) data.getSerializableExtra("data");
                    setName(mUserInfoBean.getNickname());
                    notifyResult();
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mImageSelectorManage.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //ImageSelectorManage 回执 ↓
    @Override
    public void onCompressStart() {
        showLoading();
    }

    @Override
    public void onCompressCompleted(List<String> successPaths, List<String> exceptionPaths) {
        hideLoading();
        if (successPaths.size() == 0) {
            ToastUtils.show("图片选取失败");
            return;
        }
        File file = new File(successPaths.get(0));
        //上传图片
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
        maps.put("file", file);
        getPresenter().getData(ApiRequestTask.my.TAG_ADD_IMG, 1, maps);
    }

    //ImageSelectorManage 回执  ↑
    @Override
    protected void onStop() {
        super.onStop();
        if (mImageSelectorManage != null)
            mImageSelectorManage.onStop();
    }

    private void notifyResult() {
        MyEventBean bean1 = new MyEventBean();
        bean1.setType(Constant.TYPE_CHANGE_USER_INFO);
        EventBus.getDefault().post(bean1);
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case 0://修改性别
                mUserInfoBean.setSex(mUserInfoBean.getSex() == 2 ? 1 : 2);
                setSex(mUserInfoBean.getSex());
                notifyResult();
                break;
            case 1://上传图片
                HttpImgBean bean = JsonTools.fromJson(data, HttpImgBean.class);
                mUserInfoBean.setImage(bean.getPic());
                //修改头像
                HashMap<String, Object> maps = new HashMap<>();
                maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
                maps.put("image", bean.getPath());
                getPresenter().getData(ApiRequestTask.my.TAG_MODIFY_USER_INFO, 2, maps);
                break;
            case 2://修改头像
                notifyResult();
                ToastUtils.show(getString(R.string.修改成功));
                finish();
                break;
        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {
        switch (sign) {
            case 0://修改性别

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
