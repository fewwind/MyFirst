package com.fewwind.mydesign.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fewwind.mydesign.MyApplication;
import com.fewwind.mydesign.R;
import com.fewwind.mydesign.adapter.RecyclerListAdapter;
import com.fewwind.mydesign.bean.ChannelInfo;
import com.fewwind.mydesign.helper.OnStartDragListener;
import com.fewwind.mydesign.helper.SimpleItemTouchHelperCallback;
import com.fewwind.mydesign.ui.PlayListActivity;
import com.fewwind.mydesign.utils.Constants;
import com.fewwind.mydesign.utils.PreferenceUtil;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fewwind on 2015/10/13.
 */
public class FragmentMain extends Fragment implements OnStartDragListener, RecyclerListAdapter.OnMyClickListener,SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView mRecyclerView;

    private RecyclerListAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;

    private FrameLayout frameLayoutLoad;
    private TextView textViewLoad;
    private TextView mLable;
    private Handler handler;

    SwipeRefreshLayout refreshLayout;
    public static final int MSG_CHANINFO = 1;
    private static final int REFRESH =2 ;
    private LinearLayoutManager mLayoutMag;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what){

                    case MSG_CHANINFO:
                        List<ChannelInfo> channelInfos = new ArrayList<>();
                        if (msg.obj != null && msg.obj instanceof List) {
                            channelInfos = (List<ChannelInfo>) msg.obj;
                        }

                        mAdapter.updateMyChans(channelInfos);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        frameLayoutLoad.setVisibility(View.GONE);
                        break;

                    case REFRESH:

                        Snackbar.make(mRecyclerView, "点击右下角的加号可以添加频道", Snackbar.LENGTH_SHORT).show();
                        refreshLayout.setRefreshing(false);
                        break;

                }



            }
        };

    }

    private static class MyHandler extends Handler{
        private final WeakReference<FragmentMain> mActivity;

        public MyHandler(FragmentMain activity) {

            mActivity = new WeakReference<FragmentMain>(activity);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootFragment = inflater.inflate(R.layout.fragment_main, container, false);
        frameLayoutLoad = (FrameLayout) rootFragment.findViewById(R.id.id_fl_loading_main);
        textViewLoad = (TextView) rootFragment.findViewById(R.id.id_tv_loading);
        mLable = (TextView) rootFragment.findViewById(R.id.id_tv_mylable);


        refreshLayout = (SwipeRefreshLayout)rootFragment.findViewById(R.id.id_refresh_main);
        refreshLayout.setProgressBackgroundColorSchemeColor(getActivity().getResources()
                .getColor(android.R.color.white));

        refreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorAccent));
        refreshLayout.setOnRefreshListener(this);

        setupRecyclerView(rootFragment);
//        Toolbar toolbar = (Toolbar) rootFragment.findViewById(R.id.id_toolbar_main);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


//        drawer = (DrawerLayout) rootFragment.findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

//        final ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
//        ab.setDisplayHomeAsUpEnabled(true);


        loadingData();
        return rootFragment;
    }

    private void loadingData() {
        mRecyclerView.setVisibility(View.GONE);
        textViewLoad.setText("正在拼命加载······");
        frameLayoutLoad.setVisibility(View.VISIBLE);
    }

    public void setupRecyclerView(View upRecyclerView) {
        mRecyclerView = (RecyclerView) upRecyclerView.findViewById(R.id.id_rcv_main);


        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerListAdapter(getActivity(), new ArrayList<ChannelInfo>(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutMag=new LinearLayoutManager(getActivity()));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        mAdapter.setOnMyclickListener(this);


        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mAdapter.getItemCount() - 1 == mLayoutMag.findLastVisibleItemPosition()) {
                    Logger.w("我到最后一个了");
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Logger.w("水平" + dx + "垂直" + dy);
            }
        });
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onResume() {
        super.onResume();

        fillData();

    }

    public void startRefresh(){
        refreshLayout.setRefreshing(true);
    }

    public void fillData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ArrayList<ChannelInfo> datas = MyApplication.helper.getAsSerializable(Constants.CACHEMYCHANNELS);
                handler.sendMessage(handler.obtainMessage(MSG_CHANINFO, datas));
            }
        }).start();
    }


    @Override
    public void onMyClick(View v, int pos) {
        ChannelInfo info = (ChannelInfo) v.getTag();
        Intent intent = new Intent(getActivity(), PlayListActivity.class);
        Log.d("tag", "当前ChannelInfo：" + info.toString());
        intent.putExtra("cId", info.getId());
//        startActivity(intent);
//        ActivityCompat.startActivity(getActivity(), intent,
//                ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

        startActivityForResult(intent,1);


        mAdapter.setPostion(pos);


        //判断是不是新增加的频道，如果是的话就把新的 标签显示出来
        if (PreferenceUtil.getInstance(getActivity()).isNewChan(String.valueOf(info.getId()))) {

            PreferenceUtil.getInstance(getActivity()).cancleNewChan(String.valueOf(info.getId()), false);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(REFRESH,1000);
    }



}
