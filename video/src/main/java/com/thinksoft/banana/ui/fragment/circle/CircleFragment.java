package com.thinksoft.banana.ui.fragment.circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.thinksoft.banana.R;
import com.thinksoft.banana.entity.bean.home.CatesBean;
import com.thinksoft.banana.ui.activity.circle.CircleAddActivity;
import com.thinksoft.banana.ui.fragment.TestFragment;
import com.txf.ui_mvplibrary.interfaces.IFragment;
import com.txf.ui_mvplibrary.ui.adapter.LjzFragmentAdapter;
import com.txf.ui_mvplibrary.ui.fragment.BaseMvpFragment;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/3/11 0011
 * @圈子
 */
public class CircleFragment extends BaseMvpFragment {
    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;
    LjzFragmentAdapter mAdapter;
    ArrayList<String> titls;
    View addButton;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

        ArrayList<CatesBean> bean = new ArrayList<>();
        bean.add(new CatesBean(1, getString(R.string.圈子)));
        bean.add(new CatesBean(2, getString(R.string.我的)));
        initSlidingTabLayout(bean);
    }

    private void initView() {
        mViewPager = findViewById(R.id.ViewPager);
        mSlidingTabLayout = findViewById(R.id.SlidingTabLayout);
        addButton = findViewById(R.id.addButton);
        setOnClick(R.id.addButton);
    }

    private void initSlidingTabLayout(ArrayList<CatesBean> bean) {
        if (bean == null || bean.size() == 0)
            return;
        titls = new ArrayList<>();
        int deftPos = 0;
        List<IFragment> datas = new ArrayList<>();

        for (int i = 0; i < bean.size(); i++) {
            CatesBean catesBean = bean.get(i);
            IFragment fragment;
            titls.add(catesBean.getName());
            switch (bean.get(i).getId()) {
                case 1:
                    fragment = new CircleListFragemt();
                    break;
                case 2:
                    fragment = new CircleMyFragemt();
                    break;
                default:
                    fragment = new TestFragment();
                    break;
            }
            fragment.getFragment().setArguments(BundleUtils.putSerializable(catesBean));
            datas.add(fragment);
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
            case R.id.addButton:
                startActivity(new Intent(getContext(), CircleAddActivity.class));
                break;
        }
    }
}
