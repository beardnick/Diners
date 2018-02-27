package com.example.asus.diners.Model;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus on 2018/1/26.
 */

public class Place extends BmobObject {

    private String name;

    private String cost;

    public Place(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
