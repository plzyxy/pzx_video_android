package com.thinksoft.banana.entity.bean.circle;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/3/22 0022
 * @
 */
public class UploadImgBean extends BaseBean {

    /**
     * image_path : http://47.106.154.254/uploads/picture/20190322/2297b80734e7d513a20266a0bafb13d1.png
     * path : /uploads/picture/20190322/2297b80734e7d513a20266a0bafb13d1.png
     * thumbnail : /uploads/picture/20190322/min_2297b80734e7d513a20266a0bafb13d1.png
     * image_thumbnail : http://47.106.154.254/uploads/picture/20190322/min_2297b80734e7d513a20266a0bafb13d1.png
     */

    private String image_path;
    private String path;
    private String thumbnail;
    private String image_thumbnail;

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage_thumbnail() {
        return image_thumbnail;
    }

    public void setImage_thumbnail(String image_thumbnail) {
        this.image_thumbnail = image_thumbnail;
    }
}
