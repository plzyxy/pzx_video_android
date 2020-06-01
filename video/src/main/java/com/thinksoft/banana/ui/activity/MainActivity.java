package com.thinksoft.banana.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.HttpNoticeBean;
import com.thinksoft.banana.entity.bean.home.BannersBean;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.CommonStartModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.fragment.circle.CircleFragment;
import com.thinksoft.banana.ui.fragment.home.HomeGroupFragment;
import com.thinksoft.banana.ui.fragment.my.MyFragment;
import com.thinksoft.banana.ui.fragment.type.TypeFragment;
import com.thinksoft.banana.ui.fragment.video.VideoGroupFragment;
import com.thinksoft.banana.ui.view.navigation.HomeNavigation;
import com.thinksoft.banana.ui.view.window.NoticeWindow;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;
import com.txf.ui_mvplibrary.utils.BundleUtils;

public class MainActivity
        extends BaseMvpActivity<CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnFragmentListener {
    HomeNavigation mHomeNavigation;
    ImageView bgImgeView;
    FrameLayout mFrameLayout;
    int mImgeViewHeight;

    MyFragment mMyFragment;
    TypeFragment mTypeFragment;
    CircleFragment mCircleFragment;
    HomeGroupFragment mHomeGroupFragment;
    VideoGroupFragment mVideoGroupFragment;
    int mAction;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int tag = getIntent().getIntExtra("tag", -1);
        statusBarTransparent();
        
        setContract(this, new CommonPresenter(getContext(), new CommonStartModel()));
        if (tag == -1)
            getNotice();
        initView();
        setDeftPage();
    }

    private void initView() {
        bgImgeView = findViewById(R.id.bgImgeView);
        mFrameLayout = findViewById(R.id.FrameLayout);
        mHomeNavigation = findViewById(R.id.HomeNavigation);
    }

    private void setDeftPage() {
        //默认选中首页
        mHomeNavigation.setDefSel(R.id.homeButton);
    }

    private void getNotice() {
        //获取公告
        getPresenter().getData(ApiRequestTask.start.TAG_GET_NOTICE, false);
    }

    @Override
    public void onInteractionView(int action, Bundle bundle) {
        super.onInteractionView(action, bundle);
        if (action == Constant.ACTION_BANNER) {
            //Banner点击处理
            BannersBean bean = BundleUtils.getSerializable(bundle);
            startClient(bean.getLink());
            return;
        }
        if (mAction == Constant.ACTION_HOME_NAVIGATION_TAB3)
            mVideoGroupFragment.onInteractionView(action, bundle);
        switch (action) {
            case Constant.ACTION_HOME_NAVIGATION_TAB1:
                //首页
                mAction = action;
                resetUI();
                if (mVideoGroupFragment != null)
                    mVideoGroupFragment.hiedeView();
                if (mHomeGroupFragment == null) {
                    mHomeGroupFragment = new HomeGroupFragment();
                }
                showFragment(R.id.FrameLayout, mHomeGroupFragment, Constant.TAG_HOME);
                break;
            case Constant.ACTION_HOME_NAVIGATION_TAB2:
                //我的
                mAction = action;
                resetUI();
                if (mVideoGroupFragment != null)
                    mVideoGroupFragment.hiedeView();
                if (mMyFragment == null) {
                    mMyFragment = new MyFragment();
                }
                bgImgeView.setBackground(ContextCompat.getDrawable(this, R.drawable.img_my_bg));
                showFragment(R.id.FrameLayout, mMyFragment, Constant.TAG_MY);
                break;
            case Constant.ACTION_HOME_NAVIGATION_TAB3:
                //刷一刷
                mAction = action;
                resetUI();
                if (mVideoGroupFragment == null) {
                    mVideoGroupFragment = new VideoGroupFragment();
                }
                mHomeNavigation.setBackgroundColor(0x00ffffff);
                mHomeNavigation.hideLineView();
                if (showFragment(R.id.FrameLayout, mVideoGroupFragment, Constant.TAG_VIDEO)) {
                    mVideoGroupFragment.initData();
                }
                break;
            case Constant.ACTION_HOME_NAVIGATION_TAB4:
                //分类
                mAction = action;
                resetUI();
                if (mVideoGroupFragment != null)
                    mVideoGroupFragment.hiedeView();
                if (mTypeFragment == null) {
                    mTypeFragment = new TypeFragment();
                }
                showFragment(R.id.FrameLayout, mTypeFragment, Constant.TAG_TYPE);
                break;
            case Constant.ACTION_HOME_NAVIGATION_TAB5:
                //圈子
                mAction = action;
                resetUI();
                if (mVideoGroupFragment != null)
                    mVideoGroupFragment.hiedeView();
                if (mCircleFragment == null) {
                    mCircleFragment = new CircleFragment();
                }
                showFragment(R.id.FrameLayout, mCircleFragment, Constant.TAG_CIRCLE);
                break;
        }
    }

    private void resetUI() {
        bgImgeView.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_rectangle_d76dbb));
        mHomeNavigation.setBackgroundColor(0xffffffff);
        mHomeNavigation.showLineView();
    }

    @Override
    public void onInteractionFragment(int action, Object data, Bundle ext) {
        switch (action) {
            case Constant.ACTION_MY_REFRESH_ING:
                int offset = (int) data;
                ViewGroup.LayoutParams lp = bgImgeView.getLayoutParams();
                if (mImgeViewHeight == 0) {
                    mImgeViewHeight = bgImgeView.getMeasuredHeight();
                }
                lp.height = mImgeViewHeight + offset;
                bgImgeView.setLayoutParams(lp);
                break;
        }
    }

    private long lastBackPressed;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastBackPressed > 2000) {
            ToastUtils.show(getString(R.string.再次按下返回键退出APP));
            lastBackPressed = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.start.TAG_GET_NOTICE:
                HttpNoticeBean noticeBean = JsonTools.fromJson(data, HttpNoticeBean.class);
                if (noticeBean == null || noticeBean.getNotice() == null || noticeBean.getNotice().getStatus() == 0) {
                    return;
                }
                NoticeWindow window = new NoticeWindow(this);
                window.setData(noticeBean.getNotice());
                window.showPopupWindow();
                break;
        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {
    }
}
