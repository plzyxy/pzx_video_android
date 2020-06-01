package com.thinksoft.banana.ui.view.window;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author txf
 * @create 2019/2/21 0021
 * @分享窗口
 */
public class ShareWindow extends BasePopupWindow implements View.OnClickListener {
    OnAppListener.OnWindowListener listener;
    int tag;

    public OnAppListener.OnWindowListener getListener() {
        return listener;
    }

    public ShareWindow(Context context) {
        super(context);
        if (context instanceof OnAppListener.OnWindowListener)
            listener = (OnAppListener.OnWindowListener) context;
        initView();
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setListener(OnAppListener.OnWindowListener listener) {
        this.listener = listener;
    }

    private void initView() {
        findViewById(R.id.qqButton).setOnClickListener(this);
        findViewById(R.id.qqPyButton).setOnClickListener(this);
        findViewById(R.id.wxButton).setOnClickListener(this);
        findViewById(R.id.wxPyButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qqButton:
                getListener().onInteractionWindow(Constant.ACTION_SHARE_QQ, tag, null);
                break;
            case R.id.qqPyButton:
                getListener().onInteractionWindow(Constant.ACTION_SHARE_QQ_PY, tag, null);
                break;
            case R.id.wxButton:
                getListener().onInteractionWindow(Constant.ACTION_SHARE_WX, tag, null);
                break;
            case R.id.wxPyButton:
                getListener().onInteractionWindow(Constant.ACTION_SHARE_WX_PX, tag, null);
                break;
        }
        dismiss();
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.window_share);
    }

    Animation alphaAnimation;

    @Override
    protected Animation onCreateShowAnimation() {
        alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(250);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        return alphaAnimation;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(250);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        return alphaAnimation;
    }


}
