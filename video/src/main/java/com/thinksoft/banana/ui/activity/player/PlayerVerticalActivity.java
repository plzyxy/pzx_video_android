package com.thinksoft.banana.ui.activity.player;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.orhanobut.logger.Logger;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.home.BannersBean;
import com.thinksoft.banana.entity.bean.home.PerformerBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.bean.play.BalanceBean;
import com.thinksoft.banana.entity.bean.play.PlayerBean;
import com.thinksoft.banana.entity.bean.play.RecommendBean;
import com.thinksoft.banana.entity.bean.type.PerformerDataBean;
import com.thinksoft.banana.entity.item.PlayerItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.HomeModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.PayWebActivity;
import com.thinksoft.banana.ui.activity.my.PaymentActivity;
import com.thinksoft.banana.ui.activity.my.PerformerInfoActivity;
import com.thinksoft.banana.ui.activity.my.SpreadLinkActivity;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.thinksoft.banana.ui.adapter.PlayerAdapter;
import com.txf.other_playerlibrary.player.PlayerProvide;
import com.txf.other_tencentlibrary.ShareHelper;
import com.txf.other_tencentlibrary.wx.ShareContentQQ;
import com.txf.other_tencentlibrary.wx.ShareContentWebpage;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.activity.BaseActivity;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpListActivity;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationCommon;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author txf
 * @create 2019/3/14 0014
 * @竖屏播放页
 */
public class PlayerVerticalActivity
        extends BaseMvpListActivity<PlayerItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener, OnAppListener.OnWindowListener {
    PlayerAdapter mPlayerAdapter;
    int mVideoId;//上级页面传递过来的id
    PlayerBean mPlayerBean;//通过视频id查询的视频详情
    PerformerBean mPerformerBean;//演员列表
    boolean isRelease;


    public static Intent getIntent(Context context, int id) {
        Intent i = new Intent(context, PlayerVerticalActivity.class);
        i.putExtra("data", id);
        return i;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        init(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAutoRequest(false);
        setContract(this, new CommonPresenter(this, new HomeModel()));
        init(getIntent());
    }

    @Override
    protected boolean nextPage(List<PlayerItem> datas) {
        if (datas == null || datas.size() <= 0) {
            return false;
        } else if (getSize(datas, Constant.TYPE_ITEM_4) < pageSize) {
            return false;
        } else {
            return true;
        }
    }

    private int getSize(List<PlayerItem> datas, int itemType) {
        int size = 0;
        for (PlayerItem item : datas) {
            if (item.getItemType() == itemType) {
                size++;
            }
        }
        return size;
    }

    private void init(Intent intent) {
        mVideoId = intent.getIntExtra("data", -1);

        HashMap<String, Object> maps = new HashMap<>();
        maps.put("id", mVideoId);
        maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
        getPresenter().getData(ApiRequestTask.home.TAG_VIDEO_INFO, maps);
    }

    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return mPlayerAdapter = new PlayerAdapter(this, this);
    }

    @Override
    protected void request(int pageIndex, int pageSize) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("page", pageIndex);
        getPresenter().getData(ApiRequestTask.home.TAG_VIDEO_RECOMMEND_LIST, maps);
    }

    private List<PlayerItem> newItem(RecommendBean bean) {
        List<PlayerItem> items = new ArrayList<>();
        if (pageIndex == 1) {
            //视频播放
            if (mPlayerBean != null)
                items.add(new PlayerItem(mPlayerBean, Constant.TYPE_ITEM_8));

            //视频信息
            if (mPlayerBean != null)
                items.add(new PlayerItem(mPlayerBean, Constant.TYPE_ITEM_6));
            //演员列表
//            if (mPerformerBean != null && mPerformerBean.getPerformers() != null && mPerformerBean.getPerformers().size() > 0) {
//                items.add(new PlayerItem(mPerformerBean, Constant.TYPE_ITEM_7));
//            }
            //视频广告图
            if (mPlayerBean != null && mPlayerBean.getBanners() != null && mPlayerBean.getBanners().size() > 0) {
                for (BannersBean bannersBean : mPlayerBean.getBanners())
                    items.add(new PlayerItem(bannersBean, Constant.TYPE_ITEM_3));
            }
            //推荐类型
            if (bean != null && bean.getVideos() != null && bean.getVideos().size() > 0) {
                items.add(new PlayerItem(getString(R.string.精彩推荐), Constant.TYPE_ITEM_5));
            }
        }
        if (bean != null && bean.getVideos() != null && bean.getVideos().size() > 0) {
            for (VideosBean videosBean : bean.getVideos()) {
                items.add(new PlayerItem(videosBean, Constant.TYPE_ITEM_4));
            }
        }
        return items;
    }

    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        PlayerItem item;
        switch (action) {
            case Constant.TYPE_ITEM_3:
                //banner
                BannersBean bannersBean = BundleUtils.getSerializable(bundle);
                Logger.i("点击广告图: " + JsonTools.toJSON(bannersBean));
//                startActivity(DeftWebActivity.getIntent(this, bannersBean.getLink(), "外部链接"));
                startClient(bannersBean.getLink());
                break;
            case Constant.TYPE_ITEM_4:
                //推荐视频
                VideosBean videosBean = BundleUtils.getSerializable(bundle);
                PlayerProvide.getInstance().release();
                mRecyclerView.removeAllViews();
                mPlayerAdapter.setDatas(new ArrayList<PlayerItem>());

                if (videosBean.getScreen_type() == 2) {
                    startActivity(PlayerVerticalActivity.getIntent(getContext(), videosBean.getId()));
                } else {
                    startActivity(PlayerActivity.getIntent(getContext(), videosBean.getId()));
                    isRelease = true;
                    PlayerProvide.getInstance().release();
                    finish();
                }
                break;
            case Constant.ACTION_COLLECTION:
                //收藏
                requestCollection();
                break;
            case Constant.ACTION_SHARE:
                //分享
                startActivity(new Intent(getContext(), SpreadLinkActivity.class));
                break;
            case Constant.TYPE_ITEM_7:
                PerformerDataBean performerDataBean = BundleUtils.getSerializable(bundle);
                startActivity(PerformerInfoActivity.getIntent(getContext(), performerDataBean.getId()));
                break;
        }
    }

    @Override
    public void onInteractionView(int action, Bundle bundle) {
        super.onInteractionView(action, bundle);
        HashMap<String, Object> maps;
        switch (action) {
            case Constant.ACTION_COLLECTION:
                //收藏
                requestCollection();
                break;
            case Constant.ACTION_PLAYER_PAYMENT:
                //支付
                //1.获取用户剩余砖石,观看次数
                maps = new HashMap<>();
                maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
                getPresenter().getData(ApiRequestTask.home.TAG_USER_BALANCE, maps);
                break;
        }
    }

    private void requestCollection() {
        //收藏
        if (mPlayerBean.getVideo().getVideo_type() == 1) {
            //短视频
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
            maps.put("id", mVideoId);
            getPresenter().getData(ApiRequestTask.home.TAG_ADD_COLLECTION, maps);

        } else if (mPlayerBean.getVideo().getVideo_type() == 2) {
            //电影
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
            maps.put("id", mVideoId);
            getPresenter().getData(ApiRequestTask.home.TAG_ADD_COLLECTION_FILM, maps);

        } else {
            //短视频
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
            maps.put("id", mVideoId);
            getPresenter().getData(ApiRequestTask.home.TAG_ADD_COLLECTION, maps);
        }
    }


    @Override
    protected int buildColumnCount() {
        return 2;
    }

    @Override
    protected GridLayoutManager.SpanSizeLookup buildSpanSizeLookup() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getItemViewType(position)) {
                    case Constant.TYPE_ITEM_4:
                        return 1;
                    default:
                        return 2;
                }
            }
        };
    }

    @Override
    protected RecyclerView.ItemDecoration buildItemDecoration() {
        return new ItemDecorationCommon(
                2,
                dip2px(10),
                0,
                dip2px(15),
                Constant.TYPE_ITEM_4
        );
    }

    @Override
    protected void onDestroy() {
        if (!isRelease)
            PlayerProvide.getInstance().release();
        super.onDestroy();
    }

    @Override
    public void logInvalid() {
        super.logInvalid();
        UserInfoManage.getInstance().cleanUserInfo();
        new BaseActivity.Builder()
                .setWith(1024)
                .setButton3(getString(R.string.确认))
                .setContent(getString(R.string.登录过期请重新登录))
                .setCancelable(false)
                .setDialogListener(new BaseActivity.DialogListener(null, 0) {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            //登录过期
                            startActivity(new Intent(getContext(), LoginActivity.class));
                            AppManager.getInstance().retainAcitivity(LoginActivity.class);
                        }
                    }
                })
                .show();
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case -1:
                //视频信息 vip购买成功
                PlayerBean playerBean = JsonTools.fromJson(data, PlayerBean.class);
                if (playerBean.getVideo().getIs_free() == 0) {
                    mPlayerAdapter.paySuccess();
                }
                break;
            case ApiRequestTask.home.TAG_VIDEO_INFO:
                //视频信息
                mPlayerBean = JsonTools.fromJson(data, PlayerBean.class);
                if (mPlayerBean == null || mPlayerBean.getVideo() == null) {
                    ToastUtils.show(getString(R.string.无效播放地址请稍候再试));
                    return;
                }
                //请求演员列表
                HashMap<String, Object> maps = new HashMap<>();
                maps.put("id", mVideoId);
                maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
                getPresenter().getData(ApiRequestTask.home.PERFORMER_LIST, maps);
                break;
            case ApiRequestTask.home.PERFORMER_LIST:
                //演员列表
                mPerformerBean = JsonTools.fromJson(data, PerformerBean.class);
                //请求推荐列表
                pageIndex = 1;
                startRequest(pageIndex, pageSize);
                break;
            case ApiRequestTask.home.TAG_VIDEO_RECOMMEND_LIST:
                //推荐视频列表
                RecommendBean bean = JsonTools.fromJson(data, RecommendBean.class);
                refreshData(newItem(bean));
                break;
            case ApiRequestTask.home.TAG_ADD_COLLECTION:
            case ApiRequestTask.home.TAG_ADD_COLLECTION_FILM:
                //收藏
                mPlayerBean.setCollcetion(mPlayerBean.getCollcetion() == 1 ? 0 : 1);
                mPlayerAdapter.notifyItem(new PlayerItem(mPlayerBean, Constant.TYPE_ITEM_6));
                mPlayerAdapter.setCollection(mPlayerBean);
                break;
            case ApiRequestTask.home.TAG_USER_BALANCE:
                //用户剩余砖石/免费次数
                BalanceBean balanceBean = JsonTools.fromJson(data, BalanceBean.class);
                if (mPlayerBean.getVideo().getIs_used() == 1
                        && balanceBean.getFree_count() >= mPlayerBean.getVideo().getUsed_count()) {
                    //可以用免费次数购买,并且免费次数足够购买此视频
                    //调用购买接口
                    requestPayWatch(2);
                } else {
                    new Builder()
                            .setWith(1)
                            .setButton2(getString(R.string.分享))
                            .setButton3(getString(R.string.升级会员))
                            .setContent(getString(R.string.视频播放购买提示))
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
//                    new BaseActivity.Builder()
//                            .setWith(0)
//                            .setButton2(getString(R.string.取消))
//                            .setButton3(getString(R.string.确认))
//                            .setContent("去充值购买此视频?")
//                            .show();
//                }
                break;
            case ApiRequestTask.home.TAG_PAY_WATCH:
                //购买视频成功
                mPlayerAdapter.paySuccess();
                break;
        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {


    }

    /**
     * 购买视频
     *
     * @param type 1 砖石支付 2 观看次数支付
     */
    private void requestPayWatch(int type) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
        maps.put("id", mVideoId);
        maps.put("type", type);
        getPresenter().getData(ApiRequestTask.home.TAG_PAY_WATCH, maps);
    }

    @Override
    protected void onInteractionDialog(DialogInterface dialog, int which, int with, Bundle ext) {
        super.onInteractionDialog(dialog, which, with, ext);
        if (with == 0) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //跳转到充值页面
                    startActivityForResult(new Intent(this, PaymentActivity.class), 1024);
                    break;
            }
        } else if (with == 1) {
            switch (which) {
                case DialogInterface.BUTTON_NEGATIVE:
                    //分享
                    startActivity(new Intent(getContext(), SpreadLinkActivity.class));
                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    //购买会员
                    isResume = true;
                    startActivity(new Intent(getContext(), PayWebActivity.class));
                    break;
            }
        }
    }

    boolean isResume;

    @Override
    public void onResume() {
        super.onResume();
        if (isResume) {
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("id", mVideoId);
            maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
            getPresenter().getData(ApiRequestTask.home.TAG_VIDEO_INFO, -1, maps);
            isResume = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ShareHelper.getInstance().getTencent() != null)
            Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1024:
                    //充值成功
                    HashMap maps = new HashMap<>();
                    maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
                    getPresenter().getData(ApiRequestTask.home.TAG_USER_BALANCE, maps);
                    break;
            }
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

    Bundle bundle;

    private void startShare(int action) {
        if (bundle == null)
            bundle = new Bundle();
        bundle.clear();
        switch (action) {
            case Constant.ACTION_SHARE_QQ:
                ShareHelper.getInstance().shareQQ(this, new ShareContentQQ(
                        "我在测试",
                        "测试",
                        "http://connect.qq.com/",
                        "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg"
                ), qqShareListener);
                break;
            case Constant.ACTION_SHARE_QQ_PY:
                ShareHelper.getInstance().shareQzone(this, new ShareContentQQ(
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
