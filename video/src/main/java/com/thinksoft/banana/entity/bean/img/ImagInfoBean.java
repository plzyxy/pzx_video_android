package com.thinksoft.banana.entity.bean.img;

import com.thinksoft.banana.entity.BaseBean;
import com.thinksoft.banana.entity.bean.circle.CircleBean;

/**
 * @author txf
 * @create 2019/3/23 0023
 * @
 */
public class ImagInfoBean extends BaseBean {
    ImagInfoDataBean pictureInfo;

    public void setPictureInfo(ImagInfoDataBean pictureInfo) {
        this.pictureInfo = pictureInfo;
    }

    public ImagInfoDataBean getPictureInfo() {
        return pictureInfo;
    }
}
