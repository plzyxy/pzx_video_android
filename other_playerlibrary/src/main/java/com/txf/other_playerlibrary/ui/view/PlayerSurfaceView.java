package com.txf.other_playerlibrary.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import com.txf.other_playerlibrary.interfaces.IMediaPlayer;
import com.txf.other_playerlibrary.tools.Logger;

/**
 * @author txf
 * @create 2019/2/14 0014
 * @
 */
public class PlayerSurfaceView extends SurfaceView {
    public PlayerSurfaceView(Context context) {
        super(context);
    }

    public PlayerSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    ValueAnimator mAnimator;
    RelativeLayout.LayoutParams lp;
    int targetWidth;
    int targetHeight;
    int measuredWidth;
    int measuredHeight;

    public void changeVideoSize(IMediaPlayer mediaPlayer, int width, int height) {
        Logger.show("视频宽度: " + mediaPlayer.getVideoWidth());
        Logger.show("视频高度: " + mediaPlayer.getVideoHeight());
        Logger.show("容器宽度: " + width);
        Logger.show("容器高度: " + height);


//        //根据视频尺寸去计算-&gt;视频可以在sufaceView中放大的最大倍数。
//        float max;
//        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
//            //竖屏模式下按视频宽度计算放大倍数值
//            max = Math.max((float) mediaPlayer.getVideoWidth() / (float) width, (float) mediaPlayer.getVideoHeight() / (float) height);
//        } else {
//            //横屏模式下按视频高度计算放大倍数值
//            max = Math.max(((float) mediaPlayer.getVideoWidth() / (float) height), (float) mediaPlayer.getVideoHeight() / (float) width);
//        }
//        //视频宽高分别/最大倍数值 计算出放大后的视频尺寸
//        targetWidth = (int) Math.ceil((float) mediaPlayer.getVideoWidth() / max);
//        targetHeight = (int) Math.ceil((float) mediaPlayer.getVideoHeight() / max);
//        measuredWidth = getMeasuredWidth();
//        measuredHeight = getMeasuredHeight();
//
//        lp = new RelativeLayout.LayoutParams(
//                targetWidth,
//                targetHeight);
//        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
//        setLayoutParams(lp);

//        mAnimator = ValueAnimator.ofFloat(0f, 1f);
//        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float value = (float) animation.getAnimatedValue();
//                int width = (int) (measuredWidth - (measuredWidth - targetWidth) * value);
//                int height = (int) (measuredHeight - (measuredHeight - targetHeight) * value);
//                lp.width = width;
//                lp.height = height;
//                setLayoutParams(lp);
//            }
//        });
//        mAnimator.setDuration(500);
//        mAnimator.start();
    }
}
