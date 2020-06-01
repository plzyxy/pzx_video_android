package com.thinksoft.banana.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author txf
 * @create 2019/3/26 0026
 * @
 */
public class CircleRecyclerView extends RecyclerView {
    public CircleRecyclerView(Context context) {
        super(context);
    }

    public CircleRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }

}
