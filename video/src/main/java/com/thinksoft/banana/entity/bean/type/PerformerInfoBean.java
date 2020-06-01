package com.thinksoft.banana.entity.bean.type;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;

/**
 * @author txf
 * @create 2019/3/13 0013
 * @
 */
public class PerformerInfoBean extends BaseBean implements Serializable {

    /**
     * performer : {"id":12,"name":"古天乐","letter":"G","image":"http://47.106.154.254//uploads/picture/20190312/22b44b15c4ee3a99231206e91e6acb12.jpg","description":"刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华","is_collection":0}
     */

    private PerformerInfoDataBean performer;

    public PerformerInfoDataBean getPerformer() {
        return performer;
    }

    public void setPerformer(PerformerInfoDataBean performer) {
        this.performer = performer;
    }
}
