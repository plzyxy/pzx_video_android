package com.thinksoft.banana.entity.item;

import com.thinksoft.banana.entity.bean.circle.HttpImgBean;
import com.thinksoft.banana.entity.bean.circle.UploadImgBean;
import com.txf.ui_mvplibrary.bean.BaseItem;

/**
 * @author txf
 * @create 2019/3/21 0021
 * @
 */
public class CircleItem<Data> extends BaseItem<Data> {
    public CircleItem(Data data, int type, int action) {
        super(data, type, action);
    }

    public CircleItem(Data data, int type) {
        super(data, type);
    }


    private int type;//1为文字+图片信息，2文字+为视频信息;
    private String video_id;
    private HttpImgBean mHttpImgBean;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setUploadImgBean(UploadImgBean mUploadImgBean) {
        if (mUploadImgBean == null)
            return;
        mHttpImgBean = new HttpImgBean();
        mHttpImgBean.setMax(mUploadImgBean.getPath());
        mHttpImgBean.setMin(mUploadImgBean.getThumbnail());
    }

    public HttpImgBean getHttpImgBean() {
        return mHttpImgBean;
    }

}
