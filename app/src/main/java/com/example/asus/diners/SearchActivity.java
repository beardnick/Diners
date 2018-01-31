package com.example.asus.diners;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.View.DishAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SearchActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private ArrayList<Dish>mDishes;
    public static final String TAG = "SearchActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dish_layout);
    }

    public void createView(){
        mSearchView = (SearchView) findViewById(R.id.search_dish);
        mRecyclerView = (RecyclerView) findViewById(R.id.dish);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(SearchActivity.this , LinearLayout.VERTICAL ,false );
        mRecyclerView.setLayoutManager(manager);
        test();
    }

    private void test(){
        BmobQuery<Dish> query = new BmobQuery<Dish>();
        query.findObjects(new FindListener<Dish>() {
            @Override
            public void done(List<Dish> list, BmobException e) {
                if(e == null){
                    mDishes = new ArrayList<Dish>(list);
                    Log.v(TAG , "查询成功，共：" + list.size() + " 条数据");
                }else{
                    Log.v(TAG , "查询失败" + e.toString());
                }
            }
        });
        DishAdapter adapter = new DishAdapter(mDishes);
        mRecyclerView.setAdapter(adapter);
    }

}
