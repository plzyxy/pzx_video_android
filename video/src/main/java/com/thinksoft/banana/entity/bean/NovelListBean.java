package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/4/4 0004
 * @
 */
public class NovelListBean extends BaseBean {
    List<NovelBean> novelList;

    public void setNovelList(List<NovelBean> novelList) {
        this.novelList = novelList;
    }

    public List<NovelBean> getNovelList() {
        return novelList;
    }
}
