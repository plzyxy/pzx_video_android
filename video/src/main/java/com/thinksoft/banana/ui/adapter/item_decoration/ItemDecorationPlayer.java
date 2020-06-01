package com.thinksoft.banana.ui.adapter.item_decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.thinksoft.banana.app.Constant;

/**
 * @author txf
 * @create 2019/2/17
 */
public class ItemDecorationPlayer extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    Context mContext;
    int mOrientation;
    Drawable mDivider;
    private final Rect mBounds = new Rect();

    public ItemDecorationPlayer(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        if (mDivider == null) {
//            Log.w(getClass().getName(), "@android:attr/listDivider was not set in the theme used for this "
//                    + "DividerItemDecoration. Please set that attribute all call setDrawable()");
        }
        a.recycle();
        this.mContext = context;
        this.mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || mDivider == null) {
            return;
        }
        drawVertical(c, parent);
    }


    protected int dip2px(float dipValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        int left;
        int right;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }
        left = left + dip2px(15);
        right = right - dip2px(15);

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {

            final View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            int itemType = parent.getAdapter().getItemViewType(position);

            if (itemType == Constant.TYPE_ITEM_1) {
                parent.getDecoratedBoundsWithMargins(child, mBounds);
                final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
                final int top = bottom - mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
        }
        canvas.restore();
    }
}

