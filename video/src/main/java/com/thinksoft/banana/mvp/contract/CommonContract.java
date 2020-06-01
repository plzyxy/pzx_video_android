package com.thinksoft.banana.mvp.contract;

import com.thinksoft.banana.mvp.base.BaseAppPresenter;
import com.thinksoft.banana.mvp.base.BaseAppView;

/**
 * @author txf
 * @create 2019/1/22 0022
 * @
 */

public interface CommonContract {
    interface View extends BaseAppView {
    }
    abstract class Presenter extends BaseAppPresenter<View> {
    }
}
