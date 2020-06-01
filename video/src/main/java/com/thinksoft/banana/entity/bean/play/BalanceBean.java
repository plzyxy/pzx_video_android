package com.thinksoft.banana.entity.bean.play;

import com.thinksoft.banana.entity.BaseBean;
import com.txf.other_toolslibrary.tools.StringTools;

/**
 * @author txf
 * @create 2019/3/13 0013
 * @
 */
public class BalanceBean extends BaseBean {

    /**
     * free_count : 1000
     * diamond : 1000000.00
     */

    private int free_count;
    private String diamond;

    public int getFree_count() {
        return free_count;
    }

    public void setFree_count(int free_count) {
        this.free_count = free_count;
    }

    public String getDiamond() {
        return diamond;
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }
    public double getDiamondDouble() {
        if (StringTools.isNull(diamond))
            return 0;
        return Double.valueOf(diamond);
    }
}
