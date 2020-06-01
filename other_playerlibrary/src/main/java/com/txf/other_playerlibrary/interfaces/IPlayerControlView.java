package com.txf.other_playerlibrary.interfaces;

import android.view.SurfaceHolder;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author txf
 * @create 2019/2/14 0014
 * @媒体播放控制视图接口
 */
public interface IPlayerControlView {
    View getView();

    RelativeLayout.LayoutParams getViewLayoutParams();

    void setIMediaPlayer(IMediaPlayer iMediaPlayer);

    IPlayCallback getIPlayCallback();

    void surfaceCreated(SurfaceHolder holder);

    void surfaceChanged(SurfaceHolder holder, int format, int width,
                        int height);

    void surfaceDestroyed(SurfaceHolder holder);

    void setExtraListener(IExtraListener listener);

    void changeOrientation(int orientation);

    void setHorizontalScreen(boolean horizontalScreen);
}
