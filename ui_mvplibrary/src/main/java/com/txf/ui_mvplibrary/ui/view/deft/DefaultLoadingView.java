package com.txf.ui_mvplibrary.ui.view.deft;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.txf.ui_mvplibrary.R;
import com.txf.ui_mvplibrary.interfaces.ILoadingView;
import com.txf.ui_mvplibrary.ui.view.BaseViewGroup;

import static com.txf.ui_mvplibrary.interfaces.OnAppListener.OnViewListener.ACTION_CLICK_FINISH_LOADING;

/**
 * @author txf
 * @create 2019/2/12 0012
 * @
 */
public class DefaultLoadingView extends BaseViewGroup implements ILoadingView {
    public DefaultLoadingView(Context context) {
        super(context);
    }

    public DefaultLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreate(Context context) {
        View.inflate(context, R.layout.libs_view_default_loading, this);
        interceptClick(true);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public FrameLayout.LayoutParams getViewLayoutParams() {
        return new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void interceptClick(boolean off) {
        if (off) {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    hide();
                    if (getListener() != null)
                        getListener().onInteractionView(ACTION_CLICK_FINISH_LOADING, null);
                }
            });
        } else {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    ObjectAnimator animator;

    @Override
    public void show() {
        if (getVisibility() == VISIBLE)
            return;
        cancel();
        animator = ObjectAnimator.ofFloat(this, View.ALPHA, 0f, 1f);
        animator.setDuration(400);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                setVisibility(VISIBLE);
            }
        });
        animator.start();
    }

    @Override
    public void hide() {
        if (getVisibility() == GONE)
            return;
        animator = ObjectAnimator.ofFloat(this, View.ALPHA, 1f, 0f);
        animator.setDuration(400);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                setVisibility(GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(GONE);
            }
        });
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancel();
    }

    private void cancel() {
        if (animator != null) {
            if (animator.isRunning())
                animator.cancel();
            animator = null;
        }
    }
}
