package com.txf.ui_mvplibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;


/**
 * @author txf
 * @create 2019/3/12 0012
 * @
 */
public class FrescoUtils {

    public static void setGifImgUrl(SimpleDraweeView imgView, String url) {
        setGifImgUrl(imgView, url, null);
    }

    public static void setGifImgUrl(SimpleDraweeView imgView, String url, @Nullable ControllerListener controllerListener) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(url))
                .setControllerListener(controllerListener)
                .setAutoPlayAnimations(true)
                .build();
        imgView.setController(controller);
    }

    public static ControllerListener getControllerListener(final SimpleDraweeView imgView, final int viewWidth) {
        final ViewGroup.LayoutParams layoutParams = imgView.getLayoutParams();
        return new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                int height = imageInfo.getHeight();
                int width = imageInfo.getWidth();
                layoutParams.width = viewWidth;
                int heightScale = (int) ((float) (viewWidth * height) / (float) width);
                layoutParams.height = heightScale;
                imgView.setLayoutParams(layoutParams);
            }
        };
    }

    public static void setImgUrl(SimpleDraweeView imgView, String url, BaseControllerListener<ImageInfo> l) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(url)
                .setControllerListener(l)
                .setAutoPlayAnimations(false)
                .build();
        imgView.setController(controller);
    }

    /**
     * @param context
     * @param picUrl
     * @return
     */
    public static void getUrlBitmap(Context context, String picUrl, BaseBitmapDataSubscriber dataSubscriber) {
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

        dataSource.subscribe(dataSubscriber, UiThreadImmediateExecutorService.getInstance());
    }
}
