package com.thinksoft.banana.ui.view.player.tx;

import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tencent.ijk.media.player.IjkMediaPlayer;
import com.tencent.rtmp.TXVodPlayer;
import com.txf.other_playerlibrary.interfaces.IMediaPlayer;
import com.txf.other_playerlibrary.interfaces.IPlayCallback;

import java.io.IOException;


/**
 * @author txf
 * @create 2019/1/29 0029
 * @播放器
 */
public class MusicPlayer implements IMediaPlayer {
    public final String TAG = "playerTTTTT";
    private IjkMediaPlayer mMediaPlayer;
    private int mState;
    private IPlayCallback mIPlayCallback;

    public MusicPlayer() {
        mMediaPlayer = new IjkMediaPlayer();
        mState = IMediaPlayer.STATE_UNDEF;

        mMediaPlayer.setOnBufferingUpdateListener(new MyOnBufferingUpdateListener(this));
        mMediaPlayer.setOnCompletionListener(new MyOnCompletionListener(this));
        mMediaPlayer.setOnErrorListener(new MyOnErrorListener(this));
        mMediaPlayer.setOnInfoListener(new MyOnInfoListener(this));
        mMediaPlayer.setOnPreparedListener(new MyOnPreparedListener(this));
        mMediaPlayer.setOnSeekCompleteListener(new MyOnSeekCompleteListener(this));
        mMediaPlayer.setOnVideoSizeChangedListener(new MyOnVideoSizeChangedListener(this));
    }

    /**
     * 设置播放状态回调
     */
    @Override
    public void setPlayCallback(IPlayCallback l) {
        this.mIPlayCallback = l;
    }

    @Override
    public void coverPlayCallback(IPlayCallback l) {
        this.mIPlayCallback = l;
    }

    /**
     * 检查播放器状态
     */
    public int checkPlayerState() {
        if (mMediaPlayer == null)
            return -1;
        return 0;
    }

    @Override
    public int getCurrentState() {
        return mState;
    }

    @Override
    public void setCurrentState(int state) {
        this.mState = state;
    }

    @Override
    public boolean captureImage(String file, String fileName) {
//        if (checkPlayerState() == -1) {
//            Log.i(TAG, "------>>> 播放器为空 getCurrentPosition()");
//            mIPlayCallback.onImageCaptured(
//                    this,
//                    new File(file + File.separator + fileName).getAbsolutePath(),
//                    false);
//            return false;
//        }
//        int width = getVideoWidth();
//        int height = getVideoHeight();
//        Bitmap srcBitmap = Bitmap.createBitmap(width,
//                height, Bitmap.Config.ARGB_4444);
//
//
//        boolean flag = mMediaPlayer.getCurrentFrame(srcBitmap);
//        if (flag) {
//            // 保存图片
//            File screenshotsDirectory = new File(file);
//            if (!screenshotsDirectory.exists()) {
//                screenshotsDirectory.mkdirs();
//            }
//            File savePath = new File(
//                    screenshotsDirectory.getPath()
//                            + File.separator
//                            + fileName);
//
//            try {
//                FileOutputStream out = new FileOutputStream(savePath);
//                srcBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//                out.flush();
//                out.close();
//
//                mIPlayCallback.onImageCaptured(
//                        this,
//                        new File(file + File.separator + fileName).getAbsolutePath(),
//                        flag);
//
//            } catch (FileNotFoundException e) {
//                mIPlayCallback.onImageCaptured(
//                        this,
//                        new File(file + File.separator + fileName).getAbsolutePath(),
//                        false);
//
//            } catch (IOException e) {
//                mIPlayCallback.onImageCaptured(
//                        this,
//                        new File(file + File.separator + fileName).getAbsolutePath(),
//                        false);
//            }
//        }
//        return flag;
//        mMediaPlayer.captureImage(mMediaPlayer.getCurrentPosition());
        return false;
    }

    @Override
    public long getCurrentPosition() {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>> 播放器为空 getCurrentPosition()");
            return -1;
        }
        return (int) mMediaPlayer.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>> 播放器为空 getDuration()");
            return -1;
        }
        return mMediaPlayer.getDuration();
    }

    @Override
    public int getVideoHeight() {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>> 播放器为空 getVideoHeight()");
            return -1;
        }
        return mMediaPlayer.getVideoHeight();
    }

    @Override
    public int getVideoWidth() {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>> 播放器为空 getVideoWidth()");
            return -1;
        }
        return mMediaPlayer.getVideoWidth();
    }

    @Override
    public boolean isPlaying() {
        if (checkPlayerState() == -1) {
            return false;
        }
        return mMediaPlayer.isPlaying();
    }

    @Override
    public void pause() {
        if (!isPlaying()) {
            Log.i(TAG, "------>>> 没有开始播放 pause()失败");
            return;
        }
        try {
            mMediaPlayer.pause();
            mState = STATE_PAUSED;
        } catch (IllegalStateException e) {
            Log.i(TAG, "------>>> pause()异常");
            e.printStackTrace();
        }
    }

    @Override
    public void prepare() {
        prepareAsync();
    }

    @Override
    public void prepareAsync() {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>>播放器为空 prepareAsync()");
            return;
        }
        try {
            mMediaPlayer.prepareAsync();
            mState = STATE_PREPARING;
        } catch (IllegalStateException e) {
            Log.i(TAG, "------>>> prepareAsync()异常");
            e.printStackTrace();
        }
    }

    @Override
    public void release() {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>>播放器为空 release()");
            return;
        }
        mMediaPlayer.release();
        mMediaPlayer = null;
        mState = STATE_UNDEF;
    }

    @Override
    public void reSet() {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>>播放器为空 reset()");
            return;
        }
        mMediaPlayer.reset();
        mState = STATE_IDLE;
    }

    @Override
    public void seekTo(long var1) {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>>播放器为空 seekTo()");
            return;
        }
        try {
            mMediaPlayer.seekTo(var1);
            mState = STATE_SEEKTO_START;
        } catch (IllegalStateException e) {
            Log.i(TAG, "------>>> seekTo()异常");
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>>播放器为空 prepareStart()");
            return;
        }
        try {
            mMediaPlayer.start();
            mState = STATE_STARTED;
        } catch (IllegalStateException e) {
            Log.i(TAG, "------>>> prepareStart()异常");
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>>播放器为空 stop()");
            return;
        }
        try {
            mMediaPlayer.stop();
            mState = STATE_STOPED;
        } catch (IllegalStateException e) {
            Log.i(TAG, "------>>> stop()异常");
            e.printStackTrace();
        }
    }

    @Override
    public void setDisplay(SurfaceHolder holder) {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>>播放器为空 setDisplay()");
            return;
        }
        mMediaPlayer.setDisplay(holder);
        mMediaPlayer.setScreenOnWhilePlaying(true);
    }

    int tag;

    public int getTag() {
        return tag;
    }

    @Override
    public void setDisplay(SurfaceHolder holder, int tag) {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>>播放器为空 setDisplay()");
            return;
        }
        this.tag = tag;
        mMediaPlayer.setDisplay(holder);
        mMediaPlayer.setScreenOnWhilePlaying(true);
    }

    @Override
    public void setSurface(Surface surface) {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>>播放器为空 setSurface()");
            return;
        }
        mMediaPlayer.setSurface(surface);
    }

    @Override
    public void setSurfaceType(SurfaceView surfaceView) {
        if (surfaceView != null && surfaceView.getHolder() != null)
            surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void setAudioStreamType(int var1) {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>>播放器为空 setAudioStreamType()");
            return;
        }
        mMediaPlayer.setAudioStreamType(var1);
    }

    @Override
    public void setDataSource(String uri) {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>>播放器为空 setDataSource()");
            return;
        }
        try {
            mMediaPlayer.setDataSource(uri);
        } catch (IOException e) {
            Log.i(TAG, "------>>>setDataSource() 异常");
            e.printStackTrace();
        }
    }

    @Override
    public void setScreenOnWhilePlaying(boolean var1) {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>>播放器为空 setScreenOnWhilePlaying()");
            return;
        }
        mMediaPlayer.setScreenOnWhilePlaying(var1);
    }

    @Override
    public void setVolume(float var1, float var2) {
        if (checkPlayerState() == -1) {
            Log.i(TAG, "------>>>播放器为空 setVolume()");
            return;
        }
        mMediaPlayer.setVolume(var1, var2);
    }

    class MyOnBufferingUpdateListener implements com.tencent.ijk.media.player.IMediaPlayer.OnBufferingUpdateListener {
        private IMediaPlayer im;

        public MyOnBufferingUpdateListener(IMediaPlayer im) {
            this.im = im;
        }
        @Override
        public void onBufferingUpdate(com.tencent.ijk.media.player.IMediaPlayer iMediaPlayer, int i) {
            if (mIPlayCallback != null) {
                mIPlayCallback.onBufferingUpdate(im, i);
            }
        }
    }

    class MyOnCompletionListener implements com.tencent.ijk.media.player.IMediaPlayer.OnCompletionListener {
        private IMediaPlayer im;

        public MyOnCompletionListener(IMediaPlayer im) {
            this.im = im;
        }

        @Override
        public void onCompletion(com.tencent.ijk.media.player.IMediaPlayer iMediaPlayer) {
            mState = STATE_PLAYBACK_COMPLETE;
            Log.i(TAG, "---->>>>  播发器原始侦听 播放完成");
            if (mIPlayCallback != null) {
                mIPlayCallback.onCompletion(im);
            }
        }
    }

    class MyOnErrorListener implements com.tencent.ijk.media.player.IMediaPlayer.OnErrorListener {
        private IMediaPlayer im;

        public MyOnErrorListener(IMediaPlayer im) {
            this.im = im;
        }

        @Override
        public boolean onError(com.tencent.ijk.media.player.IMediaPlayer iMediaPlayer, int i, int i1) {
            mState = STATE_ERROR;
            Log.i(TAG, "---->>>>  播发器原始侦听 播放错误 : " + i);
            if (mIPlayCallback != null) {
                return mIPlayCallback.onError(im, i, 0);
            } else {
                return false;
            }
        }
    }

    class MyOnInfoListener implements com.tencent.ijk.media.player.IMediaPlayer.OnInfoListener {
        private IMediaPlayer im;

        public MyOnInfoListener(IMediaPlayer im) {
            this.im = im;
        }

        @Override
        public boolean onInfo(com.tencent.ijk.media.player.IMediaPlayer iMediaPlayer, int i, int i1) {
//            Log.i(TAG, "---->>>>  播发器原始侦听 播放信息 : " + i);
            if (mIPlayCallback != null) {
                return mIPlayCallback.onInfo(im, i, i1);
            } else {
                return false;
            }
        }
    }

    class MyOnPreparedListener implements com.tencent.ijk.media.player.IMediaPlayer.OnPreparedListener {
        private IMediaPlayer im;

        public MyOnPreparedListener(IMediaPlayer im) {
            this.im = im;
        }


        @Override
        public void onPrepared(com.tencent.ijk.media.player.IMediaPlayer iMediaPlayer) {
            mState = IMediaPlayer.STATE_PREPARED;
            Log.i(TAG, "---->>>>  播发器原始侦听  准备完成");
            if (mIPlayCallback != null) {
                mIPlayCallback.onPrepared(im);
            }
        }
    }

    class MyOnSeekCompleteListener implements com.tencent.ijk.media.player.IMediaPlayer.OnSeekCompleteListener {
        private IMediaPlayer im;

        public MyOnSeekCompleteListener(IMediaPlayer im) {
            this.im = im;
        }


        @Override
        public void onSeekComplete(com.tencent.ijk.media.player.IMediaPlayer iMediaPlayer) {
            Log.i(TAG, "---->>>>  播发器原始侦听  寻求完成");
            mState = STATE_SEEKTO_END;
            if (mIPlayCallback != null) {
                mIPlayCallback.onSeekComplete(im);
            }
        }
    }

    class MyOnVideoSizeChangedListener implements com.tencent.ijk.media.player.IMediaPlayer.OnVideoSizeChangedListener {
        private IMediaPlayer im;

        public MyOnVideoSizeChangedListener(IMediaPlayer im) {
            this.im = im;
        }

        @Override
        public void onVideoSizeChanged(com.tencent.ijk.media.player.IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {
            Log.i(TAG, "---->>>>  播发器原始侦听  视频大小发生改变 ; " + i + " , " + i1);
            if (mIPlayCallback != null) {
                mIPlayCallback.onVideoSizeChanged(im, i, i1, 0, 0);
            }
        }
    }
}
