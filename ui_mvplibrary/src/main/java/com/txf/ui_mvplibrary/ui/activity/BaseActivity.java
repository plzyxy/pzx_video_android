package com.txf.ui_mvplibrary.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.txf.ui_mvplibrary.R;
import com.txf.ui_mvplibrary.interfaces.ExtrListener;
import com.txf.ui_mvplibrary.interfaces.ILoadingView;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.view.deft.DefaultLoadingView;

import java.util.List;


/**
 * @author txf
 * @create 2019/1/29 0029
 * 已实现接口:
 * {@link OnAppListener.OnViewListener}
 * <p>
 * 1.提供 AlertDialog显示
 * {{@link Builder}
 * AlertDialog按钮点击默认回调{@link #onInteractionDialog(DialogInterface, int, int, Bundle)}
 * <p>
 * 2.提供默认loadingView, 显示{@link #showLoading()} 隐藏 {@link #hideLoading()}}
 * 可通过重写 {@link #buildLoadingView()}改变默认loadingView
 * <p>
 * 3.Fragment控制
 * 显示:{@link #showFragment(int, Fragment, String)}
 * 4.系统UI控制
 * 隐藏导航栏{@link #hideNavigationBar()}
 * 状态栏透明{@link #statusBarTransparent()}}
 */
public abstract class BaseActivity extends AppCompatActivity
        implements View.OnClickListener, OnAppListener.OnViewListener, ExtrListener {
    private AlertDialog alertDialog;
    private ILoadingView mILoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }

    /**
     * 重写 setContentView() 添加loading层
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mILoadingView = buildLoadingView();
        if (mILoadingView == null) {
            super.setContentView(layoutResID);
        } else {
            mILoadingView.getView().setVisibility(View.GONE);
            View view = LayoutInflater.from(this).inflate(R.layout.libs_activity_base, null);
            FrameLayout base_activity_content = (FrameLayout) view.findViewById(R.id.base_activity_content);
            View childview = LayoutInflater.from(this).inflate(layoutResID, null);
            base_activity_content.addView(childview, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            base_activity_content.addView(mILoadingView.getView(), mILoadingView.getViewLayoutParams());
            super.setContentView(view);
        }
    }

    public void setOnClick(int... ids) {
        for (int id : ids)
            findViewById(id).setOnClickListener(this);
    }

    protected boolean showFragment(@IdRes int containerViewId, Fragment fragment, @Nullable String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        boolean isShow;
        List<Fragment> frags = manager.getFragments();
        for (Fragment frag : frags) {
            if (frag == null)
                break;
            transaction.hide(frag);
        }
        if (manager.findFragmentByTag(tag) == null) {
            transaction.add(containerViewId, fragment, tag);
            isShow = false;
        } else {
            transaction.show(manager.findFragmentByTag(tag));
            isShow = true;
        }
        transaction.commit();
        return isShow;
    }

    /**
     * 跳转到外部浏览器
     */
    protected void startClient(String url) {
        if (url == null || url.length() == 0)
            return;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }

    protected ILoadingView buildLoadingView() {
        return new DefaultLoadingView(this);
    }

    protected abstract int getLayoutId();

    protected Context getContext() {
        return this;
    }

    protected void showLoading() {
        mILoadingView.show();
    }

    protected void hideLoading() {
        mILoadingView.hide();
    }

    @Override
    public void showILoading() {
        showLoading();
    }

    @Override
    public void hideILoading() {
        hideLoading();
    }

    @Override
    public void logInvalid() {

    }

    protected int dip2px(float dipValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    protected void dismissDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    public void statusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            int color = Color.TRANSPARENT;
            if (option != decorView.getSystemUiVisibility()) {
                decorView.setSystemUiVisibility(option);
            }
            if (color != getWindow().getStatusBarColor()) {
                getWindow().setStatusBarColor(color);
            }
        }
    }

    protected void hideNavigationBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * @param which DialogInterface.BUTTON_NEUTRAL
     *              DialogInterface.BUTTON_NEGATIVE
     *              DialogInterface.BUTTON_POSITIVE
     */
    protected void onInteractionDialog(DialogInterface dialog, int which, int with, Bundle ext) {
        dismissDialog();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onInteractionView(int action, Bundle bundle) {

    }

    public class DialogListener implements DialogInterface.OnClickListener {
        Bundle ext;
        int with;

        public DialogListener(Bundle ext, int with) {
            this.ext = ext;
            this.with = with;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            onInteractionDialog(dialog, which, with, ext);
        }
    }

    public class Builder {
        private int with;
        private String title;
        private String content;
        private String button1;
        private String button2;
        private String button3;
        private Bundle ext;
        private boolean cancelable = true;
        private DialogListener l;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setButton1(String button1) {
            this.button1 = button1;
            return this;
        }

        public Builder setButton2(String button2) {
            this.button2 = button2;
            return this;
        }

        public Builder setButton3(String button3) {
            this.button3 = button3;
            return this;
        }

        public Builder setExt(Bundle ext) {
            this.ext = ext;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setWith(int with) {
            this.with = with;
            return this;
        }

        public Builder setDialogListener(DialogListener l) {
            this.l = l;
            return this;
        }

        public AlertDialog show() {
            dismissDialog();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(title);
            builder.setMessage(content);
            if (button1 != null) {
                builder.setNeutralButton(button1, l == null ? new DialogListener(ext, with) : l);
            }
            if (button2 != null) {
                builder.setNegativeButton(button2, l == null ? new DialogListener(ext, with) : l);
            }
            if (button3 != null) {
                builder.setPositiveButton(button3, l == null ? new DialogListener(ext, with) : l);
            }
            builder.setCancelable(cancelable);
            alertDialog = builder.create();
            alertDialog.show();
            return alertDialog;
        }
    }
}
