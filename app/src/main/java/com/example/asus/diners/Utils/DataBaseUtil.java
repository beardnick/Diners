package com.example.asus.diners.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

/**
 * Created by asus on 2018/2/12.
 */

public class DataBaseUtil{
    private static final String TAG = "DataBaseUtil";

    public static Bitmap getImage(BmobFile file){
        final Bitmap[] image = new Bitmap[1];
        file.download(new DownloadFileListener(){
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    Log.v(TAG , "下载成功，保存路径：" + s);
                    image[0] = BitmapFactory.decodeFile(s);
                }else {
                    Log.v(TAG , "下载失败" + e.getMessage());
                    image[0] = null;
                }
            }
            @Override
            public void onProgress(Integer integer, long l) {
            }
        });
        return image[0];
    }

    public static Bitmap getImage(String path){
        Bitmap image = BitmapFactory.decodeFile(path);
        if(image == null){
            Log.v(TAG , "路径无效");
        }
        return image;
    }

}
