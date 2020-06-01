package com.thinksoft.banana.ui.fragment.type;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.home.CatesBean;
import com.thinksoft.banana.entity.bean.home.TypeBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.item.HomeItem;
import com.thinksoft.banana.entity.item.TypeHomeListItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.HomeModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.home.TypeActivity;
import com.thinksoft.banana.ui.activity.player.PlayerActivity;
import com.thinksoft.banana.ui.adapter.TypeFilmAdapter;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationCommon;
import com.txf.ui_mvplibrary.ui.fragment.BaseMvpListLjzFragment;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/18 0018
 * @视频
 */
public class TypeVideoFragemt
        extends BaseMvpListLjzFragment<TypeHomeListItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener {
    CatesBean mCatesBean;
    TypeBean mTypeBean;
    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return new TypeFilmAdapter(getContext(), this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCatesBean = BundleUtils.getSerializable(getArguments());
        setContract(this, new CommonPresenter(getContext(), this, new HomeModel()));
    }

    @Override
    protected boolean nextPage(List<TypeHomeListItem> datas) {
        return false;
    }

    @Override
    protected void request(final int pageIndex, int pageSize) {
        getPresenter().getData(ApiRequestTask.home.TAG_TYPE_LIST);
    }

    private List<TypeHomeListItem> newItem(TypeBean bean) {
        List<TypeHomeListItem> items = new ArrayList<>();
        if (bean == null || bean.getCates() == null || bean.getCates().size() == 0)
            return items;
        for (CatesBean catesBean : bean.getCates()) {
            items.add(new TypeHomeListItem(catesBean, Constant.TYPE_ITEM_2));
        }
        return items;
    }


    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        CatesBean catesBean;
        switch (action) {
            case Constant.TYPE_ITEM_2:
                catesBean = BundleUtils.getSerializable(bundle);
                startActivity(TypeActivity.getIntent(getContext(), new HomeItem(catesBean, 0), mTypeBean.getCates()));
                break;
        }
    }
    @Override
    public void httpOnSuccess(int tag, JsonElement data, String message) {
        switch (tag) {
            case ApiRequestTask.home.TAG_TYPE_LIST:
                mTypeBean = JsonTools.fromJson(data, TypeBean.class);
                refreshData(newItem(mTypeBean));
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
    protected RecyclerView.ItemDecoration buildItemDecoration() {
        return new ItemDecorationCommon(
                2,
                new Rect(dip2px(15), dip2px(15), dip2px(15), dip2px(15)),
                dip2px(8),
                Constant.TYPE_ITEM_2
        );
    }

}
