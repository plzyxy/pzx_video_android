package com.thinksoft.banana.ui.activity.circle;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.donkingliang.imageselector.OnImgSelectorListener;
import com.donkingliang.imageselector.utils.ImageSelectorManage;
import com.google.gson.JsonElement;
import com.orhanobut.logger.Logger;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.StringBean;
import com.thinksoft.banana.entity.bean.circle.HttpImgBean;
import com.thinksoft.banana.entity.bean.circle.RegionsBean;
import com.thinksoft.banana.entity.bean.circle.RegionsDataBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.CircleModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.adapter.CircleAddAdapter;
import com.thinksoft.banana.ui.view.window.BottomListWindow;
import com.txf.other_tencentl_uploadlibrary.utils.FileUtils;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationCommon;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/3/21 0021
 * @圈子发布页
 */
public class CircleAddActivity
        extends BaseMvpActivity<CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener, OnImgSelectorListener, OnAppListener.OnWindowListener {
    public static final int REQUEST_CODE_VIDEO = 1;

    TextView titleTV, confirmButton, cytyButton;
    EditText editText;
    RecyclerView imgListView;
    CircleAddAdapter mAdapter;
    List<CircleItem> mDatas;

    ImageSelectorManage mImageSelectorManage;
    BottomListWindow mCameraWindow, mCityWindow;
    RegionsBean mRegionsBean;
    StringBean selRegion;

    int itemType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_add;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(this, new CircleModel()));
        initView();
        initData();
    }

    private void initData() {
        titleTV.setText(getString(R.string.发布));
        itemType = Constant.TYPE_ITEM_3;
        imgListView.setLayoutManager(new GridLayoutManager(this, 3));
        imgListView.setAdapter(mAdapter = new CircleAddAdapter(this, this));
        imgListView.addItemDecoration(new ItemDecorationCommon(
                3,
                new Rect(0, 0, 0, 0),
                dip2px(8)));

        mDatas = new ArrayList<>();
        mDatas.add(new CircleItem(null, Constant.TYPE_ITEM_1));
        mAdapter.setDatas(mDatas);
        mAdapter.notifyDataSetChanged();
        mImageSelectorManage = new ImageSelectorManage(this, this);
        requestRegions();
    }

    private void requestRegions() {
        getPresenter().getData(ApiRequestTask.circle.TAG_CIRCLE_REGIONS);
    }

    private void initView() {
        titleTV = findViewById(R.id.titleTV);
        editText = findViewById(R.id.editText);
        imgListView = findViewById(R.id.imgListView);
        confirmButton = findViewById(R.id.confirmButton);
        cytyButton = findViewById(R.id.cytyButton);

        setOnClick(R.id.backButton, R.id.confirmButton, R.id.cytyButton);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.cytyButton:
                //添加地点
                if (mCityWindow == null) {
                    mCityWindow = new BottomListWindow(this);
                    mCityWindow.setOnBackListener(this, 1);
                    List<StringBean> datas = new ArrayList<>();
                    if (mRegionsBean == null || mRegionsBean.getRegions() == null || mRegionsBean.getRegions().size() == 0) {
                        datas.add(new StringBean(2, "成都"));
                    } else {
                        for (RegionsDataBean dataBean : mRegionsBean.getRegions()) {
                            datas.add(new StringBean(dataBean.getId(), dataBean.getName()));
                        }
                    }
                    mCityWindow.setData(datas);
                }
                mCityWindow.showPopupWindow();
                break;
            case R.id.backButton:
                finish();
                break;
            case R.id.confirmButton:
                if (StringTools.isNull(editText.getText().toString()) && mAdapter.getDataState() == 0) {
                    ToastUtils.show(getString(R.string.请输入这一刻的想法));
                    return;
                }
                if (selRegion == null) {
                    ToastUtils.show(getString(R.string.请添加一个地点));
                    return;
                }
                HashMap<String, Object> maps = new HashMap<>();
                maps.put("region_id", selRegion.getId());
                maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
                if (!StringTools.isNull(editText.getText().toString())) {
                    maps.put("content", editText.getText().toString());
                }
                maps.put("type", mAdapter.getDataState());
                switch (mAdapter.getDataState()) {
                    case 2:
                        if (StringTools.isNull(mAdapter.getDatas().get(0).getVideo_id())) {
                            ToastUtils.show("视频还未上传成功,请稍候...");
                            return;
                        }
                        maps.put("video_id", mAdapter.getDatas().get(0).getVideo_id());
                        break;
                    case 1:
                        List<HttpImgBean> images = new ArrayList<>();
                        for (CircleItem item : mAdapter.getDatas()) {
                            if (item.getItemType() != Constant.TYPE_ITEM_1) {
                                if (item.getHttpImgBean() == null) {
                                    ToastUtils.show("图片还未上传成功,请稍候...");
                                    return;
                                }
                                images.add(item.getHttpImgBean());
                            }
                        }
                        maps.put("images", JsonTools.toJSON(images));
                        break;
                }
                getPresenter().getData(ApiRequestTask.circle.TAG_CIRCLE_SAVECIRCLE, maps);
                break;
        }
    }

    @Override
    public void onInteractionAdapter(int action, Bundle ext) {
        switch (action) {
            case Constant.TYPE_ITEM_1:
                if (mCameraWindow == null) {
                    mCameraWindow = new BottomListWindow(this);
                    mCameraWindow.setOnBackListener(this, 0);
                }
                List<StringBean> datas = new ArrayList<>();
                switch (mAdapter.getDataState()) {
                    case 0:
                        datas.add(new StringBean(1, getString(R.string.视频)));
                        datas.add(new StringBean(0, getString(R.string.拍照)));
                        datas.add(new StringBean(2, getString(R.string.从相册中选择)));
                        break;
                    case 2:
                        datas.add(new StringBean(1, getString(R.string.视频)));
                        break;
                    case 1:
                        datas.add(new StringBean(0, getString(R.string.拍照)));
                        datas.add(new StringBean(2, getString(R.string.从相册中选择)));
                        break;
                }
                mCameraWindow.setData(datas);
                mCameraWindow.showPopupWindow();
                break;
        }
    }

    @Override
    public void onInteractionWindow(int action, int tag, Bundle ext) {
        StringBean bean;
        switch (tag) {
            case 0:
                bean = BundleUtils.getSerializable(ext);
                if (bean.getId() == 0) {
                    mImageSelectorManage.openPhoto(); //打开相机拍照
                } else if (bean.getId() == 1) {
                    openVideo();//视频
                } else if (bean.getId() == 2) {
                    mImageSelectorManage.openPhotoAlbum(); //打开相册
                }
                break;
            case 1:
                selRegion = BundleUtils.getSerializable(ext);
                cytyButton.setText(selRegion.getText());
                break;
        }
    }

    private void openVideo() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        Intent wrapIntent = Intent.createChooser(intent, null);
        startActivityForResult(wrapIntent, REQUEST_CODE_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageSelectorManage.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VIDEO) {
            if (resultCode == RESULT_OK) {
                //视频
                Uri uri = data.getData();
                String path = FileUtils.getRealPathFromUri(this, uri);
                Logger.i("File Uri: " + uri.toString());
                Logger.i("File Path: " + path);

                mDatas.add(0, new CircleItem(path, Constant.TYPE_ITEM_2));
                mDatas.remove(1);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    //图片
    @Override
    public void onCompressStart() {
        showLoading();
    }

    @Override
    public void onCompressCompleted(List<String> successPaths, List<String> exceptionPaths) {
        hideLoading();
        if (successPaths == null || successPaths.size() <= 0)
            return;
        itemType++;
        mDatas.add(0, new CircleItem(successPaths.get(0), itemType));
        mAdapter.notifyDataSetChanged();
    }

    //服务器回调
    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.circle.TAG_CIRCLE_SAVECIRCLE:
                ToastUtils.show(getString(R.string.发布成功等待管理员审核通过));
                finish();
                break;
            case ApiRequestTask.circle.TAG_CIRCLE_REGIONS:
                //地区列表
                mRegionsBean = JsonTools.fromJson(data, RegionsBean.class);
                break;
        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {

    }
}
