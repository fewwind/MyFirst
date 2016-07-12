package incarmedia.com.viewhelperlistgridscroll.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.orhanobut.logger.Logger;

import incarmedia.com.viewhelperlistgridscroll.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        testStatic();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ThirdActivity.startActivity(SecondActivity.this);
                SecondActivity.this.finish();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public static void startActivity(Context context ){
        Intent intent = new Intent(context,SecondActivity.class);
        context.startActivity(intent);
    }

    public static void testStatic(){Logger.init("tag");
        Log.e("tag","我是静态方法第二个累");
        Logger.i("我是静态方法第二个累");
    }

     static {Logger.init("tag");
        Logger.i("我是静态代码快");
    }
}
