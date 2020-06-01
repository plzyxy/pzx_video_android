package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.donkingliang.imageselector.constant.Constants;
import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.circle.HttpImgBean;
import com.thinksoft.banana.entity.bean.circle.VideoBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author txf
 * @create 2019/3/21 0021
 * @
 */
public class CircleImgListAdapter extends BaseCompleteRecyclerAdapter<CircleItem> {
    public CircleImgListAdapter(Context context) {
        super(context);
    }

    public CircleImgListAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_circle_imglist_img);
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_circle_imglist_video);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, CircleItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                bindItem1(holder, position, item);


                break;
            case Constant.TYPE_ITEM_2:
                bindItem2(holder, position, item);

                break;
        }
    }

    private void bindItem2(BaseViewHoder holder, int position, final CircleItem item) {
        final VideoBean bean = (VideoBean) item.getData();

        SimpleDraweeView imgView = holder.getViewById(R.id.imgView);
        View view = holder.getViewById(R.id.view);
        imgView.setImageURI(bean.getImage());

        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(90));
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(160));
        if (getDatas().size() > 1) {
            imgView.setLayoutParams(lp1);
            view.setLayoutParams(lp1);
        } else {
            imgView.setLayoutParams(lp2);
            view.setLayoutParams(lp2);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.ACTION_CIRCLE_VIDEO, BundleUtils.putSerializable(bean));
            }
        });
    }

    private void bindItem1(BaseViewHoder holder, int position, final CircleItem item) {
        final HttpImgBean imgBean = (HttpImgBean) item.getData();
        SimpleDraweeView imgView = holder.getViewById(R.id.imgView);
        View view = holder.getViewById(R.id.view);

        imgView.setImageURI(imgBean.getMin());

        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(90));
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(160));
        if (getDatas().size() > 1) {
            imgView.setLayoutParams(lp1);
            view.setLayoutParams(lp1);
        } else {
            imgView.setLayoutParams(lp2);
            view.setLayoutParams(lp2);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data1", imgBean);
                bundle.putSerializable("data2", (ArrayList<CircleItem>) getDatas());
                getListener().onInteractionAdapter(Constant.ACTION_CIRCLE_IMG, bundle);
            }
        });

    }
}
