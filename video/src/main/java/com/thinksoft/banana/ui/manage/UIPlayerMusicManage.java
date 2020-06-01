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

import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.ui.view.player.PlayerGestureControlView;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.ui.view.BaseViewGroup;

/**
 * @author txf
 * @create 2019/2/26 0026
 * @
 */
public class UIPlayerMusicManage {
    Context context;
    TextView nameTV, currentTimeTV, totalTimeTV;
    AppCompatSeekBar mSeekBar;
    ImageView playerIcon;
    View loadingView, topControlView, bottomControlView;
    long totalTime;
    long currentTime;
    PlayerGestureControlView mGestureControl;
    SimpleDraweeView bg_imgView;

    public UIPlayerMusicManage(Context context, BaseViewGroup view) {
        this.context = context;
        mGestureControl = (PlayerGestureControlView) view;

        View.inflate(context, R.layout.view_player_music_control, view);
        topControlView = view.getViewById(R.id.topControlView);
        bottomControlView = view.getViewById(R.id.bottomControlView);
        bg_imgView = view.getViewById(R.id.bg_imgView);
        nameTV = view.getViewById(R.id.nameTV);
        playerIcon = view.getViewById(R.id.playerIcon);
        currentTimeTV = view.getViewById(R.id.currentTimeTV);
        totalTimeTV = view.getViewById(R.id.totalTimeTV);
        mSeekBar = view.getViewById(R.id.SeekBar);
        loadingView = view.getViewById(R.id.loadingView);
        view.setOnClick(R.id.playerButton);
    }

    private Context getContext() {
        return context;
    }

    public void setBg_imgView(String url) {
        if (StringTools.isNull(url)) {
            bg_imgView.setVisibility(View.GONE);
        } else {
            bg_imgView.setVisibility(View.VISIBLE);
            bg_imgView.setImageURI(url);
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
        if (topControlView.getVisibility() != View.VISIBLE) {
            showControl();
        } else {
            hideControl();
        }
    }

    AnimatorSet mSet;

    public void hideControl() {
        mSet = new AnimatorSet();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(topControlView, View.TRANSLATION_Y,
                topControlView.getTranslationY(), -topControlView.getMeasuredHeight());
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(bottomControlView, View.TRANSLATION_Y,
                bottomControlView.getTranslationY(), bottomControlView.getMeasuredHeight());
        mSet.playTogether(animator1, animator2);
        mSet.setDuration(250);

        mSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                topControlView.setVisibility(View.GONE);
            }
        });
        mSet.start();
    }

    public void showControl() {
        mSet = new AnimatorSet();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(topControlView, View.TRANSLATION_Y,
                topControlView.getTranslationY(), 0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(bottomControlView, View.TRANSLATION_Y, bottomControlView.getTranslationY(), 0);
        mSet.playTogether(animator1, animator2);
        mSet.setDuration(250);
        mSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                topControlView.setVisibility(View.VISIBLE);
            }
        });
        mSet.start();
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
