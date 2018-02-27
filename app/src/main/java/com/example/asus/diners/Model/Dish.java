package com.example.asus.diners.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by asus on 2018/1/26.
 */

public class Dish extends BmobObject implements Parcelable{
    //名称
    private String name;
    //性质
    private String type;
    //图片
    //不要乱改名字，这些名字是和云端的数据库绑定的
    private BmobFile pic;
    private String imagePath;
    //口味
    private String taste;
    //卡路里
    private String calorie;
    //原料
    private String material;
    //菜系
    private String system;


    public  Dish(String name){
        super();
        this.name = name;
    }

    //厉害厉害，Android Studio自动实现Parcel接口
    protected Dish(Parcel in) {
        //读取的变量和对应的变量绑定时是通过赋值的顺序来确定的
        name = in.readString();
        type = in.readString();
        imagePath = in.readString();
        taste = in.readString();
        calorie = in.readString();
        material = in.readString();
        system = in.readString();
        setObjectId(in.readString());
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BmobFile getPic() {
        return pic;
    }

    public void setPic(BmobFile pic) {
        this.pic = pic;
    }


    public String getImagePath() {
        if(imagePath == null)return null;
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(imagePath);
        dest.writeString(taste);
        dest.writeString(calorie);
        dest.writeString(material);
        dest.writeString(system);
        dest.writeString(getObjectId());
    }
}
