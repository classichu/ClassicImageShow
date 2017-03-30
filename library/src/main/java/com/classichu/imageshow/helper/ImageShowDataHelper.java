package com.classichu.imageshow.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.classichu.imageshow.ImageShowActivity;
import com.classichu.imageshow.bean.ImageShowBean;
import com.classichu.imageshow.bean.ImageShowDataWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2016/11/14.
 */

public class ImageShowDataHelper {

    public static void setDataAndToImageShow(Context context, String imageUrl,boolean isTitleCenter) {
        Intent intent = new Intent(context, ImageShowActivity.class);
        //
        List<ImageShowBean> imageShowBeanList = new ArrayList<>();
        ImageShowBean imageShowBean = new ImageShowBean();
        imageShowBean.setImageUrl(imageUrl);
        imageShowBean.setTitle(imageUrl);
        imageShowBean.setContent(imageUrl);
        imageShowBeanList.add(imageShowBean);

        ImageShowDataWrapper imageShowDataWrapper=new ImageShowDataWrapper(imageShowBeanList,0,isTitleCenter);

        Bundle bundle=new Bundle();
        bundle.putSerializable("imageShowDataWrapper", imageShowDataWrapper);
        intent.putExtras(bundle);
        //
        context.startActivity(intent);
    }

    public static void setDataAndToImageShow(Context context, ImageShowBean imageShowBean,boolean isTitleCenter) {
        Intent intent = new Intent(context, ImageShowActivity.class);
        //
        List<ImageShowBean> imageShowBeanList = new ArrayList<>();
        imageShowBeanList.add(imageShowBean);

        ImageShowDataWrapper imageShowDataWrapper=new ImageShowDataWrapper(imageShowBeanList,0,isTitleCenter);

        Bundle bundle=new Bundle();
        bundle.putSerializable("imageShowDataWrapper", imageShowDataWrapper);
        intent.putExtras(bundle);
        //
        context.startActivity(intent);
    }

    public static void setDataAndToImageShow(Context context, List<ImageShowBean> imageShowBeanList, int nowSeletedPos,
                                             boolean isTitleCenter) {
        Intent intent = new Intent(context, ImageShowActivity.class);
        //
        List<ImageShowBean> imageShowBeanArrayList = new ArrayList<>();
        imageShowBeanArrayList.addAll(imageShowBeanList);

        ImageShowDataWrapper imageShowDataWrapper=new ImageShowDataWrapper(imageShowBeanArrayList,nowSeletedPos,isTitleCenter);

        Bundle bundle=new Bundle();
        bundle.putSerializable("imageShowDataWrapper", imageShowDataWrapper);
        intent.putExtras(bundle);
        //
        context.startActivity(intent);
    }
}
