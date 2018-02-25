package com.example.asus.diners.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.asus.diners.DishActivity;
import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.R;
import com.example.asus.diners.Utils.DataBaseUtil;

import java.util.ArrayList;

/**
 * Created by asus on 2018/1/29.
 */

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder>{
    
    private ArrayList<Dish> list;
    private static final String TAG = "DishAdapter";
    private Context mContext;

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
        mContext = parent.getContext();
        //inflate()的第一个参数是指每个item的view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_view , parent , false );
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       final Dish dish = list.get(position);
       holder.dishName.setText(dish.getName());
       DataBaseUtil.setImage(dish.getImage() , dish);
       if(dish.getImageBitmap() != null)
       holder.dishImage.setImageBitmap(dish.getImageBitmap());
       DataBaseUtil.setScore(dish , holder.star);
       holder.dishImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(mContext , DishActivity.class);
               intent.putExtra("dish" ,dish);
               mContext.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        Log.v(TAG , "列表大小：" +list.size());
        return list.size();
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView dishImage;
        TextView dishName;
        RatingBar star;

        public ViewHolder(View itemView) {
            super(itemView);
            dishImage = (ImageView) itemView.findViewById(R.id.dish_image);
            dishName = (TextView) itemView.findViewById(R.id.name);
            star = (RatingBar) itemView.findViewById(R.id.star);
        }
    }
}
