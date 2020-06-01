package com.txf.ui_mvplibrary.ui.view.banner;

import android.os.Build;
import android.util.SparseArray;
import android.view.View;

/**
 * View回收器
 * */
public class RecycleBean {
    private View[] activeViews = new View[0];
    private int[] activeViewTypes = new int[0];

    private SparseArray<View>[] scrapViews;

    private int viewTypeCount;

    private SparseArray<View> currentScrapViews;

    public void setViewTypeCount(int viewTypeCount) {
        if (viewTypeCount < 1) {
            throw new IllegalArgumentException("Can't have a viewTypeCount < 1");
        }
        //noinspection unchecked
        SparseArray<View>[] scrapViews = new SparseArray[viewTypeCount];
        for (int i = 0; i < viewTypeCount; i++) {
            scrapViews[i] = new SparseArray<View>();
        }
        this.viewTypeCount = viewTypeCount;
        currentScrapViews = scrapViews[0];
        this.scrapViews = scrapViews;
    }

    protected boolean shouldRecycleViewType(int viewType) {
        return viewType >= 0;
    }

   public View getScrapView(int position, int viewType) {
        if (viewTypeCount == 1) {
            return retrieveFromScrap(currentScrapViews, position);
        } else if (viewType >= 0 && viewType < scrapViews.length) {
            return retrieveFromScrap(scrapViews[viewType], position);
        }
        return null;
    }
   public void addScrapView(View scrap, int position, int viewType) {
        if (viewTypeCount == 1) {
            currentScrapViews.put(position, scrap);
        } else {
            scrapViews[viewType].put(position, scrap);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            scrap.setAccessibilityDelegate(null);
        }
    }
    static View retrieveFromScrap(SparseArray<View> scrapViews, int position) {
        int size = scrapViews.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                int fromPosition = scrapViews.keyAt(i);
                View view = scrapViews.get(fromPosition);
                if (fromPosition == position) {
                    scrapViews.remove(fromPosition);
                    return view;
                }
            }
            int index = size - 1;
            View r = scrapViews.valueAt(index);
            scrapViews.remove(scrapViews.keyAt(index));
            return r;
        } else {
            return null;
        }
    }
}
