package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.bean.type.PerformerDataBean;
import com.thinksoft.banana.entity.bean.type.ScreenBean;
import com.thinksoft.banana.entity.item.CollectItem;
import com.thinksoft.banana.entity.item.PerformerItem;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;

/**
 * @author txf
 * @create 2019/3/12 0012
 * @
 */
public class CollectAdapter extends BaseCompleteRecyclerAdapter<CollectItem> {


    public CollectAdapter(Context context) {
        super(context);
    }

    public CollectAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_collect_performer_content);
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_collect_film_content);
        addItemLayout(Constant.TYPE_ITEM_3, R.layout.item_collect_video_content);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, CollectItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                bindItem1(holder, item);
                break;
            case Constant.TYPE_ITEM_2:
                bindItem2(holder, item);
                break;
            case Constant.TYPE_ITEM_3:
                bindItem3(holder, item);
                break;
        }
    }

    private void bindItem3(BaseViewHoder holder, final CollectItem item) {
        VideosBean videosBean = (VideosBean) item.getData();
        SimpleDraweeView mSimpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
        TextView contentTV1 = holder.getViewById(R.id.contentTV1);
        TextView contentTV2 = holder.getViewById(R.id.contentTV2);
        mSimpleDraweeView.setImageURI(videosBean.getImage());
        contentTV1.setText(videosBean.getTitle());
        contentTV2.setText(videosBean.getHis());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.TYPE_ITEM_3, BundleUtils.putBaseItem(item));
            }
        });
    }

    private void bindItem2(BaseViewHoder holder, final CollectItem item) {
        VideosBean videosBean = (VideosBean) item.getData();
        SimpleDraweeView mSimpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
        TextView contentTV1 = holder.getViewById(R.id.contentTV1);
        TextView contentTV2 = holder.getViewById(R.id.contentTV2);
        mSimpleDraweeView.setImageURI(videosBean.getImage());
        contentTV1.setText(videosBean.getTitle());
        contentTV2.setText(videosBean.getHis());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.TYPE_ITEM_2, BundleUtils.putBaseItem(item));
            }
        });
    }

    private void bindItem1(BaseViewHoder holder, final CollectItem item) {
        final PerformerDataBean dataBean = (PerformerDataBean) item.getData();

        SimpleDraweeView mSimpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
        TextView TV1 = holder.getViewById(R.id.TV1);
        mSimpleDraweeView.setImageURI(dataBean.getImage());
        TV1.setText(dataBean.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.TYPE_ITEM_1, BundleUtils.putSerializable(dataBean));
            }
        });
    }


}
