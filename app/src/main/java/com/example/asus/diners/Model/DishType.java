package com.example.asus.diners.Model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by asus on 2018/1/26.
 */

public class DishType extends BmobObject {

    //类型指代近期新品，健身轻餐等

    private Dish dish;

    private Type type;

    public DishType(Dish dish, Type type) {
        super();
        this.dish = dish;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
