package com.txf.ui_mvplibrary.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author txf
 * @Title
 * @package com.common.library.ui.view
 * @date 2018/3/15/015
 */

public class ViewPagerSlide extends ViewPager {
    //    //是否可以进行滑动
    private boolean isSlide = true;

    public ViewPagerSlide(Context context) {
        super(context);
    }

    public ViewPagerSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置其是否能滑动换页
     *
     * @param slide false 不能换页， true 可以滑动换页
     */
    public void setSlide(boolean slide) {
        isSlide = slide;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isSlide && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isSlide && super.onTouchEvent(ev);

    }
}
