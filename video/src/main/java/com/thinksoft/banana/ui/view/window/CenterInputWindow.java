package com.thinksoft.banana.ui.view.window;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.TextView;

import com.thinksoft.banana.R;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author txf
 * @create 2019/2/20
 * @中心输入窗口
 */
public class CenterInputWindow extends BasePopupWindow implements View.OnClickListener {
    TextView titleTV, cancelButton, confirmButton;
    EditText editText;
    View backButton;

    protected OnAppListener.OnWindowListener l;

    protected int tag;//用于区分不同中心弹窗回调
    protected Object o;

    public CenterInputWindow(Context context) {
        super(context);
        init();
    }

    public CenterInputWindow(Context context, boolean delayInit) {
        super(context, delayInit);
        init();
    }

    public CenterInputWindow(Context context, int width, int height) {
        super(context, width, height);
        init();
    }

    private void init() {
        initViews();
        initListener();
    }

    private void initListener() {
        cancelButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    private void initViews() {
        backButton = findViewById(R.id.backButton);

        titleTV = findViewById(R.id.titleTV);
        editText = findViewById(R.id.editText);
        cancelButton = findViewById(R.id.cancelButton);
        confirmButton = findViewById(R.id.confirmButton);
    }

    public void setTitleString(String text) {
        titleTV.setText(text);
    }

    public void setCancelButtonString(String text) {
        cancelButton.setText(text);
    }

    public void setConfirmButtonString(String text) {
        confirmButton.setText(text);
    }

    public void setEditTextHintString(String text) {
        editText.setHint(text);
    }

    public void setEditTextString(String text) {
        editText.setText(text);
    }

    public void setAutoShowInputMethod(boolean autoShow) {
        setAutoShowInputMethod(editText, autoShow);
    }

    protected int dip2px(float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void setOnBackListener(OnAppListener.OnWindowListener l, int tag) {
        setOnBackListener(l, tag, null);
    }

    public void setOnBackListener(OnAppListener.OnWindowListener l, int tag, Object o) {
        this.tag = tag;
        this.l = l;
        this.o = o;
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.window_centerinput);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButton:
                dismiss();
                break;
            case R.id.cancelButton:
                dismiss();
                break;
            case R.id.confirmButton:
                if (!StringTools.isNull(editText.getText().toString())) {
                    l.onInteractionWindow(0, tag, BundleUtils.putString(editText.getText().toString()));
                }
                dismiss();
                break;
        }
    }

    Animation alphaAnimation;

    @Override
    protected Animation onCreateShowAnimation() {
        alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(250);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        return alphaAnimation;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(250);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        return alphaAnimation;
    }

    public static class Builder {
        String titleString;
        String cancelButtonString;
        String confirmButtonString;
        String editTextHint;
        String editTextString;

        public Builder setTitleString(String titleString) {
            this.titleString = titleString;
            return this;
        }

        public Builder setCancelButtonString(String cancelButtonString) {
            this.cancelButtonString = cancelButtonString;
            return this;
        }

        public Builder setConfirmButtonString(String confirmButtonString) {
            this.confirmButtonString = confirmButtonString;
            return this;
        }

        public Builder setEditTextHint(String editTextHint) {
            this.editTextHint = editTextHint;
            return this;
        }

        public Builder setEditTextString(String editTextString) {
            this.editTextString = editTextString;
            return this;
        }

        public CenterInputWindow onCreate(Context context) {
            CenterInputWindow window = new CenterInputWindow(context);
            window.setTitleString(titleString);
            window.setCancelButtonString(cancelButtonString);
            window.setConfirmButtonString(confirmButtonString);
            window.setEditTextHintString(editTextHint);
            window.setEditTextString(editTextString);
            return window;
        }

    }

}