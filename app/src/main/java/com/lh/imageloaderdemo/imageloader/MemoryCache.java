package com.lh.imageloaderdemo.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by lhh on 2016/10/12 0012.
 * 内存缓存
 */

public class MemoryCache implements ImageCache {
    private LruCache<String,Bitmap> memoryCache ;

    public MemoryCache(){

        //获取系统分配给每个应用程序的最大内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int mCacheSize = maxMemory / 8;
        memoryCache = new LruCache<String, Bitmap>(mCacheSize){

            //必须重写此方法，来测量Bitmap的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }

        };
    }



    @Override
    public Bitmap get(String url) {
        return memoryCache.get(url);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        memoryCache.put(url,bitmap);
    }

    @Override
    public void setCachePath(String cachePath) {
    }


}
