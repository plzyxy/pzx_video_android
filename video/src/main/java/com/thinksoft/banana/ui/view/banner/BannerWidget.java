package com.thinksoft.banana.ui.view.banner;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.home.BannersBean;
import com.txf.ui_mvplibrary.ui.view.banner.BaseBannerWidget;
import com.txf.ui_mvplibrary.utils.BundleUtils;
import com.txf.ui_mvplibrary.utils.FrescoUtils;


/**
 * @author txf
 * @create 2019/2/23 0023
 * @
 */
public class BannerWidget extends BaseBannerWidget<BannersBean> {

    public BannerWidget(Context context) {
        super(context);
    }

    public BannerWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View buildSelectorDot() {
        return new View(getContext());
    }

    @Override
    protected View getItemView(int position, View contentView, ViewGroup container) {
        View view;
        if (contentView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_banner_img, null);
        } else {
            view = contentView;
        }
        final BannersBean bean = getDatas().get(position);
        SimpleDraweeView mSimpleDraweeView = view.findViewById(R.id.SimpleDraweeView);
        FrescoUtils.setGifImgUrl(mSimpleDraweeView, bean.getImage());
        mSimpleDraweeView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionView(Constant.ACTION_BANNER, BundleUtils.putSerializable(bean));
            }
        });
        return view;
    }
}
