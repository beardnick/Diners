package com.example.asus.diners;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.Utils.DataBaseUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class DishActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ImageView dishImage;
    private static final String TAG = "DishActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        onBindView();
    }

    private void onBindView(){
        dishImage = (ImageView) findViewById(R.id.dish_big_image);
        mViewPager = (ViewPager) findViewById(R.id.dish_actions);
        Intent intent = getIntent();
        try {
            Dish dish =   intent.getParcelableExtra("dish");
            if(DataBaseUtil.setImage(dish.getImagePath()) != null)
            dishImage.setImageBitmap(DataBaseUtil.setImage(dish.getImagePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO: 2018/2/26 搞清楚为啥不能通过query得到对象
//        BmobQuery<Dish> query = new BmobQuery<>();
//        final Dish[] dishes = new Dish[1];
//        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
//        try {
//            query.getObject(intent.getStringExtra("dish_id"), new QueryListener<Dish>() {
//                @Override
//                public void done(Dish dish, BmobException e) {
//                    if(e == null){
//                        dishes[0] = dish;
//                        DataBaseUtil.setImage(dishes[0] , dishImage);
//                        Log.v(TAG , "查询成功" + dish.getName());
//                    }else {
//                        Log.v(TAG , "查询失败" + e.getMessage());
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
