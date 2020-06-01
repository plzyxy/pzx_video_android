package com.txf.ui_mvplibrary.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.txf.ui_mvplibrary.R;
import com.txf.ui_mvplibrary.bean.BaseItem;
import com.txf.ui_mvplibrary.interfaces.INavigationBar;
import com.txf.ui_mvplibrary.interfaces.ITitleBar;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.mvp.presenter.BasePresenter;
import com.txf.ui_mvplibrary.mvp.view.BaseView;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationGrid;
import com.txf.ui_mvplibrary.ui.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * @author txf
 * @create 2019/2/1 0001
 * 1.三段式布局 头部  内容区域  导航栏
 * 2.头部 导航栏 默认为空 通过方法 {@link #buildTitleBar()}{@link #buildNavigationBar()}} 添加不同的布局
 * 3.内容区域为 刷新控件内置RecyclerView
 * <p>
 * 4.Recycler配置 :
 * 必须重写方法 {@link #buildAdapter()}配置Adapter
 * 可选重写方法 {@link #buildItemDecoration()}配置ItemDecoration
 * 可选重写方法 {@link #buildSpanSizeLookup()} 配置SpanSizeLookup
 * 可选重写方法 {@link #buildColumnCount()}配置ColumnCount RecyclerView布局列数
 * <p>
 * 5.刷新控件配置:
 * 可选重写方法{@link #nextPage(List)}配置是否还有下一页
 * <p>
 * 6.数据相关配置:
 * 调用方法{@link #refreshData(List)}刷新数据 基类已实现 判断是刷新数据 还是加载数据
 * 必须重写方法{@link #request(int, int)}请求数据
 * <p>
 * 7.重写 {@link #showLoading()} 刷新过程不会出现loading
 */

public abstract class BaseMvpListFragment<Data extends BaseItem, V extends BaseView, P extends BasePresenter<V>> extends BaseMvpFragment<V, P>
        implements OnAppListener.OnViewListener {
    protected ITitleBar mITitleBar;
    protected INavigationBar mINavigationBar;

    protected RelativeLayout mTitleBarRoot;
    protected RefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected RelativeLayout mNavigationRoot;

    protected BaseRecyclerAdapter<Data> mAdapter;
    protected GridLayoutManager mManager;
    protected RecyclerView.ItemDecoration mItemDecoration;

    private int mColumnCount = 1;//列数
    protected int pageIndex = 1;//页码
    protected int pageSize = 10;//每页条数

    protected long tmie = 30 * 60000;//Resume自动刷新数据时间间隔
    protected long lastTime;

    @Override
    protected int getLayoutId() {
        return R.layout.libs_fragment_base_mvp_recyclerview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initExtLayout();
        initRecycler();
        initRefresh();
    }

    private void initView() {
        mTitleBarRoot = findViewById(R.id.titleBarRoot);
        mRefreshLayout = findViewById(R.id.RefreshLayout);
        mRecyclerView = findViewById(R.id.RecyclerView);
        mNavigationRoot = findViewById(R.id.NavigationRoot);
    }

    private void initRefresh() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(final com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                //刷新
                pageIndex = 1;
                mAdapter.setDatas(new ArrayList<Data>());
                mRefreshLayout.finishLoadMore();//结束加载
                startRequest(pageIndex, pageSize);
            }

            @Override
            public void onLoadMore(final com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                //加载
                pageIndex++;
                startRequest(pageIndex, pageSize);
            }
        });
    }

    protected void setContentBackgroundColor(int color) {
        mRefreshLayout.setBackgroundColor(color);
    }

    private void initRecycler() {
        mRecyclerView.setLayoutManager(mManager = new GridLayoutManager(getContext(), mColumnCount = buildColumnCount()));
        mRecyclerView.setAdapter(mAdapter = buildAdapter());
        mRecyclerView.addItemDecoration(mItemDecoration = buildItemDecoration());
        mManager.setSpanSizeLookup(buildSpanSizeLookup());
    }

    private void initExtLayout() {
        if (buildTitleBar() != null) {
            mITitleBar = buildTitleBar();
            mTitleBarRoot.addView(mITitleBar.getView(), mITitleBar.getViewLayoutParams());
        }
        if (buildNavigationBar() != null) {
            mINavigationBar = buildNavigationBar();
            mNavigationRoot.addView(mINavigationBar.getView(), mINavigationBar.getViewLayoutParams());
        }
    }


    protected boolean nextPage(List<Data> datas) {
        if (datas == null || datas.size() <= 0) {
            return false;
        } else if (datas.size() < pageSize) {
            return false;
        } else {
            return true;
        }
    }

    public void refreshData(List<Data> datas) {
        if (mAdapter == null) {
            throw new NullPointerException("重写buildAdapter()方法");
        }
        if (pageIndex == 1) {//刷新数据
            mRefreshLayout.finishRefresh();//结束刷新
            mAdapter.setDatas(datas);
            if (nextPage(datas)) {
                mRefreshLayout.resetNoMoreData();
                mRefreshLayout.setEnableLoadMore(true);
            } else {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        } else {//加载数据
            mRefreshLayout.finishLoadMore();//结束加载
            mAdapter.addDatas(datas);
            if (nextPage(datas)) {
                mRefreshLayout.resetNoMoreData();
                mRefreshLayout.setEnableLoadMore(true);
            } else {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
        mAdapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void onResume() {
        super.onResume();
        onBaseResume();
    }

    protected void onBaseResume() {
        if (System.currentTimeMillis() - lastTime > tmie) {
            lastTime = System.currentTimeMillis();
            startRequest(pageIndex, pageSize);
        }
    }

    protected void startRequest(int pageIndex, int pageSize) {
        showLoading();
        request(pageIndex, pageSize);
    }

    @Override
    protected void showLoading() {
        //不在刷新过程中 调用生效
        if (mRefreshLayout.getState() == RefreshState.None) {
            super.showLoading();
        }
    }

    @Override
    public void showILoading() {
        showLoading();
    }

    @Override
    public void onInteractionView(int action, Bundle bundle) {
        if (action == ACTION_CLICK_FINISH_ACTIVITY) {
            getActivity().finish();
        }
    }

    protected abstract BaseRecyclerAdapter<Data> buildAdapter();

    protected abstract void request(int pageIndex, int pageSize);

    protected INavigationBar buildNavigationBar() {
        return null;
    }

    protected ITitleBar buildTitleBar() {
        return null;
    }

    protected GridLayoutManager.SpanSizeLookup buildSpanSizeLookup() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        };
    }

    protected RecyclerView.ItemDecoration buildItemDecoration() {
        return new ItemDecorationGrid(mColumnCount, 0, false);
    }

    protected int buildColumnCount() {
        return mColumnCount;
    }
}
