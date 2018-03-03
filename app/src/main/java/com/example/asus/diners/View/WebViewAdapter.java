package com.example.asus.diners.View;

import android.annotation.SuppressLint;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.asus.diners.R;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by asus on 2018/3/3.
 */

public class WebViewAdapter extends PagerAdapter {

    ArrayList<String> list;

    public WebViewAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.article_layout , container , false);
        WebView article = (WebView) view.findViewById(R.id.article);
        article.getSettings().setJavaScriptEnabled(true);
        article.setWebViewClient(new WebViewClient());
        article.loadUrl(list.get(position));
        return article;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
