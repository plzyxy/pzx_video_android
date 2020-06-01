package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.bean.type.PerformerInfoBean;
import com.thinksoft.banana.entity.item.PerformerItem;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.utils.BundleUtils;

/**
 * @author txf
 * @create 2019/3/12 0012
 * @
 */
public class PerformerInfoAdapter extends BaseCompleteRecyclerAdapter<PerformerItem> {
    public PerformerInfoAdapter(Context context) {
        super(context);
    }

    public PerformerInfoAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_performer_info);
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_performer_info_video);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, final PerformerItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                final PerformerInfoBean mPerformerInfoBean = (PerformerInfoBean) item.getData();
                if (mPerformerInfoBean.getPerformer() == null)
                    return;

                SimpleDraweeView simpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
                TextView TV1 = holder.getViewById(R.id.TV1);
                TextView TV2 = holder.getViewById(R.id.TV2);
                TextView collectionButton = holder.getViewById(R.id.collectionButton);

                simpleDraweeView.setImageURI(mPerformerInfoBean.getPerformer().getImage());
                TV1.setText(mPerformerInfoBean.getPerformer().getName());
                TV2.setText(mPerformerInfoBean.getPerformer().getDescription());

                if (mPerformerInfoBean.getPerformer().getIs_collection() == 1) {
                    collectionButton.setText(getString(R.string.已收藏));
                    collectionButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_radius_d9d9d9_40));
                } else {
                    collectionButton.setText(getString(R.string.收藏));
                    collectionButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_radius_d76dbb_40));
                }
                collectionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_1, BundleUtils.putSerializable(mPerformerInfoBean));
                    }
                });
                break;
            case Constant.TYPE_ITEM_2:
                VideosBean videosBean = (VideosBean) item.getData();
                SimpleDraweeView simpleDraweeView2 = holder.getViewById(R.id.SimpleDraweeView);
                simpleDraweeView2.setImageURI(videosBean.getImage());
                holder.setText(R.id.contentTV1, videosBean.getTitle());
                holder.setText(R.id.contentTV2, videosBean.getHis());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_2, BundleUtils.putBaseItem(item));
                    }
                });
                break;
        }
    }
}
