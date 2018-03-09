package com.lh.imageloaderdemo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lh.imageloaderdemo.imageloader.FileUtil;
import com.lh.imageloaderdemo.imageloader.ImageLoader;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageview);




        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission != PackageManager.PERMISSION_GRANTED){
            explainDialog();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoader.getInstance().cancleThread();
    }

    public void showImage(View view) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.setCachePath(FileUtil.getExternalStorageDirectory(this).getPath());
        imageLoader.display("http://120.76.98.225/resource/cmall_resource/data/files/goods/store_1/201705181220423842.jpg",imageView);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }else{
            Toast.makeText(this,"用户拒绝了",Toast.LENGTH_SHORT).show();
        }
    }


    private void explainDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("应用需要获取您的读取文件权限,是否授权？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //请求权限
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                }).setNegativeButton("取消", null)
                .create().show();
    }

}
