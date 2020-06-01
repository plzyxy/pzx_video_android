package com.thinksoft.banana.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.thinksoft.banana.R;
import com.thinksoft.banana.entity.bean.home.CatesBean;
import com.thinksoft.banana.entity.item.HomeItem;
import com.thinksoft.banana.ui.fragment.home.TypeListFragemt;
import com.txf.ui_mvplibrary.interfaces.IFragment;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;
import com.txf.ui_mvplibrary.ui.adapter.LjzFragmentAdapter;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/18 0018
 * @分类
 */
public class TypeActivity
        extends BaseMvpActivity {
    CatesBean mCatesBean;
    ArrayList<CatesBean> mCatesBeans;

    View backButton;
    ImageView backIconView;
    TextView titleText;

    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;

    LjzFragmentAdapter mAdapter;
    List<String> titls;

    public static Intent getIntent(Context context, CatesBean bean, ArrayList<CatesBean> catesBeans) {
        Intent i = new Intent(context, TypeActivity.class);
        i.putExtra("data1", bean);
        i.putExtra("data2", catesBeans);
        return i;
    }

    public static Intent getIntent(Context context, HomeItem item, ArrayList<CatesBean> catesBeans) {
        return getIntent(context, (CatesBean) item.getData(), catesBeans);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCatesBean = (CatesBean) getIntent().getSerializableExtra("data1");
        mCatesBeans = (ArrayList<CatesBean>) getIntent().getSerializableExtra("data2");
        initView();
        initTitleBar();
        initListener();
        initSlidingTabLayout(mCatesBeans);
    }

    private void initView() {
        backButton = findViewById(R.id.backButton);
        backIconView = findViewById(R.id.backIconView);
        titleText = findViewById(R.id.titleText);

        mViewPager = findViewById(R.id.ViewPager);
        mSlidingTabLayout = findViewById(R.id.SlidingTabLayout);
    }

    private void initTitleBar() {
        titleText.setTextColor(0xff333333);
        titleText.setText(getString(R.string.分类));
        backIconView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_search_left));
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
            TypeListFragemt testFragment = new TypeListFragemt();
            testFragment.setArguments(BundleUtils.putSerializable(catesBean));
            datas.add(testFragment);
            //默认选中
            if (catesBean.getId() == mCatesBean.getId()) {
                deftPos = i;
            }
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

    private void initListener() {
        setOnClick(R.id.backButton);
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
}
