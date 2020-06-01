package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.home.BannersBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.item.TypeFilmItem;
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
 * @
 */
public class TypeFilmDataAdapter extends BaseCompleteRecyclerAdapter<TypeFilmItem> {
    public TypeFilmDataAdapter(Context context) {
        super(context);
    }

    public TypeFilmDataAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_video_content);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, final TypeFilmItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                VideosBean videosBean = (VideosBean) item.getData();

                SimpleDraweeView simpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
                TextView contentTV1 = holder.getViewById(R.id.contentTV1);

                simpleDraweeView.setImageURI(videosBean.getImage());
                if (videosBean.getIs_free() != 0) {
                    holder.setText(R.id.tagTextView, getString(R.string.收费));
                } else {
                    holder.setText(R.id.tagTextView, getString(R.string.免费));
                }
                holder.setText(R.id.contentTV1, videosBean.getTitle());
                holder.setText(R.id.contentTV2, videosBean.getHis());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_1, BundleUtils.putBaseItem(item));
                    }
                });
                break;
        }
    }
}
