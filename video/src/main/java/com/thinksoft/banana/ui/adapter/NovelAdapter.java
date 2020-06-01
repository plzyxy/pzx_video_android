package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.NovelTypeDataBean;
import com.thinksoft.banana.entity.bean.home.BannersBean;
import com.thinksoft.banana.entity.item.HomeItem;
import com.thinksoft.banana.ui.view.banner.BannerWidget;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.List;

/**
 * @author txf
 * @create 2019/2/16
 */
public class NovelAdapter extends BaseCompleteRecyclerAdapter<HomeItem> {
    public NovelAdapter(Context context) {
        super(context);
    }

    public NovelAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_home_img);
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_home_type);
        addItemLayout(Constant.TYPE_ITEM_3, R.layout.item_home_novel_content);
        addItemLayout(Constant.TYPE_ITEM_4, R.layout.item_home_title);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, HomeItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                onBindItem1(holder, position, item);
                break;
            case Constant.TYPE_ITEM_2:
                onBindItem2(holder, position, item);
                break;
            case Constant.TYPE_ITEM_3:
                onBindItem3(holder, position, item);
                break;
            case Constant.TYPE_ITEM_4:
                final NovelTypeDataBean bean = (NovelTypeDataBean) item.getData();
                holder.setText(R.id.tv1, bean.getName());
                TextView tv2 = holder.getViewById(R.id.tv2);
                if (bean.getId() == -1) {
                    tv2.setVisibility(View.GONE);
                } else {
                    tv2.setVisibility(View.VISIBLE);
                    tv2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          getListener().onInteractionAdapter(Constant.TYPE_ITEM_4, BundleUtils.putSerializable(bean));
                        }
                    });
                }
                break;
        }
    }

    private void onBindItem1(BaseViewHoder holder, int position, HomeItem item) {
        BannerWidget bannerWidget = holder.getViewById(R.id.BannerWidget);
        List<BannersBean> banners = (List<BannersBean>) item.getData();
        bannerWidget.setData(banners);
    }

    private void onBindItem3(BaseViewHoder holder, int position, final HomeItem item) {
        final NovelTypeDataBean bean = (NovelTypeDataBean) item.getData();
        SimpleDraweeView simpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
        simpleDraweeView.setImageURI(bean.getImage());
        holder.setText(R.id.contentTV1, bean.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.TYPE_ITEM_3, BundleUtils.putSerializable(bean));
            }
        });
    }

    private void onBindItem2(BaseViewHoder holder, int position, final HomeItem item) {
        SimpleDraweeView simpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
        if (item.getData() == null) {
            holder.itemView.setOnClickListener(null);
            simpleDraweeView.setVisibility(View.INVISIBLE);
            holder.setText(R.id.valueTextView, "");
            return;
        }
        final NovelTypeDataBean bean = (NovelTypeDataBean) item.getData();
        simpleDraweeView.setVisibility(View.VISIBLE);
        if (bean.getId() == -1) {
            simpleDraweeView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_gengduo));
        } else {
            simpleDraweeView.setImageURI(bean.getImage());
        }
        holder.setText(R.id.valueTextView, bean.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.TYPE_ITEM_2, BundleUtils.putSerializable(bean));
            }
        });
    }
}
