package com.thinksoft.banana.ui.fragment.type;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;

import com.google.gson.JsonElement;
import com.orhanobut.logger.Logger;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.home.CatesBean;
import com.thinksoft.banana.entity.bean.type.PerformerBean;
import com.thinksoft.banana.entity.bean.type.PerformerDataBean;
import com.thinksoft.banana.entity.bean.type.ScreenBean;
import com.thinksoft.banana.entity.item.HomeItem;
import com.thinksoft.banana.entity.item.PerformerItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.TypeModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.my.PerformerInfoActivity;
import com.thinksoft.banana.ui.adapter.TypePerformerAdapter;
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
 * @create 2019/3/12 0012
 * @演员
 */
public class TypePerformerFragment
        extends BaseMvpListLjzFragment<PerformerItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener {
    TypePerformerAdapter mAdapter;
    CatesBean mCatesBean;

    List<ScreenBean> mScreens;
    List<ScreenBean> mScreenNames;

    String[] screenTexts2;
    String[] screenTexts1;

    public ScreenBean getScreen() {
        for (ScreenBean bean : mScreens) {
            if (bean.isCheck()) {
                return bean;
            }
        }
        return null;
    }

    public ScreenBean getScreenName() {
        for (ScreenBean bean : mScreenNames) {
            if (bean.isCheck()) {
                return bean;
            }
        }
        return null;
    }

    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return mAdapter = new TypePerformerAdapter(getContext(), this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCatesBean = BundleUtils.getSerializable(getArguments());
        setContract(this, new CommonPresenter(getContext(), this, new TypeModel()));
        initScreenData();
    }

    private void initScreenData() {
        if (screenTexts2 == null)
            screenTexts2 = new String[]{
                    "全部",
                    "A", "B", "C", "D", "E",
                    "F", "G", "H", "I", "J",
                    "K", "L", "M", "N", "O",
                    "P", "Q", "R", "S", "T",
                    "U", "V", "W", "X", "Y",
                    "Z"
            };
        if (screenTexts1 == null)
            screenTexts1 = new String[]{
                    getString(R.string.全部), getString(R.string.人气最高), getString(R.string.片量最多)
            };
        mScreens = new ArrayList<>();
        mScreenNames = new ArrayList<>();
        for (int i = 0; i < screenTexts1.length; i++) {
            if (i == 0) {
                mScreens.add(new ScreenBean(i, screenTexts1[i], true));
            } else {
                mScreens.add(new ScreenBean(i, screenTexts1[i]));
            }
        }
        for (int i = 0; i < screenTexts2.length; i++) {
            if (i == 0) {
                mScreenNames.add(new ScreenBean(i, screenTexts2[i], true));
            } else {
                mScreenNames.add(new ScreenBean(i, screenTexts2[i]));
            }
        }
    }

    private List<PerformerItem> getScreenItem() {
        List<PerformerItem> items = new ArrayList<>();
        items.add(new PerformerItem(mScreens, Constant.TYPE_ITEM_1));
        items.add(new PerformerItem(mScreenNames, Constant.TYPE_ITEM_2));
        return items;
    }


    @Override
    protected void request(int pageIndex, int pageSize) {
        if (pageIndex == 1) {
            initScreenData();
        }
        startScreen(pageIndex);
    }

    private void startScreen(int pageIndex) {
        HashMap<String, Object> maps = new HashMap<>();
        if (!getScreenName().getText().equals(screenTexts2[0]))
            maps.put("letter", getScreenName().getText());
        maps.put("page", pageIndex);

        if (getScreen().getText().equals(getString(R.string.全部))) {

            getPresenter().getData(ApiRequestTask.type.TAG_TYPE_PERFORMER_LIS0T_ALL, maps);

        } else if (getScreen().getText().equals(getString(R.string.人气最高))) {

            getPresenter().getData(ApiRequestTask.type.TAG_TYPE_PERFORMER_LIST_HIGHEST, maps);

        } else if (getScreen().getText().equals(getString(R.string.片量最多))) {

            getPresenter().getData(ApiRequestTask.type.TAG_TYPE_PERFORMER_LIST_MANY, maps);

        }
    }

    private List<PerformerItem> newItem(PerformerBean bean) {

        List<PerformerItem> items = new ArrayList<>();
        if (pageIndex == 1) {
            items.addAll(getScreenItem());
        }
        if (bean == null || bean.getPerformerList() == null || bean.getPerformerList().size() == 0)
            return items;
        for (PerformerDataBean dataBean : bean.getPerformerList())
            items.add(new PerformerItem(dataBean, Constant.TYPE_ITEM_3));

        return items;
    }

    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        switch (action) {
            case Constant.TYPE_ITEM_1:
            case Constant.TYPE_ITEM_2:
                //筛选条件发生改变
                Logger.i(JsonTools.toJSON(getScreen()));
                Logger.i(JsonTools.toJSON(getScreenName()));
                pageIndex = 1;
                startScreen(pageIndex);
                break;

            case Constant.TYPE_ITEM_3:
                PerformerDataBean dataBean = BundleUtils.getSerializable(bundle);
                startActivity(PerformerInfoActivity.getIntent(getContext(), dataBean.getId()));
                break;
        }
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.type.TAG_TYPE_PERFORMER_LIS0T_ALL:
            case ApiRequestTask.type.TAG_TYPE_PERFORMER_LIST_HIGHEST:
            case ApiRequestTask.type.TAG_TYPE_PERFORMER_LIST_MANY:
                PerformerBean bean = JsonTools.fromJson(data, PerformerBean.class);
                refreshData(newItem(bean));
                break;
        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {

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
                    case Constant.TYPE_ITEM_3:
                        return 1;
                    default:
                        return 4;
                }
            }
        };
    }
}
