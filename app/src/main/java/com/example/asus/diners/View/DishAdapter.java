package com.example.asus.diners.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.R;

import java.io.File;
import java.util.ArrayList;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

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
       Dish dish = list.get(position);
       holder.dishName.setText(dish.getName());
        // TODO: 2018/1/31 为什么只能用final而且还是数组
        final Bitmap[] image = new Bitmap[1];
       dish.getPic().download(new DownloadFileListener() {
           @Override
           public void done(String s, BmobException e) {
               if(e == null){
                   Log.v(TAG , "下载成功，保存路径：" + s);
                   image[0] = BitmapFactory.decodeFile(s);
                   if(image[0] == null){
                       Log.v(TAG , "image is null");
                       holder.dishImage.setImageResource(R.mipmap.dish);
                   }else{
                       holder.dishImage.setImageBitmap(image[0]);
                   }
               }else {
                   Log.v(TAG , "下载失败" + e.getMessage());
                   image[0] = null;
               }
           }
           @Override
           public void onProgress(Integer integer, long l) {
           }
       });
//       holder.star = new LinearLayout(mContext);
//       ImageView star = new ImageView(mContext);
//       star.setImageResource(R.drawable.ic_star_black_24dp);
//       star.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
//       holder.star.addView(star);
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
            dishImage = (ImageView) itemView.findViewById(R.id.dish_image);
            dishName = (TextView) itemView.findViewById(R.id.name);
            star = (LinearLayout) itemView.findViewById(R.id.star);
        }
    }
}
