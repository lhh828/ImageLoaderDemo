package com.lh.imageloaderdemo.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by ${lhh} on 2018/3/9.
 */

public class ImageBean {
    private ImageView imageView;
    private Bitmap bitmap;
    private String url;

    public ImageBean(ImageView imageView, Bitmap bitmap,String url) {
        this.imageView = imageView;
        this.bitmap = bitmap;
        this.url = url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getUrl() {
        return url;
    }
}
