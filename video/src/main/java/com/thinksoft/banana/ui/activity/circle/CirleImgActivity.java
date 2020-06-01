package com.thinksoft.banana.ui.activity.circle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.donkingliang.imageselector.utils.ImageUtil;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.thinksoft.banana.R;
import com.thinksoft.banana.entity.bean.circle.HttpImgBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.txf.ui_mvplibrary.ui.activity.BaseActivity;
import com.txf.ui_mvplibrary.ui.view.banner.BasePagerAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/3/23 0023
 * @圈子图片查看
 */
public class CirleImgActivity extends BaseActivity {
    ViewPager mViewPager;
    BasePagerAdapter<HttpImgBean> mAdapter;
    int defPos;

    HttpImgBean imgBean;//点击的图片
    ArrayList<HttpImgBean> mImgBeans;//全部图片集合

    public static Intent getIntent(Context context, HttpImgBean imgBean, List<CircleItem> datas) {
        return getIntent(context, imgBean, changeBean(datas));
    }

    private static ArrayList<HttpImgBean> changeBean(List<CircleItem> datas) {
        ArrayList<HttpImgBean> mImgBeans = new ArrayList<>();
        for (CircleItem circleItem : datas) {
            HttpImgBean bean = (HttpImgBean) circleItem.getData();
            mImgBeans.add(bean);
        }
        return mImgBeans;
    }

    public static Intent getIntent(Context context, HttpImgBean imgBean, ArrayList<HttpImgBean> mImgBeans) {
        Intent i = new Intent(context, CirleImgActivity.class);
        i.putExtra("data1", imgBean);
        i.putExtra("data2", mImgBeans);
        return i;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cirle_img;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initViewPager();
    }

    private void initViewPager() {
        mViewPager.setAdapter(mAdapter = new BasePagerAdapter<HttpImgBean>() {
            @Override
            protected View getItemView(int position, View contentView, ViewGroup container) {
                final PhotoView view;
                if (contentView == null) {
                    PhotoView imageView = new PhotoView(container.getContext());
                    imageView.setAdjustViewBounds(true);

                    view = imageView;
                } else {
                    view = (PhotoView) contentView;
                }
                final HttpImgBean bean = getData().get(position);
                showLoading();
                Glide.with(getContext().getApplicationContext())
                        .load(bean.getMax())
                        .asBitmap()
                        .listener(new RequestListener<String, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                hideLoading();
                                return false;
                            }
                            @Override
                            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                hideLoading();
                                return false;
                            }
                        })
                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (resource != null) {
                            int bw = resource.getWidth();
                            int bh = resource.getHeight();
                            if (bw > 8192 || bh > 8192) {
                                Bitmap bitmap = ImageUtil.zoomBitmap(resource, 8192, 8192);
                                setBitmap(view, bitmap);
                            } else {
                                setBitmap(view, resource);
                            }
                        } else {
                            view.setImageBitmap(null);
                        }
                    }
                });
                return view;
            }
        });
        mAdapter.setData(mImgBeans);
        mViewPager.setCurrentItem(defPos);
    }

    private void initView() {
        mViewPager = findViewById(R.id.ViewPager);
        setOnClick(R.id.backButton);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                finish();
                break;
        }
    }

    private void initData() {
        imgBean = (HttpImgBean) getIntent().getSerializableExtra("data1");
        mImgBeans = (ArrayList<HttpImgBean>) getIntent().getSerializableExtra("data2");
        if (mImgBeans == null)
            return;
        for (int i = 0; i < mImgBeans.size(); i++) {
            HttpImgBean bean = mImgBeans.get(i);
            if (bean.getMin().equals(imgBean.getMin())) {
                defPos = i;
            }
        }
    }

    private void setBitmap(PhotoView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        if (bitmap != null) {
            int bw = bitmap.getWidth();
            int bh = bitmap.getHeight();
            int vw = imageView.getWidth();
            int vh = imageView.getHeight();
            if (bw != 0 && bh != 0 && vw != 0 && vh != 0) {
                if (1.0f * bh / bw > 1.0f * vh / vw) {
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    float offset = (1.0f * bh * vw / bw - vh) / 2;
                    adjustOffset(imageView, offset);
                } else {
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }
        }
    }

    private void adjustOffset(PhotoView view, float offset) {
        PhotoViewAttacher attacher = view.getAttacher();
        try {
            Field field = PhotoViewAttacher.class.getDeclaredField("mBaseMatrix");
            field.setAccessible(true);
            Matrix matrix = (Matrix) field.get(attacher);
            matrix.postTranslate(0, offset);
            Method method = PhotoViewAttacher.class.getDeclaredMethod("resetMatrix");
            method.setAccessible(true);
            method.invoke(attacher);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
