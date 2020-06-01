package com.thinksoft.banana.ui.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.ui_mvplibrary.ui.view.BaseViewGroup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.thinksoft.banana.app.Constant.TYPE_CODE;
import static com.thinksoft.banana.app.Constant.TYPE_PASSWORD;
import static com.thinksoft.banana.app.Constant.TYPE_PHONE;

/**
 * @author txf
 * @create 2019/2/15 0015
 * @
 */
public class InputView extends BaseViewGroup {
    EditText editText;
    ImageView iconView;
    TextView textView;
    View lineView;
    public int inputType;

    public InputView(Context context) {
        super(context);
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreate(Context context) {
        inflate(context, R.layout.view_input, this);
        editText = getViewById(R.id.editText);
        textView = getViewById(R.id.textView);
        iconView = getViewById(R.id.iconView);
        lineView = getViewById(R.id.lineView);
        setEditTextSingleLine(editText);
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
        iconView.setVisibility(VISIBLE);
        textView.setVisibility(VISIBLE);
        switch (inputType) {
            case TYPE_PHONE:
                textView.setVisibility(GONE);
                editText.setHint(R.string.手机号码);
                iconView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_phone));
                break;
            case TYPE_PASSWORD:
                textView.setVisibility(GONE);
                editText.setHint(R.string.输入密码);
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                iconView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_password));
                break;
            case TYPE_CODE:
                iconView.setVisibility(GONE);
                textView.setVisibility(VISIBLE);
                textView.setOnClickListener(this);
                editText.setHint(R.string.输入验证码);
                break;
            case Constant.TYPE_INVITE:
                textView.setVisibility(GONE);
                editText.setHint(R.string.邀请码);
                iconView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_invite));
                break;

        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.textView:
                getListener().onInteractionView(Constant.ACTION_GET_CODE, null);
                break;
        }
    }
    public void startCode() {
        textView.setClickable(false);
        textView.setText("60s");
        startCountTimer(60000, 1000, new TimerListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                int remainTime = (int) (millisUntilFinished / 1000L) + 1;
                textView.setText(remainTime + "s");
            }

            @Override
            public void onFinish() {
                stopCode();
            }
        });
    }

    public void stopCode() {
        stopCountTimer();
        textView.setClickable(true);
        textView.setText("获取验证码");
    }

    public void setTextViewString(CharSequence text) {
        if (textView.getVisibility() == VISIBLE)
            textView.setText(text);
    }

    public View getLineView() {
        return lineView;
    }

    public TextView getTextView() {
        return textView;
    }

    public String getInputString() {
        return editText.getText().toString();
    }

    public EditText getEditText() {
        return editText;
    }

    public ImageView getIconView() {
        return iconView;
    }

    public boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }

    public boolean isMobile(String str) {
//        Pattern p = null;
//        Matcher m = null;
//        boolean b = false;
//        String s2 = "^[1](([3][0-9])|([4][5,7,9])|([5][^4,6,9])|([6][6])|([7][3,5,6,7,8])|([8][0-9])|([9][8,9]))[0-9]{8}$";// 验证手机号
//        if (!StringTools.isNull(str)) {
//            p = Pattern.compile(s2);
//            m = p.matcher(str);
//            b = m.matches();
//        }
//        return b;

        return true;
    }
}
