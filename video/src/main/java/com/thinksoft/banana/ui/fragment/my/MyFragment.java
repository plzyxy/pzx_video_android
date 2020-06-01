package com.thinksoft.banana.ui.fragment.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonElement;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.UserInfoBean;
import com.thinksoft.banana.entity.event_bean.MyEventBean;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.MyModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.PayWebActivity;
import com.thinksoft.banana.ui.activity.my.AboutActivity;
import com.thinksoft.banana.ui.activity.my.CollectActivity;
import com.thinksoft.banana.ui.activity.my.HistoryActivity;
import com.thinksoft.banana.ui.activity.my.PaymentActivity;
import com.thinksoft.banana.ui.activity.my.PersonDataActivity;
import com.thinksoft.banana.ui.activity.my.SettingActivity;
import com.thinksoft.banana.ui.activity.my.SpreadActivity;
import com.thinksoft.banana.ui.activity.my.SpreadLinkActivity;
import com.thinksoft.banana.ui.activity.my.WalletActivity;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.txf.other_tencentlibrary.ShareHelper;
import com.txf.other_tencentlibrary.wx.ShareContentQQ;
import com.txf.other_tencentlibrary.wx.ShareContentWebpage;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.fragment.BaseMvpFragment;
import com.txf.ui_mvplibrary.ui.listener.OnMultiPurposeAdapter;
import com.txf.ui_mvplibrary.ui.view.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

/**
 * @author txf
 * @create 2019/2/16
 * 我的页面
 */
public class MyFragment
        extends BaseMvpFragment<CommonContract.View, CommonContract.Presenter>
        implements OnAppListener.OnWindowListener, CommonContract.View {
    RefreshLayout mRefreshLayout;

    SimpleDraweeView mSimpleDraweeView, img_level_1;
    TextView nameTV, csTextView, qqTextView, tv_level_1, vipTV1, vipTV2;


    UserInfoBean mUserInfoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(getContext(), this, new MyModel()));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initListener();
        refreshData(true);
    }

    private void refreshData(boolean isLoading) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
        getPresenter().getData(ApiRequestTask.my.TAG_USER_INFO, maps, isLoading);
    }

    private void setUserInfo(UserInfoBean bean) {
        mRefreshLayout.finishRefresh();
        if (bean == null)
            return;
        mUserInfoBean = bean;
        //头像
        if (mUserInfoBean.getImage() == null) {
            mUserInfoBean.setImage("");
        }
        mSimpleDraweeView.setImageURI(mUserInfoBean.getImage());
        //昵称
        if (mUserInfoBean.getNickname() == null) {
            mUserInfoBean.setNickname("");
        }
        nameTV.setText(mUserInfoBean.getNickname());
        //余额
        if (mUserInfoBean.getDiamond() == null) {
            mUserInfoBean.setDiamond("0.0");
        }
        //剩余观看次数
        csTextView.setText(String.valueOf(bean.getFree_count()) + "/" + String.valueOf(bean.getAll_free_count()));
        //用户等级
        img_level_1.setImageURI(bean.getLevel_image());
        tv_level_1.setText(bean.getLevel_name());
        //客服qq
        if (StringTools.isNull(mUserInfoBean.getKefu_qq())) {
            mUserInfoBean.setKefu_qq("");
        }
        qqTextView.setText(mUserInfoBean.getKefu_qq());
        //vip
        if (mUserInfoBean.getIsVip() == 1) {
            vipTV1.setText(getString(R.string.vip会员));
            vipTV2.setText("剩余天数: " + mUserInfoBean.getDay() + "天");
        } else {
            vipTV1.setText(getString(R.string.你还不是会员));
            vipTV2.setText(getString(R.string.去购买));
        }
    }

    private void initListener() {
        setOnClick(
                R.id.vipButton,
                R.id.outButton,
                R.id.czButton,
                R.id.tgButton,
                R.id.tgButton1,
                R.id.zlButton,
                R.id.qbButton,
                R.id.scButton,
                R.id.lsButton,
                R.id.wmButton,
                R.id.szButton);
        mRefreshLayout.setOnMultiPurposeListener(new OnMultiPurposeAdapter() {
            @Override
            public void onRefresh(@NonNull com.scwang.smartrefresh.layout.api.RefreshLayout refreshLayout) {
                refreshData(false);
            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight);
                getListener().onInteractionFragment(Constant.ACTION_MY_REFRESH_ING, offset, null);
            }
        });
    }

    private void initViews() {
        mRefreshLayout = findViewById(R.id.RefreshLayout);
        mSimpleDraweeView = findViewById(R.id.SimpleDraweeView);
        nameTV = findViewById(R.id.nameTV);
        csTextView = findViewById(R.id.csTextView);

        img_level_1 = findViewById(R.id.img_level_1);
        tv_level_1 = findViewById(R.id.tv_level_1);

        vipTV1 = findViewById(R.id.vipTV1);
        vipTV2 = findViewById(R.id.vipTV2);

        qqTextView = findViewById(R.id.qqTextView);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.getClassicsHeader().setAccentColor(0xffffffff);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.vipButton:
                if (checkUserInfo())
                    startActivity(new Intent(getContext(), PayWebActivity.class));
                break;
            case R.id.outButton://退出
                new Builder()
                        .setWith(0)
                        .setButton2(getString(R.string.取消))
                        .setButton3(getString(R.string.确认))
                        .setContent(getString(R.string.确认退出此账号))
                        .show();
                break;
            case R.id.czButton://去充值
                if (checkUserInfo())
                    startActivity(new Intent(getContext(), PaymentActivity.class));
                break;
            case R.id.tgButton://推广
                if (checkUserInfo())
                    startActivity(new Intent(getContext(), SpreadLinkActivity.class));
                break;
            case R.id.tgButton1:
                //推广页面
                if (checkUserInfo())
                    startActivity(new Intent(getContext(), SpreadActivity.class));
                break;
            case R.id.zlButton://个人资料
                if (checkUserInfo())
                    startActivity(PersonDataActivity.getIntent(getContext(), mUserInfoBean));
                break;
            case R.id.qbButton://我的钱包
                if (checkUserInfo())
                    startActivity(WalletActivity.getIntent(getContext(), mUserInfoBean));
                break;
            case R.id.scButton://我的收藏
                if (checkUserInfo())
//                    startActivity(new Intent(getContext(), CollectActivity2.class));//1.0 2.0版本跳转
                    startActivity(new Intent(getContext(), CollectActivity.class));//3.0跳转
                break;
            case R.id.lsButton://观看历史
                if (checkUserInfo())
                    startActivity(new Intent(getContext(), HistoryActivity.class));
                break;
            case R.id.wmButton://关于我们
                startActivity(new Intent(getContext(), AboutActivity.class));
                break;
            case R.id.szButton://设置
                if (checkUserInfo())
                    startActivity(SettingActivity.getIntent(getContext(), mUserInfoBean));
                break;
        }
    }

    private boolean checkUserInfo() {
        if (mUserInfoBean == null) {
            ToastUtils.show(getString(R.string.用户信息为空请刷新页面后重试));
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onInteractionDialog(DialogInterface dialog, int which, int with, Bundle ext) {
        super.onInteractionDialog(dialog, which, with, ext);
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                if (with == 0) {
                    //确认退出登录
                    HashMap<String, Object> mags = new HashMap<>();
                    mags.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
                    getPresenter().getData(ApiRequestTask.my.TAG_LOGIN_OUT, mags);
                }
                break;
        }
    }

    @Override
    public void onInteractionWindow(int action, int tag, Bundle ext) {
        switch (tag) {
            case 0:
                startShare(action);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ShareHelper.getInstance().getTencent() != null)
            Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
    }

    Bundle bundle;

    private void startShare(int action) {
        if (bundle == null)
            bundle = new Bundle();
        bundle.clear();
        switch (action) {
            case Constant.ACTION_SHARE_QQ:
                ShareHelper.getInstance().shareQQ(getActivity(), new ShareContentQQ(
                        "我在测试",
                        "测试",
                        "http://connect.qq.com/",
                        "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg"
                ), qqShareListener);
                break;
            case Constant.ACTION_SHARE_QQ_PY:
                ShareHelper.getInstance().shareQzone(getActivity(), new ShareContentQQ(
                        "我在测试",
                        "测试",
                        "http://connect.qq.com/",
                        "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg"
                ), qqShareListener);
                break;
            case Constant.ACTION_SHARE_WX:
                ShareHelper.getInstance().shareWX(
                        new ShareContentWebpage(
                                "我在测试",
                                "测试",
                                "http://connect.qq.com/",
                                R.mipmap.ic_launcher));
                break;
            case Constant.ACTION_SHARE_WX_PX:
                ShareHelper.getInstance().shareWXFrends(new ShareContentWebpage(
                        "我在测试",
                        "测试",
                        "http://connect.qq.com/",
                        R.mipmap.ic_launcher));
                break;
        }
    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            Logger.i("分享取消");
        }

        @Override
        public void onComplete(Object response) {
            Logger.i("分享成功");
        }

        @Override
        public void onError(UiError e) {
            Logger.i("分享失败: " + JsonTools.toJSON(e));
        }
    };

    @Override
    public void httpOnSuccess(int tag, JsonElement data, String message) {
        switch (tag) {
            case ApiRequestTask.my.TAG_USER_INFO:
                UserInfoBean bean = JsonTools.fromJson(data, UserInfoBean.class);
                setUserInfo(bean);
                break;
            case ApiRequestTask.my.TAG_LOGIN_OUT:
                UserInfoManage.getInstance().cleanUserInfo();
                startActivity(new Intent(getContext(), LoginActivity.class));
                AppManager.getInstance().retainAcitivity(LoginActivity.class);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEnentBusMessage(MyEventBean bean) {
        switch (bean.getType()) {
            case Constant.TYPE_CHANGE_USER_INFO:
                refreshData(false);
                break;
        }
    }

    @Override
    public void httpOnError(int tag, int error, String message) {
        switch (tag) {
            case ApiRequestTask.my.TAG_USER_INFO:
                setUserInfo(null);
                break;
        }
    }

    @Override
    protected void showLoading() {
        //不在刷新过程中 调用生效
        if (mRefreshLayout.getState() == RefreshState.None)
            super.showLoading();
    }

    @Override
    public void showILoading() {
        showLoading();
    }

    @Override
    public void logInvalid() {
        super.logInvalid();
        mRefreshLayout.finishRefresh();
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
