package com.txf.other_playerlibrary.ui.view.player.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author txf
 * @create 2019/2/14 0014
 * @
 */
public abstract class BaseViewGroup extends RelativeLayout {
    public BaseViewGroup(Context context) {
        super(context);
        onCreate();
    }

    public BaseViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreate();

    }

    public BaseViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onCreate();
    }

    public int dip2px(float dipValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    protected abstract void onCreate();
}
