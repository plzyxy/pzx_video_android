package com.thinksoft.banana.ui.view.window;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.NoticeBean;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author txf
 * @create 2019/3/23 0023
 * @
 */
public class NoticeWindow extends BasePopupWindow implements View.OnClickListener {
    OnAppListener.OnWindowListener listener;
    TextView tv1, tv2;
    int tag;

    public OnAppListener.OnWindowListener getListener() {
        return listener;
    }

    public NoticeWindow(Context context) {
        super(context);
        if (context instanceof OnAppListener.OnWindowListener)
            listener = (OnAppListener.OnWindowListener) context;
        initView();
    }

    public void setData(NoticeBean notice) {
        tv2.setText(notice.getContent());
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setListener(OnAppListener.OnWindowListener listener) {
        this.listener = listener;
    }

    private void initView() {
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv1.setText(getContext().getResources().getString(R.string.app_name) + "公告");
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                break;
        }
        dismiss();
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.window_notice);
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
