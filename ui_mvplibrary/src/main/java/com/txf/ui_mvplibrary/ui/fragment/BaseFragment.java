package com.txf.ui_mvplibrary.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.txf.ui_mvplibrary.R;
import com.txf.ui_mvplibrary.interfaces.ExtrListener;
import com.txf.ui_mvplibrary.interfaces.ILoadingView;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.view.deft.DefaultLoadingView;


/**
 * @author txf
 * @create 2019/2/1 0001
 * 1.提供对外接口 获取: {@link #getListener()} 设置: {@link #setListener(OnAppListener.OnFragmentListener)}} 或Activity实现接口 {@link OnAppListener.OnFragmentListener}
 * <p>
 * 2.提供 AlertDialog显示
 * {@link Builder}
 * AlertDialog按钮点击默认回调{@link #onInteractionDialog(DialogInterface, int, int, Bundle)}
 * <p>
 * 3.提供默认loadingView, 显示{@link #showLoading()} 隐藏 {@link #hideLoading()}}
 * 可通过重写 {@link #buildLoadingView()}改变默认loadingView
 */
public abstract class BaseFragment
        extends Fragment
        implements View.OnClickListener, ExtrListener {
    OnAppListener.OnFragmentListener l;
    private ILoadingView mILoadingView;
    private AlertDialog alertDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAppListener.OnFragmentListener)
            l = (OnAppListener.OnFragmentListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        mILoadingView = buildLoadingView();
        if (mILoadingView == null) {
            if (getLayoutId() == -1)
                view = container;
            else
                view = inflater.inflate(getLayoutId(), container, false);
        } else {
            mILoadingView.getView().setVisibility(View.GONE);
            view = inflater.inflate(R.layout.libs_fragment_base, container, false);
            FrameLayout base_activity_content = (FrameLayout) view.findViewById(R.id.base_activity_content);
            View childview = LayoutInflater.from(getContext()).inflate(getLayoutId(), null);
            base_activity_content.addView(childview, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            base_activity_content.addView(mILoadingView.getView(), mILoadingView.getViewLayoutParams());
        }
        return view;
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return getView().findViewById(id);
    }

    public void setOnClick(@IdRes int... ids) {
        for (int id : ids)
            findViewById(id).setOnClickListener(this);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public ILoadingView getILoadingView() {
        return mILoadingView;
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

    protected abstract int getLayoutId();

    protected void dismissDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    protected int dip2px(float dipValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    protected ILoadingView buildLoadingView() {
        return new DefaultLoadingView(getContext());
    }

    public void setListener(OnAppListener.OnFragmentListener l) {
        this.l = l;
    }

    public OnAppListener.OnFragmentListener getListener() {
        Bundle bundle = new Bundle();
        return l;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissDialog();
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


    public class DialogListener implements DialogInterface.OnClickListener {
        protected Bundle ext;
        protected int with;

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
