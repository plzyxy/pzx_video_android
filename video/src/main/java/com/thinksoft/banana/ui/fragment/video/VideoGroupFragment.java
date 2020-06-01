package com.thinksoft.banana.ui.fragment.video;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.JsonElement;
import com.orhanobut.logger.Logger;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.play.BalanceBean;
import com.thinksoft.banana.entity.bean.video.VideoGroupBean;
import com.thinksoft.banana.entity.bean.video.VideoGroupDataBean;
import com.thinksoft.banana.entity.item.VideoItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.HomeModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.my.SpreadLinkActivity;
import com.thinksoft.banana.ui.adapter.VideoGroupAdapter;
import com.thinksoft.banana.ui.view.player.PlayerListView;
import com.txf.other_playerlibrary.interfaces.IMediaPlayer;
import com.txf.other_tencentlibrary.ShareHelper;
import com.txf.other_tencentlibrary.wx.ShareContentQQ;
import com.txf.other_tencentlibrary.wx.ShareContentWebpage;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.fragment.BaseMvpFragment;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author txf
 * @create 2019/3/27 0027
 * @刷一刷(视频垂直播放列表)
 */
public class VideoGroupFragment
        extends BaseMvpFragment<CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnViewListener, OnAppListener.OnWindowListener {
    RecyclerView mRecyclerView;
    VideoGroupAdapter mAdapter;
    int firstVisibleItem, lastVisibleItem;
    PlayerListView mPlayerListView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_group;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(getContext(), new HomeModel()));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        initRecyclerView();
    }

    public void initData() {
        if (getPresenter() == null)
            setContract(this, new CommonPresenter(getContext(), new HomeModel()));
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("limit", 100);
        getPresenter().getData(ApiRequestTask.home.TAG_VIDEO_GROUP_LIST, maps, false);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                        Logger.i("停止滚动");
                        autoPlayVideo(recyclerView);
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://惯性滑动
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
        mRecyclerView.setAdapter(mAdapter = new VideoGroupAdapter(getContext()));
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.RecyclerView);
    }

    public void hiedeView() {
        if (mPlayerListView != null) {
            mPlayerListView.getControlView().hiedeView();
            mPlayerListView = null;
        }
    }


    private void autoPlayVideo(RecyclerView recyclerView) {
        if (firstVisibleItem == 0 && lastVisibleItem == 0 && recyclerView.getChildAt(0) != null) {
            mPlayerListView = recyclerView.getChildAt(0).findViewById(R.id.PlayerListView);
            switch (mPlayerListView.getControlView().getCurrentState()) {
                case IMediaPlayer.STATE_STARTED:
                case IMediaPlayer.STATE_PAUSED:
                    break;
                default:
                    requestCount();
                    mPlayerListView.getControlView().start();
                    break;
            }
            return;
        }
        for (int i = 0; i <= lastVisibleItem; i++) {
            if (recyclerView == null || recyclerView.getChildAt(i) == null) {
                return;
            }
            mPlayerListView = recyclerView.getChildAt(i).findViewById(R.id.PlayerListView);
            switch (mPlayerListView.getControlView().getCurrentState()) {
                case IMediaPlayer.STATE_STARTED:
                case IMediaPlayer.STATE_PAUSED:
                    break;
                default:
                    requestCount();
                    mPlayerListView.getControlView().start();
                    break;
            }
        }
    }

    private void requestCount() {
        if (mPlayerListView == null || mPlayerListView.getControlView() == null || mPlayerListView.getControlView().getPlayerBean() == null)
            return;
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("id", mPlayerListView.getControlView().getPlayerBean().getId());
        getPresenter().getData(ApiRequestTask.home.TAG_VIDEO_SEE_OPREATION, maps, false);
    }

    /**
     * 购买视频
     *
     * @param type 1 砖石支付 2 观看次数支付
     */
    private void requestPayWatch(int type) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
        maps.put("id", mVideoGroupDataBean.getId());
        maps.put("type", type);
        getPresenter().getData(ApiRequestTask.home.TAG_PAY_WATCH, maps);
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.home.TAG_VIDEO_GROUP_LIST:
                VideoGroupBean bean = JsonTools.fromJson(data, VideoGroupBean.class);
                refreshData(newItem(bean));
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        autoPlayVideo(mRecyclerView);
                    }
                });
                break;
            case ApiRequestTask.home.TAG_ADD_COLLECTION:
            case ApiRequestTask.home.TAG_ADD_COLLECTION_FILM:
                //收藏
                mVideoGroupDataBean.setCollcetion(mVideoGroupDataBean.getCollcetion() == 0 ? 1 : 0);
                mPlayerListView.getControlView().setCollection((mVideoGroupDataBean.getCollcetion() == 1));
                break;
            case ApiRequestTask.home.TAG_VIDEO_GROUP_FABULOUS:
                //点赞
                if (mVideoGroupDataBean.getIslove() == 1) {
                    mVideoGroupDataBean.setIslove(0);
                    mVideoGroupDataBean.setLove(mVideoGroupDataBean.getLove() - 1);
                } else {
                    mVideoGroupDataBean.setIslove(1);
                    mVideoGroupDataBean.setLove(mVideoGroupDataBean.getLove() + 1);
                }
                mPlayerListView.getControlView().setFabulous(mVideoGroupDataBean.getIslove() == 1, mVideoGroupDataBean.getLove());
                break;
            case ApiRequestTask.home.TAG_USER_BALANCE:
                //用户剩余砖石/免费次数
                BalanceBean balanceBean = JsonTools.fromJson(data, BalanceBean.class);
                if (mVideoGroupDataBean.getIs_used() == 1
                        && balanceBean.getFree_count() >= mVideoGroupDataBean.getUsed_count()) {
                    //可以用免费次数购买,并且免费次数足够购买此视频
                    //调用购买接口
                    requestPayWatch(2);
                } else {
                    new Builder()
                            .setWith(1)
                            .setButton2(getString(R.string.取消))
                            .setButton3(getString(R.string.确认))
                            .setContent("免费观影次数不足,通过分享可获得更多观影次数?")
                            .show();
                }
//                if (mPlayerBean.getVideo().getIs_used() == 1
//                        && balanceBean.getFree_count() >= mPlayerBean.getVideo().getUsed_count()) {
//                    //可以用免费次数购买,并且免费次数足够购买此视频
//                    //调用购买接口
//                    requestPayWatch(2);
//
//                } else if (balanceBean.getDiamondDouble() >= mPlayerBean.getVideo().getDiamondDouble()) {
//                    //不可以用免费次数购买,并且砖石足够购买此视频
//                    //调用购买接口
//                    requestPayWatch(1);
//                } else {
//                    //以上条件都不满足, 调用充值页面
//                    new Builder()
//                            .setWith(0)
//                            .setButton2(getString(R.string.取消))
//                            .setButton3(getString(R.string.确认))
//                            .setContent("去充值购买此视频?")
//                            .show();
//                }
                break;
            case ApiRequestTask.home.TAG_PAY_WATCH:
                //购买视频成功
                if (mPlayerListView != null && mPlayerListView.getControlView() != null)
                    mPlayerListView.getControlView().paySuccess();
                break;
        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {

    }

    private void refreshData(List<VideoItem> datas) {
        if (datas.size() == 0) {
//            VideoItem videoItem=new VideoItem();

        }

        mAdapter.setDatas(datas);
        mAdapter.notifyDataSetChanged();
    }

    private List<VideoItem> newItem(VideoGroupBean bean) {
        List<VideoItem> items = new ArrayList<>();
        String[] linkls = new String[]{
                "http://ali-cdn.kwai.net/upic/2015/10/12/10/BMjAxNTEwMTIxMDIzMTlfMTY3ODUwOTFfNDEzMTgzNDY5XzJfMw==.mp4",
                "http://ali-cdn.kwai.net/upic/2016/06/25/11/BMjAxNjA2MjUxMTQ2MzRfMTQ0MTUyNDU1Xzg0MTYzNTMwOV8xXzM=.mp4",
                "http://ali-cdn.kwai.net/upic/2016/06/26/19/BMjAxNjA2MjYxOTA0NDJfMTY3ODUwOTFfODQ1Mjg5MDUxXzJfMw==.mp4",
                "http://ali-cdn.kwai.net/upic/2016/07/04/20/BMjAxNjA3MDQyMDI2MTFfMjI2MzMzNTA2Xzg2NTIzMzI1OF8yXzM=.mp4",
                "http://ali-cdn.kwai.net/upic/2016/07/16/18/BMjAxNjA3MTYxODAzMTNfMTY3Mjc0OTkwXzg5NjMzNjAyNV8yXzM=.mp4",
                "http://ali-cdn.kwai.net/upic/2016/07/22/21/BMjAxNjA3MjIyMTMyNDlfMTQ0MTUyNDU1XzkxMzAyNDM5MV8xXzM=.mp4",
                "http://ali-cdn.kwai.net/upic/2016/07/28/21/BMjAxNjA3MjgyMTIwNTFfMTQ0MTUyNDU1XzkyODk4NDIyMF8xXzM=.mp4",
                "http://ali-cdn.kwai.net/upic/2016/08/10/19/BMjAxNjA4MTAxOTMzMDNfMTQ0MTUyNDU1Xzk2NTMzNTI2MV8xXzM=.mp4",
                "http://ali-cdn.kwai.net/upic/2016/09/08/14/BMjAxNjA5MDgxNDA0MDBfMTMzMDkxODc4XzEwNDQzNTkxMDFfMV8z.mp4",
                "http://ali-cdn.kwai.net/upic/2016/09/13/23/BMjAxNjA5MTMyMzIxMDVfMTQ0MTUyNDU1XzEwNTk2MTQyNThfMV8z.mp4"


        };


//        if (bean == null || bean.getList() == null || bean.getList().size() == 0)
//        { return items;}
        for (int i = 0; i < 10; i++) {
            VideoGroupDataBean dataBean = new VideoGroupDataBean();
            dataBean.setId(28);
            dataBean.setIs_free(0);
            dataBean.setComment_num(1);
            dataBean.setLink(linkls[i]);
            dataBean.setImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2608671933,792210037&fm=26&gp=0.jpg");
            dataBean.setHis("00:00:30");
            dataBean.setFree_time(20);

            items.add(new VideoItem(dataBean, Constant.TYPE_ITEM_1));

            /**
             * id : 28
             * is_free : 0
             * title : 测试水水水水
             * comment_num : 0
             * link : http://1258668526.vod2.myqcloud.com/22ad2b93vodcq1258668526/356de9115285890786156117148/e6Q2EITmLhQA.mp4
             * image : http://1258668526.vod2.myqcloud.com/22ad2b93vodcq1258668526/356de9115285890786156117148/5285890786156117149.jpg
             * his : 00:00:01
             * free_time : 0
             * unit : s
             * is_used : 0
             * diamond : 0.00
             * used_count : 0
             * is_usecomm : 0
             * banners : []
             * advert_id : 3
             * video_type : 1
             * screen_type : 2
             * buyStatus : 0
             * collcetion : 0
             * love
             * islove
             */

        }

        for (VideoGroupDataBean dataBean : bean.getList()) {
            items.add(new VideoItem(dataBean, Constant.TYPE_ITEM_1));
        }
        return items;
    }

    VideoGroupDataBean mVideoGroupDataBean;

    @Override
    public void onInteractionView(int action, Bundle ext) {
        HashMap<String, Object> maps;
        switch (action) {
            case Constant.ACTION_FABULOUS:
                //点赞
                mVideoGroupDataBean = BundleUtils.getSerializable(ext);
                maps = new HashMap<>();
                maps.put("id", mVideoGroupDataBean.getId());
                getPresenter().getData(ApiRequestTask.home.TAG_VIDEO_GROUP_FABULOUS, maps);
                break;
            case Constant.ACTION_COLLECTION:
                //收藏
                mVideoGroupDataBean = BundleUtils.getSerializable(ext);
                if (mVideoGroupDataBean.getVideo_type() == 1) {
                    //短视频
                    maps = new HashMap<>();
                    maps.put("id", mVideoGroupDataBean.getId());
                    getPresenter().getData(ApiRequestTask.home.TAG_ADD_COLLECTION, maps);

                } else if (mVideoGroupDataBean.getVideo_type() == 2) {
                    //电影
                    maps = new HashMap<>();
                    maps.put("id", mVideoGroupDataBean.getId());
                    getPresenter().getData(ApiRequestTask.home.TAG_ADD_COLLECTION_FILM, maps);
                }
                break;
            case Constant.ACTION_SHARE:
                //分享
                startActivity(new Intent(getContext(), SpreadLinkActivity.class));
                break;
            case Constant.ACTION_PLAYER_PAYMENT:
                //支付
                //1.获取用户剩余砖石,观看次数
                mVideoGroupDataBean = BundleUtils.getSerializable(ext);
                maps = new HashMap<>();
                getPresenter().getData(ApiRequestTask.home.TAG_USER_BALANCE, maps);
                break;
        }
    }

    @Override
    protected void onInteractionDialog(DialogInterface dialog, int which, int with, Bundle ext) {
        super.onInteractionDialog(dialog, which, with, ext);
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                if (with == 1) {
                    startActivity(new Intent(getContext(), SpreadLinkActivity.class));
                }
                break;
        }
    }

    @Override
    public void onInteractionWindow(int action, int tag, Bundle ext) {
        switch (tag) {
            case 0:
                startShare(action);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ShareHelper.getInstance().getTencent() != null)
            Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
    }

    Bundle bundle;

    private void startShare(int action) {
        if (bundle == null)
            bundle = new Bundle();
        bundle.clear();
        switch (action) {
            case Constant.ACTION_SHARE_QQ:
                ShareHelper.getInstance().shareQQ(getActivity(), new ShareContentQQ(
                        "我在测试",
                        "测试",
                        "http://connect.qq.com/",
                        "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg"
                ), qqShareListener);
                break;
            case Constant.ACTION_SHARE_QQ_PY:
                ShareHelper.getInstance().shareQzone(getActivity(), new ShareContentQQ(
                        "我在测试",
                        "测试",
                        "http://connect.qq.com/",
                        "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg"
                ), qqShareListener);
                break;
            case Constant.ACTION_SHARE_WX:
                ShareHelper.getInstance().shareWX(
                        new ShareContentWebpage(
                                "我在测试",
                                "测试",
                                "http://connect.qq.com/",
                                R.mipmap.ic_launcher));
                break;
            case Constant.ACTION_SHARE_WX_PX:
                ShareHelper.getInstance().shareWXFrends(new ShareContentWebpage(
                        "我在测试",
                        "测试",
                        "http://connect.qq.com/",
                        R.mipmap.ic_launcher));
                break;
        }
    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            Logger.i("分享取消");
        }

        @Override
        public void onComplete(Object response) {
            Logger.i("分享成功");
        }

        @Override
        public void onError(UiError e) {
            Logger.i("分享失败: " + JsonTools.toJSON(e));
        }
    };
}
