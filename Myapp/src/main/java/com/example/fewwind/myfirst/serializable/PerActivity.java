package com.example.fewwind.myfirst.serializable;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.example.fewwind.myfirst.R;
import com.example.fewwind.myfirst.Util.DownUtil;
import com.example.fewwind.myfirst.Util.LocalImageHolderView;
import com.example.fewwind.myfirst.recyclerview.ItemTouchHelperIml;
import com.example.fewwind.myfirst.recyclerview.RecycleLinerAdapter;
import com.example.fewwind.myfirst.recyclerview.RecyclerGridAdapter;
import com.example.fewwind.myfirst.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PerActivity extends BaseActivity implements View.OnClickListener {
    TextView mTv;

    ProgressBar pb;

    int persent;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            handler.post(MyRunnable);
        }
    };


    private Button mLiner;
    private Button mGrid;
    private Button mStagge;

    private RecyclerView recyclerView;

    private List<Integer> mdatas;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mdatas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mdatas.add(i);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mDialog = new AlertDialog.Builder(PerActivity.this);
                mDialog.setTitle("title").setMessage("message").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mDialog.show();

            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.id_recycler);
        mTv = (TextView) findViewById(R.id.id_tv_pre);
        pb = (ProgressBar) findViewById(R.id.pb);
        Intent intent = getIntent();

        Book book = intent.getParcelableExtra("pac");
        mTv.setText(book.toString());

        mLiner = (Button) findViewById(R.id.btn1);
        mGrid = (Button) findViewById(R.id.btn2);
        mStagge = (Button) findViewById(R.id.btn3);
        mLiner.setOnClickListener(this);
        mGrid.setOnClickListener(this);
        mStagge.setOnClickListener(this);
    }


    DownUtil downUtil;

    public void down(View view) {

        downUtil = new DownUtil(Environment.getExternalStorageDirectory() + "/incar.apk", "http://api.in-carmedia.com/app/InCarMedia.apk", 4);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    downUtil.downLoad();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                handler.sendEmptyMessage(0);


            }
        }).start();
    }


    Runnable MyRunnable = new Runnable() {
        @Override
        public void run() {
            double statu = downUtil.getPercent();
            persent = (int) (statu * 100);
            Log.e("tag", "下载大小：：" + persent);
            pb.setProgress(persent);
            if (persent < 100) {

                handler.postDelayed(MyRunnable, 1000);
            } else if (persent >= 100) {
                pb.setProgress(100);
            }

        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn1:
                 ArrayList<Integer> localImages = new ArrayList<Integer>();
                for (int i = 0; i <6 ; i++) {
                    localImages.add(R.drawable.icon_logo);
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PerActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                RecycleLinerAdapter<Integer> recycleViewAdapter = new RecycleLinerAdapter<>(mdatas,PerActivity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.top, null);
                ConvenientBanner convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);

                convenientBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages);
                recycleViewAdapter.addHeaderView(view);
                View view1 = layoutInflater.inflate(R.layout.footer, null);
                recycleViewAdapter.addFooterView(view1);


                ItemTouchHelperIml callback = new ItemTouchHelperIml(){
                    @Override
                    public boolean isLongPressDragEnabled() {
                        return true;
                    }

                    @Override
                    public boolean isItemViewSwipeEnabled() {
                        return true;
                    }
                };
                ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                touchHelper.attachToRecyclerView(recyclerView);
                recyclerView.setAdapter(recycleViewAdapter);
                recyclerView.setLayoutManager(linearLayoutManager);
                break;
            case R.id.btn2:
                ArrayList<Integer> localImage = new ArrayList<Integer>();
                for (int i = 0; i <6 ; i++) {
                    localImage.add(R.drawable.icon_logo);
                }
                final GridLayoutManager     gridLayoutManager = new GridLayoutManager(PerActivity.this, 2);
                final RecyclerGridAdapter<Integer> recycleGridAdapter = new RecyclerGridAdapter<>(mdatas);
                inflater = getLayoutInflater();
                View view2 = inflater.inflate(R.layout.top, null);

                ConvenientBanner convenientBanner2 = (ConvenientBanner) view2.findViewById(R.id.convenientBanner);

                convenientBanner2.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImage);

                recycleGridAdapter.addHeaderView(view2);
                View view3 = inflater.inflate(R.layout.footer, null);
                recycleGridAdapter.addFooterView(view3);


                recycleGridAdapter.setChangeGridLayoutSpance(new RecyclerGridAdapter.ChangeGridLayoutSpance() {
                    @Override
                    public void changeSpnce(final int size, final boolean isAddHead, final boolean isAddFooter) {
                        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

                            @Override
                            public int getSpanSize(int position) {
                                int spenSize = 1;

                                if (isAddHead) {
                                    if (position == 0)
                                        spenSize = gridLayoutManager.getSpanCount();
                                }

                                if (isAddFooter) {
                                    if (position == size)
                                        spenSize = gridLayoutManager.getSpanCount();
                                }


                                return spenSize;
                            }
                        });
                    }
                });




                recyclerView.setAdapter(recycleGridAdapter);
                recyclerView.setLayoutManager(gridLayoutManager);

                break;
            case R.id.btn3:

                break;
        }
    }

}
