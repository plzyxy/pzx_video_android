package com.txf.ui_mvplibrary.ui.activity;

import android.content.Context;
import android.content.Intent;
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
public class DeftWebActivity extends BaseWebActivity {

    public static Intent getIntent(Context context, String url, String title) {
        Intent i = new Intent(context, DeftWebActivity.class);
        i.putExtra("url",url);
        i.putExtra("title",title);
        return i;
    }

    @Override
    protected String buildUrlString() {
        return getIntent().getStringExtra("url");
    }

    @Override
    protected String buildTitleString() {
        return getIntent().getStringExtra("title");
    }
}
