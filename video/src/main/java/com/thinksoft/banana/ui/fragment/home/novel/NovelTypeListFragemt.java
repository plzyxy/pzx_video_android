package com.thinksoft.banana.ui.fragment.home.novel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.gson.JsonElement;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.ADBean;
import com.thinksoft.banana.entity.bean.NovelBean;
import com.thinksoft.banana.entity.bean.NovelListBean;
import com.thinksoft.banana.entity.bean.NovelTypeDataBean;
import com.thinksoft.banana.entity.bean.home.BannersBean;
import com.thinksoft.banana.entity.bean.video.BannerBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.HomeModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.home.novel.NovelDetailsActivity;
import com.thinksoft.banana.ui.activity.my.SpreadLinkActivity;
import com.thinksoft.banana.ui.adapter.NovelListAdapter;
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
public class NovelTypeListFragemt
        extends BaseMvpListLjzFragment<CircleItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener{
    NovelTypeDataBean mNovelTypeDataBean;

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
        mNovelTypeDataBean = BundleUtils.getSerializable(getArguments());
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
        maps.put("cate_one", mNovelTypeDataBean.getParent_id());
        maps.put("cate_two", mNovelTypeDataBean.getId());
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

}
