package com.example.asus.diners.Model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by asus on 2018/1/26.
 */

public class DishPlace extends BmobObject {

    private Dish dish;

    private Place place;

    //该菜品在这个店子中的价格
    private Float cost;

    private BmobFile image;

    public DishPlace(Dish dish, Place place, Float cost) {
        super();
        this.dish = dish;
        this.place = place;
        this.cost = cost;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }
}
