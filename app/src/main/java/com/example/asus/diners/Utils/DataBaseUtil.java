package com.example.asus.diners.Utils;

import android.content.BroadcastReceiver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.asus.diners.Model.Comment;
import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.Model.DishPlace;
import com.example.asus.diners.Model.DishType;
import com.example.asus.diners.Model.Type;
import com.example.asus.diners.View.CommentAdapter;
import com.example.asus.diners.View.DishAdapter;
import com.example.asus.diners.View.PlaceAdapter;

import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by asus on 2018/2/12.
 */

public class DataBaseUtil{
    private static final String TAG = "DataBaseUtil";

    public static void getImage(final Dish dish , final ImageView imageView){
        if(dish.getPic() == null)return;
        String path = imageView.getContext().getApplicationContext()
                .getCacheDir()+"/bmob/" + dish.getPic().getFilename();
        Log.d(TAG, "getImage: 路径 = " + path);
        if(getImage(path) != null){
            imageView.setImageBitmap(getImage(path));
            dish.setImagePath(path);
            return;
        }
        dish.getPic().download(new DownloadFileListener(){
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    Log.v(TAG , "下载成功，保存路径：" + s);
                    dish.setImagePath(s);
                    imageView.setImageBitmap(getImage(s));
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
            return null;
        }
        return image;
    }

    //如果采用返回ArrayList的方法，将会返回空的list
    //因为查询是在子线程中执行的，还没得到list就已经返回了
    public static void searchDish(String queryString , final DishAdapter adapter){
        final BmobQuery<Dish> query = new BmobQuery<>();
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
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
//                                downloadImage(x);
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

    public static void searchDishByType(String type , final DishAdapter adapter){
        if(type == null || adapter == null || adapter.getList() ==  null){
            Log.d(TAG, "searchDishByType: type or adapter or list is null");
            return;
        }
        BmobQuery<Type> typeQuery = new BmobQuery<>();
        BmobQuery<Dish> query = new BmobQuery<>();
        typeQuery.addWhereEqualTo("name" , type);
        typeQuery.findObjects(new FindListener<Type>() {
            @Override
            public void done(List<Type> list, BmobException e) {
                if(e == null){
                    if(list.size() > 0){
                        BmobQuery<DishType> dishTypeQuery = new BmobQuery<>();
                            dishTypeQuery.addWhereEqualTo("type" , new BmobPointer(list.get(0)));
                            dishTypeQuery.include("dish");
                            dishTypeQuery.findObjects(new FindListener<DishType>() {
                                @Override
                                public void done(List<DishType> list, BmobException e) {
                                    adapter.getList().clear();
                                    if (e == null) {
                                        for (DishType x: list
                                             ) {
                                            adapter.getList().add(x.getDish());
                                            Log.d(TAG, "searchDishByType :" + x.getDish().getObjectId()
                                                    + x.getDish().getName() + x.getDish().getTaste());
                                        }
                                        Log.d(TAG, "searchDishByType :查询成功 dish" +adapter.getList().size() );
                                    }else {
                                        Log.d(TAG, "searchDishByType :查询失败" + e.getMessage());
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            });
                    }
                    Log.d(TAG, "searchDishByType :查询成功 type" + list.size());
                }else {
                    Log.d(TAG, "searchDishByType :查询失败" + e.getMessage());
                }
            }
        });
    }

    public static void searchDishByAttribute(String attribute , final DishAdapter adapter){
        if(attribute == null || adapter == null || adapter.getList() == null){
            Log.d(TAG, "searchDishByAttribute: attribute or adapter or list is null");
            return;
        }
        BmobQuery<Dish> query = new BmobQuery<>();
        query.addWhereEqualTo("attribute" , attribute);
        query.findObjects(new FindListener<Dish>() {
            @Override
            public void done(List<Dish> list, BmobException e) {
                adapter.getList().clear();
                if(e == null){
                    adapter.getList().addAll(list);
                    Log.d(TAG, "searchDishByAttribute :查询成功" + list.size());
                }else {
                    Log.d(TAG, "searchDishByAttribute :查询失败" + e.getMessage());
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public static void searchDishPlace(final Dish dish , final PlaceAdapter adapter){
        BmobQuery<DishPlace> query = new BmobQuery<>();
        query.addWhereEqualTo("dish" , new BmobPointer(dish));
        query.include("place");
        query.findObjects(new FindListener<DishPlace>() {
            @Override
            public void done(List<DishPlace> list, BmobException e) {
                try {
                    if(adapter.getList().size() != 0)
                        adapter.getList().clear();
                    if(e == null){
                        adapter.getList().addAll(list);
                        Log.d(TAG, "searchDishPlace :查询成功" + list.size());
                        if(list.size() > 0)
                        Log.d(TAG, "searchDishPlace :查询成功" +
                                list.get(0).getPlace().getName() + list.get(0).getDish().getName());
                    }else {
                        Log.d(TAG, "searchDishPlace :查询失败" + e.getMessage());
                    }
                    if(adapter.getList().size() != 0)
                        adapter.notifyDataSetChanged();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void searchComment(final Dish dish, final CommentAdapter adapter){
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("dish" , new BmobPointer(dish));
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                    adapter.getList().clear();
                Log.d(TAG, "searchComment: objectId: " + dish.getObjectId());
                if(list.size() > 0)
                    Log.d(TAG, "searchComment: " +
                list.get(0).getContent() + list.get(0).getScore());
                if(e == null){
                    adapter.getList().addAll(list);
                    Log.d(TAG, "searchComment: 查询成功" +list.size() );
                }else {
                    Log.d(TAG, "searchComment: 查询失败" + e.getMessage());
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public static void setScore(final Dish dish , final RatingBar score){
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addQueryKeys("score");
        query.addWhereEqualTo("dish" , new BmobPointer(dish));
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                Log.d(TAG, "setScore: objectId: " + dish.getObjectId());
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
