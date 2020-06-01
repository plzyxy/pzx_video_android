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
public class UIPlayerManage {
    Context context;
    TextView nameTV, scButton, currentTimeTV, totalTimeTV, priceTV;
    AppCompatSeekBar mSeekBar;
    ImageView qpIconIcon, playerIcon;
    View loadingView, qpIconButton, topControlView, bottomControlView, paymentRoot, lockIcon, lockButton, playerState;
    long totalTime;
    long currentTime;
    PlayerGestureControlView mGestureControl;

    public UIPlayerManage(Context context, BaseViewGroup view) {
        this.context = context;
        mGestureControl = (PlayerGestureControlView) view;

        View.inflate(context, R.layout.view_playercontrol, view);
        topControlView = view.getViewById(R.id.topControlView);
        bottomControlView = view.getViewById(R.id.bottomControlView);

        nameTV = view.getViewById(R.id.nameTV);
        scButton = view.getViewById(R.id.scButton);
        playerIcon = view.getViewById(R.id.playerIcon);
        currentTimeTV = view.getViewById(R.id.currentTimeTV);
        totalTimeTV = view.getViewById(R.id.totalTimeTV);
        mSeekBar = view.getViewById(R.id.SeekBar);
        qpIconButton = view.getViewById(R.id.qpIconButton);
        qpIconIcon = view.getViewById(R.id.qpIconIcon);
        loadingView = view.getViewById(R.id.loadingView);

        paymentRoot = view.getViewById(R.id.paymentRoot);
        priceTV = view.getViewById(R.id.priceTV);
        lockIcon = view.getViewById(R.id.lockIcon);
        lockButton = view.getViewById(R.id.lockButton);
        playerState = view.getViewById(R.id.playerState);

        hidePayMentRoot();
        playerState.setVisibility(View.GONE);
        lockButton.setVisibility(View.GONE);

        nameTV.setVisibility(View.GONE);
        scButton.setVisibility(View.GONE);

        view.setOnClick(R.id.backButton, R.id.scButton, R.id.playerButton, R.id.qpIconButton, R.id.paymentButton, R.id.lockButton);
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
        if (topControlView.getVisibility() != View.VISIBLE) {
            showControl();
            showLock();
        } else {
            hideControl();
            hideLock();
        }
    }

    public void setLock(boolean isLock) {
        if (isLock) {
            lockIcon.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_player_lock_true));
        } else {
            lockIcon.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_player_lock_false));
        }
    }


    public void showState() {
        AnimatorSet mSet = new AnimatorSet();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(playerState, View.SCALE_Y,
                0.5f, 2);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(playerState, View.SCALE_X,
                0.5f, 2);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(playerState, View.ALPHA,
                1, 0);
        mSet.playTogether(animator1, animator2, animator3);
        mSet.setDuration(400);
        mSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                playerState.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                playerState.setVisibility(View.GONE);
            }
        });
        mSet.start();
    }

    public void showLock() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(lockButton, View.TRANSLATION_X,
                lockButton.getTranslationX(), 0);
        animator1.setDuration(250);
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                lockButton.setVisibility(View.VISIBLE);
            }
        });
        animator1.start();
    }

    public void hideLock() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(lockButton, View.TRANSLATION_X,
                lockButton.getTranslationX(), -lockButton.getMeasuredWidth());
        animator1.setDuration(250);
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                lockButton.setVisibility(View.GONE);
            }
        });
        animator1.start();
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
            scButton.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.icon_play_sc_true), null, null, null);
        } else {
            scButton.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.icon_play_sc_false), null, null, null);
        }
    }

    public void start() {
        playerIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.libs_icon_pause));
        setPlayerState(false);
    }

    public void pause() {
        playerIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.libs_icon_play));
        setPlayerState(true);
    }

    public void prepareStart() {
        start();
        showLoading();
    }

    private void setPlayerState(boolean isPause) {
        if (isPause) {
            playerState.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.libs_icon_play));
        } else {
            playerState.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.libs_icon_pause));
        }
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
