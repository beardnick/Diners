package com.example.asus.diners.View;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.diners.MainActivity;
import com.example.asus.diners.R;

/**
 * Created by asus on 2018/1/27.
 */

//菜品性质的recyclerview的Adapter

public class DishAttributeAdapter extends RecyclerView.Adapter<DishAttributeAdapter.ViewHolder>{

    private String[] list;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView attribute;
        public ViewHolder(View itemView) {
            super(itemView);
            this.attribute = (TextView) itemView.findViewById(R.id.dish_attribute_item);
        }
    }

    public DishAttributeAdapter(String[] list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_attribute_view , parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(DishAttributeAdapter.ViewHolder holder, final int position) {
        holder.attribute.setText(list[position]);
        holder.attribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.ATTRIBUTE_ACTION);
                intent.putExtra("attribute" , list[position]);
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.length;
    }
}
