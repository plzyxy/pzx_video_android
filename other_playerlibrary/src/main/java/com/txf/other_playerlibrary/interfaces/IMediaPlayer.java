package com.txf.other_playerlibrary.interfaces;

import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author txf
 * @create 2019/1/29 0029
 * @播放器接口
 */
public interface IMediaPlayer {
    public static int STATE_UNDEF = -1;
    public static int STATE_IDLE = 0;//空闲状态
    public static int STATE_INITIALIZED = 1;//初始化
    public static int STATE_PREPARING = 2;//准备中。。
    public static int STATE_PREPARED = 3;//准备完成
    public static int STATE_STARTED = 4;//开始

    public static int STATE_PAUSED = 5;//暂停
    public static int STATE_STOPED = 6;//停止
    public static int STATE_PLAYBACK_COMPLETE = 7;//播放完成
    public static int STATE_ERROR = 8;//播放错误
    public static int STATE_SEEKTO_START = 9;//寻求开始
    public static int STATE_SEEKTO_END = 10;//寻求结束

    /**
     * 设置播放状态回调
     */
    void setPlayCallback(IPlayCallback l);

    /**
     * 覆盖播放状态回调
     */
    void coverPlayCallback(IPlayCallback l);

    void setSurface(Surface surface);

    /**
     * 获取当前播放状态
     */
    int getCurrentState();

    /**
     * 设置当前播放状态
     */
    void setCurrentState(int state);

    /**
     * 获取当前播放位置
     */
    long getCurrentPosition();

    /**
     * 获视频总时长
     */
    long getDuration();

    /**
     * 获取视频高度
     */
    int getVideoHeight();

    /**
     * 获取视频宽度
     */
    int getVideoWidth();

    /**
     * 是否在播放
     */
    boolean isPlaying();

    /**
     * 暂停
     */
    void pause();

    /**
     * 准备
     */
    void prepare();

    /**
     * 准备异步
     */
    void prepareAsync();

    /**
     * 释放
     */
    void release();

    /**
     * 重置
     */
    void reSet();

    /**
     * 图片截取
     */
    boolean captureImage(String file, String fileName);

    /**
     * @param var1 移动至？处播放
     */
    void seekTo(long var1);

    /**
     * 开始播放
     */
    void start();

    /**
     * 停止
     */
    void stop();

    /**
     * 设置视频画面显示的目标
     */
    void setDisplay(SurfaceHolder var1);

    /**
     * 设置视频画面显示的目标
     */
    void setDisplay(SurfaceHolder var1, int tag);

    /**
     * 设置表面类型
     */
    void setSurfaceType(SurfaceView var1);

    /**
     * 设置音频流类型
     */
    void setAudioStreamType(int var1);

    /**
     * @param uri 设置数据源
     */
    void setDataSource(String uri);

    /**
     * 设置视频播放画面在屏幕上
     */
    void setScreenOnWhilePlaying(boolean var1);

    /**
     * 设置音量
     */
    void setVolume(float var1, float var2);

}
