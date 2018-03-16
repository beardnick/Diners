package com.example.asus.diners;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.asus.diners.Fragment.CommunityFragment;
import com.example.asus.diners.Fragment.FirstFragment;
import com.example.asus.diners.Fragment.FootPrintFragment;
import com.example.asus.diners.Fragment.MyFragment;
import com.example.asus.diners.View.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import devlight.io.library.ntb.NavigationTabBar;

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
    private NavigationTabBar pageTabs;
    private ViewPager pages;
    private FirstFragment firstFragment;
    private MyFragment myFragment;
    private FootPrintFragment footPrintFragment;
    private CommunityFragment communityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.app_title);
        setSupportActionBar(mToolbar);
        onInitBmob();
        onBindView();
//        onInitData();
    }

    private void onInitBmob(){
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
        BmobInstallationManager.getInstance().initialize(new InstallationListener<BmobInstallation>() {
            @Override
            public void done(BmobInstallation bmobInstallation, BmobException e) {
                if(e == null){
                    Log.i(TAG, "注册成功" + bmobInstallation.getInstallationId());
                }else {
                    Log.i(TAG, "注册失败" + e.getMessage());
                }
            }
        });
    }

    private void onBindView(){
        pageTabs = (NavigationTabBar) findViewById(R.id.page_tabs);
        pages = (ViewPager) findViewById(R.id.pages);
        ArrayList<android.support.v4.app.Fragment> list = new ArrayList<>();
        firstFragment = new FirstFragment();
        myFragment = new MyFragment();
        footPrintFragment = new FootPrintFragment();
        communityFragment = new CommunityFragment();
        firstFragment.setAppCompatActivity(this);
        myFragment.setAppCompatActivity(this);
        footPrintFragment.setAppCompatActivity(this);
        communityFragment.setAppCompatActivity(this);
        list.add(firstFragment);
        list.add(communityFragment);
        list.add(footPrintFragment);
        list.add(myFragment);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager() , list);
        pages.setAdapter(adapter);
        pages.setOffscreenPageLimit(2 );
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_home_black_24dp) ,
                        getResources().getColor(R.color.colorAccent)
                ).title("首页")
                        .badgeTitle("first_page")
                        .build()
        );
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_people_black_24dp) ,
                        getResources().getColor(R.color.colorAccent)
                ).title("社区")
                        .badgeTitle("community")
                        .build()
        );
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_payment_black_24dp) ,
                        getResources().getColor(R.color.colorAccent)
                ).title("足迹")
                        .badgeTitle("foot_print")
                        .build()
        );
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_account_circle_black_24dp) ,
                        getResources().getColor(R.color.colorAccent)
                ).title("我的")
                        .badgeTitle("mine")
                        .build()
        );
        pageTabs.setModels(models);
        pageTabs.setViewPager(pages);
    }
//    private void onBindView(){
//        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.dish_attribute_button);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        DishAttributeAdapter dishAttributeAdapter = new DishAttributeAdapter(dishAttributes);
//        recyclerView.setAdapter(dishAttributeAdapter);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        mSearchView = (SearchView) findViewById(R.id.search);
//        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        more = (Button) findViewById(R.id.more);
//        randomArticle = (WebView) findViewById(R.id.random_article);
//        mSearchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
//        mSearchView.setSubmitButtonEnabled(true);
//        mToolbar = (Toolbar) findViewById(R.id.app_title);
//        setSupportActionBar(mToolbar);
//        onBindButtons();
//        onCreateArticle();
//    }
//
//    private void onBindButtons(){
//        final Button[] typeBtns = new Button[6];
//        int[] btnIds = {R.id.breakfast , R.id.lunch , R.id.supper , R.id.newproducts , R.id.support , R.id.lighteat};
//        for (int i = 0; i < typeBtns.length; i++) {
//             typeBtns[i] = (Button) findViewById(btnIds[i]);
//             final String text = typeBtns[i].getText().toString();
//             typeBtns[i].setOnClickListener(new View.OnClickListener() {
//                 @Override
//                 public void onClick(View v) {
//                     Intent intent = new Intent(TYPE_ACTION);
//                     intent.putExtra("type" ,text );
//                     startActivity(intent);
//                 }
//             });
//        }
//    }

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
            }
        });
    }

    private SearchView searchView;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager manager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        assert manager != null;
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)
                searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.LTGRAY);
        searchAutoComplete.setTextColor(Color.WHITE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getSupportActionBar().setTitle("");
        searchView.setBackgroundColor(Color.WHITE);
        return true;
    }

}
