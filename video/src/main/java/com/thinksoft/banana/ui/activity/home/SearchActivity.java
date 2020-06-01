package com.thinksoft.banana.ui.activity.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.orhanobut.logger.Logger;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.home.SearchDataBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.item.HomeItem;
import com.thinksoft.banana.entity.item.SearchItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.HomeModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.player.PlayerActivity;
import com.thinksoft.banana.ui.activity.player.PlayerVerticalActivity;
import com.thinksoft.banana.ui.adapter.SearchAdapter;
import com.thinksoft.banana.ui.view.titlebar.SearchTitleBar;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.ui_mvplibrary.interfaces.ITitleBar;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpListActivity;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationCommon;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/18 0018
 * @
 */
public class SearchActivity extends BaseMvpListActivity<SearchItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener {
    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return new SearchAdapter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAutoRequest(false);
        mRefreshLayout.setEnableRefresh(false);
        setContract(this, new CommonPresenter(this, new HomeModel()));
    }

    private void startSearch(String string) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("title", string);
        getPresenter().getData(ApiRequestTask.home.TAG_SEARCH_VIDEO, maps);
    }

    @Override
    protected boolean nextPage(List<SearchItem> datas) {
        return false;
    }

    @Override
    protected void request(int pageIndex, int pageSize) {

    }

    private List<SearchItem> newItem(SearchDataBean bean) {
        List<SearchItem> items = new ArrayList<>();
        if (bean.getVideos() == null || bean.getVideos() == null || bean.getVideos().size() == 0) {
            items.add(new SearchItem("没有找到你搜索的电影", Constant.TYPE_NULL));
            return items;
        }
        items.add(new SearchItem(null, Constant.TYPE_ITEM_1));
        for (VideosBean videosBean : bean.getVideos()) {
            items.add(new SearchItem(videosBean, Constant.TYPE_ITEM_2));
        }
        return items;
    }


    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        SearchItem item;
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
    public void onInteractionView(int action, Bundle bundle) {
        super.onInteractionView(action, bundle);
        switch (action) {
            case Constant.ACTION_HOME_INPUT_TEXT:
                String text = BundleUtils.getString(bundle);
                Logger.i("搜索文字 : " + text);
                if (StringTools.isNull(text)) {
                    refreshData(new ArrayList<SearchItem>());
                } else {
                    startSearch(text);
                }
                break;
        }
    }

    @Override
    public void httpOnSuccess(int tag, JsonElement data, String message) {
        switch (tag) {
            case ApiRequestTask.home.TAG_SEARCH_VIDEO:
                SearchDataBean bean = JsonTools.fromJson(data, SearchDataBean.class);
                refreshData(newItem(bean));
                break;
        }
    }

    @Override
    public void httpOnError(int tag, int error, String message) {

    }

    @Override
    protected int buildColumnCount() {
        return 2;
    }

    @Override
    protected ITitleBar buildTitleBar() {
        return new SearchTitleBar(this);
    }

    @Override
    protected GridLayoutManager.SpanSizeLookup buildSpanSizeLookup() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getItemViewType(position)) {
                    case Constant.TYPE_ITEM_1:
                        return 2;
                    case Constant.TYPE_ITEM_2:
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
                Constant.TYPE_ITEM_2);
    }

}
