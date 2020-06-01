package com.thinksoft.banana.ui.activity.home.novel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.NovelTypeDataBean;
import com.thinksoft.banana.entity.item.HomeItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.ui.adapter.NovelTypeListAdapter;
import com.txf.ui_mvplibrary.interfaces.ITitleBar;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpListActivity;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.view.deft.DefaultTitleBar;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/4/9 0009
 * @小说二级分类列表
 */
public class NovelTypeListActivity
        extends BaseMvpListActivity<HomeItem, CommonContract.View, CommonContract.Presenter>
        implements OnAppListener.OnAdapterListener {
    NovelTypeDataBean mNovelTypeDataBean;

    public static Intent getIntent(Context context, NovelTypeDataBean bean) {
        Intent i = new Intent(context, NovelTypeListActivity.class);
        i.putExtra("data", bean);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNovelTypeDataBean = (NovelTypeDataBean) getIntent().getSerializableExtra("data");
        mITitleBar.setTitleText(mNovelTypeDataBean.getName());
    }

    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return new NovelTypeListAdapter(this, this);
    }

    @Override
    protected boolean nextPage(List<HomeItem> datas) {
        return false;
    }

    @Override
    protected void request(int pageIndex, int pageSize) {
        refreshData(newData(mNovelTypeDataBean));
    }

    private List<HomeItem> newData(NovelTypeDataBean bean) {
        List<HomeItem> items = new ArrayList<>();
        if (bean == null || bean.getSon() == null || bean.getSon().size() == 0)
            return items;
        for (NovelTypeDataBean bean1 : bean.getSon()) {
            items.add(new HomeItem(bean1, Constant.TYPE_ITEM_1));
        }
        return items;
    }

    @Override
    public void onInteractionAdapter(int action, Bundle ext) {
        NovelTypeDataBean bean;
        switch (action) {
            case Constant.TYPE_ITEM_1:
                bean = BundleUtils.getSerializable(ext);
                startActivity(NovelListActivity.getIntent(this, bean));
                break;
        }
    }

    @Override
    protected int buildColumnCount() {
        return 4;
    }

    @Override
    protected ITitleBar buildTitleBar() {
        return new DefaultTitleBar(getContext());
    }
}
