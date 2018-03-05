package com.example.asus.diners.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.asus.diners.R;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by asus on 2018/3/3.
 */

public class ArticleAdapter extends PagerAdapter {

    private static final String TAG = "ArticleAdapter";
    private String[] list;
    private Context context;
    public ArticleAdapter(String[] list , Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.article_layout , container , false);
        WebView article = (WebView) view.findViewById(R.id.article);
        article.getSettings().setUseWideViewPort(true);
        article.getSettings().setLoadWithOverviewMode(true);
        article.setWebViewClient(new WebViewClient());
        Log.i(TAG, "instantiateItem: " + list[position]);
        article.loadUrl(list[position]);
//        TextView url = (TextView) view.findViewById(R.id.url);
//        url.setText(list.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
