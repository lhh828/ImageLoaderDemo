package com.lh.imageloaderdemo.imageloader;

import android.graphics.Bitmap;

/**
 * Created by lhh on 2016/10/12 0012.
 * 缓存策略接口（实现此接口来定义自己的缓存策略）
 */

public interface ImageCache {
    /**
     * 获取url对应的bitmap
     * @param url
     * @return
     */
    Bitmap get(String url);

    /**
     * 将图片存入内存和本地文件中
     * @param url
     * @param bitmap
     */
    void put(String url, Bitmap bitmap);

    /**
     * 设置本地缓存路径
     * @param cachePath
     */
    void setCachePath(String cachePath);
}
