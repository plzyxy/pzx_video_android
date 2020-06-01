package com.thinksoft.banana.ui.view.navigation;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.txf.ui_mvplibrary.ui.view.BaseViewGroup;
import com.umeng.commonsdk.debug.I;

/**
 * @author txf
 * @create 2019/2/16
 */
public class HomeNavigation extends BaseViewGroup {
    TextView homeTextView, videoTextView, typeTextView, circleTextView, myTextView;
    View lineView;

    public HomeNavigation(Context context) {
        super(context);
    }

    public HomeNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreate(Context context) {
        inflate(context, R.layout.view_homenavigation, this);

        lineView = getViewById(R.id.lineView);
        homeTextView = getViewById(R.id.homeTextView);
        videoTextView = getViewById(R.id.videoTextView);
        typeTextView = getViewById(R.id.typeTextView);
        circleTextView = getViewById(R.id.circleTextView);
        myTextView = getViewById(R.id.myTextView);

        setOnClick(R.id.homeButton, R.id.videoButton, R.id.typeButton, R.id.circleButton, R.id.myButton);
    }

    public void setDefSel(@IdRes int id) {
        getViewById(id).performClick();
    }

    public void showLineView() {
        lineView.setBackgroundColor(0xffE6E6E6);
    }
    public void hideLineView() {
        lineView.setBackgroundColor(0x00000000);
    }
    public View getLineView() {
        return lineView;
    }

    View selView;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        //防止重复点击
        if (v.isSelected())
            return;
        v.setSelected(true);
        if (selView != null)
            resetLast(selView);
        selView = v;
        switch (v.getId()) {

            case R.id.homeButton:
                homeTextView.setTextColor(0xffDF6DB7);
                homeTextView.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        ContextCompat.getDrawable(getContext(), R.drawable.icon_home_group_ture),
                        null,
                        null);
                getListener().onInteractionView(Constant.ACTION_HOME_NAVIGATION_TAB1, null);
                break;

            case R.id.videoButton:
                videoTextView.setTextColor(0xffDF6DB7);
                videoTextView.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        ContextCompat.getDrawable(getContext(), R.drawable.icon_video_group_true),
                        null,
                        null);
                getListener().onInteractionView(Constant.ACTION_HOME_NAVIGATION_TAB3, null);
                break;

            case R.id.typeButton:
                typeTextView.setTextColor(0xffDF6DB7);
                typeTextView.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        ContextCompat.getDrawable(getContext(), R.drawable.icon_type_true),
                        null,
                        null);
                getListener().onInteractionView(Constant.ACTION_HOME_NAVIGATION_TAB4, null);
                break;
            case R.id.circleButton:
                circleTextView.setTextColor(0xffDF6DB7);
                circleTextView.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        ContextCompat.getDrawable(getContext(), R.drawable.icon_circle_true),
                        null,
                        null);
                getListener().onInteractionView(Constant.ACTION_HOME_NAVIGATION_TAB5, null);
                break;
            case R.id.myButton:
                myTextView.setTextColor(0xffDF6DB7);
                myTextView.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        ContextCompat.getDrawable(getContext(), R.drawable.icon_my_true),
                        null,
                        null);
                getListener().onInteractionView(Constant.ACTION_HOME_NAVIGATION_TAB2, null);
                break;
        }
    }

    private void resetLast(View v) {
        v.setSelected(false);
        switch (v.getId()) {
            case R.id.homeButton:
                homeTextView.setTextColor(0xffcacaca);
                homeTextView.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        ContextCompat.getDrawable(getContext(), R.drawable.icon_home_group_false),
                        null,
                        null);
                break;
            case R.id.videoButton:
                videoTextView.setTextColor(0xffcacaca);
                videoTextView.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        ContextCompat.getDrawable(getContext(), R.drawable.icon_video_group_false),
                        null,
                        null);
                break;

            case R.id.typeButton:
                typeTextView.setTextColor(0xffcacaca);
                typeTextView.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        ContextCompat.getDrawable(getContext(), R.drawable.icon_type_false),
                        null,
                        null);
                break;
            case R.id.circleButton:
                circleTextView.setTextColor(0xffcacaca);
                circleTextView.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        ContextCompat.getDrawable(getContext(), R.drawable.icon_circle_false),
                        null,
                        null);
                break;
            case R.id.myButton:
                myTextView.setTextColor(0xffcacaca);
                myTextView.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        ContextCompat.getDrawable(getContext(), R.drawable.icon_my_false),
                        null,
                        null);
                break;
        }

    }
}
