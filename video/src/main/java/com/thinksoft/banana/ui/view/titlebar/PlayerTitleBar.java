package com.thinksoft.banana.ui.view.titlebar;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.play.PlayerBean;
import com.thinksoft.banana.ui.view.player.PlayerControlView;
import com.txf.other_playerlibrary.ui.view.PlayerView;
import com.txf.ui_mvplibrary.interfaces.ITitleBar;
import com.txf.ui_mvplibrary.ui.view.BaseViewGroup;

/**
 * @author txf
 * @create 2019/2/21 0021
 * @
 */
public class PlayerTitleBar
        extends BaseViewGroup
        implements ITitleBar, PlayerView.OnSurfaceHolderListener {
    public PlayerView mPlayerView;
    public PlayerControlView mPlayerControlView;
    public PlayerBean mPlayerBean;

    public PlayerTitleBar(Context context) {
        super(context);
    }

    public PlayerTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreate(Context context) {
        inflate(context, R.layout.view_player_titlebar, this);
        initView();
    }

    private void initView() {
        mPlayerView = getViewById(R.id.PlayerView);
        mPlayerView.setPlayerControlView(mPlayerControlView = new PlayerControlView(getContext()));
        mPlayerView.setOnSurfaceHolderListener(this);
        isSurfaceCreated = false;
    }


    @Override
    public void setTitleText(CharSequence text) {

    }

    public void setCollection(boolean is) {
        if (mPlayerControlView != null)
            mPlayerControlView.setCollection(is);
    }

    public void setData(PlayerBean bean) {
        if (bean == null || bean.getVideo() == null)
            return;
        mPlayerBean = bean;
        setCollection(mPlayerBean.getCollcetion() == 1);
        if (isSurfaceCreated) {
            surfaceCreated();
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

    boolean isSurfaceCreated;
    @Override
    public void surfaceCreated() {
        isSurfaceCreated = true;
        if (mPlayerBean != null) {
            mPlayerControlView.setPlayerBean(mPlayerBean);
            mPlayerControlView.start();
        }
    }
}

