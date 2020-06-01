package com.txf.ui_mvplibrary.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.txf.ui_mvplibrary.R;

/**
 * @author txf
 * @create 2019/2/20 0020
 * @
 */
public abstract class BaseWebActivity extends BaseActivity {
    protected View backButton;
    protected ImageView iconView;
    protected TextView titleTV;
    protected WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.libs_activity_commonweb;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initWebView();
        titleTV.setText(buildTitleString());
        mWebView.loadUrl(buildUrlString());
    }

    protected void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        //设置默认编码
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                页面请求完成
                hideLoading();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                页面开始加载
                showLoading();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
//                访问错误时回调
                hideLoading();
            }
        });
    }

    protected abstract String buildUrlString();

    protected abstract String buildTitleString();


    private void initListener() {
        setOnClick(R.id.backButton);
    }

    private void initView() {
        backButton = findViewById(R.id.backButton);
        iconView = findViewById(R.id.iconView);
        titleTV = findViewById(R.id.titleTV);
        mWebView = findViewById(R.id.mWebView);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.backButton) {
            finish();
        }
    }
}
