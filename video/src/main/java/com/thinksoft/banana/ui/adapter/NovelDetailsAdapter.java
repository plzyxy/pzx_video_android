package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.orhanobut.logger.Logger;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.NovelInfoBean;
import com.thinksoft.banana.entity.bean.NovelInfoDataBean;
import com.thinksoft.banana.entity.bean.circle.CircleCommentBean;
import com.thinksoft.banana.entity.bean.circle.HttpImgBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.thinksoft.banana.ui.view.banner.ImgsWidget;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationCommon;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.io.IOException;
import java.util.ArrayList;

import cn.droidlover.xrichtext.ImageLoader;
import cn.droidlover.xrichtext.XRichText;

/**
 * @author txf
 * @create 2019/3/22 0022
 * @
 */
public class NovelDetailsAdapter extends BaseCompleteRecyclerAdapter<CircleItem> {
    public NovelDetailsAdapter(Context context) {
        super(context);
    }

    public NovelDetailsAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_3, R.layout.item_novel_details_banner);
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_novel_details_content);
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_circle_comment);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    ItemDecorationCommon mItemDecorationCommon = new ItemDecorationCommon(
            3,
            new Rect(0, 0, 0, 0),
            dip2px(8),
            Constant.TYPE_ITEM_1);

    ItemDecorationCommon mItemDecorationCommon2 = new ItemDecorationCommon(
            1,
            new Rect(0, 0, 0, 0),
            0,
            Constant.TYPE_ITEM_1);

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, CircleItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                binditem1(holder, position, item);
                break;
            case Constant.TYPE_ITEM_2:
                binditem2(holder, position, item);
                break;
            case Constant.TYPE_ITEM_3:
                ImgsWidget imgsWidget = holder.getViewById(R.id.BannerWidget);
                final ArrayList<HttpImgBean> imgs = (ArrayList<HttpImgBean>) item.getData();
                imgsWidget.setData(imgs);
                imgsWidget.setOnViewChangeListener(new OnAppListener.OnViewListener() {
                    @Override
                    public void onInteractionView(int action, Bundle ext) {
                        ext.putSerializable("ext", imgs);
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_3, ext);
                    }
                });
                break;
        }
    }

    private void binditem2(BaseViewHoder holder, int position, final CircleItem item) {
        final CircleCommentBean bean = (CircleCommentBean) item.getData();
        SimpleDraweeView userImg = holder.getViewById(R.id.SimpleDraweeView);
        TextView nameTV = holder.getViewById(R.id.nameTV);
        TextView timeTV = holder.getViewById(R.id.timeTV);
        TextView contentTV = holder.getViewById(R.id.contentTV);
        TextView zButton = holder.getViewById(R.id.zButton);

        userImg.setImageURI(bean.getImage());
        nameTV.setText(bean.getNickname());
        timeTV.setText(bean.getDate());
        contentTV.setText(bean.getContent());

        if (bean.getIslove() == 1) {
            zButton.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    ContextCompat.getDrawable(getContext(), R.drawable.icon_play_z_true),
                    null,
                    null);
        } else {
            zButton.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    ContextCompat.getDrawable(getContext(), R.drawable.icon_play_z_false),
                    null,
                    null);
        }
        zButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.ACTION_CIRCLE_COMMENT_Z, BundleUtils.putSerializable(bean));
            }
        });
    }

    private void binditem1(BaseViewHoder holder, int position, final CircleItem item) {
        final NovelInfoBean infoBean = (NovelInfoBean) item.getData();
        final NovelInfoDataBean bean = infoBean.getNovelInfo();

        //title
        TextView nameTV = holder.getViewById(R.id.nameTV);
        TextView shareButton = holder.getViewById(R.id.shareButton);
        TextView zhangButton = holder.getViewById(R.id.zhangButton);
        nameTV.setText(bean.getTitle());
        holder.setText(R.id.commentTV, bean.getComment_num() + "");
        zhangButton.setText(bean.getLove() + "");
        if (bean.getIslove() == 1) {
            zhangButton.setTextColor(0xffd76dbb);
            zhangButton.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.icon_circle_z_true), null, null, null);
        } else {
            zhangButton.setTextColor(0xff999999);
            zhangButton.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.icon_circle_z_false), null, null, null);
        }


        //联系方式
        final TextView phoneTV = holder.getViewById(R.id.phoneTV);
        final TextView phoneTV1 = holder.getViewById(R.id.phoneTV1);
        final TextView phoneButton = holder.getViewById(R.id.phoneButton);
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infoBean.getIsVip() == 1) {
                    phoneTV1.setText("联系方式: ");
                    phoneTV.setText(bean.getConcat() == null ? "无" : bean.getConcat());
                } else {
                    getListener().onInteractionAdapter(Constant.ACTION_NOVEL_DETAILS_VIP, null);
                }
            }
        });

        //类容
        XRichText contentTV = holder.getViewById(R.id.contentTV);
        contentTV.imageDownloader(new ImageLoader() {
            @Override
            public Bitmap getBitmap(String url) throws IOException {
                return getUrlBitmap(getContext(), url);
            }
        }).text(bean.getContent());


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.ACTION_CIRCLE_SHARE, BundleUtils.putSerializable(bean));
            }
        });
        zhangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.ACTION_CIRCLE_Z, BundleUtils.putSerializable(bean));
            }
        });
    }


    /**
     * @param context
     * @param picUrl
     * @return
     */
    public Bitmap getUrlBitmap(Context context, String picUrl) {
        final Bitmap[] bitMap = {null};
        Uri uri = Uri.parse(picUrl);
        ImageDecodeOptions decodeOptions = ImageDecodeOptions.newBuilder()
                .build();
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setImageDecodeOptions(decodeOptions)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .setProgressiveRenderingEnabled(false)
                .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, context);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                bitMap[0] = bitmap;
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                Logger.i("图片加载失败");
            }
        }, UiThreadImmediateExecutorService.getInstance());
        return bitMap[0];
    }
}
