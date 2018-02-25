package com.example.asus.diners;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewParent;
import android.widget.ImageView;

import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.Utils.DataBaseUtil;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.helper.BmobNative;

public class DishActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ImageView dishImage;

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
        // TODO: 2018/2/25 解决这里可能出现的bug，强转出错 
        Dish dish = (Dish) intent.getSerializableExtra("dish");
        if(dish.getImageBitmap() != null)
        dishImage.setImageBitmap(dish.getImageBitmap());
    }
}
