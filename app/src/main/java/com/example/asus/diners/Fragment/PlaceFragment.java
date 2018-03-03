package com.example.asus.diners.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.Model.DishPlace;
import com.example.asus.diners.R;
import com.example.asus.diners.Utils.DataBaseUtil;
import com.example.asus.diners.View.PlaceAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class PlaceFragment extends Fragment {

    private static final String TAG = "PlaceFragment";
    private Dish dish;
    private PlaceAdapter adapter;
    private RelativeLayout noPlace;

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
        noPlace = (RelativeLayout) view.findViewById(R.id.no_place_layout);
        places = (RecyclerView) view.findViewById(R.id.places);
        places.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new PlaceAdapter(new ArrayList<DishPlace>());
        places.setAdapter(adapter);
        searchDishPlace(dish);
        return view;
    }

    private void searchDishPlace(final Dish dish){
        BmobQuery<DishPlace> query = new BmobQuery<>();
        query.addWhereEqualTo("dish" , new BmobPointer(dish));
        query.include("place");
        query.findObjects(new FindListener<DishPlace>() {
            @Override
            public void done(List<DishPlace> list, BmobException e) {
                try {
                        adapter.getList().clear();
                    if(e == null){
                        adapter.getList().addAll(list);
                        Log.i(TAG, "searchDishPlace :查询成功" + list.size());
                        if(list.size() > 0)
                            Log.i(TAG, "searchDishPlace :查询成功" +
                                    list.get(0).getPlace().getName() + list.get(0).getDish().getName());
                    }else {
                        Log.i(TAG, "searchDishPlace :查询失败" + e.getMessage());
                    }
                        notFoundAction();
                        adapter.notifyDataSetChanged();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void notFoundAction(){
        if(adapter.getList().size() == 0){
            noPlace.setVisibility(View.VISIBLE);
        }else {
            noPlace.setVisibility(View.INVISIBLE);
        }
    }
}
