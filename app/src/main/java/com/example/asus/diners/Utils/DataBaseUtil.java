package com.example.asus.diners.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.View.DishAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

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

    public static ArrayList<Dish> searchDish(String queryString){
        BmobQuery<Dish> query = new BmobQuery<>();
        final char[] queryChars = queryString.toCharArray();
        for(char x : queryChars)Log.v( TAG , String.valueOf(x));
        final ArrayList<Dish> result = new ArrayList<>();
        query.findObjects(new FindListener<Dish>() {
            @Override
            public void done(List<Dish> list, BmobException e) {
                if(e == null){
                    for(Dish x :list){
                        boolean ok = false;
                        for(char y : queryChars){
                            if(x.getName().contains(String.valueOf(y))){
                                ok = true;
                                break;
                            }
                        }
                        if(ok){
                            result.add(x);
                        }
                    }
                    Log.v(TAG , "查询成功，共：" + result.size() + " 条数据");
                }else{
                    Log.v(TAG , "查询失败" + e.getMessage());
                }
            }
        });
        return result;
    }
}
