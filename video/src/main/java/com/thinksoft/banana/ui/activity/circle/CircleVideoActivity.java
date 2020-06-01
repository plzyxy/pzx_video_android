package com.thinksoft.banana.ui.activity.circle;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.circle.VideoBean;
import com.thinksoft.banana.entity.bean.play.PlayerBean;
import com.thinksoft.banana.entity.bean.play.VideoDataBean;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.view.player.PlayerCircleControlView;
import com.thinksoft.banana.ui.view.player.PlayerControlView;
import com.txf.other_playerlibrary.ui.view.PlayerView;
import com.txf.ui_mvplibrary.ui.activity.BaseActivity;

import java.util.HashMap;

/**
 * @author txf
 * @create 2019/3/23 0023
 * @圈子视频查看
 */
public class CircleVideoActivity extends BaseActivity implements PlayerView.OnSurfaceHolderListener {
    PlayerView mPlayerView;
    public PlayerCircleControlView mPlayerControlView;

    VideoBean videoBean;//点击的视频
    public static Intent getIntent(Context context, VideoBean videoBean) {
        Intent i = new Intent(context, CircleVideoActivity.class);
        i.putExtra("data", videoBean);
        return i;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cirle_video;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initPlayer();
    }

    @Override
    public void onInteractionView(int action, Bundle bundle) {
        super.onInteractionView(action, bundle);
        switch (action) {
            case ACTION_CLICK_FINISH_ACTIVITY:
                finish();
                break;
            case Constant.ACTION_PLAYER_PORTRAIT:
                setHorizontalScreen(false);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case Constant.ACTION_PLAYER_LANDSCAPE:
                setHorizontalScreen(true);
                switch (mPlayerView.getOrientation()) {
                    case 90:
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                        break;
                    case 270:
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        break;
                    default:
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        break;
                }
                break;
        }
    }

    private void setHorizontalScreen(boolean horizontalScreen) {
        if (horizontalScreen) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void initPlayer() {
        mPlayerView.setPlayerControlView(mPlayerControlView = new PlayerCircleControlView(getContext()));
        mPlayerView.setOnSurfaceHolderListener(this);
    }

    private void initData() {
        videoBean = (VideoBean) getIntent().getSerializableExtra("data");
    }

    private void initView() {
        mPlayerView = findViewById(R.id.PlayerView);
        setOnClick(R.id.root);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.root:
                finish();
                break;
        }
    }

    @Override
    public void surfaceCreated() {
        PlayerBean mPlayerBean = new PlayerBean();
        VideoDataBean video = new VideoDataBean();
        if(videoBean.getType()==0){
            video.setTitle("");
            video.setLink(videoBean.getLink());
        }else {
            video.setTitle(videoBean.getBean().getTitle());
            video.setLink(videoBean.getBean().getFilepath());
            video.setImage(videoBean.getBean().getImage());
        }
        mPlayerBean.setVideo(video);
        mPlayerControlView.setPlayerBean(mPlayerBean);
        mPlayerControlView.start();
    }
}
