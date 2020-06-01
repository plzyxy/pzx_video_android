package com.thinksoft.banana.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.entity.bean.home.CatesBean;
import com.thinksoft.banana.entity.bean.video.TypeBean;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.VideoModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.ui_mvplibrary.interfaces.IFragment;
import com.txf.ui_mvplibrary.ui.adapter.LjzFragmentAdapter;
import com.txf.ui_mvplibrary.ui.fragment.BaseMvpLjzFragment;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/3/9 0009
 * @电影
 */
public class VideoFragment
        extends BaseMvpLjzFragment<CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View {
    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;
    LjzFragmentAdapter mAdapter;
    ArrayList<String> titls;
    View resetButton;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(getContext(), new VideoModel()));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    protected void request() {
        //获取分类列表
        getILoadingView().getView().setVisibility(View.VISIBLE);
        getPresenter().getData(ApiRequestTask.video.TAG_VIDEO_TYPE_LIST, false);
    }

    private void initView() {
        mViewPager = findViewById(R.id.ViewPager);
        mSlidingTabLayout = findViewById(R.id.SlidingTabLayout);
        resetButton = findViewById(R.id.resetButton);
        resetButton.setVisibility(View.GONE);
        setOnClick(R.id.resetButton);
    }

    private void initSlidingTabLayout(ArrayList<CatesBean> bean) {
        if (bean == null || bean.size() == 0)
            return;
        titls = new ArrayList<>();
        int deftPos = 0;
        List<IFragment> datas = new ArrayList<>();

        for (int i = 0; i < bean.size(); i++) {
            CatesBean catesBean = bean.get(i);
            //tab
            titls.add(catesBean.getName());
            //fragment
            VideoListFragemt listFragemt = new VideoListFragemt();
            listFragemt.setArguments(BundleUtils.putSerializable(catesBean));
            datas.add(listFragemt);
        }
        if (bean.size() > 5) {
            mSlidingTabLayout.setTabSpaceEqual(false);
            mViewPager.setOffscreenPageLimit(5);
        } else {
            mSlidingTabLayout.setTabSpaceEqual(true);
            mViewPager.setOffscreenPageLimit(bean.size());
        }
        mViewPager.setAdapter(mAdapter = new LjzFragmentAdapter(getChildFragmentManager()));
        mAdapter.setData(datas, mViewPager);
        mSlidingTabLayout.setViewPager(mViewPager, titls.toArray(new String[]{}));
        mViewPager.setCurrentItem(deftPos);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.resetButton:
                //获取分类列表
                getILoadingView().getView().setVisibility(View.VISIBLE);
                getPresenter().getData(ApiRequestTask.video.TAG_VIDEO_TYPE_LIST);
                break;
        }
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.video.TAG_VIDEO_TYPE_LIST:
                getILoadingView().getView().setVisibility(View.GONE);
                resetButton.setVisibility(View.GONE);
                TypeBean bean = JsonTools.fromJson(data, TypeBean.class);
                initSlidingTabLayout(bean.getCates());
                break;
        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {
        switch (sign) {
            case ApiRequestTask.video.TAG_VIDEO_TYPE_LIST:
                getILoadingView().getView().setVisibility(View.GONE);
                resetButton.setVisibility(View.VISIBLE);
                break;
        }
    }
}
