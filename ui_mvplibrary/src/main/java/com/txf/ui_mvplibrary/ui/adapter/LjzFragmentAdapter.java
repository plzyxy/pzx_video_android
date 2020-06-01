package com.txf.ui_mvplibrary.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.txf.ui_mvplibrary.interfaces.IFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @Title 懒加载 FragmentPagerAdapter 适配器
 * @package com.zhsj.tvbee.android.ui.adapter
 * @date 2016/9/9 0009
 */
public class LjzFragmentAdapter extends FragmentPagerAdapter {
    List<IFragment> datas = new ArrayList<>();
    ViewPager mViewPager;

    public LjzFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<IFragment> datas, ViewPager viewPager) {
        this.mViewPager = viewPager;
        if (datas != null && datas.size() > 0) {
            this.datas = datas;
            notifyDataSetChanged();
        }
    }

    @Override
    public Fragment getItem(int position) {
        return datas.size() > 0 ? datas.get(position).getFragment() : null;
    }

    @Override
    public int getCount() {
        return datas.size() > 0 ? datas.size() : 0;
    }

    int currentItem = -1;

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (mViewPager != null && currentItem != mViewPager.getCurrentItem()) {
            currentItem = mViewPager.getCurrentItem();
            datas.get(currentItem).show();
        }
        super.setPrimaryItem(container, position, object);
    }

    ////////////////////以下是为了让notifyDataSetChanged();方法生效 ////////////////////
    protected int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
