package com.thinksoft.banana.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.home.BannersBean;
import com.thinksoft.banana.entity.bean.home.CatesBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.bean.video.BannerBean;
import com.thinksoft.banana.entity.bean.video.TypeDataBean;
import com.thinksoft.banana.entity.item.HomeItem;
import com.thinksoft.banana.entity.item.PlayerItem;
import com.thinksoft.banana.entity.item.TypeItem;
import com.thinksoft.banana.entity.item.VideoItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.HomeModel;
import com.thinksoft.banana.mvp.model.VideoModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.player.PlayerActivity;
import com.thinksoft.banana.ui.activity.player.PlayerVerticalActivity;
import com.thinksoft.banana.ui.adapter.TypeListAdapter;
import com.thinksoft.banana.ui.adapter.VideoListAdapter;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationCommon;
import com.txf.ui_mvplibrary.ui.fragment.BaseMvpListLjzFragment;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/18 0018
 * @
 */
public class VideoListFragemt
        extends BaseMvpListLjzFragment<VideoItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener {
    CatesBean mCatesBean;
    BannerBean mBannerBean;

    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return new VideoListAdapter(getContext(), this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCatesBean = BundleUtils.getSerializable(getArguments());
        setContract(this, new CommonPresenter(getContext(), this, new VideoModel()));
    }

    @Override
    protected boolean nextPage(List<VideoItem> datas) {
        if (datas == null || datas.size() <= 0) {
            return false;
        } else if (getSize(datas, Constant.TYPE_ITEM_2) < pageSize) {
            return false;
        } else {
            return true;
        }
    }

    private int getSize(List<VideoItem> datas, int itemType) {
        int size = 0;
        for (VideoItem item : datas) {
            if (item.getItemType() == itemType) {
                size++;
            }
        }
        return size;
    }

    @Override
    protected void request(final int pageIndex, int pageSize) {
        if (pageIndex == 1) {
            getPresenter().getData(ApiRequestTask.video.TAG_VIDEO_BANNER);
        } else {
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("id", mCatesBean.getId());
            maps.put("page", pageIndex);
            getPresenter().getData(ApiRequestTask.video.TAG_VIDEO_TYPE_DATA_LIST, maps);
        }
    }


    private List<VideoItem> newItem(TypeDataBean typeDataBean) {
        List<VideoItem> items = new ArrayList<>();

        if (pageIndex == 1) {
            if (mBannerBean != null && mBannerBean.getBanners() != null && mBannerBean.getBanners().size() > 0)
                items.add(new VideoItem(mBannerBean.getBanners(), Constant.TYPE_ITEM_1));
        }
        if (typeDataBean != null && typeDataBean.getVdieos() != null && typeDataBean.getVdieos().size() > 0) {
            for (VideosBean videosBean : typeDataBean.getVdieos()) {
                items.add(new VideoItem(videosBean, Constant.TYPE_ITEM_2));
            }
        }
        return items;
    }

    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        VideoItem item;
        switch (action) {
            case Constant.TYPE_ITEM_2:
                item = BundleUtils.getBaseItem(bundle);
                VideosBean mVideosBean = (VideosBean) item.getData();
                if (mVideosBean.getScreen_type() == 2) {
                    startActivity(PlayerVerticalActivity.getIntent(getContext(), mVideosBean.getId()));
                } else {
                    startActivity(PlayerActivity.getIntent(getContext(), mVideosBean.getId()));
                }
                break;
        }
    }

    @Override
    protected int buildColumnCount() {
        return 1;
    }

    @Override
    protected RecyclerView.ItemDecoration buildItemDecoration() {
        return new ItemDecorationCommon(
                1,
                dip2px(15),
                dip2px(15),
                dip2px(15),
                Constant.TYPE_ITEM_2
        );
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.video.TAG_VIDEO_BANNER:
                mBannerBean = JsonTools.fromJson(data, BannerBean.class);

                pageIndex = 1;
                HashMap<String, Object> maps = new HashMap<>();
                maps.put("id", mCatesBean.getId());
                maps.put("page", pageIndex);
                getPresenter().getData(ApiRequestTask.video.TAG_VIDEO_TYPE_DATA_LIST, maps);
                break;
            case ApiRequestTask.video.TAG_VIDEO_TYPE_DATA_LIST:
                TypeDataBean typeDataBean = JsonTools.fromJson(data, TypeDataBean.class);
                refreshData(newItem(typeDataBean));
                break;
        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {

    }
}
