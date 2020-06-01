package com.thinksoft.banana.ui.view.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.txf.other_playerlibrary.interfaces.IPlayerControlView;
import com.txf.other_playerlibrary.ui.view.PlayerSurfaceView;
import com.txf.other_playerlibrary.ui.view.player.base.BaseViewGroup;

/**
 * @author txf
 * @create 2019/2/14 0014
 * @
 */
public class PlayerListView extends BaseViewGroup implements SurfaceHolder.Callback {
    PlayerSurfaceView mSurfaceView;
    PlayerVideoGroupControlView mControlView;

    public PlayerListView(Context context) {
        super(context);
    }

    public PlayerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlayerVideoGroupControlView getControlView() {
        return mControlView;
    }

    @Override
    protected void onCreate() {
        mControlView = new PlayerVideoGroupControlView(getContext());
        addSurfaceView();
        setPlayerControlView(mControlView);
    }

    private void addSurfaceView() {
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);

        mSurfaceView = new PlayerSurfaceView(getContext());
        mSurfaceView.getHolder().addCallback(this);

        addView(mSurfaceView, lp);
    }

    public void setPlayerControlView(IPlayerControlView iPlayerControlView) {
        if (mControlView != null) {
            removeView(mControlView.getView());
        }
        mControlView = (PlayerVideoGroupControlView) iPlayerControlView;
        //设置控制层背景 防止隐藏后 显示出来 不绘制
        mControlView.getView().setBackgroundColor(0x01000000);
        addView(mControlView.getView(), mControlView.getViewLayoutParams());
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mControlView.surfaceCreated(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mControlView.surfaceChanged(holder, format, width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mControlView.surfaceDestroyed(holder);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


}
