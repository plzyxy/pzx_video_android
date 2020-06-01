package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.bean.home.VideosBean;

/**
 * @author txf
 * @create 2019/3/4 0004
 * @
 */
public class HistoryDataBean extends VideosBean {
    String diamond;
    int type;
    long record_time;

    public String getDiamond() {
        return diamond;
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getRecord_time() {
        return record_time * 1000l;
    }

    public void setRecord_time(long record_time) {
        this.record_time = record_time;
    }
}
