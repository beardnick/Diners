package com.example.asus.diners.Model;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus on 2018/1/26.
 */

public class Comment extends BmobObject {

    //与菜品是多对一的关系
    private Dish dish;

    private DishPlace dishPlace;

    //内容
    private String content;

    //评分,为0 1 2 3 4 5
    private Float score;

    private Place place;

    public Comment(Dish dish , String content) {
        super();
        this.dish = dish;
        this.content = content;
    }

    public Dish getDish() {
        if(dishPlace != null && dishPlace.getDish() != null){
            dish = dishPlace.getDish();
        }
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


    public Float getScore() {
        if(score == null || score < 0 || score > 5)return 0f;
        return score;
    }

    public void setScore(Float score) {
        if(score >= 0 && score <= 5)
        this.score = score;
    }

    public DishPlace getDishPlace() {
        return dishPlace;
    }

    public void setDishPlace(DishPlace dishPlace) {
    if(dishPlace != null && dishPlace.getDish() != null){
        dish = dishPlace.getDish();
    if(dishPlace.getPlace() != null){
        place = dishPlace.getPlace();
    }
    }
        this.dishPlace = dishPlace;
    }

    public void setDishPlace(Place place){
        this.dishPlace = new DishPlace(dish , place , null);
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
