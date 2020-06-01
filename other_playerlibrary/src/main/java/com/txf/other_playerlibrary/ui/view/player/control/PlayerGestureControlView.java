package com.txf.other_playerlibrary.ui.view.player.control;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.txf.other_playerlibrary.R;
import com.txf.other_playerlibrary.ui.view.player.base.BaseViewGroup;
import com.txf.other_playerlibrary.ui.view.player.progress.PlayProgressView;
import com.txf.other_playerlibrary.ui.view.player.progress.ProgressView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author txf
 * @create 2018/12/17 0017
 * @视屏播放 手势控制
 */
public class PlayerGestureControlView extends BaseViewGroup {
    private String TAG = getClass().getName();
    private ProgressView mProgressView;
    private PlayProgressView mPlayProgressView;
    GestureDetector detector;//手势检测器实例
    private AudioManager mAudioManager;
    private int mMaxVoice;//最大音量
    private int mCurrentVoice;//当前音量
    private int mVoiceProportion;
    private int mMaxBrightness;//最大亮度
    private int mCurrentBrightness;//当前亮度
    private int mBrightnessProportion;

    protected long mMaxTime;//视频总时长
    protected long mCurrentTime;//播放当前时间

    private int mDx;
    private float downX, downY;//手指按下时的坐标
    private float minMove = 40;//最小滑动距离
    private boolean leftORright, topORbottom;//当前滑动方向
    private boolean isRefreshProgress;//是否更新进度

    protected boolean mPauseOrStart;//暂停或播放
//    protected IMediaPlayer iMediaPlaye;

    public PlayerGestureControlView(Context context) {
        super(context);
    }

    public PlayerGestureControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerGestureControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreate() {
        init();
    }

    private void init() {
        //创建手势检测器
        detector = new GestureDetector(getContext(), new MyGestureListener());
        //添加控制 音量 亮度UI
        addVolumeView();
        //添加控制 进度UI
        addPlayProgressView();
    }

    /**
     * 请在视频开始播放时调用该方法
     * 否则控制层会失效
     */
    public void startPlayer() {
//        this.iMediaPlaye = iMediaPlaye;
//        setMaxTime(iMediaPlaye.getDuration());
//        setCurrentTime(iMediaPlaye.getCurrentPosition());
//        mPauseOrStart = true;
    }

    /**
     * 请在视频播放错误时调用该方法
     */
    public void errorPlayer() {
//        this.iMediaPlaye = null;
        setMaxTime(0);
        setCurrentTime(0);
        mPauseOrStart = false;
    }

    /**
     * 请在视频播放完成时调用该方法
     */
    public void completionPlayer() {
        mPauseOrStart = false;
        setCurrentTime(0);
    }

    public int dip2px(float dipValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private void addPlayProgressView() {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(dip2px(100), dip2px(80));
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mPlayProgressView = new PlayProgressView(getContext());
        hidePlayProgressView();
        addView(mPlayProgressView, lp);
    }

    private void addVolumeView() {
        mCurrentVoice = -1;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(dip2px(100), dip2px(80));
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mProgressView = new ProgressView(getContext());
        hideVolumeView();
        addView(mProgressView, lp);
    }

    private void showPlayProgressView() {
        if (mPlayProgressView.getVisibility() == INVISIBLE) {
            mPlayProgressView.setVisibility(VISIBLE);
        }
    }

    private void hidePlayProgressView() {
        if (mPlayProgressView.getVisibility() == VISIBLE) {
            mPlayProgressView.setVisibility(INVISIBLE);
        }
    }

    private void showVolumeView() {
        mProgressView.setImageResource(R.drawable.libs_icon_volume);
        if (mProgressView.getVisibility() == INVISIBLE) {
            mProgressView.setVisibility(VISIBLE);
        }
    }

    private void hideVolumeView() {
        if (mProgressView.getVisibility() == VISIBLE) {
            mProgressView.setVisibility(INVISIBLE);
        }
    }

    private void showBrightnessView() {
        mProgressView.setImageResource(R.drawable.libs_icon_brightness);
        if (mProgressView.getVisibility() == INVISIBLE) {
            mProgressView.setVisibility(VISIBLE);
        }
    }

    private void hideBrightnessView() {
        if (mProgressView.getVisibility() == VISIBLE) {
            mProgressView.setVisibility(INVISIBLE);
        }
    }

    /**
     * 设置音量百分比
     *
     * @param volume 音量百分比 1~100
     */
    public void setVolumePercent(int volume) {
        getAudioManager().setStreamVolume(AudioManager.STREAM_MUSIC, (getMaxVoice() / (100 / volume)), 0);
    }

    /**
     * 设置亮度百分比
     *
     * @param brightness 亮度百分比 1~100
     */
    public void setBrightnessPercent(int brightness) {
        setWindowBrightness((getMaxBrightness() / (100 / brightness)));
    }

    private AudioManager getAudioManager() {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        }
        return mAudioManager;
    }

    private int getMaxVoice() {
        if (mMaxVoice == 0) {
            mMaxVoice = getAudioManager().getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        }
        return mMaxVoice;
    }

    private int getCurrentVoice() {
        if (mCurrentVoice == -1) {
            mCurrentVoice = getAudioManager().getStreamVolume(AudioManager.STREAM_MUSIC);
        }
        return mCurrentVoice;
    }

    private int getVoiceProportion() {
        if (mVoiceProportion == 0)
            mVoiceProportion = getMeasuredHeight() / 2 / getMaxVoice();
        return mVoiceProportion;
    }

    private int getMaxBrightness() {
        if (mMaxBrightness == 0)
            mMaxBrightness = 255;
        return mMaxBrightness;
    }

    private int getCurrentBrightness() {
        if (mCurrentBrightness == -1) {
            mCurrentBrightness = (int) (((Activity) getContext()).getWindow().getAttributes().screenBrightness * 255f);
            mCurrentBrightness = mCurrentBrightness < 0 ? getSystemBrightness() : mCurrentBrightness;
        }
        return mCurrentBrightness;
    }

    /**
     * 获得系统亮度
     *
     * @return
     */
    private int getSystemBrightness() {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            return systemBrightness;
        }
        return systemBrightness;
    }

    public int getmBrightnessProportion() {
        if (mBrightnessProportion == 0)
            mBrightnessProportion = getMeasuredHeight() / 2 / getMaxBrightness();
        return mBrightnessProportion;
    }

    private void setWindowBrightness(int brightness) {
        Window window = ((Activity) getContext()).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255.0f;
        window.setAttributes(lp);
    }

    public void setCurrentTime(long currentTime) {
        this.mCurrentTime = currentTime;
    }

    public long getCurrentTime() {
        return mCurrentTime;
    }

    public void setMaxTime(long maxTime) {
        this.mMaxTime = maxTime;
    }

    public long getMaxTime() {
        return mMaxTime;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float startX = event.getX();
        float startY = event.getY();
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = startX;
                downY = startY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(downX - startX) > minMove && !leftORright) {   //左右滑动
                    topORbottom = true;
                    // 计算手指此时的坐标和上次的坐标滑动的距离。
                    int dx = (int) (startX - downX);
                    downX = startX;
                    if (minMove == 0) {
                        refreshProgress(dx);//刷新进度
                    } else {
                        gestureControl(true);
                        startSeek();
                    }
                    minMove = 0;
                } else if (Math.abs(downY - startY) > minMove && !topORbottom) {   //上下滑动
                    leftORright = true;
                    // 计算手指此时的坐标和上次的坐标滑动的距离。
                    int dx = (int) (startY - downY);
                    downY = startY;
                    if (minMove == 0) {
                        gestureControl(true);
                        if (downX >= (getMeasuredWidth() / 2)) {
                            refreshVolume(dx);//右边开始刷新音量
                        } else {
                            refreshBrightness(dx);//左边开始刷新亮度
                        }
                    }
                    minMove = 0;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                gestureControl(false);
                if (isRefreshProgress)
                    stopSeek(getCurrentTime());
                isRefreshProgress = topORbottom = leftORright = false;
                mCurrentVoice = mCurrentBrightness = -1;
                minMove = 40;
                mDx = 0;
                hideVolumeView();
                hidePlayProgressView();
                break;
        }
        return true;
    }

    public void gestureControl(boolean isStart) {

    }

    /**
     * 刷新进度
     */
    private void refreshProgress(int dx) {
        if (getMaxTime() <= 0 || dx == 0) return;
        isRefreshProgress = true;
        showPlayProgressView();
        long playProgress = dx;
        if (dx > 0) {//前进
            playProgress = getCurrentTime() + playProgress * 1000;
            if (playProgress > getMaxTime()) {
                playProgress = getMaxTime();
            }
        } else if (dx < 0) {//后退
            playProgress = getCurrentTime() - Math.abs(playProgress) * 1000;
            if (playProgress < 0) {
                playProgress = 0;
            }
        }
        seekChanged(playProgress);
        mPlayProgressView.setBrightnessPercent(playProgress * 100 / getMaxTime(), timeFormat(playProgress));
    }

    public String timeFormat(long time) {
        return timeFormat(time, time);
    }

    private String timeFormat(long totalTime, long formatTime) {
        String text;
        if (totalTime >= 3600000) {
            text = String.format("%02d:%02d:%02d", formatTime / 1000 / 60 / 60, formatTime / 1000 / 60 % 60, formatTime / 1000 % 60);
        } else {
            text = String.format("%02d:%02d", formatTime / 1000 / 60, formatTime / 1000 % 60);
        }
        return text;
    }

    public String formatDate(long millisecond, String pattern) {
        Date now = new Date(millisecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(now);
    }


    /**
     * 刷新亮度
     */
    private void refreshBrightness(int dx) {
        showBrightnessView();
        //累加移动距离
        mDx = mDx + dx;
        int brightness = 0;
        if (Math.abs(mDx) >= getmBrightnessProportion()) {
            brightness = mDx / getmBrightnessProportion();
            if (brightness > 0) {
                brightness = getCurrentBrightness() - brightness;
                if (brightness < 0) {
                    mDx = 0;
                    brightness = 0;
                    mCurrentBrightness = -1;
                }
            } else if (brightness < 0) {
                brightness = getCurrentBrightness() + Math.abs(brightness);
                if (brightness > getMaxBrightness()) {
                    mDx = 0;
                    brightness = getMaxBrightness();
                    mCurrentBrightness = -1;
                }
            }
            setWindowBrightness(brightness);
            mProgressView.setProgressPercent(brightness * 100 / getMaxBrightness());
        }
    }

    /**
     * 刷新音量
     */
    private void refreshVolume(int dx) {
        showVolumeView();
        //累加移动距离
        mDx = mDx + dx;
        int volume = 0;
        if (Math.abs(mDx) >= getVoiceProportion()) {
            volume = mDx / getVoiceProportion();
            if (volume > 0) {
                volume = getCurrentVoice() - volume;
                if (volume < 0) {
                    mDx = 0;
                    volume = 0;
                    mCurrentVoice = -1;
                }
            } else if (volume < 0) {
                volume = getCurrentVoice() + Math.abs(volume);
                if (volume > getMaxVoice()) {
                    mDx = 0;
                    volume = getMaxVoice();
                    mCurrentVoice = -1;
                }
            }
            getAudioManager().setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
            mProgressView.setProgressPercent((volume * 100 / getMaxVoice()));
        }
    }

    /**
     * 单击手势
     */
    protected boolean singleTapConfirmed(MotionEvent e) {
        return false;
    }

    /**
     * 双击手势
     */
    protected boolean doubleTap(MotionEvent e) {
        return false;
    }

    /**
     * 手势推拽进度开始
     */
    protected void startSeek() {

    }

    /**
     * 手势推拽进度发生改变
     *
     * @param time 毫秒值
     */
    protected void seekChanged(long time) {

    }

    /**
     * 手势拖拽进度结束
     *
     * @param time 毫秒值
     */
    protected void stopSeek(long time) {

    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return singleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return doubleTap(e);
        }
    }
}
