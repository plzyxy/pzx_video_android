package com.thinksoft.banana.ui.activity.type;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.home.CatesBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.bean.video.BannerBean;
import com.thinksoft.banana.entity.bean.video.TypeDataBean;
import com.thinksoft.banana.entity.item.HomeItem;
import com.thinksoft.banana.entity.item.TypeFilmItem;
import com.thinksoft.banana.entity.item.VideoItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.TypeModel;
import com.thinksoft.banana.mvp.model.VideoModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.player.PlayerActivity;
import com.thinksoft.banana.ui.activity.player.PlayerVerticalActivity;
import com.thinksoft.banana.ui.adapter.TypeFilmDataAdapter;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.ui_mvplibrary.interfaces.ITitleBar;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpListActivity;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationCommon;
import com.txf.ui_mvplibrary.ui.view.deft.DefaultTitleBar;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/3/12 0012
 * @电影类型 展示页面
 */
public class TypeFilmDataActivity
        extends BaseMvpListActivity<TypeFilmItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener {

    CatesBean mCatesBean;

    public static Intent getIntent(Context context, CatesBean bean) {
        Intent i = new Intent(context, TypeFilmDataActivity.class);
        i.putExtra("data", bean);
        return i;
    }

    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return new TypeFilmDataAdapter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(this, new VideoModel()));
        mCatesBean = (CatesBean) getIntent().getSerializableExtra("data");
        mITitleBar.setTitleText(mCatesBean.getName());
    }

    @Override
    protected void request(int pageIndex, int pageSize) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("id", mCatesBean.getId());
        maps.put("page", pageIndex);
        getPresenter().getData(ApiRequestTask.video.TAG_VIDEO_TYPE_DATA_LIST, maps);
    }

    private List<TypeFilmItem> newItem(TypeDataBean typeDataBean) {
        List<TypeFilmItem> items = new ArrayList<>();
        if (typeDataBean != null && typeDataBean.getVdieos() != null && typeDataBean.getVdieos().size() > 0) {
            for (VideosBean videosBean : typeDataBean.getVdieos()) {
                items.add(new TypeFilmItem(videosBean, Constant.TYPE_ITEM_1));
            }
        }
        return items;
    }

    @Override
    public void onInteractionAdapter(int action, Bundle ext) {
        TypeFilmItem item;
        switch (action) {
            case Constant.TYPE_ITEM_1:
                item = BundleUtils.getBaseItem(ext);
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
    protected ITitleBar buildTitleBar() {
        return new DefaultTitleBar(this);
    }

    @Override
    protected RecyclerView.ItemDecoration buildItemDecoration() {
        return new ItemDecorationCommon(
                1,
                dip2px(15),
                dip2px(15),
                dip2px(15),
                Constant.TYPE_ITEM_1
        );
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
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
