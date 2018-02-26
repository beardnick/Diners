package com.example.asus.diners.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by asus on 2018/2/26.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> list;

   public ViewPagerAdapter(FragmentManager manager , ArrayList<Fragment> list){
       super(manager);
       this.list = list;
   }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
