package com.classichu.imageshow.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by louisgeek on 2016/12/8.
 */

public class ImageShowDataWrapper implements Serializable{
    private List<ImageShowBean> imageShowBeanList;
    private int nowSeletedPos;

    public boolean isTitleCenter() {
        return isTitleCenter;
    }

    private boolean isTitleCenter;

    public ImageShowDataWrapper(List<ImageShowBean> imageShowBeanList, int nowSeletedPos,boolean isTitleCenter) {
        this.imageShowBeanList = imageShowBeanList;
        this.nowSeletedPos = nowSeletedPos;
        this.isTitleCenter = isTitleCenter;
    }

    public List<ImageShowBean> getImageShowBeanList() {
        return imageShowBeanList;
    }

    public int getNowSeletedPos() {
        return nowSeletedPos;
    }
}
