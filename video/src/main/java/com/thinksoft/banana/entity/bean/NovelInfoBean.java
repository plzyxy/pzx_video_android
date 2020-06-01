package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/4/4 0004
 * @
 */
public class NovelInfoBean extends BaseBean {
    NovelInfoDataBean novelInfo;
    int isVip;


    public void setNovelInfo(NovelInfoDataBean novelInfo) {
        this.novelInfo = novelInfo;
    }

    public NovelInfoDataBean getNovelInfo() {
        return novelInfo;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public int getIsVip() {
        return isVip;
    }
}
