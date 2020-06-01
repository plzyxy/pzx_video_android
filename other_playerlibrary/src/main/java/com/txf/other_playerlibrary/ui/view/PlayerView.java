package com.txf.other_playerlibrary.ui.view;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.txf.other_playerlibrary.interfaces.IExtraListener;
import com.txf.other_playerlibrary.interfaces.IMediaPlayer;
import com.txf.other_playerlibrary.interfaces.IPlayerControlView;
import com.txf.other_playerlibrary.player.PlayerProvide;
import com.txf.other_playerlibrary.ui.view.player.base.BaseViewGroup;

/**
 * @author txf
 * @create 2019/2/14 0014
 * @
 */
public class PlayerView extends BaseViewGroup implements SurfaceHolder.Callback, IExtraListener {
    IPlayerControlView mControlView;
    PlayerSurfaceView mSurfaceView;
    OrientationEventListener mOrientationListener;
    int mOrientation;

    public PlayerView(Context context) {
        super(context);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
    }

    public int getOrientation() {
        return mOrientation;
    }

    @Override
    protected void onCreate() {
        addSurfaceView();
        mOrientationListener = new OrientationEventListener(getContext(), SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
//                Logger.show("屏幕角度发生改变: " + orientation);
                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return;  //手机平放时，检测不到有效的角度
                }
                //只检测是否有四个角度的改变
                if (orientation > 350 || orientation < 10) { //0度
                    orientation = 0;
                } else if (orientation > 80 && orientation < 100) { //90度
                    orientation = 90;
                } else if (orientation > 170 && orientation < 190) { //180度
                    orientation = 180;
                } else if (orientation > 260 && orientation < 280) { //270度
                    orientation = 270;
                }
                if (getOrientation() == orientation)
                    return;
                if (orientation != 0 && orientation != 90 && orientation != 180 && orientation != 270)
                    return;
                setOrientation(orientation);
                if (mControlView != null)
                    mControlView.changeOrientation(orientation);
            }
        };
        if (mOrientationListener.canDetectOrientation()) {
            mOrientationListener.enable();
        } else {
            mOrientationListener.disable();
        }
    }

    boolean mHorizontalScreen;

    public void setHorizontalScreen(boolean horizontalScreen) {
        this.mHorizontalScreen = horizontalScreen;
        if (mControlView != null)
            mControlView.setHorizontalScreen(horizontalScreen);
    }

    public boolean isHorizontalScreen() {
        return mHorizontalScreen;
    }

    int mWidth, mHeight;

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setHorizontalScreen(false);
            RelativeLayout.LayoutParams lp = (LayoutParams) getLayoutParams();
            lp.width = mWidth;
            lp.height = mHeight;
            setLayoutParams(lp);
            onVideoSizeChanged(PlayerProvide.getInstance().getMediaPlayer());
        } else {
            setHorizontalScreen(true);
            mWidth = getWidth();
            mHeight = getHeight();
            RelativeLayout.LayoutParams lp = (LayoutParams) getLayoutParams();
            lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            lp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            setLayoutParams(lp);
            onVideoSizeChanged(PlayerProvide.getInstance().getMediaPlayer());
        }
    }

    private void addSurfaceView() {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);

        SurfaceView surfaceView = new SurfaceView(getContext());
        surfaceView.setBackgroundColor(0xff000000);
        addView(surfaceView, 0, lp);

        mSurfaceView = new PlayerSurfaceView(getContext());
        mSurfaceView.getHolder().addCallback(this);
        mSurfaceView.setZOrderOnTop(true);
        mSurfaceView.setZOrderMediaOverlay(true);
        addView(mSurfaceView, 1, lp);
    }

    public void setPlayerControlView(IPlayerControlView iPlayerControlView) {
        if (mControlView != null) {
            removeView(mControlView.getView());
        }
        mControlView = iPlayerControlView;
        //设置控制层背景 防止隐藏后 显示出来 不绘制
        mControlView.setExtraListener(this);
        mControlView.getView().setBackgroundColor(0x01000000);
        addView(mControlView.getView(), mControlView.getViewLayoutParams());
    }

    public void setOnSurfaceHolderListener(final OnSurfaceHolderListener l) {
        mSurfaceView.post(new Runnable() {
            @Override
            public void run() {
                l.surfaceCreated();
            }
        });
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer) {
        if (iMediaPlayer == null)
            return;
        if (isHorizontalScreen()) {
            //全屏
            Resources resources = this.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            mSurfaceView.changeVideoSize(iMediaPlayer, width, height);
        } else {
            //竖屏
            mSurfaceView.changeVideoSize(iMediaPlayer,
                    mWidth == 0 ? getMeasuredWidth() : mWidth,
                    mHeight == 0 ? getMeasuredHeight() : mHeight);
        }
    }

    public interface OnSurfaceHolderListener {
        void surfaceCreated();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        PlayerProvide.getInstance().setSurfaceHolder(holder);
        if (mControlView != null)
            mControlView.surfaceCreated(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mControlView != null)
            mControlView.surfaceChanged(holder, format, width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        PlayerProvide.getInstance().clearSurfaceHolder();
        if (mControlView != null)
            mControlView.surfaceDestroyed(holder);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mOrientationListener != null)
            mOrientationListener.disable();
    }


}
