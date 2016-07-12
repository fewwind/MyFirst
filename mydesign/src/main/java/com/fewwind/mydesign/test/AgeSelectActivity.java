package com.fewwind.mydesign.test;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.fewwind.mydesign.R;
import com.fewwind.mydesign.test.adapter.AgeSelectAdapter;
import com.orhanobut.logger.Logger;

public class AgeSelectActivity extends AppCompatActivity {


    RecyclerView mRv;
    AgeSelectAdapter mAdapter;

    TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_select);

        mTv = (TextView) findViewById(R.id.id_test_age_tv);
        initRecycleView();

    }


    public void initRecycleView() {
        mRv = (RecyclerView) findViewById(R.id.id_test_rv_age);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRv.setLayoutManager(manager);
        mRv.setAdapter(mAdapter = new AgeSelectAdapter(80, 0, this));


        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Logger.d("dx--"+dx+"--dy--"+dy+"--scrollOffset--"+ mRv.computeHorizontalScrollOffset()+"--pos---"+getScrollPostion());
                mTv.setText(getMiddlePostion() + "");
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mAdapter.setmHighLight(getMiddlePostion());
                    mRv.scrollToPosition(getScrollPostion());

                }
            }
        });


        mAdapter.setmHighLight(getMiddlePostion());
    }


    public int getMiddlePostion() {
        return getScrollPostion() + (AgeSelectAdapter.ITEM_NUM / 2);
    }

    public int getScrollPostion() {

        return mRv.computeHorizontalScrollOffset() / mAdapter.getItemWidth();
    }


    public static void startAgeSelectActivity(Context context) {
        Intent intent = new Intent(context, AgeSelectActivity.class);
        context.startActivity(intent);
    }
}
