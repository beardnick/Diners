package com.example.asus.diners.View;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.asus.diners.Model.Comment;
import com.example.asus.diners.R;

import java.util.ArrayList;

/**
 * Created by asus on 2018/2/27.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    ArrayList<Comment> list;

    private static final String TAG = "CommentAdapter";

    public ArrayList<Comment> getList() {
        return list;
    }

    public void setList(ArrayList<Comment> list) {
        this.list = list;
    }

    public CommentAdapter(ArrayList<Comment> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_view , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = list.get(position);
        Log.d(TAG, "onBindViewHolder: 星级" +comment.getScore() );
        holder.score.setRating(comment.getScore());
        holder.content.setText(comment.getContent());
        holder.time.setText(comment.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{

        public TextView time;
        public TextView content;
        public RatingBar score;
        public ViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            content = (TextView) itemView.findViewById(R.id.content);
            score = (RatingBar) itemView.findViewById(R.id.score);
        }
    }
}
