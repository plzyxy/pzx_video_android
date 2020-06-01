package com.txf.ui_mvplibrary.interfaces;

import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author txf
 * @create 2019/1/30 0030
 * @
 */

public interface ITitleBar {
    /**
     * @return 返回需要附加到Lyout的View
     */
    View getView();

    /**
     * @return 返回需要附加到Lyout的View布局参数
     */
    RelativeLayout.LayoutParams getViewLayoutParams();

    /**
     * @param text 设置title 文字
     */
    void setTitleText(CharSequence text);
}
