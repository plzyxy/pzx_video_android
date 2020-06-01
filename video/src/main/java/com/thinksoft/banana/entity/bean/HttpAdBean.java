package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/3/20 0020
 * @
 */
public class HttpAdBean extends BaseBean {

    /**
     * advert : {"status":1,"image":"http://59.188.25.220//uploads/picture/20190320/4b6c5ffa5cff2b7c833006748dbf6d2b.jpg","link":"https://www.baidu.com/"}
     */

    private AdvertBean advert;

    public AdvertBean getAdvert() {
        return advert;
    }

    public void setAdvert(AdvertBean advert) {
        this.advert = advert;
    }

    public static class AdvertBean {
        /**
         * status : 1
         * image : http://59.188.25.220//uploads/picture/20190320/4b6c5ffa5cff2b7c833006748dbf6d2b.jpg
         * link : https://www.baidu.com/
         */

        private int status;
        private String image;
        private String link;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
