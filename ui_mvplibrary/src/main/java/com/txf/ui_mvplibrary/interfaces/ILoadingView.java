package com.txf.ui_mvplibrary.interfaces;

import android.view.View;
import android.widget.FrameLayout;

/**
 * @author txf
 * @create 2019/2/12 0012
 * @
 */

public interface ILoadingView {
    /**
     * @return 返回需要附加到Layout的View
     */
    View getView();

    /**
     * @return 返回需要附加到Layout的View布局参数
     */
    FrameLayout.LayoutParams getViewLayoutParams();

    /**
     * 显示logding时 是否可以点击取消
     */
    void interceptClick(boolean off);

    void show();

    void hide();
}
