package com.example.asus.diners.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.diners.Model.Comment;
import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.R;
import com.example.asus.diners.Utils.DataBaseUtil;
import com.example.asus.diners.View.CommentAdapter;

import java.util.ArrayList;


public class CommentFragment extends Fragment {

    private Dish dish;
    private RecyclerView comments;

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        comments = (RecyclerView) view.findViewById(R.id.comments);
        comments.setLayoutManager(new LinearLayoutManager(view.getContext()));
        CommentAdapter adapter = new CommentAdapter(new ArrayList<Comment>());
        comments.setAdapter(adapter);
        DataBaseUtil.searchComment(dish , adapter);
        return view;
    }

}
