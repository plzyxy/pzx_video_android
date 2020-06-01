package com.txf.ui_mvplibrary.ui.view;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.txf.ui_mvplibrary.interfaces.OnAppListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author txf
 * @create 2018/12/11 0011
 */
public abstract class BaseViewGroup extends RelativeLayout implements View.OnClickListener {
    OnAppListener.OnViewListener l;

    public OnAppListener.OnViewListener getListener() {
        return l;
    }

    public void setOnViewChangeListener(OnAppListener.OnViewListener l) {
        this.l = l;
    }

    public int dip2px(float dipValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public BaseViewGroup(Context context) {
        super(context);
        if (context instanceof OnAppListener.OnViewListener)
            this.l = (OnAppListener.OnViewListener) context;
        onCreate(context);
    }

    public BaseViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (context instanceof OnAppListener.OnViewListener)
            this.l = (OnAppListener.OnViewListener) context;
        onCreate(context);
    }

    public BaseViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (context instanceof OnAppListener.OnViewListener)
            this.l = (OnAppListener.OnViewListener) context;
        onCreate(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onCreate(context);
    }

    protected String getString(@StringRes int ids) {
        return getContext().getResources().getString(ids);
    }

    protected abstract void onCreate(Context context);

    public <E extends View> E getViewById(@IdRes int id) {
        return (E) super.findViewById(id);
    }

    public void setOnClick(int... ids) {
        for (int id : ids) {
            getViewById(id).setOnClickListener(this);
        }
    }

    public void setOnClick(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

    }

    CountDownTimer mTimer;//计时器
    TimerListener listener;
    public void startCountTimer(long millisInFuture, long countDownInterval, TimerListener listener) {
        this.listener = listener;
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (BaseViewGroup.this.listener != null)
                    BaseViewGroup.this.listener.onTick(millisUntilFinished);
            }
            @Override
            public void onFinish() {
                if (BaseViewGroup.this.listener != null)
                    BaseViewGroup.this.listener.onFinish();
            }
        };
        mTimer.start();
    }

    public void stopCountTimer() {
        this.listener = null;
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    protected interface TimerListener {
        void onTick(long millisUntilFinished);

        void onFinish();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopCountTimer();
    }


    /**
     * 设置输入框不能换行
     */
    protected void setEditTextSingleLine(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setLines(1);
            editText.setSingleLine(true);
        }
    }

    protected void setEditTextSZ(EditText... editTexts) {
        for (EditText editText : editTexts) {
            setEditTextSZ(editText);
        }
    }

    /**
     * 设置输入框不能输入空格 特殊字符等..
     */
    public void setEditTextSZ(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                        Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                String speChat = "[-_`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、{}？]";
                Pattern pattern = Pattern.compile(speChat);

                Matcher matcher = pattern.matcher(source.toString());
                Matcher m = p.matcher(source);

                if (matcher.find()) return "";
                if (source.equals(" ")) return "";
                if (m.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }
}
