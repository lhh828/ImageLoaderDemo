package com.lh.imageloaderdemo.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lhh on 2016/10/12 0012.
 */

public class HttpUtils {
    public static Bitmap getBitmap(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    /**
     * 根据url下载图片在指定的文件
     *
     * @param imageUrl
     * @param imageview
     * @return
     */
    public static Bitmap downloadImgByUrl(String imageUrl, ImageView imageview) {
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            is = new BufferedInputStream(conn.getInputStream());
            is.mark(is.available());

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeStream(is,null, opts);

            //获取imageview想要显示的宽和高
            ImageSizeUtil.ImageSize imageViewSize = ImageSizeUtil.getImageViewSize(imageview);
            opts.inSampleSize = ImageSizeUtil.caculateInSampleSize(opts,
                    imageViewSize.width, imageViewSize.height);

            opts.inJustDecodeBounds = false;
            is.reset();
            bitmap = BitmapFactory.decodeStream(is, null, opts);

            conn.disconnect();
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }


        }

        return null;

    }


}
