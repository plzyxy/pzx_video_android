package com.thinksoft.banana.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.gson.JsonElement;
import com.orhanobut.logger.Logger;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.NovelBean;
import com.thinksoft.banana.entity.bean.NovelListBean;
import com.thinksoft.banana.entity.bean.ADBean;
import com.thinksoft.banana.entity.bean.circle.CircleBean;
import com.thinksoft.banana.entity.bean.circle.CircleListBean;
import com.thinksoft.banana.entity.bean.circle.RegionsBean;
import com.thinksoft.banana.entity.bean.circle.RegionsDataBean;
import com.thinksoft.banana.entity.bean.home.BannersBean;
import com.thinksoft.banana.entity.bean.type.ScreenBean;
import com.thinksoft.banana.entity.bean.video.BannerBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.CircleModel;
import com.thinksoft.banana.mvp.model.HomeModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.circle.CircleDetailsActivity;
import com.thinksoft.banana.ui.activity.home.NovelDetailsActivity;
import com.thinksoft.banana.ui.activity.my.SpreadLinkActivity;
import com.thinksoft.banana.ui.adapter.CircleListAdapter;
import com.thinksoft.banana.ui.adapter.NovelListAdapter;
import com.thinksoft.banana.ui.view.window.ShareWindow;
import com.txf.other_tencentlibrary.ShareHelper;
import com.txf.other_tencentlibrary.wx.ShareContentQQ;
import com.txf.other_tencentlibrary.wx.ShareContentWebpage;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.fragment.BaseMvpListLjzFragment;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/18 0018
 * @小说列表
 */
public class NovelListFragemt
        extends BaseMvpListLjzFragment<CircleItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener, OnAppListener.OnWindowListener {
    BannerBean mBannerBean;
    ADBean mADBean;
    NovelBean mNovelBean;

    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return new NovelListAdapter(getContext(), this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(getContext(), this, new HomeModel()));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void request(int pageIndex, int pageSize) {
        if (pageIndex == 1) {
            requestBanner();
        } else {
            requestList(pageIndex);
        }
    }

    private void requestList(int pageIndex) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("page", pageIndex);
        getPresenter().getData(ApiRequestTask.home.TAG_NOVEL_LIST, maps);
    }

    private void requestBanner() {
        getPresenter().getData(ApiRequestTask.home.TAG_NOVEL_BANNER);
    }

    private void requestAD() {
        getPresenter().getData(ApiRequestTask.home.TAG_NOVEL_ADVERT);
    }

    private List<CircleItem> newItem(NovelListBean bean) {
        List<CircleItem> items = new ArrayList<>();

        if (pageIndex == 1) {
            //banner
            if (mBannerBean != null && mBannerBean.getBanners() != null && mBannerBean.getBanners().size() > 0) {
                items.add(new CircleItem(mBannerBean.getBanners(), Constant.TYPE_ITEM_1));
            }
        }
        //内容条目
        if (bean != null && bean.getNovelList() != null && bean.getNovelList().size() > 0) {
            if (mADBean == null || mADBean.getBanners() == null || mADBean.getBanners().size() == 0) {

                for (int i = 0; i < bean.getNovelList().size(); i++) {
                    NovelBean circleBean = bean.getNovelList().get(i);
                    items.add(new CircleItem(circleBean, Constant.TYPE_ITEM_2));
                }

            } else {
                //穿插广告数据
                int count = mAdapter.getItemTypeCount(Constant.TYPE_ITEM_2);
                ArrayList<BannersBean> bannersBeans = mADBean.getBanners();

                for (int i = count; i < count + bean.getNovelList().size(); i++) {

                    if (i != 0 && i % 5 == 0) {
                        int index = i / 5 % bannersBeans.size();
                        items.add(new CircleItem(bannersBeans.get(index), Constant.TYPE_ITEM_4));
                    }
                    NovelBean circleBean = bean.getNovelList().get(i - count);
                    items.add(new CircleItem(circleBean, Constant.TYPE_ITEM_2));
                }
            }
        }
        return items;
    }

    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        CircleItem item;
        switch (action) {
            case Constant.ACTION_CIRCLE_DETAILS:
                item = BundleUtils.getBaseItem(bundle);
                NovelBean bean = (NovelBean) item.getData();
                //详情
                startActivity(NovelDetailsActivity.getIntent(getContext(), bean));
                break;
            case Constant.ACTION_CIRCLE_SHARE:
                item = BundleUtils.getBaseItem(bundle);
                //分享
                startActivity(new Intent(getContext(), SpreadLinkActivity.class));
                break;
            case Constant.ACTION_CIRCLE_Z:
                item = BundleUtils.getBaseItem(bundle);
                mNovelBean = (NovelBean) item.getData();
                //点赞
                HashMap<String, Object> maps = new HashMap<>();
                maps.put("id", mNovelBean.getId());
                getPresenter().getData(ApiRequestTask.home.TAG_NOVEL_LOVE, maps);
                break;
            case Constant.TYPE_ITEM_4:
                //列表广告跳转
                BannersBean bannersBean = BundleUtils.getSerializable(bundle);
                startClient(bannersBean.getLink());
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
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.home.TAG_NOVEL_BANNER:
                //Banner 图
                mBannerBean = JsonTools.fromJson(data, BannerBean.class);
                requestAD();
                break;
            case ApiRequestTask.home.TAG_NOVEL_ADVERT:
                //穿插广告
                mADBean = JsonTools.fromJson(data, ADBean.class);
                pageIndex = 1;
                requestList(pageIndex);
                break;

            case ApiRequestTask.home.TAG_NOVEL_LIST:
                //列表
                NovelListBean bean = JsonTools.fromJson(data, NovelListBean.class);
                refreshData(newItem(bean));
                break;

            case ApiRequestTask.home.TAG_NOVEL_LOVE:
                //点赞
                if (mNovelBean.getIslove() == 1) {
                    mNovelBean.setIslove(0);
                    mNovelBean.setLove(mNovelBean.getLove() - 1);
                } else {
                    mNovelBean.setIslove(1);
                    mNovelBean.setLove(mNovelBean.getLove() + 1);
                }
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void httpOnError(int tag, int error, String message) {


    }

    private void startShare(int action) {
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
