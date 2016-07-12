package com.fewwind.mydesign.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fewwind.mydesign.MyApplication;
import com.fewwind.mydesign.R;
import com.fewwind.mydesign.bean.CatBean;
import com.fewwind.mydesign.utils.Constants;

import java.util.List;

/**
 * Created by fewwind on 2015/10/13.
 */
public class FragmentSysChan extends BaseFragment{

    private String mTitles = "错误";
    private int mType ;
    private FragmentListAdapter mFragAdapter;
    TabLayout mTablayout;
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootFragment  =inflater.inflate(R.layout.fragment_sys_chan, container, false);
        mTablayout  = (TabLayout) rootFragment.findViewById(R.id.id_fg_tab);
        mViewPager = (ViewPager) rootFragment.findViewById(R.id.id_fg_vp);
        List<CatBean> temp =MyApplication.helper.getAsSerializable(Constants.CACHECATS) ;
                mFragAdapter = new FragmentListAdapter(getChildFragmentManager(),temp);

        if (mViewPager != null) {
            mViewPager.setAdapter(mFragAdapter);
//            mViewPager.setOffscreenPageLimit(0);
        }

        mTablayout.setupWithViewPager(mViewPager);
        return rootFragment;
    }

    @Override
    protected void loadData() {

    }

//    public void updataList(String [] data) {
//        mFragAdapter.updataTitle(data);
//    }
}
