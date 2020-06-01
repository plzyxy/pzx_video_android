package com.thinksoft.banana.ui.manage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.thinksoft.banana.R;
import com.thinksoft.banana.ui.view.player.PlayerGestureControlView;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.ui.view.BaseViewGroup;

/**
 * @author txf
 * @create 2019/2/26 0026
 * @
 */
public class UIPlayerVideoGroupManage {
    Context context;
    TextView nameTV, zTV, scButton, zButton, currentTimeTV, totalTimeTV, priceTV;
    AppCompatSeekBar mSeekBar;
    ImageView playerIcon;
    View loadingView, controlView, topControlView, bottomControlView, paymentRoot;
    long totalTime;
    long currentTime;
    PlayerGestureControlView mGestureControl;

    public UIPlayerVideoGroupManage(Context context, BaseViewGroup view) {
        this.context = context;
        mGestureControl = (PlayerGestureControlView) view;
        View.inflate(context, R.layout.view_player_video_group_control, view);
        topControlView = view.getViewById(R.id.topControlView);
        bottomControlView = view.getViewById(R.id.bottomControlView);

        controlView = view.getViewById(R.id.controlView);

        nameTV = view.getViewById(R.id.nameTV);
        scButton = view.getViewById(R.id.scButton);
        zButton = view.getViewById(R.id.zButton);
        zTV = view.getViewById(R.id.zTV);
        playerIcon = view.getViewById(R.id.playerIcon);
        currentTimeTV = view.getViewById(R.id.currentTimeTV);
        totalTimeTV = view.getViewById(R.id.totalTimeTV);
        mSeekBar = view.getViewById(R.id.SeekBar);
        loadingView = view.getViewById(R.id.loadingView);

        paymentRoot = view.getViewById(R.id.paymentRoot);
        priceTV = view.getViewById(R.id.priceTV);

        hidePayMentRoot();
        view.setOnClick(R.id.scButton, R.id.playerButton, R.id.shareButton, R.id.paymentButton, R.id.zButton);
    }

    private Context getContext() {
        return context;
    }

    public void showScButton() {
        if (scButton.getVisibility() != View.VISIBLE) {
            scButton.setVisibility(View.VISIBLE);
        }
    }

    public void hideScButton() {
        if (scButton.getVisibility() != View.GONE) {
            scButton.setVisibility(View.GONE);
        }
    }

    public void showNameTV() {
        if (nameTV.getVisibility() != View.VISIBLE) {
            nameTV.setVisibility(View.VISIBLE);
        }
    }

    public void hideNameTV() {
        if (nameTV.getVisibility() != View.GONE) {
            nameTV.setVisibility(View.GONE);
        }
    }

    public void showOrHideControl() {
        if (controlView.getVisibility() != View.VISIBLE) {
            showControl();
        } else {
            hideControl();
        }
    }

    AnimatorSet mSet;

    public void hideControl() {
        mSet = new AnimatorSet();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(controlView, View.ALPHA,
                controlView.getAlpha(), 0);
        mSet.playTogether(animator1);
        mSet.setDuration(250);
        mSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                controlView.setVisibility(View.GONE);
            }
        });
        mSet.start();
//        mSet = new AnimatorSet();
//        ObjectAnimator animator1 = ObjectAnimator.ofFloat(topControlView, View.TRANSLATION_Y,
//                topControlView.getTranslationY(), -topControlView.getMeasuredHeight());
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(bottomControlView, View.TRANSLATION_Y,
//                bottomControlView.getTranslationY(), bottomControlView.getMeasuredHeight() + mGestureControl.dip2px(50));
//        mSet.playTogether(animator1, animator2);
//        mSet.setDuration(250);
//        mSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                topControlView.setVisibility(View.GONE);
//            }
//        });

//        mSet.start();
    }

    public void showControl() {
        if (controlView.getVisibility() == View.VISIBLE)
            return;
        mSet = new AnimatorSet();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(controlView, View.ALPHA,
                controlView.getAlpha(), 1);
        mSet.playTogether(animator1);
        mSet.setDuration(250);
        mSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationEnd(animation);
                controlView.setVisibility(View.VISIBLE);
            }
        });
        mSet.start();

//        mSet = new AnimatorSet();
//        ObjectAnimator animator1 = ObjectAnimator.ofFloat(topControlView, View.TRANSLATION_Y,
//                topControlView.getTranslationY(), 0);
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(bottomControlView, View.TRANSLATION_Y,
//                bottomControlView.getTranslationY(), 0);
//        mSet.playTogether(animator1, animator2);
//        mSet.setDuration(250);
//        mSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//                topControlView.setVisibility(View.VISIBLE);
//            }
//        });
//        mSet.start();
    }

    public void showPayMentRoot() {
        if (paymentRoot.getVisibility() != View.VISIBLE)
            paymentRoot.setVisibility(View.VISIBLE);
    }

    public void hidePayMentRoot() {
        if (paymentRoot.getVisibility() != View.GONE)
            paymentRoot.setVisibility(View.GONE);
    }

    public void setPrice(String price) {
        priceTV.setText(price);
    }

    public void seekStart() {
        showLoading();
    }

    public void onSeekComplete() {
        hideLoading();
    }

    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener l) {
        mSeekBar.setOnSeekBarChangeListener(l);
    }

    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    public void setCollection(boolean is) {
        if (is) {
            scButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_video_sc_true));
        } else {
            scButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_video_sc_false));
        }
    }

    public void setFabulous(boolean is, int love) {
        zTV.setText(love + "");
        if (is) {
            zButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_video_group_z_true));
            zTV.setTextColor(0xffd76dbb);
        } else {
            zButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_video_group_z_false));
            zTV.setTextColor(0xffffffff);
        }
    }

    public void start() {
        playerIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.libs_icon_pause));
    }

    public void pause() {
        playerIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.libs_icon_play));
    }

    public void prepareStart() {
        start();
        showLoading();
    }


    public void prepareCompletion() {
        hideLoading();
    }

    public void onCompletion() {
        hideLoading();
        pause();
        setTotalTime(0);
        setCurrentTime(0, 0);
    }

    public void onError() {
        hideLoading();
        pause();
        setTotalTime(0);
        setCurrentTime(0, 0);
        ToastUtils.show("播放失败");
    }

    public void setName(String name) {
        nameTV.setText(name);
    }

    public void setCurrentTime(long totalTime, long currentTime) {
        if (currentTime == this.currentTime)
            return;
        this.currentTime = currentTime;
        currentTimeTV.setText(formatTime(totalTime, currentTime));
        mSeekBar.setProgress((int) (currentTime / 1000));

        mGestureControl.setCurrentTime(currentTime);
    }

    public void setTotalTime(long totalTime) {
        if (totalTime == this.totalTime)
            return;
        this.totalTime = totalTime;
        mSeekBar.setMax((int) (totalTime / 1000));
        totalTimeTV.setText(formatTime(totalTime, totalTime));

        mGestureControl.setMaxTime(totalTime);
    }

    private String formatTime(long totalTime, long formatTime) {
        String text;
        if (totalTime >= 3600000) {
            text = String.format("%02d:%02d:%02d", formatTime / 1000 / 60 / 60, formatTime / 1000 / 60 % 60, formatTime / 1000 % 60);
        } else {
            text = String.format("%02d:%02d", formatTime / 1000 / 60, formatTime / 1000 % 60);
        }
        return text;
    }
}
