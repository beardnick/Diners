package com.example.asus.diners.View;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.asus.diners.Model.Comment;
import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.Model.DishPlace;
import com.example.asus.diners.Model.Place;
import com.example.asus.diners.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by asus on 2018/1/29.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder>{


    public PlaceAdapter(ArrayList<DishPlace> list) {
        this.list = list;
    }

    private static final String TAG = "PlaceAdapter";

    public ArrayList<DishPlace> getList() {
        return list;
    }

    public void setList(ArrayList<DishPlace> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_view , parent , false);
       return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceAdapter.ViewHolder holder, int position) {
        DishPlace dishPlace = list.get(position);
        holder.cost.setText(String.valueOf(dishPlace.getCost()));
        holder.placeName.setText(dishPlace.getPlace().getName());
        setPlaceScore(dishPlace.getPlace() , dishPlace.getDish() , holder.score);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    private ArrayList<DishPlace> list;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView cost;
        public TextView placeName;
        public CheckBox buy;
        public TextView score;

        public ViewHolder(View itemView) {
            super(itemView);
            cost = (TextView) itemView.findViewById(R.id.cost);
            placeName = (TextView) itemView.findViewById(R.id.place_name);
            buy = (CheckBox) itemView.findViewById(R.id.buy);
            score = (TextView) itemView.findViewById(R.id.place_score);
        }
    }

    private void setPlaceScore(Place place, Dish dish ,  final TextView score){
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("place",new BmobPointer(place) );
        query.addWhereEqualTo("dish" , new BmobPointer(dish));
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if(e == null){
                    if(list.size() != 0){
                        Float sum = 0f;
                        for(Comment x : list){
                            sum += x.getScore();
                        }
                        BigDecimal bd = new BigDecimal(sum / list.size());
                        bd.setScale(2);
                        score.setText(String.valueOf(bd.floatValue()));
                    }
                    Log.v(TAG , "获取分数成功");
                }else {
                    Log.i(TAG, "获取分数失败" + e.getMessage());
                }
            }
        });
    }

}
