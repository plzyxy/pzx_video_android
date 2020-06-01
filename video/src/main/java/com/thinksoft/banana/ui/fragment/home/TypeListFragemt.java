package com.thinksoft.banana.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.home.CatesBean;
import com.thinksoft.banana.entity.bean.home.TypeDataBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.item.HomeItem;
import com.thinksoft.banana.entity.item.TypeItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.HomeModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.player.PlayerActivity;
import com.thinksoft.banana.ui.activity.player.PlayerVerticalActivity;
import com.thinksoft.banana.ui.adapter.TypeListAdapter;
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
public class TypeListFragemt
        extends BaseMvpListLjzFragment<TypeItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener {
    CatesBean mCatesBean;

    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return new TypeListAdapter(getContext(), this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCatesBean = BundleUtils.getSerializable(getArguments());
        setContract(this, new CommonPresenter(getContext(), this, new HomeModel()));
    }

    @Override
    protected void request(final int pageIndex, int pageSize) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("id", mCatesBean.getId());
        maps.put("page", String.valueOf(pageIndex));
        getPresenter().getData(ApiRequestTask.home.TAG_TYPE_DATA, maps);
    }

    private List<TypeItem> newItem(TypeDataBean bean) {
        List<TypeItem> items = new ArrayList<>();
        if (bean == null || bean.getVdieos() == null || bean.getVdieos().size() == 0)
            return items;
        for (VideosBean videosBean : bean.getVdieos()) {
            items.add(new TypeItem(videosBean, Constant.TYPE_ITEM_1));
        }
        return items;
    }

    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        TypeItem item;
        switch (action) {
            case Constant.TYPE_ITEM_1:
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
    public void httpOnSuccess(int tag, JsonElement data, String message) {
        switch (tag) {
            case ApiRequestTask.home.TAG_TYPE_DATA:
                TypeDataBean bean = JsonTools.fromJson(data, TypeDataBean.class);
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
    protected RecyclerView.ItemDecoration buildItemDecoration() {
        return new ItemDecorationCommon(
                2,
                dip2px(6),
                0,
                dip2px(15),
                Constant.TYPE_ITEM_1
        );
    }

}
