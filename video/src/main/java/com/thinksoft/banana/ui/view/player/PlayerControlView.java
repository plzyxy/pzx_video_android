package com.thinksoft.banana.ui.view.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.play.PlayerBean;
import com.thinksoft.banana.ui.manage.UIPlayerManage;
import com.txf.other_playerlibrary.interfaces.IExtraListener;
import com.txf.other_playerlibrary.interfaces.IMediaPlayer;
import com.txf.other_playerlibrary.interfaces.IPlayCallback;
import com.txf.other_playerlibrary.interfaces.IPlayerControlView;
import com.txf.other_playerlibrary.player.PlayerProvide;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.ui.view.banner.DelayedTask;

import org.greenrobot.eventbus.Subscribe;

import static com.txf.ui_mvplibrary.interfaces.OnAppListener.OnViewListener.ACTION_CLICK_FINISH_ACTIVITY;
import static tv.danmaku.ijk.media.player.IMediaPlayer.MEDIA_INFO_BUFFERING_END;
import static tv.danmaku.ijk.media.player.IMediaPlayer.MEDIA_INFO_BUFFERING_START;

/**
 * @author txf
 * @create 2019/2/26 0026
 * @
 */
public class PlayerControlView
        extends PlayerGestureControlView
        implements IPlayerControlView, IPlayCallback, DelayedTask.OnDelayedTaskListener, SeekBar.OnSeekBarChangeListener {
    IMediaPlayer mIMediaPlayer;
    PlayerBean mPlayerBean;
    UIPlayerManage mUIManage;
    DelayedTask mDelayedTask;

    SurfaceHolder mSurfaceHolder;
    IExtraListener listener;
    boolean isStopCountTime;
    long mFreeTime;
    boolean isLock;

    public PlayerControlView(Context context) {
        super(context);
    }

    public PlayerControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreate(Context context) {
        super.onCreate(context);
        mUIManage = new UIPlayerManage(context, this);
        mUIManage.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                if (mHorizontalScreen) {
                    getListener().onInteractionView(Constant.ACTION_PLAYER_PORTRAIT, null);
                } else {
                    getListener().onInteractionView(ACTION_CLICK_FINISH_ACTIVITY, null);
                }
                break;
            case R.id.playerButton://播放
                start();
                break;
            case R.id.scButton://收藏
                getListener().onInteractionView(Constant.ACTION_COLLECTION, null);
                break;
            case R.id.qpIconButton://全屏
                if (mHorizontalScreen) {
                    getListener().onInteractionView(Constant.ACTION_PLAYER_PORTRAIT, null);
                } else {
                    getListener().onInteractionView(Constant.ACTION_PLAYER_LANDSCAPE, null);
                }
                break;
            case R.id.lockButton:
                setLock(!isLock());
                mUIManage.setLock(isLock());
                break;
            case R.id.paymentButton:
                //支付
                getListener().onInteractionView(Constant.ACTION_PLAYER_PAYMENT, null);
                break;
        }
    }

    public void paySuccess() {
        mPlayerBean.getVideo().setIs_free(0);
        setFreeTime(mPlayerBean);
        mUIManage.hidePayMentRoot();
        start();
    }

    public void setCollection(boolean is) {
        mUIManage.setCollection(is);
    }

    public void setPlayerBean(PlayerBean mPlayerBean) {
        this.mPlayerBean = mPlayerBean;
        mUIManage.setPrice(getString(R.string.价格) + mPlayerBean.getVideo().getDiamond());
        setFreeTime(mPlayerBean);
    }

    /**
     * 设置试看时长,
     */
    public void setFreeTime(PlayerBean mPlayerBean) {
        if (mPlayerBean.getBuyStatus() == 0 && mPlayerBean.getVideo().getIs_free() == 1) {
            //收费
            mFreeTime = mPlayerBean.getVideo().getFree_time() == 0 ? 6000 : mPlayerBean.getVideo().getFree_time() * 1000;
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
                if (mPlayerBean == null || mPlayerBean.getVideo() == null) {
                    ToastUtils.show(getString(R.string.无效播放地址));
                    return;
                }
                mUIManage.setName(mPlayerBean.getVideo().getTitle());
                PlayerProvide.getInstance().createPlayer();
                setIMediaPlayer(PlayerProvide.getInstance().getMediaPlayer());
                PlayerProvide.getInstance().prepareStart(mPlayerBean.getVideo().getLink(), mSurfaceHolder, this);
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

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public boolean isLock() {
        return isLock;
    }

    //PlayerView  回调
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        if (mIMediaPlayer != null
                && mIMediaPlayer.getCurrentState() == IMediaPlayer.STATE_PAUSED) {
            mIMediaPlayer.start();
            mUIManage.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        switch (mIMediaPlayer == null ? -2 : mIMediaPlayer.getCurrentState()) {
            case IMediaPlayer.STATE_STARTED:
                mIMediaPlayer.pause();
                mUIManage.pause();
                break;
        }
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public RelativeLayout.LayoutParams getViewLayoutParams() {
        return new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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
        if (isLock() || !isHorizontalScreen())
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
            mUIManage.hideLock();
            mUIManage.hideNameTV();
            mUIManage.hideScButton();
        } else {
            mUIManage.showLock();
            mUIManage.showNameTV();
            mUIManage.showScButton();
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
        if (isHorizontalScreen())
            mUIManage.showOrHideControl();
        return false;
    }

    @Override
    protected boolean doubleTap(MotionEvent e) {
        findViewById(R.id.playerButton).performClick();
        if (isHorizontalScreen())
            mUIManage.showState();
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
        super.onDetachedFromWindow();
    }
}
