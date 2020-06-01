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
import com.thinksoft.banana.entity.bean.home.BannersBean;
import com.thinksoft.banana.entity.bean.home.PerformerBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.bean.play.CommentDataBean;
import com.thinksoft.banana.entity.bean.play.PlayerBean;
import com.thinksoft.banana.entity.bean.type.PerformerDataBean;
import com.thinksoft.banana.entity.item.PlayerItem;
import com.thinksoft.banana.ui.view.player.PlayerVerticalControlView;
import com.txf.other_playerlibrary.ui.view.PlayerView;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.utils.BundleUtils;
import com.txf.ui_mvplibrary.utils.FrescoUtils;

/**
 * @author txf
 * @create 2019/2/21 0021
 * @
 */
public class PlayerAdapter extends BaseCompleteRecyclerAdapter<PlayerItem> {
    public PlayerAdapter(Context context) {
        super(context);
    }

    public PlayerAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    public void notifyItem(PlayerItem item) {
        for (int i = 0; i < getItemCount(); i++) {
            if (getItemViewType(i) == item.getItemType()) {
                getItem(i).setData(item.getData());
                notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_player_comment);
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_player_type);

        addItemLayout(Constant.TYPE_ITEM_3, R.layout.item_player_img);
        addItemLayout(Constant.TYPE_ITEM_4, R.layout.item_player_video);
        addItemLayout(Constant.TYPE_ITEM_5, R.layout.item_player_type2);
        addItemLayout(Constant.TYPE_ITEM_6, R.layout.item_player_info);

        addItemLayout(Constant.TYPE_ITEM_7, R.layout.item_player_performer_list);

        addItemLayout(Constant.TYPE_ITEM_8, R.layout.item_player_view);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    TextView valueTV;
    int CommentCount;

    public void setCommentCount(int commentCount) {
        if (commentCount == 0)
            return;
        this.CommentCount = commentCount;
        if (valueTV != null)
            valueTV.setText(getString(R.string.精彩评论) + "  (" + CommentCount + ")");
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, final PlayerItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                CommentDataBean dataBean = (CommentDataBean) item.getData();
                SimpleDraweeView mSimpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
                TextView nameTV = holder.getViewById(R.id.nameTV);
                TextView contentTV = holder.getViewById(R.id.contentTV);
                TextView zButton = holder.getViewById(R.id.zButton);

                mSimpleDraweeView.setImageURI(dataBean.getImage());
                nameTV.setText(dataBean.getNickname());
                contentTV.setText(dataBean.getContent());
                zButton.setText(String.valueOf(dataBean.getLove()));

                if (dataBean.getIs_love() == 1) {
                    zButton.setTextColor(0xffDF6DB7);
                    zButton.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            ContextCompat.getDrawable(getContext(), R.drawable.icon_play_z_true),
                            null,
                            null);

                } else {
                    zButton.setTextColor(0xff999999);
                    zButton.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            ContextCompat.getDrawable(getContext(), R.drawable.icon_play_z_false),
                            null,
                            null);
                }
                zButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_1, BundleUtils.putBaseItem(item));
                    }
                });


                break;
            case Constant.TYPE_ITEM_2:
                valueTV = holder.getViewById(R.id.valueTV);
                setCommentCount(CommentCount);

                break;
            case Constant.TYPE_ITEM_3:
                final BannersBean bannersBean = (BannersBean) item.getData();
                final SimpleDraweeView imgView = holder.getViewById(R.id.imgView);

                FrescoUtils.setGifImgUrl(
                        imgView,
                        bannersBean.getImage(),
                        FrescoUtils.getControllerListener(imgView, getContext().getResources().getDisplayMetrics().widthPixels));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_3, BundleUtils.putSerializable(bannersBean));
                    }
                });
                break;
            case Constant.TYPE_ITEM_4:
                if (item.getData() == null)
                    return;
                final VideosBean bean = (VideosBean) item.getData();
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
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_4, BundleUtils.putSerializable(bean));
                    }
                });
                break;
            case Constant.TYPE_ITEM_5:
                TextView valueTV = holder.getViewById(R.id.valueTV);
                valueTV.setText((String) item.getData());
                break;
            case Constant.TYPE_ITEM_6:
                bindItem6(holder, position, item);
                break;
            case Constant.TYPE_ITEM_7:
                bindItem7(holder, position, item);
                break;
            case Constant.TYPE_ITEM_8:
                bindItem8(holder, position, item);
                break;

        }
    }

    PlayerVerticalControlView mPlayerControlView;

    public void setCollection(PlayerBean bean) {
        if (mPlayerControlView != null)
            mPlayerControlView.setCollection(bean.getCollcetion() == 1);
    }

    public void paySuccess() {
        if (mPlayerControlView != null)
            mPlayerControlView.paySuccess();
    }

    private void bindItem8(BaseViewHoder holder, int position, PlayerItem item) {
        final PlayerBean bean = (PlayerBean) item.getData();
        PlayerView mPlayerView = holder.getViewById(R.id.PlayerView);

        if (bean.getState() == 0) {
            mPlayerControlView = new PlayerVerticalControlView(getContext());
            mPlayerControlView.setCloseProgress();
            setCollection(bean);
            mPlayerView.setPlayerControlView(mPlayerControlView);
            mPlayerView.setOnSurfaceHolderListener(new PlayerView.OnSurfaceHolderListener() {
                @Override
                public void surfaceCreated() {
                    bean.setState(1);
                    mPlayerControlView.setPlayerBean(bean);
                    mPlayerControlView.start();
                }
            });
        }
    }

    private void bindItem7(BaseViewHoder holder, int position, PlayerItem item) {
        PerformerBean mPerformerBean = (PerformerBean) item.getData();
        RecyclerView performerList = holder.getViewById(R.id.RecyclerView);
        performerList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        performerList.setAdapter(mPerformerAdapter);
        mPerformerAdapter.setDatas(mPerformerBean.getPerformers());
        mPerformerAdapter.notifyDataSetChanged();
    }

    private void bindItem6(BaseViewHoder holder, int position, PlayerItem item) {
        PlayerBean bean = (PlayerBean) item.getData();

        TextView nameTV = holder.getViewById(R.id.nameTV);
        TextView collectionButton = holder.getViewById(R.id.collectionButton);
        TextView shareButton = holder.getViewById(R.id.shareButton);

        if (bean.getCollcetion() == 1) {
            collectionButton.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.icon_play_sc_true), null, null, null);
        } else {
            collectionButton.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.icon_play_sc_false_hei), null, null, null);
        }
        nameTV.setText(bean.getVideo().getTitle());

        collectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.ACTION_COLLECTION, null);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.ACTION_SHARE, null);
            }
        });
    }

    private BaseCompleteRecyclerAdapter<PerformerDataBean> mPerformerAdapter = new BaseCompleteRecyclerAdapter<PerformerDataBean>(getContext()) {
        @Override
        protected void setItemLayout() {
            super.setItemLayout();
            addItemLayout(0, R.layout.item_player_performer);
        }

        @Override
        protected void onBindBaseViewHoder(BaseViewHoder holder, int position, final PerformerDataBean item) {
            SimpleDraweeView simpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
            TextView TV1 = holder.getViewById(R.id.TV1);
            TextView TV2 = holder.getViewById(R.id.TV2);
            simpleDraweeView.setImageURI(item.getImage());
            TV1.setText(item.getName());
            if (item.getIs_collection() == 1) {
                TV2.setText(getString(R.string.已收藏));
                TV2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_radius_d9d9d9_40));
            } else {
                TV2.setText(getString(R.string.收藏));
                TV2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_radius_d76dbb_40));
            }
            holder.getViewById(R.id.contentRoot).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayerAdapter.this.getListener().onInteractionAdapter(Constant.TYPE_ITEM_7, BundleUtils.putSerializable(item));
                }
            });
        }
    };

}
