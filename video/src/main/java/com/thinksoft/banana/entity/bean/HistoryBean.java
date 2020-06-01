package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/3/4 0004
 * @
 */
public class HistoryBean extends BaseBean {
    List<HistoryDataBean> watchList;

    public List<HistoryDataBean> getWatchList() {
        return watchList;
    }

    public void setWatchList(List<HistoryDataBean> watchList) {
        this.watchList = watchList;
    }
}
