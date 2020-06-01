package com.txf.ui_mvplibrary.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @Title 常见的FragmentPagerAdapter 适配器
 * @package com.zhsj.tvbee.android.ui.adapter
 * @date 2016/9/9 0009
 */
public class SimpleFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> datas = new ArrayList<>();

    public SimpleFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<Fragment> datas) {
        if (datas != null && datas.size() > 0) {
            this.datas = datas;
            notifyDataSetChanged();
        }
    }

    @Override
    public Fragment getItem(int position) {
        return datas.size() > 0 ? datas.get(position) : null;
    }

    @Override
    public int getCount() {
        return datas.size() > 0 ? datas.size() : 0;
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
