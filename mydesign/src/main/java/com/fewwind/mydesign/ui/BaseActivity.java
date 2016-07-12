package com.fewwind.mydesign.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fewwind.mydesign.utils.ActivityCollector;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        ActivityCollector.addActivity(this);
        initViews();
        initDatas();
        initEvents();
    }

   public  abstract void initViews();
    public  abstract void initDatas();
    public  abstract void initEvents();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ActivityCollector.removeActivity(this);
    }


    public void openActivity(Context context,Class<?> clazz){

    }
}
