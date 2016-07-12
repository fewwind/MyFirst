package com.fewwind.mydesign.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fewwind.mydesign.MyApplication;
import com.fewwind.mydesign.R;
import com.fewwind.mydesign.adapter.CusChanRecycAdapter;
import com.fewwind.mydesign.bean.ChannelInfo;
import com.fewwind.mydesign.utils.Constants;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCustom extends BaseFragment {


    private RecyclerView mRecyclerView;

    private CusChanRecycAdapter mAdapter;
    private Handler handler;

    public FragmentCustom() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootFragment =  inflater.inflate(R.layout.fragment_fragment_custom, container, false);

        setupRecyclerView(rootFragment);
        return rootFragment;
    }

    ArrayList<ChannelInfo> datas;
    @Override
    public void onResume() {
        super.onResume();

        new AsyncTask<Void,Void,Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {

                datas= MyApplication.helper.getAsSerializable(Constants.CACHE_CUSCHANNELS);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                mAdapter.updateSysChans(datas);
            }
        }.execute();
    }

    public void setupRecyclerView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.id_rcv_cus);
        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CusChanRecycAdapter(getActivity(), new ArrayList<ChannelInfo>());


        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {

    }
}
