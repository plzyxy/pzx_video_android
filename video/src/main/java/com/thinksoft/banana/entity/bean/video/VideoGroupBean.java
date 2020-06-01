package com.thinksoft.banana.entity.bean.video;

import com.thinksoft.banana.entity.BaseBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/3/27 0027
 * @
 */
public class VideoGroupBean extends BaseBean {
    private List<VideoGroupDataBean> list;

    public void setList(List<VideoGroupDataBean> list) {
        this.list = list;
    }

    public List<VideoGroupDataBean> getList() {
        return list;
    }
}
