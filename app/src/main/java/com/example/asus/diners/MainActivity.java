package com.example.asus.diners;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus.diners.Model.Comment;
import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.Model.DishPlace;
import com.example.asus.diners.Model.DishType;
import com.example.asus.diners.Model.Place;
import com.example.asus.diners.View.DishAttributeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends AppCompatActivity  {

    public static final String TYPE_ACTION = "com.example.asus.diners.MainActivity.TYPE_ACTION";
    public static final String ATTRIBUTE_ACTION = "com.example.asus.diners.MainActivity.ATTRIBUTE_ACTION";

    private final static String TAG = "MainActivity";
    private SearchView mSearchView;
    private String[] dishAttributes = {"小吃", "米饭套餐", "面食", "粥汤", "火锅"};
    private Button more;
    private WebView randomArticle;
    private ArrayList<String> urls;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
//        initDishattribute();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.dish_attribute_button);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DishAttributeAdapter dishAttributeAdapter = new DishAttributeAdapter(dishAttributes);
        recyclerView.setAdapter(dishAttributeAdapter);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config =new BmobConfig.Builder(this)
        //设置appkey
        .setApplicationId("087b71a382e85618bd2622d8dfad9a58")
        //请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(30)
        //文件分片上传时每片的大小（单位字节），默认512*1024
        .setUploadBlockSize(1024*1024)
        //文件的过期时间(单位为秒)：默认1800s
        .setFileExpiration(2500)
        .build();
        Bmob.initialize(config);
        onBindView();
        onInitData();
    }

//    private void initDishType(){
//        for(int i=0;i<7;i++){
//            DishType nutritious_breakfast =new DishType("营养早餐",R.drawable.breakfast);
//            dishTypeList.add(nutritious_breakfast);
//            DishType great_lunch =new DishType("丰盛午餐",R.drawable.lunch);
//            dishTypeList.add(great_lunch);
//            DishType warm_dinner =new DishType("温馨晚餐",R.drawable.dinner);
//            dishTypeList.add(warm_dinner);
//        }
//    }

//    private void initDishattribute(){
//        for(int i=0;i<5;i++)
//
//    }

    private void onBindView(){
        mSearchView = (SearchView) findViewById(R.id.search);
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        more = (Button) findViewById(R.id.more);
        randomArticle = (WebView) findViewById(R.id.random_article);
//        breakfastBtn.setOnClickListener(this);
//        lunchBtn.setOnClickListener(this);
//        supperBtn .setOnClickListener(this);
//        lighteatBtn.setOnClickListener(this);
//        newProductsBtn.setOnClickListener(this);
//        supportBtn.setOnClickListener(this);
        mSearchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        mSearchView.setSubmitButtonEnabled(true);
        mToolbar = (Toolbar) findViewById(R.id.app_title);
        setSupportActionBar(mToolbar);
        onBindButtons();
        onCreateArticle();
    }

    private void onBindButtons(){
        final Button[] typeBtns = new Button[6];
        int[] btnIds = {R.id.breakfast , R.id.lunch , R.id.supper , R.id.newproducts , R.id.support , R.id.lighteat};
        for (int i = 0; i < typeBtns.length; i++) {
             typeBtns[i] = (Button) findViewById(btnIds[i]);
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
                Intent intent = new Intent(MainActivity.this , ArticleActivity.class);
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
//                adapter.notifyDataSetChanged();
//                articleViewPager.notifyDataSetChanged();
            }
        });
    }

}
