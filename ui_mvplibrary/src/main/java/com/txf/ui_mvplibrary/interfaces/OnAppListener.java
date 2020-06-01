package com.txf.ui_mvplibrary.interfaces;

import android.os.Bundle;

/**
 * 通用回调
 */
public interface OnAppListener {

    interface OnAdapterListener {
        void onInteractionAdapter(int action, Bundle ext);
    }

    interface OnViewListener {
        /**
         * 关闭 Activity
         */
        int ACTION_CLICK_FINISH_ACTIVITY = 1024;
        int ACTION_CLICK_FINISH_LOADING = 1025;
        void onInteractionView(int action, Bundle ext);
    }
    interface OnFragmentListener {
        void onInteractionFragment(int action, Object data, Bundle ext);
    }
    interface OnWindowListener {
        void onInteractionWindow(int action, int tag, Bundle ext);
    }
}
