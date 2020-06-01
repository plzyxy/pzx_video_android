package com.txf.ui_mvplibrary.ui.adapter.item_decoration;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author txf
 * @Title RecyclerView  间距
 * @package com.common.library.ui.view
 * @date 2017/6/7 0007
 */

public class ItemDecorationLinear extends RecyclerView.ItemDecoration {
    private int spacing;
    private boolean includeEdge;
    private int orientation;

    /**
     * @param orientation    布局方向
     * @param spacing   间距
     * @param includeEdge 是否包含边缘
     */
    public ItemDecorationLinear(int orientation, int spacing, boolean includeEdge) {
        this.orientation = orientation;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int count = parent.getAdapter().getItemCount();
        if(orientation == LinearLayoutManager.HORIZONTAL){
            if (!includeEdge) {
                if (position != 0)
                    outRect.left = spacing;
            } else {
                outRect.left = spacing;
                outRect.top = spacing;
                if (position == count - 1)
                    outRect.right = spacing;
            }
        }else if(orientation == LinearLayoutManager.VERTICAL){
            if (!includeEdge) {
                if (position != 0)
                    outRect.top = spacing;
            } else {
                outRect.left = spacing;
                outRect.right = spacing;
                outRect.top = spacing;
                if (position == count - 1)
                    outRect.bottom = spacing;
            }
        }
    }
}
