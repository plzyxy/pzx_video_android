package com.thinksoft.banana.ui.fragment.home;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.home.CatesBean;
import com.thinksoft.banana.entity.bean.home.CatevideosBean;
import com.thinksoft.banana.entity.bean.home.HomeBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.item.HomeItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.HomeModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.home.SearchActivity;
import com.thinksoft.banana.ui.activity.home.TypeActivity;
import com.thinksoft.banana.ui.activity.player.PlayerActivity;
import com.thinksoft.banana.ui.activity.player.PlayerVerticalActivity;
import com.thinksoft.banana.ui.adapter.HomeAdapter;
import com.thinksoft.banana.ui.adapter.item_decoration.ItemDecorationHome;
import com.thinksoft.banana.ui.view.titlebar.HomeTitleBar;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.ui_mvplibrary.interfaces.ITitleBar;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationCommon;
import com.txf.ui_mvplibrary.ui.fragment.BaseMvpListFragment;
import com.txf.ui_mvplibrary.ui.fragment.BaseMvpListLjzFragment;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/16
 * 视频
 */
public class HomeFragment
        extends BaseMvpListLjzFragment<HomeItem, CommonContract.View, CommonContract.Presenter>
        implements OnAppListener.OnAdapterListener, CommonContract.View, View.OnClickListener {
    HomeBean mHomeBean;

    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return new HomeAdapter(getContext(), this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(getContext(), this, new HomeModel()));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(R.id.searchButton).setOnClickListener(this);
    }

    @Override
    protected void request(int pageIndex, int pageSize) {
        getPresenter().getData(ApiRequestTask.home.TAG_HOME_LIST);
    }

    @Override
    protected boolean nextPage(List<HomeItem> datas) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchButton:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
        }
    }

    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        HomeItem item;
        switch (action) {
            case Constant.TYPE_ITEM_2:
                item = BundleUtils.getBaseItem(bundle);
                startActivity(TypeActivity.getIntent(getContext(), item, mHomeBean.getCates()));
                break;
            case Constant.TYPE_ITEM_3:
                item = BundleUtils.getBaseItem(bundle);
                VideosBean mVideosBean = (VideosBean) item.getData();
                if (mVideosBean.getScreen_type() == 2) {
                    startActivity(PlayerVerticalActivity.getIntent(getContext(), mVideosBean.getId()));
                } else {
                    startActivity(PlayerActivity.getIntent(getContext(), mVideosBean.getId()));
                }
                break;
            case Constant.TYPE_ITEM_4:
                CatevideosBean bean = BundleUtils.getSerializable(bundle);
                CatesBean catesBean = new CatesBean(bean.getId(),bean.getName());
                startActivity(TypeActivity.getIntent(getContext(), catesBean, mHomeBean.getCates()));
                break;

        }
    }

    private List<HomeItem> newItem(HomeBean bean) {
        List<HomeItem> items = new ArrayList<>();
        if (bean == null)
            return items;

        //banner
        if (bean.getBanners() != null && bean.getBanners().size() > 0) {
            items.add(new HomeItem(bean.getBanners(), Constant.TYPE_ITEM_1));
        }

        //分类数据
        if (bean.getCates() != null && bean.getCates().size() > 0) {
            int size = bean.getCates().size() > 7 ? 8 : bean.getCates().size();
            for (int i = 0; i < size; i++) {
                items.add(new HomeItem(bean.getCates().get(i), Constant.TYPE_ITEM_2));
            }
        }


        //猜你喜欢
        if (bean.getVideos() != null && bean.getVideos().size() > 0) {
            items.add(new HomeItem(new CatevideosBean(-1, getString(R.string.猜你喜欢)), Constant.TYPE_ITEM_4));
            for (VideosBean bean1 : bean.getVideos()) {
                items.add(new HomeItem(bean1, Constant.TYPE_ITEM_3));
            }
        }

        //视频分类展示
        if (bean.getCatevideos() != null && bean.getCatevideos().size() > 0) {
            for (CatevideosBean catevideosBean : bean.getCatevideos()) {
                if (catevideosBean.getVideos() != null && catevideosBean.getVideos().size() > 0) {
                    items.add(new HomeItem(catevideosBean, Constant.TYPE_ITEM_4));
                    for (VideosBean videosBean : catevideosBean.getVideos()) {
                        items.add(new HomeItem(videosBean, Constant.TYPE_ITEM_3));
                    }
                }
            }
        }
        return items;
    }

    @Override
    public void httpOnSuccess(int tag, JsonElement data, String message) {
        switch (tag) {
            case ApiRequestTask.home.TAG_HOME_LIST:
                mHomeBean = JsonTools.fromJson(data, HomeBean.class);
                refreshData(newItem(mHomeBean));
                break;
        }
    }
    @Override
    public void httpOnError(int tag, int error, String message) {

    }

    @Override
    protected ITitleBar buildTitleBar() {
        return new HomeTitleBar(getContext());
    }

    @Override
    protected int buildColumnCount() {
        return 4;
    }

    @Override
    protected GridLayoutManager.SpanSizeLookup buildSpanSizeLookup() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getItemViewType(position)) {
                    case Constant.TYPE_ITEM_1:
                        return 4;
                    case Constant.TYPE_ITEM_2:
                        return 1;
                    case Constant.TYPE_ITEM_3:
                        return 2;
                    default:
                        return 4;
                }
            }
        };
    }

    @Override
    protected RecyclerView.ItemDecoration buildItemDecoration() {
        return new ItemDecorationHome(
                2,
                new Rect(dip2px(15), 0, dip2px(15), 0),
                dip2px(8),
                Constant.TYPE_ITEM_3
        );
    }
}
