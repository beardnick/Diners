package com.example.asus.diners.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.diners.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends Fragment {
    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    private AppCompatActivity appCompatActivity;




    public CommunityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

}
