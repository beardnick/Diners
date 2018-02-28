package com.example.asus.diners.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.Model.DishPlace;
import com.example.asus.diners.R;
import com.example.asus.diners.Utils.DataBaseUtil;
import com.example.asus.diners.View.PlaceAdapter;

import java.util.ArrayList;


public class PlaceFragment extends Fragment {

    private Dish dish;

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public PlaceFragment() {
        // Required empty public constructor
    }

    private RecyclerView places;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        places = (RecyclerView) view.findViewById(R.id.places);
        places.setLayoutManager(new LinearLayoutManager(view.getContext()));
        PlaceAdapter adapter = new PlaceAdapter(new ArrayList<DishPlace>());
        DataBaseUtil.searchDishPlace(dish , adapter);
        places.setAdapter(adapter);
        return view;
    }

}
