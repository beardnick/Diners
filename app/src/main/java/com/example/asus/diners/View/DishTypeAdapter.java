package com.example.asus.diners.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.diners.Model.DishType;
import com.example.asus.diners.R;

import java.util.List;

/**
 * Created by asus on 2018/1/27.
 */

//菜品类型的recyclerview的adapter

public class DishTypeAdapter extends RecyclerView.Adapter<DishTypeAdapter.ViewHolder> {

    private List<DishType> list;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_layout , parent , false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO: 2018/1/27 学习怎样从BmobFile得到图片
        list.get(position).getImage();
        holder.type.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView type;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            this.type = (TextView) itemView.findViewById(R.id.type);
            this.image = (ImageView) itemView.findViewById(R.id.type_image);
        }
    }

    public DishTypeAdapter(List<DishType> list) {
        this.list = list;
    }
}
