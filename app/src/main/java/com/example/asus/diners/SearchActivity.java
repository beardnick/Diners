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
import com.example.asus.diners.Utils.DataBaseUtil;
import com.example.asus.diners.View.DishAdapter;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DishAdapter mAdapter;
    public static final String TAG = "SearchActivity";


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
        mRecyclerView.setLayoutManager(manager);
    }

    public void receiveSearch(){
        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String queryString = intent.getStringExtra(SearchManager.QUERY);
            mAdapter = new DishAdapter(DataBaseUtil.searchDish(queryString));
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
