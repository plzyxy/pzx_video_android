package com.txf.ui_mvplibrary.interfaces;

import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author txf
 * @create 2019/1/30 0030
 * @
 */

public interface INavigationBar {
    /**
     * @return 返回需要附加到Layout的View
     */
    View getView();

    /**
     * @return 返回需要附加到Layout的View布局参数
     */
    RelativeLayout.LayoutParams getViewLayoutParams();
}
