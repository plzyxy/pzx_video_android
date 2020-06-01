package com.thinksoft.banana.entity.bean.img;

import com.thinksoft.banana.entity.BaseBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/4/4 0004
 * @
 */
public class ImagListBean extends BaseBean {
    List<ImagBean> pictureList;

    public void setPictureList(List<ImagBean> pictureList) {
        this.pictureList = pictureList;
    }

    public List<ImagBean> getPictureList() {
        return pictureList;
    }
}
