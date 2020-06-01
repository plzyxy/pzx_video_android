package com.thinksoft.banana.entity.bean.home;

import com.thinksoft.banana.entity.BaseBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/2/28 0028
 * @
 */
public class TypeDataBean extends BaseBean {
    private List<VideosBean> vdieos;

    public List<VideosBean> getVdieos() {
        return vdieos;
    }

    public void setVdieos(List<VideosBean> vdieos) {
        this.vdieos = vdieos;
    }
}
