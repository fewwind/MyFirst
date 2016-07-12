package com.fewwind.mydesign.fragment;


import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by fewwind on 2016/3/17.
 */
public abstract class BaseFragment extends Fragment {

    protected boolean isVisible;
    protected boolean isViewInit;
    protected boolean isDataInit;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInit = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisible = isVisibleToUser;
        if (getUserVisibleHint()){
            loadData();
        }
        preperFetchData();
    }

    protected boolean  preperFetchData(){

        return preperFrtchData(false);
    }

    private boolean preperFrtchData(boolean forceUpdata) {
        if (isVisible&&isViewInit&&(!isDataInit||forceUpdata)){
//            loadData();
            isDataInit = true;
            return true;
        }

        return false;
    }


    protected abstract void loadData();
}
