package com.thinksoft.banana.ui.view.titlebar;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinksoft.banana.R;
import com.txf.ui_mvplibrary.ui.view.BaseViewGroup;
import com.txf.ui_mvplibrary.interfaces.ITitleBar;

/**
 * @author txf
 * @create 2019/2/17
 */
public class HomeTitleBar extends BaseViewGroup implements ITitleBar {
    TextView iconView;

    public HomeTitleBar(Context context) {
        super(context);
    }

    public HomeTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreate(Context context) {
        inflate(context, R.layout.view_home_titlebar, this);
        iconView = getViewById(R.id.iconView);

        iconView.setText(getString(R.string.app_name));
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public LayoutParams getViewLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void setTitleText(CharSequence text) {

    }

}
