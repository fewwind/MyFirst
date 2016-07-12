package com.fewwind.mydesign.fragment;

import android.app.Fragment;
import android.app.FragmentManager;

import com.fewwind.mydesign.adapter.FragmentPagerAdapter;
import com.fewwind.mydesign.bean.CatBean;

import java.util.List;

/**
 * Created by fewwind on 2015/10/13.
 */
public class FragmentListAdapter extends FragmentPagerAdapter {


    private List<CatBean> mTitles;
    public FragmentListAdapter(FragmentManager fm,List<CatBean> titles ) {
        super(fm);
        this.mTitles = titles;
    }



    @Override
    public Fragment getItem(int position) {
        return FragmentChanList.getInstance(position);
    }

    @Override
    public int getCount() {
        return mTitles==null?0:mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).getCatName();
    }
}
