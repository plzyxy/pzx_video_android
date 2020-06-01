package com.txf.ui_mvplibrary.ui.adapter.hoder;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * @author txf
 * @title ViewHoder
 * 1.提供 getViewById方法查找子view {@link #getViewById(int)}
 * 2.提供 通过id设置TextView类容 {@link #setText(int, String)}
 * 3.提供 通过id设置View背景 {@link #setBackgroundResource(int, int)} {@link #setBackgroundColor(int, int)}
 * 3.提供 通过id设置View显示隐藏 {@link #setVisibility(int, boolean)}
 * @date 2017/11/2/002
 */
public class BaseViewHoder extends RecyclerView.ViewHolder {

    public BaseViewHoder(View itemView) {
        super(itemView);
    }

    public void setText(@IdRes int id, String text) {
        ((TextView) getViewById(id)).setText(text);
    }

    public void setVisibility(@IdRes int id, boolean is) {
        if (is) {
            getViewById(id).setVisibility(View.VISIBLE);
        } else {
            getViewById(id).setVisibility(View.GONE);
        }
    }

    public void setBackgroundColor(@IdRes int id, @ColorInt int color) {
        getViewById(id).setBackgroundColor(color);
    }

    public void setBackgroundResource(@IdRes int id, @DrawableRes int resid) {
        getViewById(id).setBackgroundResource(resid);
    }

    public <E extends View> E getViewById(@IdRes int id) {
        return (E) itemView.findViewById(id);
    }

}
