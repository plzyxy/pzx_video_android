package com.thinksoft.banana.ui.view.titlebar;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.view.BaseViewGroup;
import com.txf.ui_mvplibrary.interfaces.ITitleBar;
import com.txf.ui_mvplibrary.utils.BundleUtils;

/**
 * @author txf
 * @create 2019/2/18 0018
 * @
 */
public class SearchTitleBar extends BaseViewGroup implements ITitleBar {
    EditText editText;

    public SearchTitleBar(Context context) {
        super(context);
    }

    public SearchTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreate(Context context) {
        inflate(context, R.layout.view_search_titlebar, this);
        editText = getViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getListener().onInteractionView(Constant.ACTION_HOME_INPUT_TEXT, BundleUtils.putString(s.toString()));
            }
        });
        setOnClick(R.id.backButton);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                getListener().onInteractionView(OnAppListener.OnViewListener.ACTION_CLICK_FINISH_ACTIVITY, null);
                break;
        }
    }

    @Override
    public LayoutParams getViewLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void setTitleText(CharSequence text) {

    }


}
