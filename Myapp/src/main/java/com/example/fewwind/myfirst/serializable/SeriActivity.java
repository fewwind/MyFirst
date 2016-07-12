package com.example.fewwind.myfirst.serializable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.fewwind.myfirst.R;
import com.example.fewwind.myfirst.view.BaseActivity;
import com.example.fewwind.myfirst.view.DragView;

public class SeriActivity extends BaseActivity implements  BaseActivity.IopenDrawerListener  {


    private static final String TAG ="tag" ;
    DragView view;
    CoordinatorLayout cl;
    ImageView btn;
    ImageView image;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seri);
        setIncarTimeDrawerListener(this);

        Intent intent = getIntent();
        Person person = (Person) intent.getSerializableExtra("ser");
        view= (DragView) findViewById(R.id.id_tv_ser);
        btn = (ImageView) findViewById(R.id.id_btn);
        image = (ImageView) findViewById(R.id.id_iv);
        ll = (LinearLayout) findViewById(R.id.id_ser);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setImageBitmap(getViewBitmap(ll));
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setImageBitmap(getViewBitmap(btn));
            }
        });




//        Log.e(TAG, "onCreate: width--"+view.getMeasuredWidth()+"__Height--"+view.getMeasuredHeight() );
//        Log.e(TAG, "onCreate: width--"+view.getWidth()+"__Height--"+view.getHeight() );

        view.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "onCreate: Measuredwidth--" + view.getMeasuredWidth() + "__MeasuredHeight--" + view.getMeasuredHeight());
                Log.e(TAG, "onCreate: width--" + view.getWidth() + "__Height--" + view.getHeight());
            }
        });
        
        

    }

    @Override
    public void openDrawerListener() {
        Log.d("tag", "SeriActivity: ");
    }



    @Override
    protected void onStart() {
        super.onStart();
//        addView(SeriActivity.this, true);
        bindService();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        Log.e(TAG, "onCreate: Measuredwidth--" + view.getMeasuredWidth() + "__MeasuredHeight--" + view.getMeasuredHeight());
//        Log.e(TAG, "onCreate: width--"+view.getWidth()+"__Height--"+view.getHeight() );

    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService();
    }

    private Bitmap getViewBitmap( View view ){
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = null;
        try{
            if( null != view.getDrawingCache( ) ){
                bitmap = Bitmap.createScaledBitmap( view.getDrawingCache( ), 256, 192, false );
            }else{
                Bitmap bitmapTmp =( (BitmapDrawable)( getResources( ).getDrawable( R.drawable.bg_drawer ) ) ).getBitmap( );
            }
        }catch( OutOfMemoryError e ){
            e.printStackTrace( );
        }finally{
            view.setDrawingCacheEnabled( false );
            view.destroyDrawingCache( );
        }

        return bitmap;
    }
}
