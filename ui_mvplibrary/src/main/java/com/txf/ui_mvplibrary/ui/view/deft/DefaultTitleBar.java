package com.txf.ui_mvplibrary.ui.view.deft;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.txf.ui_mvplibrary.R;
import com.txf.ui_mvplibrary.interfaces.ITitleBar;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.view.BaseViewGroup;


/**
 * @author txf
 * @create 2019/1/30 0030
 * @
 */

public class DefaultTitleBar extends BaseViewGroup implements ITitleBar {
    public DefaultTitleBar(Context context) {
        super(context);
    }

    public DefaultTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    TextView titleTV;

    @Override
    protected void onCreate(Context context) {
        View.inflate(context, R.layout.libs_view_default_titlebar, this);
        titleTV = getViewById(R.id.titleTV);
        setOnClick(R.id.backButton);
    }

    public DefaultTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public LayoutParams getViewLayoutParams() {
        return new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.backButton) {
            getListener().onInteractionView(OnAppListener.OnViewListener.ACTION_CLICK_FINISH_ACTIVITY, null);
        }
    }

    @Override
    public void setTitleText(CharSequence text) {
        titleTV.setText(text);
    }

}
