package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/3/4 0004
 * @
 */
public class HttpImgBean extends BaseBean {

    /**
     * pic : 图片地址
     * path : 图片相对路径，需要保存到数据库
     */

    private String pic;
    private String path;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
