package com.txf.other_playerlibrary.player;

import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.view.SurfaceHolder;

import com.txf.other_playerlibrary.interfaces.IMediaPlayer;
import com.txf.other_playerlibrary.interfaces.IPlayCallback;
import com.txf.other_playerlibrary.tools.Logger;

/**
 * @author txf
 * @create 2019/1/29 0029
 * @播放器提供者 IMediaPlayer
 */
public class PlayerProvide {
    private static PlayerProvide mPlayerProvide;

    private IMediaPlayer mIMediaPlayer;

    private PlayerProvide() {
    }

    public synchronized static PlayerProvide getInstance() {
        if (mPlayerProvide == null)
            mPlayerProvide = new PlayerProvide();
        return mPlayerProvide;
    }

    public IMediaPlayer getMediaPlayer() {
        return mIMediaPlayer;
    }

    public void release() {
        if (mIMediaPlayer != null) {
            mIMediaPlayer.setPlayCallback(null);
            mIMediaPlayer.release();
            mIMediaPlayer = null;
        }
    }

    public void releaseHolder(SurfaceHolder holder) {
        if (holder != null) {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.BLACK);
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    /**
     * 设置表面
     */
    public void setSurfaceHolder(SurfaceHolder holder) {
        if (mIMediaPlayer == null) {
            return;
        }
        mIMediaPlayer.setDisplay(holder);
    }

    /**
     * 设置表面
     */
    public void clearSurfaceHolder() {
        if (mIMediaPlayer == null) {
            return;
        }
        mIMediaPlayer.setDisplay(null);
    }

    /**
     * 创建播放器
     */
    public void createPlayer() {
        if (mIMediaPlayer != null) {
            release();
        }
        mIMediaPlayer = new Player();
    }

    /**
     * 准备播放
     */
    public void prepareStart(String playUrl, SurfaceHolder holder, IPlayCallback l) {
        if (mIMediaPlayer == null) {
            Logger.show("准备播放失败  mIMediaPlayer == null");
            return;
        }
        releaseHolder(holder);
        mIMediaPlayer.setDisplay(holder);
        mIMediaPlayer.setPlayCallback(l);
        mIMediaPlayer.setDataSource(playUrl);
        mIMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mIMediaPlayer.prepareAsync();
    }
}
