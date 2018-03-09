package com.lh.imageloaderdemo.imageloader;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by ${lhh} on 2017/8/12.
 */

public class FileUtil {



    public static File getExternalStorageDirectory(Context context){
        File appCacheDir = null;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            appCacheDir = new File(Environment.getExternalStorageDirectory() ,"ImageLoadCache");//内部存储根目录下
        }else{
            appCacheDir = new File(context.getFilesDir(), Environment.DIRECTORY_PICTURES);

        }
        if(!appCacheDir.exists()){
            appCacheDir.mkdirs();
        }

        return appCacheDir;
    }

}
