package com.example.asus.diners;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.asus.diners.View.ArticleAdapter;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class ArticleActivity extends AppCompatActivity {

    private HorizontalInfiniteCycleViewPager articleViewPager;
    private ArticleAdapter adapter;
    private static final String TAG = "ArticleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        onBindView();
    }


    private void onBindView(){
        Intent intent = getIntent();
        String[] urls = intent.getStringArrayExtra("urls");
        articleViewPager = (HorizontalInfiniteCycleViewPager) findViewById(R.id.article_viewpager);
        adapter = new ArticleAdapter(urls , getBaseContext());
        articleViewPager.setAdapter(adapter);
        articleViewPager.setScrollDuration(500);
//        BmobQuery query = new BmobQuery("_Article");
//        query.findObjectsByTable(new QueryListener<JSONArray>() {
//            @Override
//            public void done(JSONArray array, BmobException e) {
//                adapter.getList().clear();
//                if(e == null){
//                    for (int i = 0; i < array.length(); i++) {
//                        try {
//                            JSONObject x = array.getJSONObject(i);
//                            adapter.getList().add(x.getString("url"));
//                        } catch (JSONException e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//                    Log.i(TAG, "done: 查询成功" + adapter.getList().size());
//                }else {
//                    Log.i(TAG, "done: " + e.getMessage());
//                }
////                adapter.notifyDataSetChanged();
//                articleViewPager.notifyDataSetChanged();
//            }
//        });
    }
}
