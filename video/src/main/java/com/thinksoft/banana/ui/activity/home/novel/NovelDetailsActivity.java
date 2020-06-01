package com.thinksoft.banana.ui.activity.home.novel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.NovelBean;
import com.thinksoft.banana.entity.bean.NovelInfoBean;
import com.thinksoft.banana.entity.bean.NovelInfoDataBean;
import com.thinksoft.banana.entity.bean.circle.CircleCommentBean;
import com.thinksoft.banana.entity.bean.circle.CircleCommentListBean;
import com.thinksoft.banana.entity.bean.circle.HttpImgBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.HomeModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.PayWebActivity;
import com.thinksoft.banana.ui.activity.circle.CirleImgActivity;
import com.thinksoft.banana.ui.activity.my.SpreadLinkActivity;
import com.thinksoft.banana.ui.adapter.NovelDetailsAdapter;
import com.thinksoft.banana.ui.view.navigation.CircleNavigationBar;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.interfaces.INavigationBar;
import com.txf.ui_mvplibrary.interfaces.ITitleBar;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpListActivity;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.view.deft.DefaultTitleBar;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/3/21 0021
 * @小说详情页
 */
public class NovelDetailsActivity
        extends BaseMvpListActivity<CircleItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener {
    CircleNavigationBar mNavigationBar;
    int mNovelId;
    NovelInfoBean mNovelInfoBean;

    NovelInfoDataBean mCircleBean;
    CircleCommentBean mCommentBean;

    public static Intent getIntent(Context context, NovelBean bean) {
        Intent i = new Intent(context, NovelDetailsActivity.class);
        i.putExtra("data", bean.getId());
        return i;
    }

    @Override
    protected boolean nextPage(List<CircleItem> datas) {
        if (datas == null || datas.size() <= 0) {
            return false;
        } else if (getSize(datas, Constant.TYPE_ITEM_2) < pageSize) {
            return false;
        } else {
            return true;
        }
    }

    private int getSize(List<CircleItem> datas, int itemType) {
        int size = 0;
        for (CircleItem item : datas) {
            if (item.getItemType() == itemType) {
                size++;
            }
        }
        return size;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(getContext(), new HomeModel()));
        mNovelId = (int) getIntent().getSerializableExtra("data");
        mITitleBar.setTitleText(getString(R.string.详情));
    }


    private void requestInfo() {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("id", mNovelId);
        getPresenter().getData(ApiRequestTask.home.TAG_NOVEL_INFO, maps);
    }

    private void requestCommentList(int pageIndex) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("id", mNovelId);
        maps.put("page", pageIndex);
        getPresenter().getData(ApiRequestTask.home.TAG_NOVEL_COMMENT, maps);
    }

    @Override
    protected BaseRecyclerAdapter<CircleItem> buildAdapter() {
        return new NovelDetailsAdapter(this, this);
    }

    @Override
    protected void request(int pageIndex, int pageSize) {
        if (pageIndex == 1) {
            requestInfo();
        } else {
            requestCommentList(pageIndex);
        }
    }


    private List<CircleItem> newItem(CircleCommentListBean bean) {
        List<CircleItem> items = new ArrayList<>();
        if (pageIndex == 1) {
            if (mNovelInfoBean != null && mNovelInfoBean.getNovelInfo() != null) {
                //图片
                if (mNovelInfoBean.getNovelInfo().getImages() != null && mNovelInfoBean.getNovelInfo().getImages().size() > 0)
                    items.add(new CircleItem(mNovelInfoBean.getNovelInfo().getImages(), Constant.TYPE_ITEM_3));
                //详情
                items.add(new CircleItem(mNovelInfoBean, Constant.TYPE_ITEM_1));
            }
        }
        if (bean == null || bean.getCommentList() == null || bean.getCommentList().size() <= 0)
            return items;

        for (CircleCommentBean commentBean : bean.getCommentList()) {
            items.add(new CircleItem(commentBean, Constant.TYPE_ITEM_2));
        }
        return items;
    }

    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        HashMap<String, Object> maps;
        switch (action) {
            case Constant.ACTION_CIRCLE_DETAILS:
                break;
            case Constant.ACTION_CIRCLE_SHARE:
                //分享
                startActivity(new Intent(getContext(), SpreadLinkActivity.class));
                break;
            case Constant.ACTION_CIRCLE_Z:
                mCircleBean = BundleUtils.getSerializable(bundle);
                //点赞信息
                maps = new HashMap<>();
                maps.put("id", mCircleBean.getId());
                getPresenter().getData(ApiRequestTask.home.TAG_NOVEL_LOVE, maps);
                break;
            case Constant.ACTION_CIRCLE_COMMENT_Z:
                mCommentBean = BundleUtils.getSerializable(bundle);
                //点赞 评论
                maps = new HashMap<>();
                maps.put("id", mCommentBean.getId());
                getPresenter().getData(ApiRequestTask.home.TAG_NOVEL_COMMENT_LOVE, maps);
                break;
            case Constant.TYPE_ITEM_3:
                HttpImgBean imgBean = BundleUtils.getSerializable(bundle);
                ArrayList<HttpImgBean> imgs = (ArrayList<HttpImgBean>) bundle.getSerializable("ext");
                startActivity(CirleImgActivity.getIntent(getContext(), imgBean, imgs));
                break;
            case Constant.ACTION_NOVEL_DETAILS_VIP:
                new Builder().
                        setWith(1).
                        setContent(getString(R.string.成为会员全站通行)).
                        setButton2(getString(R.string.算了吧)).
                        setButton3(getString(R.string.立即升级)).
                        show();
                break;
        }
    }

    boolean isResume;

    @Override
    protected void onInteractionDialog(DialogInterface dialog, int which, int with, Bundle ext) {
        super.onInteractionDialog(dialog, which, with, ext);
        switch (with) {
            case 1:
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    isResume = true;
                    startActivity(new Intent(this, PayWebActivity.class));
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isResume) {
            isResume = false;
            pageIndex = 1;
            startRequest(pageIndex, pageSize);
        }
    }

    @Override
    public void onInteractionView(int action, Bundle bundle) {
        super.onInteractionView(action, bundle);
        HashMap maps;
        switch (action) {
            case Constant.ACTION_SEND_TEXT:
                String commentText = BundleUtils.getString(bundle);
                maps = new HashMap<>();
                maps.put("id", mNovelId);
                maps.put("content", commentText);
                getPresenter().getData(ApiRequestTask.home.TAG_NOVEL_SEND_COMMENT, maps);
                break;
        }
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
//            case ApiRequestTask.home.TAG_NOVEL_BANNER:
//                //Banner 图
//                mBannerBean = JsonTools.fromJson(data, BannerBean.class);
//                requestInfo();
//                break;
            case ApiRequestTask.home.TAG_NOVEL_INFO:
                mNovelInfoBean = JsonTools.fromJson(data, NovelInfoBean.class);
                pageIndex = 1;
                requestCommentList(pageIndex);
                break;
            case ApiRequestTask.home.TAG_NOVEL_COMMENT:
                CircleCommentListBean commentListBean = JsonTools.fromJson(data, CircleCommentListBean.class);
                refreshData(newItem(commentListBean));
                break;
            case ApiRequestTask.home.TAG_NOVEL_LOVE:
                //点赞 信息
                if (mCircleBean.getIslove() == 1) {
                    mCircleBean.setIslove(0);
                    mCircleBean.setLove(mCircleBean.getLove() - 1);
                } else {
                    mCircleBean.setIslove(1);
                    mCircleBean.setLove(mCircleBean.getLove() + 1);
                }
                mAdapter.notifyDataSetChanged();
                break;
            case ApiRequestTask.home.TAG_NOVEL_COMMENT_LOVE:
                //点赞 评论
                if (mCommentBean.getIslove() == 1) {
                    mCommentBean.setIslove(0);
                } else {
                    mCommentBean.setIslove(1);
                }
                mAdapter.notifyDataSetChanged();
                break;
            case ApiRequestTask.home.TAG_NOVEL_SEND_COMMENT:
                //发送评论成功
                ToastUtils.show(getString(R.string.发送成功等待管理员审核通过));
                mRefreshLayout.autoRefresh();
                mNavigationBar.sendSuccess();
                break;

        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {
        switch (sign) {
            case ApiRequestTask.home.TAG_NOVEL_SEND_COMMENT:
                //发送评论失败
                mNavigationBar.sendError();
                break;
        }
    }

    @Override
    protected ITitleBar buildTitleBar() {
        return new DefaultTitleBar(this);
    }

    @Override
    protected INavigationBar buildNavigationBar() {
        return mNavigationBar = new CircleNavigationBar(this);
    }
}
