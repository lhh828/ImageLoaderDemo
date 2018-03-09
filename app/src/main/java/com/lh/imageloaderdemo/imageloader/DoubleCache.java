package com.lh.imageloaderdemo.imageloader;

import android.graphics.Bitmap;

/**
 * Created by lhh on 2016/10/12 0012.
 * 双缓存实现操作类
 */

public class DoubleCache implements ImageCache {
    ImageCache memoryCache ;
    ImageCache diskCache ;

    public DoubleCache() {
        memoryCache = new MemoryCache();
        diskCache = new DiskCache();
    }

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = null;
        bitmap = memoryCache.get(url);
        if(bitmap == null){
            bitmap = diskCache.get(url);
            if(bitmap != null){
                memoryCache.put(url,bitmap);
            }
        }

        return bitmap;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        memoryCache.put(url,bitmap);
        diskCache.put(url,bitmap);
    }

    @Override
    public void setCachePath(String cachePath) {
        diskCache.setCachePath(cachePath);
    }


}
