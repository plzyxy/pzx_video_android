package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.item.SearchItem;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.utils.BundleUtils;

/**
 * @author txf
 * @create 2019/2/18 0018
 * @
 */
public class SearchAdapter extends BaseCompleteRecyclerAdapter<SearchItem> {
    public SearchAdapter(Context context) {
        super(context);
    }

    public SearchAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_search_title);
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_search_content);
        addItemLayout(Constant.TYPE_NULL, R.layout.item_null);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, SearchItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_2:
                onBindItem2(holder, position, item);
                break;
            case Constant.TYPE_NULL:
                holder.setText(R.id.TV1, (String) item.getData());
                break;

        }
    }

    private void onBindItem2(BaseViewHoder holder, int position, final SearchItem item) {
        VideosBean bean = (VideosBean) item.getData();
        SimpleDraweeView simpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
        simpleDraweeView.setImageURI(bean.getImage());
        if (bean.getIs_free() != 0) {
            holder.setText(R.id.tagTextView, getString(R.string.收费));
        } else {
            holder.setText(R.id.tagTextView, getString(R.string.免费));
        }
        holder.setText(R.id.contentTV1, bean.getTitle());
        holder.setText(R.id.contentTV2, bean.getHis());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.TYPE_ITEM_2, BundleUtils.putBaseItem(item));
            }
        });

    }

}
