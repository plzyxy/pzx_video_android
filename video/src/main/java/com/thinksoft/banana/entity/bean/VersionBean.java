package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/3/15 0015
 * @
 */
public class VersionBean extends BaseBean {

    /**
     * version : {"version_name":"1.0.0","link":"/uploads/file/20190315/8e37abc4024e52c02fc1d4caeb3781f3.apk"}
     */

    private VersionDataBean version;

    public VersionDataBean getVersion() {
        return version;
    }

    public void setVersion(VersionDataBean version) {
        this.version = version;
    }

}
