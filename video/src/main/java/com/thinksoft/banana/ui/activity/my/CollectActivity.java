package com.thinksoft.banana.ui.activity.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.home.CatesBean;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.MyModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.thinksoft.banana.ui.fragment.my.CollectFilmFragemt;
import com.thinksoft.banana.ui.fragment.my.CollectPerformerFragment;
import com.thinksoft.banana.ui.fragment.my.CollectVideoFragemt;
import com.thinksoft.banana.ui.fragment.type.TypeFilmFragemt;
import com.thinksoft.banana.ui.fragment.type.TypeVideoFragemt;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.ui_mvplibrary.interfaces.IFragment;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;
import com.txf.ui_mvplibrary.ui.adapter.LjzFragmentAdapter;
import com.txf.ui_mvplibrary.ui.fragment.BaseMvpListLjzFragment;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/19
 * 我的收藏  3.0版本 收藏页面
 */
public class CollectActivity
        extends BaseMvpActivity<CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View {
    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;
    TextView titleTV;

    LjzFragmentAdapter mAdapter;
    ArrayList<String> titls;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(this, new MyModel()));
        initView();
        titleTV.setText(getString(R.string.我的收藏));
        ArrayList<CatesBean> bean = new ArrayList<>();
        bean.add(new CatesBean(3, getString(R.string.演员)));
        bean.add(new CatesBean(2, getString(R.string.电影)));
        bean.add(new CatesBean(1, getString(R.string.视频)));
        initSlidingTabLayout(bean);
    }

    private void initView() {
        mViewPager = findViewById(R.id.ViewPager);
        mSlidingTabLayout = findViewById(R.id.SlidingTabLayout);
        titleTV = findViewById(R.id.titleTV);
        setOnClick(R.id.backButton);
    }

    private void initSlidingTabLayout(ArrayList<CatesBean> bean) {
        if (bean == null || bean.size() == 0)
            return;
        titls = new ArrayList<>();
        int deftPos = 0;
        List<IFragment> datas = new ArrayList<>();

        for (int i = 0; i < bean.size(); i++) {
            CatesBean catesBean = bean.get(i);
            BaseMvpListLjzFragment fragment;
            titls.add(catesBean.getName());
            switch (bean.get(i).getId()) {
                case 3:
                    fragment = new CollectPerformerFragment();
                    break;
                case 2:
                    fragment = new CollectFilmFragemt();
                    break;
                case 1:
                    fragment = new CollectVideoFragemt();
                    break;
                default:
                    fragment = new CollectFilmFragemt();
                    break;
            }
            fragment.setArguments(BundleUtils.putSerializable(catesBean));
            datas.add(fragment);
        }
        if (bean.size() > 5) {
            mSlidingTabLayout.setTabSpaceEqual(false);
            mViewPager.setOffscreenPageLimit(5);
        } else {
            mSlidingTabLayout.setTabSpaceEqual(true);
            mViewPager.setOffscreenPageLimit(bean.size());
        }
        mViewPager.setAdapter(mAdapter = new LjzFragmentAdapter(getSupportFragmentManager()));
        mAdapter.setData(datas, mViewPager);
        mSlidingTabLayout.setViewPager(mViewPager, titls.toArray(new String[]{}));
        mViewPager.setCurrentItem(deftPos);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                finish();
                break;
        }
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {

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
