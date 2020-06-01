package com.txf.ui_mvplibrary.ui.adapter.item_decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Arrays;
import java.util.List;


/**
 * @author txf
 * @create 2019/2/17
 */
public class ItemDecorationCommon extends RecyclerView.ItemDecoration {
    private Rect mEdge;
    private int spacing;
    private int spanCount;
    private List<Integer> mItemType;

    /**
     * @param spanCount 列数
     * @param mEdge     左,上,右,下,边距
     * @param spacing   其它边距
     * @param itemType  需要添加边距的itemType
     */
    public ItemDecorationCommon(int spanCount, Rect mEdge, int spacing, Integer... itemType) {
        this.spanCount = spanCount;
        this.mEdge = mEdge;
        this.spacing = spacing;
        if (itemType != null && itemType.length > 0) {
            this.mItemType = Arrays.asList(itemType);
        }
    }

    /**
     * @param spanCount 列数
     * @param spacing   其它边距
     * @param top       上边距
     * @param edge      左右边距
     */
    public ItemDecorationCommon(int spanCount, int spacing, int top, int edge, Integer... itemType) {
        this(spanCount, new Rect(edge, top, edge, spacing), spacing, itemType);
    }

    int startRemainder;
    int startPosition;

    private int getStartPosition(int position, RecyclerView parent) {
        for (int i = position; i >= 0; i--) {
            int itemType = parent.getAdapter().getItemViewType(i);
            if (!mItemType.contains(itemType)) {
                return i + 1;
            }
        }
        return 0;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int itemType = parent.getAdapter().getItemViewType(position);

        if (mItemType == null || mItemType.contains(itemType)) {

            //初始化 开始位置
            if (position != 0 && mItemType != null) {
                startPosition = getStartPosition(position, parent);
                startRemainder = startPosition % spanCount != 0 ? spanCount - (startPosition % spanCount) : 0;
//
//                int lastItemType = parent.getAdapter().getItemViewType(position - 1);
//                if (mItemType.get(0) != lastItemType) {
//                    startPosition = position;
//                    startRemainder = startPosition % spanCount != 0 ? spanCount - (startPosition % spanCount) : 0;
//                }
            }
//            Log.i("tang", "startPosition: " + startPosition + "    position: " + position);

            int column = (position + startRemainder) % spanCount; // item column

            if (column == 0) {
                outRect.left = mEdge.left;
            } else {
                outRect.left = spacing / 2; // spacing - column * ((1f / spanCount) * spacing)
            }

            if (column == spanCount - 1) {
                outRect.right = mEdge.right;
            } else {
                outRect.right = spacing / 2; // (column + 1) * ((1f / spanCount) * spacing)
            }

            if (position < startPosition + spanCount) { // top edge
                outRect.top = mEdge.top;
            }
            outRect.bottom = spacing; // item bottom
        }
    }
}

