package com.thinksoft.banana.ui.view.player.tx;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.orhanobut.logger.Logger;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.music.MusicInfoDataBean;
import com.thinksoft.banana.entity.bean.play.PlayerBean;
import com.thinksoft.banana.ui.manage.UIPlayerCircleManage;
import com.thinksoft.banana.ui.manage.UIPlayerMusicManage;
import com.thinksoft.banana.ui.view.player.PlayerGestureControlView;
import com.txf.other_playerlibrary.interfaces.IExtraListener;
import com.txf.other_playerlibrary.interfaces.IMediaPlayer;
import com.txf.other_playerlibrary.interfaces.IPlayCallback;
import com.txf.other_playerlibrary.interfaces.IPlayerControlView;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.ui.view.banner.DelayedTask;

import org.greenrobot.eventbus.Subscribe;

import static com.tencent.rtmp.TXLiveConstants.PLAY_ERR_NET_DISCONNECT;
import static com.tencent.rtmp.TXLiveConstants.PLAY_EVT_PLAY_BEGIN;
import static com.tencent.rtmp.TXLiveConstants.PLAY_EVT_PLAY_END;
import static com.tencent.rtmp.TXLiveConstants.PLAY_EVT_PLAY_LOADING;
import static com.tencent.rtmp.TXLiveConstants.PLAY_EVT_PLAY_PROGRESS;
import static com.tencent.rtmp.TXLiveConstants.PLAY_EVT_VOD_LOADING_END;
import static com.txf.ui_mvplibrary.interfaces.OnAppListener.OnViewListener.ACTION_CLICK_FINISH_ACTIVITY;
import static tv.danmaku.ijk.media.player.IMediaPlayer.MEDIA_INFO_BUFFERING_END;
import static tv.danmaku.ijk.media.player.IMediaPlayer.MEDIA_INFO_BUFFERING_START;

/**
 * @author txf
 * @create 2019/3/23 0023
 * @音频播放控制
 */
public class MusicControlView extends PlayerGestureControlView
        implements SeekBar.OnSeekBarChangeListener, ITXVodPlayListener {
    UIPlayerMusicManage mUIManage;
    MusicInfoDataBean mMusicInfoDataBean;
    TXVodPlayer mVodPlayer;

    int state = STATE_0;//0未播放 1加载中 2播放中 3暂停 4播放完成 5播放失败

    public final static int STATE_0 = 0;
    public final static int STATE_1 = 1;
    public final static int STATE_2 = 2;
    public final static int STATE_3 = 3;
    public final static int STATE_4 = 4;
    public final static int STATE_5 = 5;

    public MusicControlView(Context context) {
        super(context);
    }

    public MusicControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MusicControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreate(Context context) {
        super.onCreate(context);
        mUIManage = new UIPlayerMusicManage(context, this);
        mUIManage.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.playerButton://播放
                start();
                break;
        }
    }

    public void setData(MusicInfoDataBean data, TXVodPlayer mTXVodPlayer) {
        mMusicInfoDataBean = data;
        if (mMusicInfoDataBean == null) {
            ToastUtils.show("无效播放地址,请稍候再试");
            return;
        }
        mVodPlayer = mTXVodPlayer;
        mVodPlayer.setVodListener(this);
        mUIManage.setBg_imgView(data.getImage());
        mUIManage.setName(data.getTitle());
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void start() {
        if (mMusicInfoDataBean == null) {
            ToastUtils.show("无效播放地址,请稍候再试");
            return;
        }
        switch (getState()) {
            case STATE_2://播放中
                mVodPlayer.pause();
                mUIManage.pause();
                setState(STATE_3);
                break;
            case STATE_3://暂停
                mVodPlayer.resume();
                mUIManage.start();
                setState(STATE_2);
                break;
            case STATE_0:
            case STATE_4:
            case STATE_5:
                mVodPlayer.startPlay(mMusicInfoDataBean.getFilepath());
                mUIManage.prepareStart();
                setState(STATE_1);
                break;
        }
    }


    boolean isStartTrackingTouch;

    //seekBar 改变回调
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (isStartTrackingTouch) {
            long currentTime = progress * 1000l;
            mUIManage.setCurrentTime(duration_time, currentTime);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (seekBar.getMax() > 0) {
            isStartTrackingTouch = true;
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (isStartTrackingTouch) {
            long currentTime = seekBar.getProgress() * 1000l;
            mVodPlayer.seek(currentTime / 1000);
            mUIManage.seekStart();
            isStartTrackingTouch = false;
        }
    }

    //手势控制
    @Override
    protected boolean singleTapConfirmed(MotionEvent e) {
        Logger.i("手势控制 单点");
        mUIManage.showOrHideControl();
        return true;
    }

    @Override
    protected boolean doubleTap(MotionEvent e) {
        findViewById(R.id.playerButton).performClick();
        return true;
    }

    @Override
    protected void startSeek() {
        isStartTrackingTouch = true;
    }

    @Override
    protected void seekChanged(long time) {
        if (isStartTrackingTouch) {
            mUIManage.setCurrentTime(duration_time, time);
        }
    }

    @Override
    protected void stopSeek(long time) {
        if (isStartTrackingTouch) {
            mVodPlayer.seek(time / 1000);
            mUIManage.seekStart();
            isStartTrackingTouch = false;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    // 播放进度, 单位是毫秒
    int progress_time;
    // 视频总长, 单位是毫秒
    int duration_time;

    @Override
    public void onPlayEvent(TXVodPlayer txVodPlayer, int event, Bundle param) {
        switch (event) {
            case PLAY_EVT_PLAY_PROGRESS://进度
                // 加载进度, 单位是毫秒
                int duration_ms = param.getInt(TXLiveConstants.EVT_PLAYABLE_DURATION_MS);

                // 视频总长, 单位是毫秒
                duration_time = param.getInt(TXLiveConstants.EVT_PLAY_DURATION_MS);
                // 播放进度, 单位是毫秒
                progress_time = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS);
                if (isStartTrackingTouch)
                    return;
                mUIManage.setTotalTime(duration_time);
                mUIManage.setCurrentTime(duration_time, progress_time);
                break;
            case PLAY_EVT_PLAY_BEGIN://开始播放
                setState(STATE_2);
                mUIManage.prepareCompletion();
                break;
            case PLAY_EVT_PLAY_LOADING://开始播放
                mUIManage.showLoading();
                break;
            case PLAY_EVT_VOD_LOADING_END://开始播放
                mUIManage.hideLoading();
                break;
            case PLAY_EVT_PLAY_END://视频播放结束
                mUIManage.onCompletion();
                setState(STATE_4);
                break;
            case PLAY_ERR_NET_DISCONNECT:
                mUIManage.onError();
                setState(STATE_5);
                break;
        }
    }

    @Override
    public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

    }
}
