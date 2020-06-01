package com.thinksoft.banana.ui.view.navigation;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.other_toolslibrary.utils.InputMethodUtils;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.interfaces.INavigationBar;
import com.txf.ui_mvplibrary.ui.view.BaseViewGroup;
import com.txf.ui_mvplibrary.utils.BundleUtils;


/**
 * @author txf
 * @create 2019/2/21 0021
 * @
 */
public class PlayerNavigationBar
        extends BaseViewGroup
        implements INavigationBar {
    EditText mEditText;
    View sendButton;

    public PlayerNavigationBar(Context context) {
        super(context);
    }

    public PlayerNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreate(Context context) {
        inflate(context, R.layout.view_player_navigationbar, this);
        initView();
    }

    private void initView() {
        mEditText = getViewById(R.id.EditText);
        sendButton = getViewById(R.id.sendButton);
        sendButton.setVisibility(GONE);
        InputMethodUtils.observerKeyboardVisibleChange((Activity) getContext(), new InputMethodUtils.OnKeyboardStateChangeListener() {
            @Override
            public void onKeyboardChange(int keyboardHeight, boolean isVisible) {
                if (!isVisible) {
                    mEditText.setText("");
                    mEditText.clearFocus();
                    sendButton.setVisibility(GONE);
                } else {
                    sendButton.setVisibility(VISIBLE);
                }
            }
        });
        setOnClick(R.id.sendButton);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sendButton:
                if (StringTools.isNull(mEditText.getText().toString())) {
                    ToastUtils.show(getString(R.string.请输入要发送的评论));
                    return;
                }
                getListener().onInteractionView(Constant.ACTION_SEND_TEXT, BundleUtils.putString(mEditText.getText().toString()));
                break;
        }
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public LayoutParams getViewLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void sendSuccess() {
        mEditText.setText("");
    }

    public void sendError() {

    }
}
