package com.example.asus.diners.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.asus.diners.Model.Place;
import com.example.asus.diners.R;

import java.util.ArrayList;

/**
 * Created by asus on 2018/3/6.
 */

public class ChoosePlaceAdapter extends RecyclerView.Adapter<ChoosePlaceAdapter.ViewHolder> {

    private ArrayList<Place> places;
    private ArrayList<Place> choosedPlace;

    public ChoosePlaceAdapter(ArrayList<Place> places) {
        this.places = places;
        choosedPlace = new ArrayList<>();
    }

    public ArrayList<Place> getChoosedPlace() {
        return choosedPlace;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_place_view , parent , false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.place.setText(places.get(position).getName());
        holder.choose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    choosedPlace.add(places.get(position));
                }else{
                    choosedPlace.remove(places.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView place;
        public CheckBox choose;

        public ViewHolder(View itemView) {
            super(itemView);
           place = (TextView) itemView.findViewById(R.id.add_place_name);
            choose = (CheckBox) itemView.findViewById(R.id.comment_it);
        }
    }
}
