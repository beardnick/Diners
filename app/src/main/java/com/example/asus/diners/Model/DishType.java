package com.example.asus.diners.Model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by asus on 2018/1/26.
 */

public class DishType extends BmobObject {


    private String name;

    private int imageid;

    BmobRelation belong;

    private BmobFile image;

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public DishType(String name,int imageid) {
        super();
        this.name = name;
        this.imageid=imageid;
    }

    public String getName() {

        return name;
    }

    public int getImageid(){
        return imageid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobRelation getBelong() {
        return belong;
    }

    public void setBelong(BmobRelation belong) {
        this.belong = belong;
    }
}
