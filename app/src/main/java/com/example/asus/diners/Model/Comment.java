package com.example.asus.diners.Model;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus on 2018/1/26.
 */

public class Comment extends BmobObject {

    //与菜品是多对一的关系
    private Dish dish;

    //内容
    private String content;

    //评分
    private Integer score;

    public Comment(Dish dish , String content) {
        super();
        this.dish = dish;
        this.content = content;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
