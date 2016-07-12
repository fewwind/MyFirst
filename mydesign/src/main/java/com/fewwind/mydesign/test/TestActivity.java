package com.fewwind.mydesign.test;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.fewwind.mydesign.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.orhanobut.logger.Logger;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionMenu mFamenu;
    FloatingActionButton mFab1;
    FloatingActionButton mFab2;
    FloatingActionButton mFab3;
    FloatingActionButton mFab4;
    FloatingActionButton mFab5;

    RecyclerView mRvType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        initEvents();

    }


    private void initViews() {
        mFamenu = (FloatingActionMenu) findViewById(R.id.main_fab_menu);

        mFab1 = (FloatingActionButton) findViewById(R.id.main_fab_1);
        mFab2 = (FloatingActionButton) findViewById(R.id.main_fab_2);
        mFab3 = (FloatingActionButton) findViewById(R.id.main_fab_3);
        mFab4 = (FloatingActionButton) findViewById(R.id.main_fab_4);
        mFab5 = (FloatingActionButton) findViewById(R.id.main_fab_5);

        mRvType = (RecyclerView) findViewById(R.id.id_test_rv_type);
        mFab1.setOnClickListener(this);
        mFab2.setOnClickListener(this);
        mFab3.setOnClickListener(this);
        mFab4.setOnClickListener(this);
        mFab5.setOnClickListener(this);
    }


    private void initEvents() {
        LinearLayoutManager mangerLin = new LinearLayoutManager(this);
        mangerLin.setOrientation(LinearLayoutManager.VERTICAL);


//        mRvType.setLayoutManager(mangerLin);
//        mRvType .setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRvType.setLayoutManager(new GridLayoutManager(this, 3));
        RvTypeAdapter mAdapter = new RvTypeAdapter();

        TextView tv1 = new TextView(this);
        tv1.setText("我是头部1");
        TextView tv2 = new TextView(this);
        tv2.setText("我是头部2");
        mAdapter.addHeaderView(tv1);
        mAdapter.addHeaderView(tv2);
        mRvType.setAdapter(mAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_fab_1:
                FragmentVpActivity.startTestActivity(v.getContext());
                break;
            case R.id.main_fab_2:
                ZhiHu2Activity.startZhiHu2Activity(v.getContext());
                break;
            case R.id.main_fab_3:
                ZhiHuActivity.startZhiHuActivity(v.getContext());
                break;
            case R.id.main_fab_4:
                AgeSelectActivity.startAgeSelectActivity(v.getContext());

                break;
            case R.id.main_fab_5:


                Resouces resouces = new Resouces();
                new Thread(new Produce(resouces)).start();
                new Thread(new Custom(resouces)).start();
                new Thread(new Produce(resouces)).start();
                new Thread(new Custom(resouces)).start();
                new Thread(new Produce(resouces)).start();
                new Thread(new Custom(resouces)).start();

                break;
        }

        mFamenu.toggle(false);
    }


    public static void startTestActivity(Context context) {
        Intent intent = new Intent(context, TestActivity.class);
        context.startActivity(intent);

//        Intent intent =  new Intent(Settings.ACTION_WIFI_SETTINGS);
//        context.startActivity(intent);


    }


    public void testAsyncTask() {
        for (int i = 0; i < 10; i++) {

            AsyncTask<String, Integer, String> task = new AsyncTask<String, Integer, String>() {
                @Override
                protected String doInBackground(String... params) {
                    Logger.d(Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return params[0] + "execute";
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    Logger.i("结果----" + s);
                }
            };

            task.execute("Task");
        }
    }


    public class Custom implements Runnable {

        private Resouces mRes;

        public Custom(Resouces res) {
            this.mRes = res;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mRes.decrease();
            }

        }
    }

    public class Produce implements Runnable {

        private Resouces mRes;

        public Produce(Resouces res) {
            this.mRes = res;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep((long) (Math.random() * 5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mRes.increase();
            }

        }
    }

    public class Resouces {
        public int size = 20;
        int num = 0;

        public synchronized void increase() {
            while (num >= size) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            num++;
            Logger.d("生产了1个" + "---总共---" + num);
            notifyAll();
        }

        public synchronized void decrease() {
            while (num <= 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            num--;
            Logger.v("消费了1个" + "---总共---" + num);
            notifyAll();
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("On Destory");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d("On onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("On onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.d("On onRestart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.d("On savedInstanceState");
    }
}
