package com.classichu.imageshow.bean;

import java.io.Serializable;

/**
 * Created by louisgeek on 2016/11/8.
 */

public class ImageShowBean implements Serializable {
    private String imageUrl;
    private String title;
    private String content;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
