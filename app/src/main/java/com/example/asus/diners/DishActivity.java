package com.example.asus.diners;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.asus.diners.Fragment.CommentFragment;
import com.example.asus.diners.Fragment.DetailFragment;
import com.example.asus.diners.Fragment.PlaceFragment;
import com.example.asus.diners.Model.Comment;
import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.Utils.DataBaseUtil;
import com.example.asus.diners.View.ViewPagerAdapter;

import java.util.ArrayList;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import devlight.io.library.ntb.NavigationTabBar;

public class DishActivity extends AppCompatActivity {

    private ViewPager dishActions;
    private ImageView dishImage;
    private Dish dish;
    private NavigationTabBar dishActionTab;
    private Toolbar toolbar;
    private static final String TAG = "DishActivity";
    private final DetailFragment detailFragment = new DetailFragment();
    private final CommentFragment commentFragment = new CommentFragment();
    private final PlaceFragment mPlaceFragment = new PlaceFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        toolbar = (Toolbar) findViewById(R.id.dish_title_bar);
        setSupportActionBar(toolbar);
        onBindView();
        onInitEvent();
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
        toolbar.setTitle(dish.getName());
        Log.i(TAG, "onBindView: 菜名" + dish.getName());
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

    private void onInitEvent(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: you have click on the come back button");
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dish_menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_comment:
                addCommentDialog();
                break;
            default:
                break;
        }
        return true;
    }

    private void addCommentDialog(){
        AlertDialog.Builder addComment = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.add_comment_layout, null);
        final RatingBar score = (RatingBar) view.findViewById(R.id.add_score);
        final EditText comment = (EditText) view.findViewById(R.id.comment_text);
        addComment.setView(view);
        addComment.setTitle("添加评论");
        addComment.setPositiveButton("确定" , null);
        addComment.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog addCommentDialog = addComment.create();
        addCommentDialog.show();
        addCommentDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder warnString = new StringBuilder();
                if(comment.getText().length() == 0){
                    warnString.append("请输入评价");
                }
                if(score.getRating() == 0){
                    if(warnString.length() > 0)warnString.append(",");
                    warnString.append("请打分");
                }
                if(warnString.length() > 0){
                    Toast.makeText(DishActivity.this, warnString, Toast.LENGTH_SHORT).show();
                }else {
                    Comment newComment = new Comment(dish, comment.getText().toString());
                    newComment.setScore(score.getRating());
                    newComment.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Log.i(TAG, "addComment :添加成功");
                            } else {
                                Log.i(TAG, "addComment :" + e.getMessage());
                            }
                        }
                    });
                    addCommentDialog.dismiss();
                }
            }
        });
    }
}
