package com.thinksoft.banana.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.thinksoft.banana.R;
import com.thinksoft.banana.entity.bean.home.CatesBean;
import com.thinksoft.banana.ui.fragment.TestFragment;
import com.thinksoft.banana.ui.fragment.home.novel.NovelTypeFragment;
import com.txf.ui_mvplibrary.interfaces.IFragment;
import com.txf.ui_mvplibrary.ui.adapter.LjzFragmentAdapter;
import com.txf.ui_mvplibrary.ui.fragment.BaseMvpFragment;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/3/27 0027
 * @主页
 */
public class HomeGroupFragment extends BaseMvpFragment {

    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;
    LjzFragmentAdapter mAdapter;
    ArrayList<String> titls;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_group;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        List<CatesBean> beans = new ArrayList<>();
        beans.add(new CatesBean(1, getString(R.string.视频)));
        beans.add(new CatesBean(2, getString(R.string.电影)));
        beans.add(new CatesBean(3, getString(R.string.福利)));
        beans.add(new CatesBean(4, getString(R.string.图片)));
//        beans.add(new CatesBean(5, getString(R.string.音频)));
        initSlidingTabLayout(beans);
    }
    private void initView() {
        mViewPager = findViewById(R.id.ViewPager);
        mSlidingTabLayout = findViewById(R.id.SlidingTabLayout);
    }

    private void initSlidingTabLayout(List<CatesBean> bean) {
        if (bean == null || bean.size() == 0)
            return;
        titls = new ArrayList<>();
        int deftPos = 0;
        List<IFragment> datas = new ArrayList<>();

        for (int i = 0; i < bean.size(); i++) {
            CatesBean catesBean = bean.get(i);
            //tab
            titls.add(catesBean.getName());
            IFragment iFragment;
            //fragment
            switch (catesBean.getId()) {
                case 1:
                    //视频
                    iFragment = new HomeFragment();
                    break;
                case 2:
                    //电影
                    iFragment = new VideoFragment();
                    break;
                case 3:
                    //小说
                    iFragment = new NovelTypeFragment();
                    break;
                case 4:
                    //图片
                    iFragment = new ImgeListFragemt();
                    break;
                case 5:
                    //音频
                    iFragment = new MusicListFragemt();
                    break;
                default:
                    iFragment = new TestFragment();
                    break;
            }
            iFragment.getFragment().setArguments(BundleUtils.putSerializable(catesBean));
            datas.add(iFragment);
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

}
