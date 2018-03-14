package com.example.asus.diners.Fragment;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.asus.diners.ArticleActivity;
import com.example.asus.diners.MainActivity;
import com.example.asus.diners.R;
import com.example.asus.diners.View.DishAttributeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class FirstFragment extends Fragment {

    public static final String TYPE_ACTION = "com.example.asus.diners.MainActivity.TYPE_ACTION";
    public static final String ATTRIBUTE_ACTION = "com.example.asus.diners.MainActivity.ATTRIBUTE_ACTION";

    private final static String TAG = "MainActivity";
    private SearchView mSearchView;
    private String[] dishAttributes = {"小吃", "米饭套餐", "面食", "粥汤", "火锅"};
    private Button more;
    private WebView randomArticle;
    private ArrayList<String> urls;
    private Toolbar mToolbar;
    private View view;


    public FirstFragment() {
        // Required empty public constructor
    }

    private AppCompatActivity appCompatActivity;

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_first, container, false);
        onBindView();
        onInitData();
        return view;
    }

    private void onBindView(){
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.dish_attribute_button);
        LinearLayoutManager layoutManager=new LinearLayoutManager(appCompatActivity);
        recyclerView.setLayoutManager(layoutManager);
        DishAttributeAdapter dishAttributeAdapter = new DishAttributeAdapter(dishAttributes);
        recyclerView.setAdapter(dishAttributeAdapter);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // TODO: 2018/3/14 remove searchview
//        mSearchView = (SearchView) view.findViewById(R.id.search);
//        SearchManager manager = (SearchManager) appCompatActivity.getSystemService(Context.SEARCH_SERVICE);
        more = (Button) view.findViewById(R.id.more);
        randomArticle = (WebView) view.findViewById(R.id.random_article);
//        mSearchView.setSearchableInfo(manager.getSearchableInfo(appCompatActivity.getComponentName()));
//        mSearchView.setSubmitButtonEnabled(true);
//        mToolbar = (Toolbar) view.findViewById(R.id.app_title);
//        appCompatActivity.setSupportActionBar(mToolbar);
        onBindButtons();
        onCreateArticle();
    }

    private void onBindButtons(){
        final Button[] typeBtns = new Button[6];
        int[] btnIds = {R.id.breakfast , R.id.lunch , R.id.supper , R.id.newproducts , R.id.support , R.id.lighteat};
        for (int i = 0; i < typeBtns.length; i++) {
            typeBtns[i] = (Button) view.findViewById(btnIds[i]);
            final String text = typeBtns[i].getText().toString();
            typeBtns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TYPE_ACTION);
                    intent.putExtra("type" ,text );
                    startActivity(intent);
                }
            });
        }
    }

    private void onCreateArticle(){
        randomArticle.setWebViewClient(new WebViewClient());
        randomArticle.getSettings().setUseWideViewPort(true);
        randomArticle.getSettings().setLoadWithOverviewMode(true);
        randomArticle.getSettings().setDomStorageEnabled(true);
        randomArticle.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        BmobQuery query = new BmobQuery("_Article");
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray array, BmobException e) {
                if(e == null){
                    try {
                        if(array.length() > 0){
                            Random random = new Random();
                            String temp =array.getJSONObject(Math.abs(random.nextInt() ) % array.length()).getString("url");
                            Log.i(TAG, "url :" + temp );
                            randomArticle.loadUrl(temp);
                        }
                        else Log.i(TAG , "array.length <= 0");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }else {
                    Log.i(TAG, "查询素材失败 :" + e.getMessage());
                }

            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(appCompatActivity, ArticleActivity.class);
                intent.putExtra("urls"  , urls.toArray(new String[urls.size()]));
                startActivity(intent);
            }
        });

    }

    private void onInitData(){
        urls = new ArrayList<>();
        BmobQuery query = new BmobQuery("_Article");
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray array, BmobException e) {
//                adapter.getList().clear();
                if(e == null){
                    for (int i = 0; i < array.length(); i++) {
                        try {
                            JSONObject x = array.getJSONObject(i);
                            urls.add(x.getString("url"));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    Log.i(TAG, "done: 查询成功" + urls.size());
                }else {
                    Log.i(TAG, "done: " + e.getMessage());
                }
            }
        });
    }

}
