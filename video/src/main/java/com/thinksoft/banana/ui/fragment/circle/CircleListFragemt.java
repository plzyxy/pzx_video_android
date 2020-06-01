package com.thinksoft.banana.ui.fragment.circle;

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
import com.thinksoft.banana.entity.bean.circle.ADBean;
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
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.circle.CircleDetailsActivity;
import com.thinksoft.banana.ui.activity.my.SpreadLinkActivity;
import com.thinksoft.banana.ui.adapter.CircleListAdapter;
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
 * @圈子列表
 */
public class CircleListFragemt
        extends BaseMvpListLjzFragment<CircleItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener, OnAppListener.OnWindowListener {
    BannerBean mBannerBean;
    List<ScreenBean> mScreenBeans;
    ADBean mADBean;
    CircleBean mCircleBean;

    public ScreenBean getScreenBean() {
        ScreenBean screenBean = new ScreenBean(-1, "全部");
        if (mScreenBeans == null || mScreenBeans.size() == 0)
            return screenBean;

        for (ScreenBean bean : mScreenBeans) {
            if (bean.isCheck()) {
                screenBean = bean;
                break;
            }
        }
        return screenBean;
    }

    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return new CircleListAdapter(getContext(), this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(getContext(), this, new CircleModel()));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void request(int pageIndex, int pageSize) {
        if (pageIndex == 1) {
            requestRegions();
        } else {
            requestCircleList(pageIndex);
        }
    }
    private void requestCircleList(int pageIndex) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("page", pageIndex);
        if (getScreenBean().getId() != -1)
            maps.put("region_id", getScreenBean().getId());
        getPresenter().getData(ApiRequestTask.circle.TAG_CIRCLE_LIST, maps);
    }

    private void requestRegions() {
        getPresenter().getData(ApiRequestTask.circle.TAG_CIRCLE_REGIONS);
    }

    private void requestBanner() {
        getPresenter().getData(ApiRequestTask.circle.TAG_CIRCLE_BANNER);
    }

    private void requestAD() {
        getPresenter().getData(ApiRequestTask.circle.TAG_CIRCLE_LIST_AD);
    }

    private List<CircleItem> newItem(CircleListBean bean) {
        List<CircleItem> items = new ArrayList<>();

        if (pageIndex == 1) {
            mAdapter.setDatas(new ArrayList<CircleItem>());
            //banner
            if (mBannerBean != null && mBannerBean.getBanners() != null && mBannerBean.getBanners().size() > 0) {
                items.add(new CircleItem(mBannerBean.getBanners(), Constant.TYPE_ITEM_1));
            }
            //筛选
            if (mScreenBeans != null && mScreenBeans.size() > 0) {
                items.add(new CircleItem(mScreenBeans, Constant.TYPE_ITEM_3));
            }
        }

        //内容条目
        if (bean != null && bean.getCircleList() != null && bean.getCircleList().size() > 0) {

            if (mADBean == null || mADBean.getBanners() == null || mADBean.getBanners().size() == 0) {
                for (int i = 0; i < bean.getCircleList().size(); i++) {
                    CircleBean circleBean = bean.getCircleList().get(i);
                    items.add(new CircleItem(circleBean, Constant.TYPE_ITEM_2));
                }
            } else {
                //穿插广告数据
                int count = mAdapter.getItemTypeCount(Constant.TYPE_ITEM_2);
                ArrayList<BannersBean> bannersBeans = mADBean.getBanners();
                for (int i = count; i < count + bean.getCircleList().size(); i++) {

                    if (i != 0 && i % 5 == 0) {
                        int index = i / 5 % bannersBeans.size();
                        items.add(new CircleItem(bannersBeans.get(index), Constant.TYPE_ITEM_4));
                    }
                    CircleBean circleBean = bean.getCircleList().get(i - count);
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
                CircleBean bean = (CircleBean) item.getData();
                //详情
                startActivity(CircleDetailsActivity.getIntent(getContext(), bean));
                break;
            case Constant.ACTION_CIRCLE_SHARE:
                item = BundleUtils.getBaseItem(bundle);
                //分享
                startActivity(new Intent(getContext(), SpreadLinkActivity.class));
                break;
            case Constant.ACTION_CIRCLE_Z:
                item = BundleUtils.getBaseItem(bundle);
                mCircleBean = (CircleBean) item.getData();
                //点赞
                HashMap<String, Object> maps = new HashMap<>();
                maps.put("id", mCircleBean.getId());
                getPresenter().getData(ApiRequestTask.circle.TAG_CIRCLE_LOVE_CIRCLE, maps);
                break;
            case Constant.TYPE_ITEM_3:
                //筛选条件发生改变
                pageIndex = 1;
                requestCircleList(pageIndex);
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
            case ApiRequestTask.circle.TAG_CIRCLE_REGIONS:
                //筛选地区列表
                RegionsBean regionsBean = JsonTools.fromJson(data, RegionsBean.class);
                initScreenBean(regionsBean);
                requestBanner();
                break;
            case ApiRequestTask.circle.TAG_CIRCLE_BANNER:
                //Banner 图
                mBannerBean = JsonTools.fromJson(data, BannerBean.class);
                requestAD();
                break;
            case ApiRequestTask.circle.TAG_CIRCLE_LIST_AD:
                //圈子列表穿插广告
                mADBean = JsonTools.fromJson(data, ADBean.class);
                pageIndex = 1;
                requestCircleList(pageIndex);
                break;

            case ApiRequestTask.circle.TAG_CIRCLE_LIST:
                //圈子列表
                CircleListBean bean = JsonTools.fromJson(data, CircleListBean.class);
                refreshData(newItem(bean));
                break;

            case ApiRequestTask.circle.TAG_CIRCLE_LOVE_CIRCLE:
                //点赞
                if (mCircleBean.getIslove() == 1) {
                    mCircleBean.setIslove(0);
                    mCircleBean.setLove(mCircleBean.getLove() - 1);
                } else {
                    mCircleBean.setIslove(1);
                    mCircleBean.setLove(mCircleBean.getLove() + 1);
                }
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void initScreenBean(RegionsBean regionsBean) {
        if (regionsBean != null && regionsBean.getRegions() != null && regionsBean.getRegions().size() > 0) {
            mScreenBeans = new ArrayList<>();
            mScreenBeans.add(new ScreenBean(-1, "全部", true));
            for (RegionsDataBean dataBean : regionsBean.getRegions()) {
                mScreenBeans.add(new ScreenBean(dataBean.getId(), dataBean.getName()));
            }
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
