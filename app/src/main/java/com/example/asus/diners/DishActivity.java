package com.example.asus.diners;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.asus.diners.Fragment.CommentFragment;
import com.example.asus.diners.Fragment.DetailFragment;
import com.example.asus.diners.Fragment.PlaceFragment;
import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.Utils.DataBaseUtil;
import com.example.asus.diners.View.ViewPagerAdapter;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class DishActivity extends AppCompatActivity {

    private ViewPager dishActions;
    private ImageView dishImage;
    private Dish dish;
    private NavigationTabBar dishActionTab;
    private static final String TAG = "DishActivity";
    private final DetailFragment detailFragment = new DetailFragment();
    private final CommentFragment commentFragment = new CommentFragment();
    private final PlaceFragment mPlaceFragment = new PlaceFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        onBindView();
    }

    private void onBindView(){
        dishImage = (ImageView) findViewById(R.id.dish_big_image);
        dishActions = (ViewPager) findViewById(R.id.dish_actions);
        dishActionTab = (NavigationTabBar) findViewById(R.id.dish_action_tab);
        Intent intent = getIntent();
        try {
//            dish =   (Dish) intent.getSerializableExtra("dish");
            dish = intent.getParcelableExtra("dish");
            if(DataBaseUtil.getImage(dish.getImagePath()) != null)
            dishImage.setImageBitmap(DataBaseUtil.getImage(dish.getImagePath()));
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
//                        DataBaseUtil.getImage(dishes[0] , dishImage);
//                        Log.v(TAG , "查询成功" + dish.getName());
//                    }else {
//                        Log.v(TAG , "查询失败" + e.getMessage());
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        initializeDishActions();
    }

    private void initializeDishActions(){
        ArrayList<android.support.v4.app.Fragment> list = new ArrayList<>();
        detailFragment.setDish(dish);
        commentFragment.setDish(dish);
        mPlaceFragment.setDish(dish);
       list.add(detailFragment);
       list.add(mPlaceFragment);
       list.add(commentFragment);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager() , list);
        dishActions.setAdapter(adapter);
        dishActions.setOffscreenPageLimit(2 );
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_description_black_24dp) ,
                        getResources().getColor(R.color.colorAccent)
                ).title("详情")
                        .badgeTitle("detail")
                        .build()
        );
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_restaurant_menu_black_24dp) ,
                        getResources().getColor(R.color.colorAccent)
                ).title("点菜")
                        .badgeTitle("dish")
                        .build()
        );
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_star_black_24dp) ,
                        getResources().getColor(R.color.colorAccent)
                ).title("评论")
                        .badgeTitle("comment")
                        .build()
        );
        dishActionTab.setModels(models);
        dishActionTab.setViewPager(dishActions);
    }
}
