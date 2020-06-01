package com.thinksoft.banana.ui.activity.player;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.circle.VideoBean;
import com.thinksoft.banana.entity.bean.music.MusicInfoDataBean;
import com.thinksoft.banana.entity.bean.play.PlayerBean;
import com.thinksoft.banana.entity.bean.play.VideoDataBean;
import com.thinksoft.banana.ui.view.player.PlayerMusicControlView;
import com.thinksoft.banana.ui.view.player.tx.MusicControlView;
import com.thinksoft.banana.ui.view.player.tx.MusicPlayerProvide;
import com.txf.other_playerlibrary.ui.view.PlayerView;
import com.txf.ui_mvplibrary.ui.activity.BaseActivity;

import static com.tencent.rtmp.TXLiveConstants.PLAY_EVT_PLAY_PROGRESS;

/**
 * @author txf
 * @create 2019/3/23 0023
 * @音频播放页
 */
public class MusicActivity extends BaseActivity {

    TXCloudVideoView mTXCloudVideoView;
    MusicControlView mMusicControlView;
    TXVodPlayer mVodPlayer;

    MusicInfoDataBean mMusicInfoDataBean;

    public static Intent getIntent(Context context, MusicInfoDataBean videoBean) {
        Intent i = new Intent(context, MusicActivity.class);
        i.putExtra("data", videoBean);
        return i;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mMusicInfoDataBean = (MusicInfoDataBean) getIntent().getSerializableExtra("data");
        //创建 player 对象
        mVodPlayer = new TXVodPlayer(getContext());
        //关键 player 对象与界面 view
        mVodPlayer.setPlayerView(mTXCloudVideoView);
        mMusicControlView.setData(mMusicInfoDataBean, mVodPlayer);
        mMusicControlView.start();

    }

    @Override
    protected void onDestroy() {
        if (mVodPlayer != null)
            mVodPlayer.stopPlay(true);
        if (mTXCloudVideoView != null)
            mTXCloudVideoView.onDestroy();
        super.onDestroy();
    }

    private void initView() {
        mTXCloudVideoView = findViewById(R.id.TXCloudVideoView);
        mMusicControlView = findViewById(R.id.mMusicControlView);
        findViewById(R.id.backButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
