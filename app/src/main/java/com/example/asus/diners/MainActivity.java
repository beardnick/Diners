package com.example.asus.diners;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.asus.diners.Model.Dish;
import com.example.asus.diners.Model.DishPlace;
import com.example.asus.diners.Model.DishType;
import com.example.asus.diners.Model.Person;
import com.example.asus.diners.Model.Place;
import com.example.asus.diners.View.DishTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {

    private List<DishType>dishTypeList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initDishType();
        RecyclerView recyclerView =(RecyclerView)findViewById(R.id.dish_type);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DishTypeAdapter adapter = new DishTypeAdapter(dishTypeList);
        recyclerView.setAdapter(adapter);

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config =new BmobConfig.Builder(this)
        //设置appkey
        .setApplicationId("087b71a382e85618bd2622d8dfad9a58")
        //请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(30)
        //文件分片上传时每片的大小（单位字节），默认512*1024
        .setUploadBlockSize(1024*1024)
        //文件的过期时间(单位为秒)：默认1800s
        .setFileExpiration(2500)
        .build();
        Bmob.initialize(config);
    }

    //测试是否能成功导入数据
    public void test(){
        Person p2 = new Person();
        p2.setName("lucky");
        p2.setAddress("北京海淀");
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    Toast.makeText(MainActivity.this , "添加数据成功，返回objectId为：" + objectId , Toast.LENGTH_SHORT).show();
                }else{
                   Toast.makeText(MainActivity.this , "创建数据失败：" + e.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String id = "";

    SaveListener<String> general = new SaveListener<String>() {
        @Override
        public void done(String objectId, BmobException e) {
            if(e==null){
                Log.v("MAINACTIVITY" , "添加数据成功，返回objectId为：" + objectId );
                id = objectId;
                //Toast.makeText(MainActivity.this , "添加数据成功，返回objectId为：" + objectId , Toast.LENGTH_SHORT).show();
            }else{
                Log.v("MAINACTIVITY" , "创建数据失败：" + e.getMessage() );
                // Toast.makeText(MainActivity.this , "创建数据失败：" + e.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }
    };



    private void initDishType(){
        for(int i=0;i<7;i++){
            DishType nutritious_breakfast =new DishType("营养早餐",R.drawable.breakfast);
            dishTypeList.add(nutritious_breakfast);
            DishType great_lunch =new DishType("丰盛午餐",R.drawable.lunch);
            dishTypeList.add(great_lunch);
            DishType warm_dinner =new DishType("温馨晚餐",R.drawable.dinner);
            dishTypeList.add(warm_dinner);
        }
    }
    //测试Java bean是否正确
    private void newTest(){
        Dish blackRice = new Dish("黑米粥");
        blackRice.setType("粥汤");
        blackRice.save(general);
        blackRice.setObjectId(id);
        Place peach = new Place("桃园");
        Place hui = new Place("荟园");
        Place bo = new Place("博园");
        peach.save(general);
        peach.setObjectId(id);
        hui.save(general);
        hui.setObjectId(id);
        bo.save(general);
        bo.setObjectId(id);

        DishPlace dp1 = new DishPlace(blackRice , peach , new Float(2));
        DishPlace dp2 = new DishPlace(blackRice , hui , new Float(1.5));
        DishPlace dp3 = new DishPlace(blackRice , bo , new Float(2));

        //要再次查询出来才是完整的对象
        dp1.save(general);
        dp2.save(general);
        dp3.save(general);
    }


}
