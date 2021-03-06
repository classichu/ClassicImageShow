package com.classichu.imageshow.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.classichu.imageshow.R;
import com.classichu.imageshow.bean.ImageShowBean;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by louisgeek on 2016/11/7.
 */

public class ImageShowPagerAdapter extends PagerAdapter {
    public ImageShowPagerAdapter(List<ImageShowBean> imageShowBeanList) {
        mImageShowBeanList = imageShowBeanList;
    }

    private List<ImageShowBean> mImageShowBeanList = new ArrayList<>();

    @Override
    public int getCount() {
        return mImageShowBeanList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_content_image_show, null, false);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.id_photo_view);
        //单击
        photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                if (mPhotoViewAttacherListener != null) {
                    mPhotoViewAttacherListener.onPhotoViewTap(view, x, y);
                }
            }
        });
        Glide.with(photoView.getContext()).load(mImageShowBeanList.get(position).getImageUrl())
                .placeholder(R.drawable.img_image_no)
                .error(R.drawable.img_image_no)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(photoView);


        if (mPhotoViewAttacherListener != null) {
            mPhotoViewAttacherListener.onPhotoViewInited(photoView);
        }

        container.addView(view);
        // return super.instantiateItem(container, position);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        // super.destroyItem(container, position, object);
    }

    public void refreshImageShowBeanList(List<ImageShowBean> imageShowBeanList) {
        mImageShowBeanList.clear();
        mImageShowBeanList.addAll(imageShowBeanList);
        notifyDataSetChanged();
    }

    public interface PhotoViewAttacherListener {
        void onPhotoViewTap(View photoView, float x, float y);

        void onPhotoViewInited(View photoView);
    }

    public void setPhotoViewAttacherListener(PhotoViewAttacherListener photoViewAttacherListener) {
        mPhotoViewAttacherListener = photoViewAttacherListener;
    }

    PhotoViewAttacherListener mPhotoViewAttacherListener;

}
