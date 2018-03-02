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
import com.example.asus.diners.Utils.DataBaseUtil;
import com.example.asus.diners.View.DishAdapter;

import java.util.ArrayList;
import java.util.Objects;


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
            DataBaseUtil.searchDish(intent.getStringExtra(SearchManager.QUERY) , mAdapter);
        } else if(Objects.equals(intent.getAction(), MainActivity.ATTRIBUTE_ACTION)){
            DataBaseUtil.searchDishByAttribute(intent.getStringExtra("attribute") , mAdapter);
        }else if(Objects.equals(intent.getAction(), MainActivity.TYPE_ACTION)){
            DataBaseUtil.searchDishByType(intent.getStringExtra("type") , mAdapter);
        }
    }
}
