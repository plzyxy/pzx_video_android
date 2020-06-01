package com.thinksoft.banana.ui.adapter;

import android.content.Context;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.play.PlayerBean;
import com.thinksoft.banana.entity.bean.play.VideoDataBean;
import com.thinksoft.banana.entity.bean.video.VideoGroupDataBean;
import com.thinksoft.banana.entity.item.VideoItem;
import com.thinksoft.banana.ui.view.player.PlayerListView;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;

/**
 * @author txf
 * @create 2019/3/27 0027
 * @
 */
public class VideoGroupAdapter extends BaseCompleteRecyclerAdapter<VideoItem> {
    public VideoGroupAdapter(Context context) {
        super(context);
    }

    public VideoGroupAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_video_group_player);
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, VideoItem item) {
        VideoGroupDataBean bean = (VideoGroupDataBean) item.getData();
        PlayerListView mPlayerListView = holder.getViewById(R.id.PlayerListView);
        mPlayerListView.getControlView().setPlayerBean(bean);
    }
}
