package com.txf.ui_mvplibrary.ui.view.window;

import android.content.Context;
import android.view.View;

import com.txf.ui_mvplibrary.interfaces.OnAppListener;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author txf
 * @create 2019/2/25 0025
 * @
 */
public abstract class BaseWindow extends BasePopupWindow implements View.OnClickListener {
    OnAppListener.OnWindowListener listener;
    int tag;

    public BaseWindow(Context context) {
        super(context);
        if (context instanceof OnAppListener.OnWindowListener)
            listener = (OnAppListener.OnWindowListener) context;
        onCreate(context);
    }

    public BaseWindow(Context context, boolean delayInit) {
        super(context, delayInit);
        if (context instanceof OnAppListener.OnWindowListener)
            listener = (OnAppListener.OnWindowListener) context;
        onCreate(context);
    }

    public BaseWindow(Context context, int width, int height) {
        super(context, width, height);
        if (context instanceof OnAppListener.OnWindowListener)
            listener = (OnAppListener.OnWindowListener) context;
        onCreate(context);
    }
    public void setOnClick(int... ids){
        for (int id:ids)
            findViewById(id).setOnClickListener(this);
    }
    protected abstract void onCreate(Context context);

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setListener(OnAppListener.OnWindowListener listener) {
        this.listener = listener;
    }

    public OnAppListener.OnWindowListener getListener() {
        return listener;
    }

    @Override
    public void onClick(View v) {

    }
}
