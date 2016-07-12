package com.fewwind.mydesign.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.fewwind.mydesign.MyApplication;
import com.fewwind.mydesign.R;
import com.fewwind.mydesign.adapter.PlayListRecycAdapter;
import com.fewwind.mydesign.bean.PlayListInfo;
import com.fewwind.mydesign.utils.Constants;
import com.fewwind.mydesign.utils.FastBlurUtil;
import com.fewwind.mydesign.utils.OkHttpClientManager;
import com.fewwind.mydesign.utils.PreferenceUtil;
import com.fewwind.mydesign.utils.StatusBarCompat;
import com.fewwind.mydesign.view.DividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.mobiwise.library.InteractivePlayerView;

public class PlayListActivity extends BaseActivity implements PlayListRecycAdapter.onCilickMyListener {


    static InteractivePlayerView ipv;
    FloatingActionButton fab;
    RecyclerView mLv;

    CollapsingToolbarLayout mCollToobar;

    Handler mHandler;

    //handler 的消息，1是加载列表，2是刷新列表
    public static final int MSG_PLAYLIST = 1;
    private static final int REFRESH_COMPLETE = 2;
    public static String PLAY_INFO = "play_info";

    List<PlayListInfo> mPlayDatas = new ArrayList<>();
    int mChanId;
    String mPlayListCount;

    PlayListRecycAdapter mAdapter;

    RelativeLayout mPlayBg;

    Intent sendIntent;
    Intent controlIntent;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_play_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar_play);
        setSupportActionBar(toolbar);
        StatusBarCompat.compat(this);
        ipv = (InteractivePlayerView) findViewById(R.id.id_ipv);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mLv = (RecyclerView) findViewById(R.id.id_rcv_play_list);
        mPlayBg = (RelativeLayout) findViewById(R.id.id_layout_bg_play);
        mCollToobar = (CollapsingToolbarLayout) findViewById(R.id.id_coordin_toolbar_play);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent l = getIntent();
        mChanId = l.getIntExtra("cId", -1);
        PlayListInfo playInfo = null;
        playInfo = (PlayListInfo) l.getSerializableExtra("playInfo");
        if (playInfo != null) {
            ipv.setCoverURL(Constants.URL_HEAD + playInfo.getIcon().substring(4));
            File bitmapFile = ImageLoader.getInstance().getDiskCache().get(Constants.URL_HEAD + playInfo.getIcon().substring(4));
            final Bitmap bitmap = FastBlurUtil.doBlur(BitmapFactory.decodeFile(bitmapFile.getAbsolutePath()), 8, false);
            mPlayBg.setBackground(new BitmapDrawable(bitmap));
//            mPlayBg.setBackground(ImageUtils.createBlurredImageFromBitmap(BitmapFactory.decodeFile(bitmapFile.getAbsolutePath()), PlayListActivity.this, 1));
            mCollToobar.setTitle(playInfo.getName());
        }
//        mLv.setAdapter(mAdapter = new PlayListAdapter(mPlayDatas, this));
        mLv.setAdapter(mAdapter = new PlayListRecycAdapter(mPlayDatas, this));
        mLv.setLayoutManager(new LinearLayoutManager(this));
        mLv.addItemDecoration(new DividerItemDecoration(PlayListActivity.this, DividerItemDecoration.VERTICAL_LIST));
//        mLv.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void initDatas() {
        ipv.setMax(240);

        if (mChanId > 0) {
            List<PlayListInfo> playListCache = new ArrayList<>();
            playListCache = MyApplication.helper.getAsSerializable(Constants.KEY_CHAN_ID + mChanId);
            if (playListCache == null || playListCache.size() == 0) {
                getPlayLists(String.valueOf(mChanId));
            } else mAdapter.updateDate(playListCache);
//            Log.d("tag", "当前频道：" + mChanId + "当前列表" + playListCache.size());
        }


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case REFRESH_COMPLETE:
//                        refreshLayout.setRefreshing(false);
                        Snackbar.make(fab, "刷新三下会有惊喜", Snackbar.LENGTH_SHORT).show();
                        break;

                    case MSG_PLAYLIST:
                        String result = (String) msg.obj;
                        if (TextUtils.isEmpty(result)) {
                            Snackbar.make(fab, "获取列表失败", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        String jsonLists = null;
                        try {
                            mPlayListCount = new JSONObject(result).get("total").toString();
                            jsonLists = new JSONObject(result).get("data").toString();
                            Type listType = new TypeToken<List<PlayListInfo>>() {
                            }.getType();
                            mPlayDatas = new Gson().fromJson(jsonLists, listType);
                            //保存当前下载的播放列表信息
                            MyApplication.helper.put(Constants.KEY_CHAN_ID + mChanId, (Serializable) mPlayDatas);

                            mAdapter.updateDate(mPlayDatas);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                }


            }
        };


    }

    @Override
    public void initEvents() {
        sendIntent = new Intent(Constants.BROAD_URL);
        controlIntent = new Intent(Constants.BROAD_Play_OR_PAUSE);
        mAdapter.setMyListener(this);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ipv.isPlaying()) {
                    ipv.start();
                    controlIntent.putExtra("play", true);
                    sendBroadcast(controlIntent);
                    fab.setImageResource(R.drawable.pause);
                } else {
                    ipv.stop();
                    controlIntent.putExtra("play", false);
                    sendBroadcast(controlIntent);
                    fab.setImageResource(R.drawable.play);
                }


            }
        });

    }


    /**
     * 根据传过来的 频道 id，获取频道的播放列表
     *
     * @param mChanId 被点击的频道id
     */
    private void getPlayLists(String mChanId) {

        OkHttpClientManager.Param param1 = new OkHttpClientManager.Param("id", mChanId);
        OkHttpClientManager.Param param2 = new OkHttpClientManager.Param("token", PreferenceUtil.getInstance(PlayListActivity.this).getStringSP("token"));
        OkHttpClientManager.Param params[] = new OkHttpClientManager.Param[]{param1, param2};
        OkHttpClientManager.postAsyn(Constants.URL_PLAYLIST, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.d("tag", "Error" + e);
            }

            @Override
            public void onResponse(String result) {
                mHandler.sendMessage(mHandler.obtainMessage(MSG_PLAYLIST, result));
            }
        });
    }

    /**
     * recycleview的 点击事件的回调
     *
     * @param pos
     */
    @Override
    public void onClickPosition(int pos) {
        PlayListInfo info = mPlayDatas.get(pos);


        sendIntent.putExtra("url", Constants.URL_HEAD + info.getPath().substring(4));
        sendBroadcast(sendIntent);
        ipv.setProgress(0);
        fab.setImageResource(R.drawable.pause);
        mCollToobar.setTitle(info.getName());


        ipv.setCoverURL(Constants.URL_HEAD + info.getIcon().substring(4));
        File bitmapFile = ImageLoader.getInstance().getDiskCache().get(Constants.URL_HEAD + info.getIcon().substring(4));
        final Bitmap bitmap = FastBlurUtil.doBlur(BitmapFactory.decodeFile(bitmapFile.getAbsolutePath()), 8, false);
        mPlayBg.setBackground(new BitmapDrawable(bitmap));
//        mPlayBg.setBackground(ImageUtils.createBlurredImageFromBitmap(BitmapFactory.decodeFile(bitmapFile.getAbsolutePath()), PlayListActivity.this, 1));
        //把当前的播放信息放到缓存中
        MyApplication.helper.put(Constants.KEY_CURRENT_PLAY, info);
        Intent intent = new Intent();
        //把当前播放信息 回传 给上一级 就是 主页面下边的 底栏
        intent.putExtra(PLAY_INFO, info);
        //把当前播放频道信息 回传 给上一级 就是 主页面下边的 底栏
        PreferenceUtil.getInstance(PlayListActivity.this).addSPInt(Constants.KEY_CHAN_ID, mChanId);
        setResult(0, intent);
    }


    /**
     * 接受播放进度和总时长的广播
     */
    public static class PlayStatusBoradCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(Constants.BROAD_PLAY_STATUS)) {
                int current = intent.getIntExtra("current", 0);
                ipv.setProgress(current);

            } else {
                int total = intent.getIntExtra("total", 240);
                ipv.setMax(total);
            }

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
