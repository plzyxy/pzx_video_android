package com.thinksoft.banana.entity.bean.circle;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/3/28 0028
 * @
 */
public class RegionsDataBean extends BaseBean {

    /**
     * id : 1
     * name : 重庆
     */

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
