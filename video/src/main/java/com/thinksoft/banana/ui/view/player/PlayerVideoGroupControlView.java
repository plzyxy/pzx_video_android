package com.thinksoft.banana.ui.view.player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.orhanobut.logger.Logger;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.play.PlayerBean;
import com.thinksoft.banana.entity.bean.video.VideoGroupDataBean;
import com.thinksoft.banana.ui.manage.UIPlayerVerticalManage;
import com.thinksoft.banana.ui.manage.UIPlayerVideoGroupManage;
import com.txf.other_playerlibrary.interfaces.IExtraListener;
import com.txf.other_playerlibrary.interfaces.IMediaPlayer;
import com.txf.other_playerlibrary.interfaces.IPlayCallback;
import com.txf.other_playerlibrary.interfaces.IPlayerControlView;
import com.txf.other_playerlibrary.player.PlayerProvide;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.ui.view.banner.DelayedTask;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import org.greenrobot.eventbus.Subscribe;

import static com.txf.ui_mvplibrary.interfaces.OnAppListener.OnViewListener.ACTION_CLICK_FINISH_ACTIVITY;
import static tv.danmaku.ijk.media.player.IMediaPlayer.MEDIA_INFO_BUFFERING_END;
import static tv.danmaku.ijk.media.player.IMediaPlayer.MEDIA_INFO_BUFFERING_START;

/**
 * @author txf
 * @create 2019/2/26 0026
 * @竖屏播放控制(视频垂直播放列表)
 */
public class PlayerVideoGroupControlView
        extends PlayerGestureControlView
        implements IPlayerControlView, IPlayCallback, DelayedTask.OnDelayedTaskListener, SeekBar.OnSeekBarChangeListener {
    IMediaPlayer mIMediaPlayer;
    VideoGroupDataBean mPlayerBean;
    UIPlayerVideoGroupManage mUIManage;
    DelayedTask mDelayedTask;

    SurfaceHolder mSurfaceHolder;
    IExtraListener listener;
    boolean isStopCountTime;
    long mFreeTime;

    public PlayerVideoGroupControlView(Context context) {
        super(context);
    }

    public PlayerVideoGroupControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerVideoGroupControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreate(Context context) {
        super.onCreate(context);
        mUIManage = new UIPlayerVideoGroupManage(context, this);
        mUIManage.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.playerButton://播放
                start();
                break;
            case R.id.scButton://收藏
                getListener().onInteractionView(Constant.ACTION_COLLECTION, BundleUtils.putSerializable(mPlayerBean));
                break;
            case R.id.shareButton://分享
                getListener().onInteractionView(Constant.ACTION_SHARE, BundleUtils.putSerializable(mPlayerBean));
                break;
            case R.id.paymentButton:
                //支付
                getListener().onInteractionView(Constant.ACTION_PLAYER_PAYMENT, BundleUtils.putSerializable(mPlayerBean));
                break;
            case R.id.zButton:
                getListener().onInteractionView(Constant.ACTION_FABULOUS, BundleUtils.putSerializable(mPlayerBean));
                break;
        }
    }

    public void paySuccess() {
        mPlayerBean.setIs_free(0);
        setFreeTime(mPlayerBean);
        mUIManage.hidePayMentRoot();
        start();
    }

    public void setCollection(boolean is) {
        mUIManage.setCollection(is);
    }

    public void setFabulous(boolean is, int love) {
        mUIManage.setFabulous(is, love);
    }

    public void setPlayerBean(VideoGroupDataBean mPlayerBean) {
        this.mPlayerBean = mPlayerBean;
        mUIManage.setPrice(getString(R.string.价格) + mPlayerBean.getDiamond());
        setFreeTime(mPlayerBean);
        setCollection(mPlayerBean.getCollcetion() == 1);
        setFabulous(mPlayerBean.getIslove() == 1, mPlayerBean.getLove());
    }
    public VideoGroupDataBean getPlayerBean() {
        return mPlayerBean;
    }

    /**
     * 设置试看时长,
     */
    public void setFreeTime(VideoGroupDataBean mPlayerBean) {
        if (mPlayerBean.getBuyStatus() == 0 && mPlayerBean.getIs_free() == 1) {
            //收费
            mFreeTime = mPlayerBean.getFree_time() == 0 ? 6000 : mPlayerBean.getFree_time() * 1000;
        } else {
            //免费
            mFreeTime = -1;
        }
    }

    /**
     * 是否可播放
     */
    public boolean isPlay() {
        if (mFreeTime != -1 && mIMediaPlayer != null && mIMediaPlayer.getCurrentPosition() >= mFreeTime) {
            //试看结束 就不可播放
            return false;
        } else {
            return true;
        }
    }

    public void hiedeView() {
        PlayerProvide.getInstance().release();
        PlayerProvide.getInstance().releaseHolder(mSurfaceHolder);
        stopCountTime();
        mUIManage.onCompletion();
    }

    public void pause() {
        if (!isPlay()) {
            //试看结束 就不在执行播放块
            return;
        }
        switch (mIMediaPlayer == null ? -2 : mIMediaPlayer.getCurrentState()) {
            case IMediaPlayer.STATE_STARTED:
                mIMediaPlayer.pause();
                mUIManage.pause();
                break;
        }
    }

    public int getCurrentState() {
        return mIMediaPlayer == null ? -2 : mIMediaPlayer.getCurrentState();
    }

    public void start() {
        if (!isPlay()) {
            //试看结束 就不在执行播放块
            return;
        }
        switch (mIMediaPlayer == null ? -2 : mIMediaPlayer.getCurrentState()) {
            case IMediaPlayer.STATE_STARTED:
                mIMediaPlayer.pause();
                mUIManage.pause();
                break;
            case IMediaPlayer.STATE_PAUSED:
                mIMediaPlayer.start();
                mUIManage.start();
                break;
            case -2:
            case IMediaPlayer.STATE_UNDEF:
            case IMediaPlayer.STATE_IDLE:
            case IMediaPlayer.STATE_STOPED://停止
            case IMediaPlayer.STATE_PLAYBACK_COMPLETE://播放完成
            case IMediaPlayer.STATE_ERROR://播放错误
                if (mPlayerBean == null || mPlayerBean.getLink() == null) {
                    ToastUtils.show(getString(R.string.无效播放地址));
                    return;
                }
                mUIManage.setName(mPlayerBean.getTitle());
                PlayerProvide.getInstance().createPlayer();
                setIMediaPlayer(PlayerProvide.getInstance().getMediaPlayer());
                PlayerProvide.getInstance().prepareStart(mPlayerBean.getLink(), mSurfaceHolder, this);
                mUIManage.prepareStart();
                break;
        }
    }

    public void startCountTime() {
        if (mDelayedTask == null) {
            mDelayedTask = new DelayedTask(this);
        }
        isStopCountTime = false;

        if (isPlay()) {
            mUIManage.setTotalTime(mIMediaPlayer.getDuration());
            mUIManage.setCurrentTime(mIMediaPlayer.getDuration(), mIMediaPlayer.getCurrentPosition());
        } else {
            mUIManage.setTotalTime(mIMediaPlayer.getDuration());
            mUIManage.setCurrentTime(mIMediaPlayer.getDuration(), mFreeTime);
            mUIManage.showPayMentRoot();
            //暂停
            switch (mIMediaPlayer.getCurrentState()) {
                case IMediaPlayer.STATE_STARTED:
                    mIMediaPlayer.pause();
                    mUIManage.pause();
                    break;
            }
        }
        mDelayedTask.startForwardTimer(1000, 0);
    }

    public void stopCountTime() {
        if (mDelayedTask != null) {
            mDelayedTask.stopForwardTimer();
            isStopCountTime = true;
            mDelayedTask = null;
        }
    }

    @Override
    public void onHandleMessage(int msg) {
        if (!isStopCountTime)
            startCountTime();
    }

    //PlayerView  回调
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Logger.i("表面创建");
        mSurfaceHolder = holder;
//        if (mIMediaPlayer != null && mIMediaPlayer.getCurrentState() == IMediaPlayer.STATE_PAUSED) {
//            mIMediaPlayer.start();
//            mUIManage.start();
//            startCountTime();
//        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Logger.i("表面销毁");
        if (mIMediaPlayer != null) {
            PlayerProvide.getInstance().release();
            mIMediaPlayer = null;
        }
        stopCountTime();
        mUIManage.onCompletion();
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public LayoutParams getViewLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void setIMediaPlayer(IMediaPlayer iMediaPlayer) {
        mIMediaPlayer = iMediaPlayer;
    }

    @Override
    public IPlayCallback getIPlayCallback() {
        return this;
    }

    @Override
    public void setExtraListener(IExtraListener listener) {
        this.listener = listener;
    }

    @Override
    public void changeOrientation(int orientation) {
        if (!isHorizontalScreen())
            return;
        if (orientation == 90 || orientation == 270) {
            getListener().onInteractionView(Constant.ACTION_PLAYER_LANDSCAPE, null);
        }
    }

    boolean mHorizontalScreen;

    @Subscribe
    public void setHorizontalScreen(boolean horizontalScreen) {
        this.mHorizontalScreen = horizontalScreen;
        if (!horizontalScreen) {
            mUIManage.showControl();
        } else {

        }
    }

    public boolean isHorizontalScreen() {
        return mHorizontalScreen;
    }

    //播放器回调
    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        stopCountTime();
        mUIManage.onCompletion();
    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int var2, int var3) {
        stopCountTime();
        mUIManage.onError();
        return true;
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        iMediaPlayer.start();
        mUIManage.prepareCompletion();
        startCountTime();
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {
        if (listener != null)
            listener.onVideoSizeChanged(iMediaPlayer);
    }

    @Override
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int var2) {

    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int var2, int var3) {
        switch (var2) {
            case MEDIA_INFO_BUFFERING_START://开始缓冲
                mUIManage.showLoading();
                return true;
            case MEDIA_INFO_BUFFERING_END://缓冲结束
                mUIManage.hideLoading();
                return true;
            default:
                if (iMediaPlayer.isPlaying()) {
                    mUIManage.hideLoading();
                }
                return false;
        }
    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {
        iMediaPlayer.start();
        mUIManage.onSeekComplete();
        mUIManage.start();
        startCountTime();
    }

    @Override
    public void onImageCaptured(IMediaPlayer var1, String filePath, boolean isTrue) {

    }

    boolean isStartTrackingTouch;

    //seekBar 改变回调
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (isStartTrackingTouch) {
            long currentTime = progress * 1000l;
            mUIManage.setCurrentTime(mIMediaPlayer.getDuration(), currentTime);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (seekBar.getMax() > 0) {
            isStartTrackingTouch = true;
            stopCountTime();
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (isStartTrackingTouch) {
            long currentTime = seekBar.getProgress() * 1000l;
            if (mFreeTime != -1 && currentTime >= mFreeTime) {
                //超过试看时长
                currentTime = mFreeTime;
            }
            mIMediaPlayer.seekTo(currentTime);
            mUIManage.seekStart();
            isStartTrackingTouch = false;
        }
    }

    //手势控制
    @Override
    protected boolean singleTapConfirmed(MotionEvent e) {
        mUIManage.showOrHideControl();
        return true;
    }

    @Override
    protected boolean doubleTap(MotionEvent e) {
//        findViewById(R.id.playerButton).performClick();
        return true;
    }

    @Override
    protected void startSeek() {
        stopCountTime();
    }

    @Override
    protected void seekChanged(long time) {
        mUIManage.setCurrentTime(mIMediaPlayer.getDuration(), time);
    }

    @Override
    protected void stopSeek(long time) {
        if (mFreeTime != -1 && time >= mFreeTime) {
            //超过试看时长
            time = mFreeTime;
        }
        mIMediaPlayer.seekTo(time);
        mUIManage.seekStart();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopCountTime();
        mUIManage.showControl();
        mIMediaPlayer = null;
        super.onDetachedFromWindow();
    }
}