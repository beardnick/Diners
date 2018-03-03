package com.example.asus.diners.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.asus.diners.Model.Comment;
import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.R;
import com.example.asus.diners.Utils.DataBaseUtil;
import com.example.asus.diners.View.CommentAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class CommentFragment extends Fragment {

    private Dish dish;
    private RecyclerView comments;
    private static final String TAG = "CommentFragment";
    private CommentAdapter adapter;
    private RelativeLayout noComment;
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
        noComment = (RelativeLayout) view.findViewById(R.id.no_comment_layout);
        comments.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new CommentAdapter(new ArrayList<Comment>());
        comments.setAdapter(adapter);
        searchComment(dish);
        return view;
    }

    private void searchComment(final Dish dish){
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("dish" , new BmobPointer(dish));
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                adapter.getList().clear();
                Log.i(TAG, "searchComment: objectId: " + dish.getObjectId());
                if(list.size() > 0)
                    Log.i(TAG, "searchComment: " +
                            list.get(0).getContent() + list.get(0).getScore());
                if(e == null){
                    adapter.getList().addAll(list);
                    Log.i(TAG, "searchComment: 查询成功" +list.size() );
                }else {
                    Log.i(TAG, "searchComment: 查询失败" + e.getMessage());
                }
                notFoundAction();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void notFoundAction(){
        if(adapter.getList().size() == 0){
            noComment.setVisibility(View.VISIBLE);
        }else {
            noComment.setVisibility(View.INVISIBLE);
        }
    }

    public CommentAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CommentAdapter adapter) {
        this.adapter = adapter;
    }
}
