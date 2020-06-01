package com.thinksoft.banana.ui.fragment.type;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.home.CatesBean;
import com.thinksoft.banana.entity.bean.video.TypeBean;
import com.thinksoft.banana.entity.item.TypeHomeListItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.TypeModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.type.TypeFilmDataActivity;
import com.thinksoft.banana.ui.adapter.TypeFilmAdapter;
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
 * @电影
 */
public class TypeFilmFragemt
        extends BaseMvpListLjzFragment<TypeHomeListItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener {
    CatesBean mCatesBean;

    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return new TypeFilmAdapter(getContext(), this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCatesBean = BundleUtils.getSerializable(getArguments());
        setContract(this, new CommonPresenter(getContext(), this, new TypeModel()));
    }

    @Override
    protected boolean nextPage(List<TypeHomeListItem> datas) {
        return false;
    }

    @Override
    protected void request(int pageIndex, int pageSize) {
        HashMap<String, Object> maps = new HashMap<>();
        getPresenter().getData(ApiRequestTask.type.TAG_VIDEO_TYPE_LIST, maps);
    }

    private List<TypeHomeListItem> newItem(TypeBean typeBean) {
        List<TypeHomeListItem> items = new ArrayList<>();
        if (typeBean == null || typeBean.getCates() == null || typeBean.getCates().size() == 0)
            return items;
        for (CatesBean catesBean : typeBean.getCates()) {
            items.add(new TypeHomeListItem(catesBean, Constant.TYPE_ITEM_1));
        }
        return items;
    }

    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        CatesBean catesBean;
        switch (action) {
            case Constant.TYPE_ITEM_1:
                catesBean = BundleUtils.getSerializable(bundle);
                startActivity(TypeFilmDataActivity.getIntent(getContext(), catesBean));
                break;
        }
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.type.TAG_VIDEO_TYPE_LIST:
                TypeBean bean = JsonTools.fromJson(data, TypeBean.class);
                refreshData(newItem(bean));
                break;
        }
    }

    @Override
    public void httpOnError(int tag, int error, String message) {

    }

    @Override
    protected int buildColumnCount() {
        return 3;
    }

    @Override
    protected RecyclerView.ItemDecoration buildItemDecoration() {
        return new ItemDecorationCommon(
                3,
                new Rect(dip2px(15), dip2px(15), dip2px(15), dip2px(15)),
                dip2px(8),
                Constant.TYPE_ITEM_1
        );
    }

}
