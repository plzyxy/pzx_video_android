package com.thinksoft.banana.entity.bean.circle;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/3/22 0022
 * @
 */
public class UploadVideoBean extends BaseBean {

    /**
     * filepath : http://58.64.180.125//uploads/video/20190326/5b57ba9183714654ad25fb67b9a15cd7.mp4
     * id : 5b57ba9183714654ad25fb67b9a15cd7
     */

    private String filepath;
    private String id;

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
