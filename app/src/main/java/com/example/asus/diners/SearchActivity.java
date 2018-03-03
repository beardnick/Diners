package com.example.asus.diners;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.Model.DishType;
import com.example.asus.diners.Model.Type;
import com.example.asus.diners.Utils.DataBaseUtil;
import com.example.asus.diners.View.DishAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class SearchActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DishAdapter mAdapter = new DishAdapter(new ArrayList<Dish>());
    public static final String TAG = "SearchActivity";

    private ImageView notFoundImage;
    private TextView notFoundText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dish_layout);
        createView();
        receiveSearch();

    }

    public void createView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.dish);
        LinearLayoutManager manager = new LinearLayoutManager(SearchActivity.this  );
        notFoundImage = (ImageView) findViewById(R.id.not_found_image);
        notFoundText = (TextView) findViewById(R.id.not_found_text);
//        notFoundText.setVisibility(View.VISIBLE);
//        notFoundImage.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void receiveSearch(){
        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            searchDish(intent.getStringExtra(SearchManager.QUERY));
        } else if(Objects.equals(intent.getAction(), MainActivity.ATTRIBUTE_ACTION)){
            searchDishByAttribute(intent.getStringExtra("attribute"));
        }else if(Objects.equals(intent.getAction(), MainActivity.TYPE_ACTION)){
            searchDishByType(intent.getStringExtra("type"));
        }
    }

    private void searchDish(String queryString){
        final BmobQuery<Dish> query = new BmobQuery<>();
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
        final char[] queryChars = queryString.toCharArray();
        for(char x : queryChars)Log.v( TAG , String.valueOf(x));
        query.findObjects(new FindListener<Dish>() {
            @Override
            public void done(List<Dish> list, BmobException e) {
                mAdapter.getList().clear();
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
                            mAdapter.getList().add(x);
//                                downloadImage(x);
                        }
                    }
                    Log.v(TAG , "查询成功，共：" + list.size() + "条数据");
                }else{
                    Log.v(TAG , "查询失败" + e.getMessage());
                }
                notFoundAction();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void searchDishByType(String type){
        if(type == null || mAdapter == null || mAdapter.getList() ==  null){
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
                                mAdapter.getList().clear();
                                if (e == null) {
                                    for (DishType x: list
                                            ) {
                                        mAdapter.getList().add(x.getDish());
                                        Log.d(TAG, "searchDishByType :" + x.getDish().getObjectId()
                                                + x.getDish().getName() + x.getDish().getTaste());
                                    }
                                    Log.d(TAG, "searchDishByType :查询成功 dish" +mAdapter.getList().size() );
                                }else {
                                    Log.d(TAG, "searchDishByType :查询失败" + e.getMessage());
                                }
                                notFoundAction();
                                mAdapter.notifyDataSetChanged();
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

    private void searchDishByAttribute(String attribute){
        if(attribute == null || mAdapter == null || mAdapter.getList() == null){
            Log.d(TAG, "searchDishByAttribute: attribute or adapter or list is null");
            return;
        }
        BmobQuery<Dish> query = new BmobQuery<>();
        query.addWhereEqualTo("attribute" , attribute);
        query.findObjects(new FindListener<Dish>() {
            @Override
            public void done(List<Dish> list, BmobException e) {
                mAdapter.getList().clear();
                if(e == null){
                    mAdapter.getList().addAll(list);
                    Log.d(TAG, "searchDishByAttribute :查询成功" + list.size());
                }else {
                    Log.d(TAG, "searchDishByAttribute :查询失败" + e.getMessage());
                }
                notFoundAction();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void notFoundAction(){
        if(mAdapter.getList().size() == 0){
            notFoundImage.setVisibility(View.VISIBLE);
            notFoundText.setVisibility(View.VISIBLE);
        }else{
            notFoundText.setVisibility(View.INVISIBLE);
            notFoundImage.setVisibility(View.INVISIBLE);
        }
    }
}
