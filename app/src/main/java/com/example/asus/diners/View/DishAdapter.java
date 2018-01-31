package com.example.asus.diners.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.R;

import java.util.ArrayList;

/**
 * Created by asus on 2018/1/29.
 */

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder>{
    
    private ArrayList<Dish> list;

    public DishAdapter(ArrayList<Dish> list) {
        this.list = list;
    }

    public ArrayList<Dish> getList() {
        return list;
    }

    public void setList(ArrayList<Dish> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_layout , parent , false );
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO: 2018/1/31 解决Bmob图片加载问题 
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView dishImage;
        TextView dishName;
        LinearLayout star;

        public ViewHolder(View itemView) {
            super(itemView);
            dishImage = (ImageView) itemView.findViewById(R.id.image);
            dishName = (TextView) itemView.findViewById(R.id.name);
            star = (LinearLayout) itemView.findViewById(R.id.star);
        }
    }
}
