package com.example.asus.diners.Model;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus on 2018/1/26.
 */

public class Place extends BmobObject {

    private String name;

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
}
