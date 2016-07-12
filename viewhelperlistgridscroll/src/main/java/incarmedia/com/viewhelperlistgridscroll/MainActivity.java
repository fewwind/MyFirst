package incarmedia.com.viewhelperlistgridscroll;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

import incarmedia.com.viewhelperlistgridscroll.Utile.Util;
import incarmedia.com.viewhelperlistgridscroll.activity.SecondActivity;
import incarmedia.com.viewhelperlistgridscroll.adapter.CommonAdapter;
import incarmedia.com.viewhelperlistgridscroll.adapter.NetworkImageHolderView;
import incarmedia.com.viewhelperlistgridscroll.adapter.ViewHolder;
import incarmedia.com.viewhelperlistgridscroll.view.MyGridView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    ListView mLv;
    CommonAdapter mAdapter;
    List<String> mDatas = new ArrayList<>();
    List<String> mDatasSquare = new ArrayList<>();
    MyGridView mGv;
    ScrollView sv;
    ConvenientBanner mBanner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();

        initDatas();




        mLv.setAdapter(mAdapter = new CommonAdapter<String>(this, mDatas, R.layout.item_card) {
            @Override
            public void convert(ViewHolder holder, String o) {
                holder.setText(R.id.id_tv_item, o);
            }
        });

        mGv.setAdapter(mAdapter);
        Util.setListViewHeightBasedOnChildren(mLv);


    }

    private void initDatas() {

        mDatasSquare.add("http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg");
        mDatasSquare.add("http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg");
        mDatasSquare.add("http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg");
        mDatasSquare.add("http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg");



        mDatas.add("http://img.my.csdn.net/uploads/201508/05/1438760758_3497.jpg");
        mDatas.add("http://img.my.csdn.net/uploads/201508/05/1438760758_6667.jpg");
        mDatas.add("http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg");
        mDatas.add("http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg");
        mDatas.add("http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg");
        mDatas.add("http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg");
        mDatas.add("http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg");
        mDatas.add("http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg");
        mDatas.add("http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg");
        mDatas.add("http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg");
        mDatas.add("http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg");
        mDatas.add("http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg");
        mDatas.add("http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg");
        mDatas.add("http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg");

        mBanner1.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, mDatas).setPageIndicator(new int[]{R.drawable.point_nomal, R.drawable.point_focured})
        //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
    }

    private void initViews() {


        sv = (ScrollView) findViewById(R.id.id_scrool);
        mBanner1 = (ConvenientBanner) findViewById(R.id.id_banner_first);
        mGv = (MyGridView) findViewById(R.id.id_gv_scrool);
        mLv = (ListView) findViewById(R.id.id_lv_scroll);

        mLv.post(new Runnable() {
            @Override
            public void run() {
                sv.scrollTo(0, 0);
            }
        });
//        mLv.addHeaderView(LayoutInflater.from(this).inflate(),null);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                SecondActivity.startActivity(MainActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mBanner1.startTurning(20000);
    }


    @Override
    protected void onStop() {
        super.onStop();

        mBanner1.stopTurning();
    }
}


    /*    mBtn = (Button) findViewById(R.id.id_btn);
        mRg = (RadioGroup) findViewById(R.id.id_kala_rg);
        mLeft = (RadioButton) findViewById(R.id.id_kala_rb_left);
        mRight = (RadioButton) findViewById(R.id.id_kala_rb_right);

        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.id_kala_rb_left) {

                    DragActivity.startActivity(MainActivity.this);
//                    mRg.check(mLeft.getId());
//                    mLeft.setChecked(true);
//                    mRight.setChecked(false);
                    finish();
                    ObjectAnimator anim = ObjectAnimator.ofFloat(mLv,"alpha",1f,0f,1f);
                    anim.setDuration(3000);
                    anim.start();

                } else if (checkedId == mRight.getId()) {
//                    mLeft.setChecked(false);
//                    mRight.setChecked(true);
//                    mRg.check(mRight.getId());
                    float x = mBtn.getTranslationX();
                    ObjectAnimator anim0 = ObjectAnimator.ofFloat(mBtn, "rotation", 0f, 360f);

//                    float x = mBtn.getX();
                    ObjectAnimator anim1 = ObjectAnimator.ofFloat(mBtn, "translationX",  -500f,x);
                    ObjectAnimator anim2 = ObjectAnimator.ofFloat(mBtn, "alpha", 1f, 0.5f);
                    AnimatorSet animationSet = new AnimatorSet();
                    animationSet.play(anim0).with(anim2).before(anim1);
                    animationSet.setDuration(5000);
                    animationSet.start();
                    Log.e(TAG, "onCheckedChanged: + right"+x );

//                    SecondActivity.startActivity(MainActivity.this);
                }

                Log.e(TAG, "onCheckedChanged: --------------+group");
            }
        });*/