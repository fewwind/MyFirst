package com.fewwind.mydesign.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fewwind.mydesign.R;
import com.fewwind.mydesign.adapter.CommonAdapter;
import com.fewwind.mydesign.adapter.ViewHolder;
import com.fewwind.mydesign.test.view.ScrollViewFloat;
import com.fewwind.mydesign.utils.FastBlurUtil;
import com.fewwind.mydesign.utils.StatusBarCompat;
import com.fewwind.mydesign.utils.UtilListView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class ZhiHuActivity extends AppCompatActivity {


    private ImageView mTopView;
    private RelativeLayout mFloatView;

    private ListView mLv;
    private ScrollViewFloat mScroll;
    List<titleBean> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_hu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        StatusBarCompat.compat(this);
        initData();
    }

    private void initData() {

        for (int i = 0; i < 30; i++) {
            if (i%8==0){
                mDatas.add(new titleBean(i+" 我是标题",true));
            } else {
                mDatas.add(new titleBean(i+" 我是内容",false));

            }
        }
        mScroll.setFloatTopView(mTopView, mFloatView);
        mLv.setAdapter(new CommonAdapter<titleBean>(this, mDatas, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder holder, titleBean s) {
                if (s.isTitle){
                    holder.getView(android.R.id.text1).setBackgroundColor(Color.GREEN);
                }
                holder.setText(android.R.id.text1, s.title);
            }
        });

        UtilListView.setListViewHeightBasedOnChildren(mLv);
        mLv.post(new Runnable() {
            @Override
            public void run() {
                mScroll.scrollTo(0, 0);
            }
        });


    }

    private void initView() {
        mTopView = (ImageView) findViewById(R.id.id_test_top);
        mFloatView = (RelativeLayout) findViewById(R.id.id_test_float);
        mLv = (ListView) findViewById(R.id.id_test_lv_zhihu);

        mScroll = (ScrollViewFloat) findViewById(R.id.id_test_sroll);
        Logger.d(mTopView.getMeasuredWidth() + "======" + mTopView.getMeasuredHeight());

        /**
         * \文／iam_wingjay（简书作者）
         原文链接：http://www.jianshu.com/p/7ae7dfe47a70#

         我们可以利用这个function来进行bitmap的缩放。其中前三个参数很明显，其中宽高我们可以选择为原图尺寸的1/10；
         第四个filter是指缩放的效果，filter为true则会得到一个边缘平滑的bitmap
         ，反之，则会得到边缘锯齿、pixelrelated的bitmap。这里我们要对缩放的图片进行虚化，所以无所谓边缘效果，filter=false。
         */
        mTopView.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_portrait);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 35, bitmap.getHeight() / 35, false);
                Bitmap blur = FastBlurUtil.doBlur(scaledBitmap, 8, false);
                mTopView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mTopView.setImageBitmap(blur);
            }
        });

    }


    public static void startZhiHuActivity(Context context) {
        Intent intent = new Intent(context, ZhiHuActivity.class);
        context.startActivity(intent);
    }

    public class titleBean {
        String title;
        boolean isTitle;

        public titleBean(String title, boolean isTitle) {
            this.title = title;
            this.isTitle = isTitle;
        }
    }

}
