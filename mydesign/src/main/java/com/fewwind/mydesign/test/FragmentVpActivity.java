package com.fewwind.mydesign.test;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.fewwind.mydesign.R;
import com.fewwind.mydesign.adapter.FragmentPagerAdapter;
import com.fewwind.mydesign.test.fragment.BlankFragment;
import com.fewwind.mydesign.test.view.ViewPagerIndicatorHorizontal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentVpActivity extends AppCompatActivity {


    private List<String> mTitles = Arrays.asList("短信","联系人","生活黄页","电话","快递");
    private ViewPager mVp;
    private List<Fragment> mFragList = new ArrayList<>();
    private ViewPagerIndicatorHorizontal mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_vp);

        initViews();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    }

    private void initViews() {
        mVp = (ViewPager) findViewById(R.id.id_test_vp);
        mIndicator = (ViewPagerIndicatorHorizontal) findViewById(R.id.id_indicator);

        mIndicator.setTabItemTitle(mTitles);
        mIndicator.setViewPager(mVp);

        for (int i = 0; i < mTitles.size(); i++) {
            BlankFragment frag = BlankFragment.newInstance(mTitles.get(i),""+i);
            mFragList.add(frag);
        }
        MyPagerAdapter adapter = new MyPagerAdapter(getFragmentManager(),mFragList);
        mVp.setAdapter(adapter);
//        mVp.setOffscreenPageLimit(0);




    }

    public static void startTestActivity(Context context){
        Intent intent = new Intent(context,FragmentVpActivity.class);
        context.startActivity(intent);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        List<android.app.Fragment> mList;

        public MyPagerAdapter(FragmentManager fm,List<android.app.Fragment> datas) {
            super(fm);
            this.mList = datas;
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
