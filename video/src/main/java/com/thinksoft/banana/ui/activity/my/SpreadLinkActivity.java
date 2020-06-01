package com.thinksoft.banana.ui.activity.my;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.entity.bean.SpreadLinkBean;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.MyModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.txf.other_playerlibrary.tools.Logger;
import com.txf.other_toolslibrary.tools.FileTools;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;
import com.txf.ui_mvplibrary.utils.BitMapUtils;
import com.txf.ui_mvplibrary.utils.FrescoUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author txf
 * @create 2019/4/3 0003
 * @
 */
public class SpreadLinkActivity
        extends BaseMvpActivity<CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View {
    SimpleDraweeView mSimpleDraweeView, bg_imgView;
    TextView ewmTV, tv1, tv2;
    View imgViewRoot;
    SpreadLinkBean mSpreadLinkBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_spread_link;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarTransparent();
        setContract(this, new CommonPresenter(this, new MyModel()));
        initView();
        getPresenter().getData(ApiRequestTask.my.TAG_SPREAD_SHARE);
    }

    private void initView() {
        mSimpleDraweeView = findViewById(R.id.SimpleDraweeView);
        bg_imgView = findViewById(R.id.bg_imgView);
        ewmTV = findViewById(R.id.ewmTV);
        imgViewRoot = findViewById(R.id.imgViewRoot);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        setOnClick(R.id.backButton, R.id.button1, R.id.button2);
    }

    private void setData(SpreadLinkBean bean) {
        mSpreadLinkBean = bean;
        bg_imgView.setImageURI(bean.getShare_back());
        mSimpleDraweeView.setImageURI(bean.getShare_qrcode());
        ewmTV.setText(bean.getInvite_code());
        tv1.setText(bean.getShare_title());
        tv2.setText(bean.getShare_desc());
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                finish();
                break;
            case R.id.button1:
                //保存二维码
                showLoading();
                if (StringTools.isNull(mSpreadLinkBean.getShare_qrcode())) {
                    ToastUtils.show("二维码链接为空,请稍候再试");
                    return;
                }
                imgViewRoot.setDrawingCacheEnabled(true);
                imgViewRoot.buildDrawingCache();  //启用DrawingCache并创建位图
                Bitmap bitmap = Bitmap.createBitmap(imgViewRoot.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
                imgViewRoot.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能

                String filePath = FileTools.getDirPath(getPackageName()).getAbsolutePath();
                BitMapUtils.notifyImgUpdate(BitMapUtils.saveBitmap(filePath, bitmap), getContext());
                hideLoading();
                ToastUtils.show("保存成功,可进入系统相册查看");
                bitmap.recycle();
                break;
            case R.id.button2:
                //复制推广链接
                if (mSpreadLinkBean == null || StringTools.isNull(mSpreadLinkBean.getShare_link())) {
                    ToastUtils.show("复制失败,推广链接为空");
                    return;
                }
                android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, mSpreadLinkBean.getShare_link()));
                ToastUtils.show("已复制到粘贴板");
                break;
        }
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.my.TAG_SPREAD_SHARE:
                SpreadLinkBean bean = JsonTools.fromJson(data, SpreadLinkBean.class);
                setData(bean);
                break;
        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {

    }
}
