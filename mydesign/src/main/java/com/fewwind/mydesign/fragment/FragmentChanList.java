package com.fewwind.mydesign.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fewwind.mydesign.MyApplication;
import com.fewwind.mydesign.R;
import com.fewwind.mydesign.adapter.SysChanRecycAdapter;
import com.fewwind.mydesign.bean.CatBean;
import com.fewwind.mydesign.bean.ChannelInfo;
import com.fewwind.mydesign.bean.Event;
import com.fewwind.mydesign.utils.Constants;
import com.fewwind.mydesign.utils.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fewwind on 2015/10/13.
 */
public class FragmentChanList extends BaseFragment implements SysChanRecycAdapter.ClickItemListener {

    private int mType;
    private SysChanRecycAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private Handler hander;
    private FrameLayout frameLayout;
    private TextView mLoadTv;
    private TextView mLable;

    public static FragmentChanList getInstance(int type) {
        FragmentChanList fm = null;
        fm = mFragments.get(type);
        if (fm == null) {
            fm = new FragmentChanList();
        }
        if (fm != null) {
            mFragments.put(type, fm);
        }
        Bundle bundle = new Bundle(1);
        bundle.putInt("type", type);
        fm.setArguments(bundle);
        return fm;
    }

    private static Map<Integer, FragmentChanList> mFragments = new HashMap<>();


//    public static FragmentChanList getInstance(int type) {
//        FragmentChanList fragment = new FragmentChanList();
//        Bundle bundle = new Bundle(1);
//        bundle.putInt("type", type);
//        fragment.setArguments(bundle);
//        return fragment;
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mType = getArguments().getInt("type");
        hander = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                List<ChannelInfo> channelInfos = new ArrayList<>();
                if (msg.obj != null && msg.obj instanceof List) {
                    channelInfos = (List<ChannelInfo>) msg.obj;
                }
                setData2List(channelInfos);
            }
        };

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventResult(Event.ItemListEvent itemListEvent){
//        Logger.d("eventBus"+itemListEvent.mLists.toString());
        setData2List(itemListEvent.mLists);
    }

    private void setData2List(List<ChannelInfo> channelInfos) {

        if (channelInfos.size() >= 0) {
            mAdapter.updateSysChans(channelInfos);
        }
        loadFinish();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootFragment = inflater.inflate(R.layout.fragment_chan_list, container, false);
        setupRecyclerView(rootFragment);
        frameLayout = (FrameLayout) rootFragment.findViewById(R.id.id_fl_loading);
        mLable = (TextView) rootFragment.findViewById(R.id.id_tv_lable_sys);
        mLoadTv = (TextView) rootFragment.findViewById(R.id.id_tv_loading);
        return rootFragment;
    }

    private void setupRecyclerView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.id_rcv_chanlist);
        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new SysChanRecycAdapter(getActivity(), new ArrayList<ChannelInfo>());

        mAdapter.setOnClickItemListener(this);

        mRecyclerView.setAdapter(mAdapter);
    }


    public void loadingUI() {
        mRecyclerView.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
        mLoadTv.setText("正在加载中请稍后······");
    }

    Event.ItemListEvent itemListEvent;
    @Override
    public void onResume() {
        super.onResume();

        if (mType >= 0) {
            fillData(mType);
        }
    }

    private void fillData(final int mType) {
        loadingUI();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<CatBean> catBeanList = MyApplication.helper.getAsSerializable(Constants.CACHECATS);
                String key = catBeanList.get(mType).getCatId();
                List<ChannelInfo> currentCatChans = MyApplication.helper.getAsSerializable(key);
                try {
                    new Thread().sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hander.sendMessage(hander.obtainMessage(mType, currentCatChans));
//                Logger.w("我开始发消息了");
//
//                EventBus.getDefault().post(itemListEvent = new Event.ItemListEvent(currentCatChans));
            }
        }).start();

    }


    public void loadFinish() {
        mRecyclerView.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);

    }

    @Override
    public void clickIcon(View v, ChannelInfo info) {


        //判断是不是新增加的频道，如果是的话就把新的 标签显示出来
        if (PreferenceUtil.getInstance(getActivity()).isMyChan(String.valueOf(info.getId()))) {

            List<ChannelInfo> mychan = MyApplication.helper.getAsSerializable(Constants.CACHEMYCHANNELS);

            PreferenceUtil.getInstance(getActivity()).deleteMyChan(String.valueOf(info.getId()), false);
            /**
             * 不知道为什么明明两个对象一样却remove不掉，只好重写equals方法，如果id相同就默认为相同元素。  remove方法就是比较equals方法，相同就删掉
             *
             */
            boolean l = mychan.remove(info);
            MyApplication.helper.put(Constants.CACHEMYCHANNELS, (Serializable) mychan);
        } else {

            List<ChannelInfo> mychans = MyApplication.helper.getAsSerializable(Constants.CACHEMYCHANNELS);
            PreferenceUtil.getInstance(getActivity()).addMyChan(String.valueOf(info.getId()), true);
            PreferenceUtil.getInstance(getActivity()).addNewChan(String.valueOf(info.getId()), true);
            mychans.add(info);
            MyApplication.helper.put(Constants.CACHEMYCHANNELS, (Serializable) mychans);
        }
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void clickTitle(ChannelInfo info) {
        Log.e("my", info.getDesc().toString());
    }

    @Override
    protected void loadData() {
        Logger.v("当前可见---"+mType);
    }
}
