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

public class DishTypeAdapter extends RecyclerView.Adapter<DishTypeAdapter.ViewHolder> {

    private List<DishType> list;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

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
