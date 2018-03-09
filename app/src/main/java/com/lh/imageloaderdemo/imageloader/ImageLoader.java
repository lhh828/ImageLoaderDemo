package com.lh.imageloaderdemo.imageloader;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lhh on 2016/10/12 0012.
 * 图片加载类，
 */

public class ImageLoader {
    //线程池的最大线程数（设置为cup的数量）
    private int poolSize = Runtime.getRuntime().availableProcessors();
    private ImageCache imageCache ;
    //线程池
    private ExecutorService mExecutorService ;

    private static ImageLoader imageloader;
    private int errorDrawable = 0;
    private MyHandler myHandler;

    private ImageLoader(){
        myHandler = new MyHandler();
        mExecutorService = Executors.newFixedThreadPool(poolSize);
        imageCache = new DoubleCache();
    }

    public static ImageLoader getInstance(){
        if(imageloader == null){
            synchronized (ImageLoader.class){
                if(imageloader == null){
                    imageloader = new ImageLoader();
                }
            }
        }
        return imageloader;

//        return SingleImageLoader.instance;
    }


    public void setErrorDrawable(int errorDrawable){
        this.errorDrawable = errorDrawable;
    }

    public void setImageCache(ImageCache cache){
        this.imageCache = cache;
    }

    public void setCachePath(String cachePath){
        imageCache.setCachePath(cachePath);
    }


    public void display(String url, ImageView imageView){
        Bitmap bitmap = imageCache.get(url);
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
        }else{
            loadFromService(url,imageView);
        }

    }

    //从服务器获取图片
    private void loadFromService(final String url, final ImageView imageView) {

        ThreadLoad threadLoad =  new ThreadLoad(url,imageView);
        mExecutorService.execute(threadLoad);
        threadArray.put(url,threadLoad);

    }


    private ArrayMap<String,ThreadLoad> threadArray = new ArrayMap<>();

    private class ThreadLoad extends Thread{
        private String url ;
        private ImageView imageView ;

        public ThreadLoad(String url,ImageView imageView){
            this.url = url;
            this.imageView = imageView;
            imageView.setTag(url);
        }

        @Override
        public void run() {
            Bitmap bitmap = HttpUtils.getBitmap(url);
            Message msg = Message.obtain();
            ImageBean imageBean = new ImageBean(imageView,bitmap,url);

            msg.obj = imageBean;
            myHandler.sendMessage(msg);
            if(threadArray.get(url) != null){
                threadArray.remove(url);
            }

        }
    }


    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            ImageBean imageBean = (ImageBean) msg.obj;

            if(imageBean.getBitmap() != null){
                if(imageBean.getUrl().equals(imageBean.getImageView().getTag())){
                    imageBean.getImageView().setImageBitmap(imageBean.getBitmap());
                }
                imageCache.put(imageBean.getUrl(),imageBean.getBitmap());//将图片加入内存和文件缓存中
            }else{
                //设置没下载出错的图片
                imageBean.getImageView().setImageResource(errorDrawable);
            }
        }
    }


//    /***
//     * 静态内部类，实现单例模式
//     */
//    private static class SingleImageLoader{
//        private static final ImageLoader instance = new ImageLoader();
//    }



    public void cancleThread(){
        if(mExecutorService != null)
        mExecutorService.shutdownNow();
    }


}
