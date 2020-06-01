package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author txf
 * @create 2019/4/9 0009
 * @
 */
public class NovelTypeBean extends BaseBean implements Serializable {
    List<NovelTypeDataBean>list;

    public void setList(List<NovelTypeDataBean> list) {
        this.list = list;
    }

    public List<NovelTypeDataBean> getList() {
        return list;
    }
}
