package com.donkingliang.imageselector;

import java.util.List;

/**
 * @author txf
 * @create 2019/2/20 0020
 * @
 */
public interface OnImgSelectorListener {
    void onCompressStart();

    void onCompressCompleted(List<String> successPaths,List<String> exceptionPaths);
}
