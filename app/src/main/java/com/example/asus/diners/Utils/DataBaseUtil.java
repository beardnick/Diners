package com.example.asus.diners.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.asus.diners.Model.Comment;
import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.View.DishAdapter;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by asus on 2018/2/12.
 */

public class DataBaseUtil{
    private static final String TAG = "DataBaseUtil";

    public static  void setImage(BmobFile file , final ImageView image){
        if(file == null)return;
        file.download(new DownloadFileListener(){
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    Log.v(TAG , "下载成功，保存路径：" + s);
                    image.setImageBitmap(BitmapFactory.decodeFile(s));
                }else {
                    Log.v(TAG , "下载失败" + e.getMessage());
                }
            }
            @Override
            public void onProgress(Integer integer, long l) {
            }
        });
    }

    public static Bitmap getImage(String path){
        Bitmap image = BitmapFactory.decodeFile(path);
        if(image == null){
            Log.v(TAG , "路径无效");
        }
        return image;
    }

    //如果采用返回ArrayList的方法，将会返回空的list
    //因为查询是在子线程中执行的，还没得到list就已经返回了
    public static void searchDish(String queryString , final DishAdapter adapter){
        final BmobQuery<Dish> query = new BmobQuery<>();
        final char[] queryChars = queryString.toCharArray();
        for(char x : queryChars)Log.v( TAG , String.valueOf(x));
            query.findObjects(new FindListener<Dish>() {
                @Override
                public void done(List<Dish> list, BmobException e) {
                    adapter.getList().clear();
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
                                //直接使用list会产生Java ConcurrentModificationException
                                //这个异常发生在迭代容器同时修改容器时
                                adapter.getList().add(x);
                            }
                        }
                        Log.v(TAG , "查询成功，共：" + list.size() + "条数据");
                    }else{
                        Log.v(TAG , "查询失败" + e.getMessage());
                    }
                    adapter.notifyDataSetChanged();
                }
            });
    }

    public static void setScore(Dish dish , final RatingBar score){
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addQueryKeys("score");
        query.addWhereEqualTo("dish" , new BmobPointer(dish));
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if(e == null){
                    if(list.size() == 0){
                        score.setRating(0);
                    }else {
                        Float sum = 0f;
                        for(Comment x : list){
                            sum += x.getScore();
                        }
                        score.setRating(sum / list.size());
                    }
                    Log.v(TAG , "获取分数成功");
                }else {
                    score.setRating(0);
                    Log.v(TAG , "获取分数失败" + e.getMessage());
                }
            }
        });
    }
}
