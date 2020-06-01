package com.thinksoft.banana.ui.fragment.home.novel;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.JsonElement;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.NovelTypeBean;
import com.thinksoft.banana.entity.bean.NovelTypeDataBean;
import com.thinksoft.banana.entity.bean.video.BannerBean;
import com.thinksoft.banana.entity.item.HomeItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.HomeModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.home.novel.NovelTypeListActivity;
import com.thinksoft.banana.ui.adapter.NovelAdapter;
import com.thinksoft.banana.ui.adapter.item_decoration.ItemDecorationHome;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.fragment.BaseMvpListLjzFragment;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/16
 * 小说分类
 */
public class NovelTypeFragment
        extends BaseMvpListLjzFragment<HomeItem, CommonContract.View, CommonContract.Presenter>
        implements OnAppListener.OnAdapterListener, CommonContract.View, View.OnClickListener {
    BannerBean mBannerBean;

    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return new NovelAdapter(getContext(), this);
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
        requestBanner();
    }

    private void requestBanner() {
        getPresenter().getData(ApiRequestTask.home.TAG_NOVEL_BANNER);
    }

    private void requestType() {
        getPresenter().getData(ApiRequestTask.home.TAG_NOVEL_TYPE);
    }

    @Override
    protected boolean nextPage(List<HomeItem> datas) {
        return false;
    }

    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        HomeItem item;
        NovelTypeDataBean dataBean;
        switch (action) {
            case Constant.TYPE_ITEM_2:
                //一级分类
                dataBean = BundleUtils.getSerializable(bundle);
                startActivity(NovelTypeListActivity.getIntent(getContext(), dataBean));
                break;
//            case Constant.TYPE_ITEM_3:
//                //二级分类
//                dataBean = BundleUtils.getSerializable(bundle);
//                break;
//            case Constant.TYPE_ITEM_4:
//                //一级分类
//                dataBean = BundleUtils.getSerializable(bundle);
//                break;
        }
    }

    private List<HomeItem> newItem(NovelTypeBean bean) {
        List<HomeItem> items = new ArrayList<>();
        if (bean == null)
            return items;

        //banner
        if (mBannerBean != null && mBannerBean.getBanners().size() > 0) {
            items.add(new HomeItem(mBannerBean.getBanners(), Constant.TYPE_ITEM_1));
        }
        //分类数据
        if (bean.getList() != null && bean.getList().size() > 0) {
            for (int i = 0; i < bean.getList().size(); i++) {
                NovelTypeDataBean dataBean = bean.getList().get(i);
                items.add(new HomeItem(dataBean, Constant.TYPE_ITEM_2));
            }
//            //视频分类展示
//            for (int i = 0; i < bean.getList().size(); i++) {
//                NovelTypeDataBean dataBean = bean.getList().get(i);
//                if (dataBean.getSon() != null && dataBean.getSon().size() > 0) {
//                    items.add(new HomeItem(dataBean, Constant.TYPE_ITEM_4));
//                    for (NovelTypeDataBean sonBean : dataBean.getSon()) {
//                        items.add(new HomeItem(sonBean, Constant.TYPE_ITEM_3));
//                    }
//                }
//            }
        }
        return items;
    }

    @Override
    public void httpOnSuccess(int tag, JsonElement data, String message) {
        switch (tag) {
            case ApiRequestTask.home.TAG_NOVEL_BANNER:
                //Banner 图
                mBannerBean = JsonTools.fromJson(data, BannerBean.class);
                requestType();
                break;
            case ApiRequestTask.home.TAG_NOVEL_TYPE:
                //分类数据
                NovelTypeBean bean = JsonTools.fromJson(data, NovelTypeBean.class);
                refreshData(newItem(bean));
                break;
        }
    }


    @Override
    public void httpOnError(int tag, int error, String message) {

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
