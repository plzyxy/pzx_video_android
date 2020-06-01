package com.thinksoft.banana.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.SpreadInfoBean;
import com.thinksoft.banana.entity.item.SpreadItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.MyModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.adapter.SpreadAdapter;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.utils.ColorUtils;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;
import com.txf.ui_mvplibrary.ui.listener.OnMultiPurposeAdapter;
import com.txf.ui_mvplibrary.ui.view.RefreshLayout;
import com.txf.ui_mvplibrary.ui.view.layoutmanager.OffsetLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/4/2 0002
 * @推广
 */
public class SpreadActivity
        extends BaseMvpActivity<CommonContract.View, CommonContract.Presenter>
        implements OnAppListener.OnAdapterListener, CommonContract.View {
    RefreshLayout mRefreshLayout;
    RecyclerView mRecyclerView;
    SpreadAdapter mAdapter;
    ImageView bgImgeView, iconView;
    TextView titleTV, rightTV;

    int mImgeViewHeight;
    int item1Height;

    SpreadInfoBean mSpreadInfoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_spread;
    }

    @Override
    protected void showLoading() {
        //不在刷新过程中 调用生效
        if (mRefreshLayout.getState() == RefreshState.None)
            super.showLoading();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarTransparent();
        setContract(this, new CommonPresenter(this, new MyModel()));
        initView();
        initListener();
        getData();
    }


    private void initView() {
        bgImgeView = findViewById(R.id.bgImgeView);
        iconView = findViewById(R.id.iconView);
        titleTV = findViewById(R.id.titleTV);
        rightTV = findViewById(R.id.rightTV);

        mRefreshLayout = findViewById(R.id.RefreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);

        mRefreshLayout.getClassicsHeader().setAccentColor(0xffD8AD9B);
        mRefreshLayout.setEnableLoadMore(false);
        mRecyclerView.setLayoutManager(new OffsetLinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter = new SpreadAdapter(getContext(), this));
    }

    private void initListener() {
        mRefreshLayout.setOnMultiPurposeListener(new OnMultiPurposeAdapter() {
            @Override
            public void onRefresh(@NonNull com.scwang.smartrefresh.layout.api.RefreshLayout refreshLayout) {
                getData();
            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight);
                ViewGroup.LayoutParams lp = bgImgeView.getLayoutParams();
                if (mImgeViewHeight == 0) {
                    mImgeViewHeight = bgImgeView.getMeasuredHeight();
                }
                lp.height = mImgeViewHeight + offset;
                bgImgeView.setLayoutParams(lp);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                float value = recyclerView.computeVerticalScrollOffset() * 1.0f / item1Height;
                if (value > 1)
                    value = 1;
                int color_bg = ColorUtils.gradual("#323232", "#d3d3d3", value);
                int color = ColorUtils.gradual("#D8AD9B", "#ffffff", value);

                iconView.setColorFilter(color);
                titleTV.setTextColor(color);
                rightTV.setTextColor(color);
                bgImgeView.setColorFilter(color_bg);
            }
        });

        setOnClick(R.id.backButton, R.id.rightTV, R.id.spreadButton);
    }

    private void getData() {
        getPresenter().getData(ApiRequestTask.my.TAG_SPREAD_INFO);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                finish();
                break;
            case R.id.spreadButton:
            case R.id.rightTV:
                //我的分享
                startActivity(new Intent(this, SpreadLinkActivity.class));
                break;
        }
    }

    @Override
    public void onInteractionAdapter(int action, Bundle ext) {
        switch (action) {
            case Constant.TYPE_ITEM_6:
            case Constant.TYPE_ITEM_1:
                //我的分享
                startActivity(new Intent(this, SpreadLinkActivity.class));
                break;
        }
    }

    private List<SpreadItem> newItems(SpreadInfoBean infoBean) {
        List<SpreadItem> datas = new ArrayList<>();
        if (infoBean == null)
            return datas;
        datas.add(new SpreadItem(infoBean, Constant.TYPE_ITEM_1));
        datas.add(new SpreadItem(infoBean, Constant.TYPE_ITEM_2));
//        datas.add(new SpreadItem(null, Constant.TYPE_ITEM_3));
        datas.add(new SpreadItem(infoBean, Constant.TYPE_ITEM_4));
//        datas.add(new SpreadItem(null, Constant.TYPE_ITEM_5));
//        datas.add(new SpreadItem(null, Constant.TYPE_ITEM_6));
        return datas;
    }

    private void refreshData(List<SpreadItem> datas) {
        mAdapter.setDatas(datas);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh();
        item1Height = dip2px(152);
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.my.TAG_SPREAD_INFO:
                mSpreadInfoBean = JsonTools.fromJson(data, SpreadInfoBean.class);
                refreshData(newItems(mSpreadInfoBean));
                break;
        }
    }


    @Override
    public void httpOnError(int sign, int error, String message) {

    }
}
