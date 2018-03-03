package com.example.asus.diners.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.R;

public class DetailFragment extends Fragment {

    private TextView material;
    private TextView calorie;
    private TextView taste;
    private TextView system;
    private Dish dish;
    private static final String TAG = "DetailFragment";

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        material = (TextView) view.findViewById(R.id.material);
        taste = (TextView) view.findViewById(R.id.taste);
        calorie = (TextView) view.findViewById(R.id.calorie);
        system = (TextView) view.findViewById(R.id.system);
        onInitData();
        return view;
    }

    private void onInitData(){
        if(dish == null){
            Log.i(TAG, "onInitData: dish为空");
            return;
        }
        material.setText(dish.getMaterial());
        taste.setText(dish.getTaste());
        system.setText(dish.getSystem());
        calorie.setText(dish.getCalorie());
        Log.i(TAG, "onInitData: " + dish.getName() + dish.getMaterial()
                + dish.getTaste() + dish.getCalorie() + dish.getSystem());
    }

}
