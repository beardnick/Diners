package com.example.asus.diners.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.diners.R;

/**
 * Created by asus on 2018/1/27.
 */

//菜品性质的recyclerview的Adapter

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private String[] list;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView attribute;
        public ViewHolder(View itemView) {
            super(itemView);
            this.attribute = (TextView) itemView.findViewById(R.id.attribute);
        }
    }

    public Adapter(String[] list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_layout , parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        holder.attribute.setText(list[position]);
    }


    @Override
    public int getItemCount() {
        return list.length;
    }
}
