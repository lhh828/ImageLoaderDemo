package com.lh.imageloaderdemo.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lhh on 2016/10/12 0012.
 * 文件存储
 */

public class DiskCache implements ImageCache {
    private String cachePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ImageLoadCache/";

    @Override
    public Bitmap get(String url) {
        String s = md5(url);
        File file = new File(cachePath + s);
        Bitmap bitmap = null ;
        if(file.exists()) {
            bitmap = BitmapFactory.decodeFile(cachePath + s);
        }

        return bitmap;

    }

    @Override
    public void put(String url, Bitmap bitmap) {
        String s = md5(url);
        File file = new File(cachePath + s);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file.getPath());
            bitmap.compress(Bitmap.CompressFormat.JPEG.PNG,100,os);
            os.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(os != null){
                closeQuietly(os);
            }
        }
    }

    @Override
    public void setCachePath(String cachePath) {
        if(!cachePath.endsWith("/"))
            cachePath = cachePath + "/";
        this.cachePath = cachePath;

    }


    public String getCachePath() {
        return cachePath;
    }


    public void closeQuietly(Closeable closeable){
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String md5(String str)
    {
        byte[] digest = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance("md5");
            digest = md.digest(str.getBytes());
            return bytes2hex02(digest);

        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 方式二
     *
     * @param bytes
     * @return
     */
    public String bytes2hex02(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (byte b : bytes)
        {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() == 1)// 每个字节8为，转为16进制标志，2个16进制位
            {
                tmp = "0" + tmp;
            }
            sb.append(tmp);
        }

        return sb.toString()+".jpg";

    }

}
