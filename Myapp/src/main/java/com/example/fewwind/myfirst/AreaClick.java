package com.example.fewwind.myfirst;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by fewwind on 2016/1/13.
 */
public class AreaClick extends AppCompatActivity implements View.OnClickListener {

    private Toast mToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_click);

        findViewById( R.id.menu_1).setOnClickListener( this);
        findViewById( R.id.menu_2).setOnClickListener( this);
        findViewById( R.id.menu_3).setOnClickListener( this);
        findViewById( R.id.menu_4).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(null != mToast) {
            mToast.cancel();
        }
        Log.v("tag", "onClick " + v.toString());
        switch(v.getId()) {
            case R.id.menu_1:
                mToast = Toast.makeText(this, "red", Toast.LENGTH_SHORT);
                break;
            case R.id.menu_2:
                mToast = Toast.makeText( this, "yellow", Toast.LENGTH_SHORT);
                break;
            case R.id.menu_3:
                mToast = Toast.makeText( this, "green", Toast.LENGTH_SHORT);
                break;
            case R.id.menu_4:
                mToast = Toast.makeText( this, "blue", Toast.LENGTH_SHORT);
                break;
        }
        mToast.show();
    }
}
