package com.example.asus.diners.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.asus.diners.Model.Place;
import com.example.asus.diners.R;

import java.util.ArrayList;

/**
 * Created by asus on 2018/1/29.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder>{


    public PlaceAdapter(ArrayList<Place> list) {
        this.list = list;
    }

    public ArrayList<Place> getList() {
        return list;
    }

    public void setList(ArrayList<Place> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_view , parent , false);
       return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Place place = list.get(position);
        holder.placeName.setText(place.getName());
        holder.cost.setText(place.getCost());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private ArrayList<Place> list;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView cost;
        public TextView placeName;
        public CheckBox buy;

        public ViewHolder(View itemView) {
            super(itemView);
            cost = (TextView) itemView.findViewById(R.id.cost);
            placeName = (TextView) itemView.findViewById(R.id.place_name);
            buy = (CheckBox) itemView.findViewById(R.id.buy);
        }
    }

}
