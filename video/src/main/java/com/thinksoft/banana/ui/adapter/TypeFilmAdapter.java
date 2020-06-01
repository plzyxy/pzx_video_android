package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.home.BannersBean;
import com.thinksoft.banana.entity.bean.home.CatesBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.item.TypeHomeListItem;
import com.thinksoft.banana.entity.item.VideoItem;
import com.thinksoft.banana.ui.view.banner.BannerWidget;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.List;

/**
 * @author txf
 * @create 2019/2/18 0018
 * @电影
 */
public class TypeFilmAdapter extends BaseCompleteRecyclerAdapter<TypeHomeListItem> {
    public TypeFilmAdapter(Context context) {
        super(context);
    }

    public TypeFilmAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_typehome_conent);
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_video_list_conent);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, final TypeHomeListItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                bindItem1(holder, item);

                break;
            case Constant.TYPE_ITEM_2:
                final CatesBean catesBean = (CatesBean) item.getData();
                SimpleDraweeView simpleDraweeView2 = holder.getViewById(R.id.SimpleDraweeView);
                TextView TV12 = holder.getViewById(R.id.TV1);
                simpleDraweeView2.setImageURI(catesBean.getImage());
                TV12.setText(catesBean.getName());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_2, BundleUtils.putSerializable(catesBean));
                    }
                });

                break;

        }
    }

    private void bindItem1(BaseViewHoder holder, TypeHomeListItem item) {
        final CatesBean catesBean = (CatesBean) item.getData();
        SimpleDraweeView simpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
        TextView TV1 = holder.getViewById(R.id.TV1);
        simpleDraweeView.setImageURI(catesBean.getImage());
        TV1.setText(catesBean.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.TYPE_ITEM_1, BundleUtils.putSerializable(catesBean));
            }
        });
    }
}
