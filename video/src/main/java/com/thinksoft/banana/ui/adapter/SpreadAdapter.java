package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.SpreadInfoBean;
import com.thinksoft.banana.entity.bean.SpreadListBean;
import com.thinksoft.banana.entity.item.SpreadItem;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/4/2 0002
 * @
 */
public class SpreadAdapter extends BaseCompleteRecyclerAdapter<SpreadItem> {
    public SpreadAdapter(Context context) {
        super(context);
    }

    public SpreadAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_spread_item1);
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_spread_item2);
//        addItemLayout(Constant.TYPE_ITEM_3, R.layout.item_spread_item3);
        addItemLayout(Constant.TYPE_ITEM_4, R.layout.item_spread_item4);
//        addItemLayout(Constant.TYPE_ITEM_5, R.layout.item_spread_item5);
        addItemLayout(Constant.TYPE_ITEM_6, R.layout.item_spread_item6);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, SpreadItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                bindItem1(holder, position, item);

                break;
            case Constant.TYPE_ITEM_2:
                bindItem2(holder, position, item);

                break;
            case Constant.TYPE_ITEM_4:
                bindItem4(holder, position, item);
                break;
            case Constant.TYPE_ITEM_6:
                holder.getViewById(R.id.spreadButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_6, null);
                    }
                });
                break;
        }
    }

    private void bindItem2(BaseViewHoder holder, int position, SpreadItem item) {
        SpreadInfoBean bean = (SpreadInfoBean) item.getData();
        holder.setText(R.id.countTV1, String.valueOf(bean.getFree_count()));
        holder.setText(R.id.countTV2, String.valueOf(bean.getAll_free_count()));
    }

    private void bindItem1(BaseViewHoder holder, int position, SpreadItem item) {
        SpreadInfoBean bean = (SpreadInfoBean) item.getData();
        SimpleDraweeView simpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
        TextView nameTV = holder.getViewById(R.id.nameTV);
        TextView yqmTV = holder.getViewById(R.id.yqmTV);

        SimpleDraweeView img_level_1 = holder.getViewById(R.id.img_level_1);
        TextView tv_level_1 = holder.getViewById(R.id.tv_level_1);
        ProgressBar progressBar = holder.getViewById(R.id.level_progressBar);
        SimpleDraweeView img_level_2 = holder.getViewById(R.id.img_level_2);
        TextView tv_level_2 = holder.getViewById(R.id.tv_level_2);

        simpleDraweeView.setImageURI(bean.getImage());
        nameTV.setText(bean.getNickname());

        img_level_1.setImageURI(bean.getLevel_image());
        tv_level_1.setText(bean.getLevel());
        progressBar.setProgress(bean.getProgress());
        img_level_2.setImageURI(bean.getNext_level_image());
        tv_level_2.setText(bean.getNext_level());

        holder.getViewById(R.id.ewmButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.TYPE_ITEM_1, null);
            }
        });
    }


    private void bindItem4(BaseViewHoder holder, int position, SpreadItem item) {
        SpreadInfoBean bean = (SpreadInfoBean) item.getData();
        TextView tv1 = holder.getViewById(R.id.tv1);
        if (StringTools.isNull(bean.getSpread_note())) {
            tv1.setVisibility(View.GONE);
        } else {
            tv1.setVisibility(View.VISIBLE);
            tv1.setText(bean.getSpread_note());
        }
        RecyclerView recyclerView = holder.getViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setDatas(bean.getSpreadList() == null ? new ArrayList<SpreadListBean>() : bean.getSpreadList());
        mAdapter.notifyDataSetChanged();
    }

    BaseCompleteRecyclerAdapter<SpreadListBean> mAdapter = new BaseCompleteRecyclerAdapter<SpreadListBean>(getContext()) {
        @Override
        protected void setItemLayout() {
            super.setItemLayout();
            addItemLayout(0, R.layout.item_spread_child);
        }

        @Override
        protected void onBindBaseViewHoder(BaseViewHoder holder, int position, SpreadListBean item) {
            SimpleDraweeView level_img = holder.getViewById(R.id.level_img);
            TextView level_tv1 = holder.getViewById(R.id.level_tv1);
            TextView level_tv2 = holder.getViewById(R.id.level_tv2);
            level_img.setImageURI(item.getImage());
            level_tv1.setText(item.getName());
            level_tv2.setText(item.getDescription());
        }
    };
}
