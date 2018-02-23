package com.example.asus.diners;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.LinearLayout;


import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.View.DishAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Dish>mDishes;
    public static final String TAG = "SearchActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
//        BmobConfig config =new BmobConfig.Builder(this)
//                //设置appkey
//                .setApplicationId("087b71a382e85618bd2622d8dfad9a58")
//                //请求超时时间（单位为秒）：默认15s
//                .setConnectTimeout(30)
//                //文件分片上传时每片的大小（单位字节），默认512*1024
//                .setUploadBlockSize(1024*1024)
//                //文件的过期时间(单位为秒)：默认1800s
//                .setFileExpiration(2500)
//                .build();
//        Bmob.initialize(config);
        setContentView(R.layout.dish_layout);
        Log.v(TAG , "before createView");
        createView();
        Log.v(TAG , "after createView");
        receiveSearch();
    }

    public void createView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.dish);
        mRecyclerView.setAdapter(new DishAdapter(new ArrayList<Dish>()));
        RecyclerView.LayoutManager manager = new LinearLayoutManager(SearchActivity.this  );
        mRecyclerView.setLayoutManager(manager);
    }

    public void receiveSearch(){
        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String queryString = intent.getStringExtra(SearchManager.QUERY);
            performSearch(queryString);
        }
    }

    public void performSearch(String queryString){
        BmobQuery<Dish> query = new BmobQuery<>();
        char[] queryChars = queryString.toCharArray();
        for(char x : queryChars)System.out.println(x);
        query.addWhereContainedIn("name"  , Arrays.asList(queryChars));
        query.findObjects(new FindListener<Dish>() {
            @Override
            public void done(List<Dish> list, BmobException e) {
                if(e == null){
                    mRecyclerView.setAdapter(new DishAdapter((ArrayList<Dish>)list));
                    Log.v(TAG , "查询成功，共：" + list.size() + " 条数据");
                }else{
                    Log.v(TAG , "查询失败" + e.getMessage());
                }
            }
        });
    }
}
