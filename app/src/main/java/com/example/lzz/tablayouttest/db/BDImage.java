package com.example.lzz.tablayouttest.db;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 2017/3/7.
 */

public class BDImage {

    private String imageTitle;

    private String imageUrl;

    private int imageWidth;

    private int imageHeight;

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }
}
