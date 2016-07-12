package com.fewwind.mydesign;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fewwind.mydesign.Service.MusicService;
import com.fewwind.mydesign.bean.CatBean;
import com.fewwind.mydesign.bean.ChanResult;
import com.fewwind.mydesign.bean.ChannelInfo;
import com.fewwind.mydesign.bean.GankAndroidBean;
import com.fewwind.mydesign.bean.PlayListInfo;
import com.fewwind.mydesign.bean.TagBean;
import com.fewwind.mydesign.bean.TegResult;
import com.fewwind.mydesign.fragment.FragmentFactory;
import com.fewwind.mydesign.net.HttpMethods;
import com.fewwind.mydesign.net.HttpMethodsInCar;
import com.fewwind.mydesign.net.PicGrid.CommonPluginBean;
import com.fewwind.mydesign.net.PicGrid.CommonRequestBean;
import com.fewwind.mydesign.net.PicGrid.HttpMethodsPicGrid;
import com.fewwind.mydesign.net.api.InCarApi;
import com.fewwind.mydesign.net.api.MeiZhi;
import com.fewwind.mydesign.tagview.FlowLayout;
import com.fewwind.mydesign.tagview.TagAdapter;
import com.fewwind.mydesign.tagview.TagFlowLayout;
import com.fewwind.mydesign.test.TestActivity;
import com.fewwind.mydesign.ui.AboutActivity;
import com.fewwind.mydesign.ui.BaseActivity;
import com.fewwind.mydesign.ui.PlayListActivity;
import com.fewwind.mydesign.ui.SettingsActivity;
import com.fewwind.mydesign.utils.ActivityCollector;
import com.fewwind.mydesign.utils.Constants;
import com.fewwind.mydesign.utils.OkHttpClientManager;
import com.fewwind.mydesign.utils.PreferenceUtil;
import com.fewwind.mydesign.utils.StatusBarCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FloatingActionButton fab;
    //双击退出记录的时间
    private long currentTime;
    private DrawerLayout drawer;

    private SharedPreferences mSP;
    private SharedPreferences.Editor mEditor;

    //类别的集合，比如：中文好歌，欧美金曲等，一个catid一个catname
    private List<CatBean> mCatList;
    private boolean mCache;

    public List<ChannelInfo> mChanLists;
    public List<TagBean> mTagLists;

    private static MyHandler mHandler;
    public ProgressDialog mDialog;

    private String currNav = "firstnav";
    private NavigationView navigationView;
    FragmentManager manager;

    private TextView mTvSinger;
    private TextView mTvName;
    private ImageView mIvAblum;
    private ProgressBar mPb;

    private RelativeLayout mPlayContainer;

    PlayListInfo infoLastPlay = null;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initViews() {

        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        manager = getFragmentManager();
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar_main);
        setSupportActionBar(toolbar);

        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.id_appbar);

        mTvSinger = (TextView) findViewById(R.id.id_tv_control_singer);
        mTvName = (TextView) findViewById(R.id.id_tv_control_name);
        mIvAblum = (ImageView) findViewById(R.id.id_iv_icon_control);
        mPb = (ProgressBar) findViewById(R.id.id_pb_control);
        mPlayContainer = (RelativeLayout) findViewById(R.id.id_rl_play_container);
        mPlayContainer.setOnClickListener(this);


        /**
         * sleep wait
         * private zhineng benlei
         * moren  benbao
         *
         * qidongmoshi
         *
         * .doOnNext(new Action1<MeiZhi.Result<List<GankAndroidBean>>>() {
        @Override public void call(MeiZhi.Result<List<GankAndroidBean>> listResult) {

        }
        })
         *
         */
//        AndroidSchedulers.mainThread()


        Subscriber<MeiZhi.Result<List<GankAndroidBean>>> observer = new Subscriber<MeiZhi.Result<List<GankAndroidBean>>>() {
            @Override
            public void onCompleted() {
                Logger.i("retrofit  completed");
            }

            @Override
            public void onError(Throwable e) {
                Logger.i("retrofit" + e.toString());
            }

            @Override
            public void onNext(MeiZhi.Result<List<GankAndroidBean>> listResult) {
                Logger.i("retrofit" + listResult.error);
            }
        };

        HttpMethods.getInstanceSync().getAndroidGank(observer, 10);

//        RestClient.api().getGankRxAndroids(10).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);

/*
        Call<MeiZhi.Result<List<GankAndroidBean>>> gankAndroids = meizhi.getGankAndroids(10);

        gankAndroids.enqueue(new Callback<MeiZhi.Result<List<GankAndroidBean>>>() {
            @Override
            public void onResponse(Response<MeiZhi.Result<List<GankAndroidBean>>> response) {
                for (GankAndroidBean bean :
                        response.body().getResultList()) {
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Logger.i("加载失败异常---" + t.toString());
            }
        });
*/
//        0, 101, 10048, "zn", "cn"
        CommonRequestBean requestBean = new CommonRequestBean(0, 101, 10048, "zn", "cn");
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, new Gson().toJson(requestBean));

        HttpMethodsPicGrid.getInstance().getPicGridApi().getCommonPlugin(body).enqueue(new Callback<CommonPluginBean>() {
            @Override
            public void onResponse(Response<CommonPluginBean> response, Retrofit retrofit) {
//                Logger.w("PicGridResponse===" + response.code() + "-----" + response.errorBody() + "------" + response.headers());
            }

            @Override
            public void onFailure(Throwable t) {
//                Logger.w("PicGrid==Failed=" + t.toString());
            }
        });

        HttpMethodsPicGrid.getInstance().getPicGridApi().getCommonPlugin2(requestBean).enqueue(new Callback<CommonPluginBean>() {
            @Override
            public void onResponse(Response<CommonPluginBean> response, Retrofit retrofit) {
                Logger.e("PicGridResponse===" + response.code() + "-----" + response.body() + "------" + response.headers());
            }

            @Override
            public void onFailure(Throwable t) {
                Logger.e("PicGrid==Failed=" + t.toString());
            }
        });




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            appbar.setPadding(0, StatusBarCompat.getStatusBarHeight(this), 0, 0);
        }

        StatusBarCompat.compat(this);
//        StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimaryDark));
        mSP = getSharedPreferences(Constants.MYSP, MODE_PRIVATE);
        mEditor = mSP.edit();
        fab = (FloatingActionButton) findViewById(R.id.fab);

        currNav = Constants.NAVIGATE_MYCHANS;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //可以显示的设置某个导航菜单的状态
        navigationView.setCheckedItem(R.id.nav_my_chan);


        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);


        mHandler = new MyHandler(this);

    }

    public static class MyHandler extends Handler {

        WeakReference<MainActivity> activityWeakReference;

        public MyHandler(MainActivity activity) {
            this.activityWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            if (msg.what == 0) {

                if (activityWeakReference.get().mDialog != null) {
                    activityWeakReference.get().mDialog.dismiss();
                }
                //展示第一个fragment，就是首页我的频道
                activityWeakReference.get().showDetailFragment(0);


            } else if (msg.what == 1) {
                if (activityWeakReference.get().mDialog != null) {
                    activityWeakReference.get().mDialog.dismiss();
                }


                String result = (String) msg.obj;

                try {
                    String jsonLists = new JSONObject(result).get("data").toString();
                    Type listType = new TypeToken<List<TagBean>>() {
                    }.getType();

                    final List<TagBean> datas = activityWeakReference.get().mTagLists = new Gson().fromJson(jsonLists, listType);

                    activityWeakReference.get().tagFlowLayout.setAdapter(activityWeakReference.get().tagAdapter = new TagAdapter<TagBean>(datas) {
                        @Override
                        public View getView(FlowLayout parent, int position, TagBean tag) {
                            TextView tvTag = (TextView) LayoutInflater.from(activityWeakReference.get()).inflate(R.layout.tv_tag, activityWeakReference.get().tagFlowLayout, false);
                            tvTag.setText(datas.get(position).getName());
                            return tvTag;
                        }
                    });
                    Log.d("tag", datas.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("tag", "handmessage" + result);
                //获取到token之后在进行操作
            } else if (msg.what == 2) {
                if (activityWeakReference.get().mSP.getBoolean("cache", false)) {
//            mDialog = ProgressDialog.show(this,null,"我是对话框cache······");
//
//            try {
//                Thread.sleep(5000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            if (mDialog!=null) mDialog.dismiss();
//            mHandler.sendEmptyMessage(0);
                    //展示第一个fragment，就是首页我的频道
                    Log.d("tag", "一缓存");
                    activityWeakReference.get().showDetailFragment(0);
                } else {
                    activityWeakReference.get().mDialog = ProgressDialog.show(activityWeakReference.get(), null, "我是对话框net······");
                    Log.d("tag", "start  dialog");
                    activityWeakReference.get().loadAllChaninfos(Constants.URL_ALLCHANS);
                }
            }

        }
    }

    ExecutorService cachedThreadPool;

    @Override
    protected void onResume() {
        super.onResume();
        infoLastPlay = MyApplication.helper.getAsSerializable(Constants.KEY_CURRENT_PLAY);
//如果上次播放信息存在，就直接设置
        if (infoLastPlay != null) {
            mTvName.setText(infoLastPlay.getName());
            mTvSinger.setText(infoLastPlay.getSinger());
            ImageLoader.getInstance().displayImage(Constants.URL_HEAD + infoLastPlay.getIcon().substring(4), mIvAblum);

        }


    }


    public void getToken(String deviceId) {

        HttpMethodsInCar.getInstance().getInCarApi().getToken(deviceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InCarApi.InCarToken>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("onError==" + e);
                    }

                    @Override
                    public void onNext(InCarApi.InCarToken inCarToken) {

                        mHandler.sendMessage(mHandler.obtainMessage(2));
                        PreferenceUtil.getInstance(MainActivity.this).addSP("token", inCarToken.data.token);
                    }
                });
    }

    @Override
    public void initEvents() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (currNav) {
                    case Constants.NAVIGATE_MYCHANS:
                        showDetailFragment(2);
                        break;

                    case Constants.NAVIGATE_CUSCHAN:
                        createCusChan();

                        break;
                }


            }
        });

    }

    //接收播放列表返回来的当前播放的媒体的信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            if (data != null) {

                PlayListInfo info = (PlayListInfo) data.getSerializableExtra(PlayListActivity.PLAY_INFO);
                mTvName.setText(info.getName());
                mTvSinger.setText(info.getSinger());
                ImageLoader.getInstance().displayImage(Constants.URL_HEAD + info.getIcon().substring(4), mIvAblum);

            }
        }
    }

    int current = 0;
    int total = 240;
    BroadcastReceiver mPbReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(Constants.BROAD_PLAY_STATUS)) {
                current = intent.getIntExtra("current", 0);
                mPb.setProgress(current);
//                Log.v("tag","当前进度："+current);
            } else {
                total = intent.getIntExtra("total", 240);
                mPb.setMax(total);
                Log.v("tag", "总共进度：" + total);
            }


        }
    };

    TagAdapter tagAdapter;
    TagFlowLayout tagFlowLayout;
    String[] tags = new String[]{};

    /**
     * 创建自定义频道的逻辑，包括选择标签和创建频道
     */
    private void createCusChan() {

        final MaterialDialog mdDialog = new MaterialDialog.Builder(this)
                .title("当前标签")
                .positiveText("ok")
                .customView(R.layout.dialog_custom, true)
                .negativeText("cancale")
                .show();
        tagFlowLayout = (TagFlowLayout) mdDialog.findViewById(R.id.id_tag_flow);

        getAllTags("0");


        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                TagBean tagBean = mTagLists.get(position);
                int id = tagBean.getId();
                mdDialog.setTitle(tagBean.getName());
                getAllTags(String.valueOf(id));

                return true;
            }
        });

    }

    private void getAllTags(String tags) {

        OkHttpClientManager.Param param1 = new OkHttpClientManager.Param("token", PreferenceUtil.getInstance(MainActivity.this).getStringSP("token"));
        OkHttpClientManager.Param param2 = new OkHttpClientManager.Param("tags", tags);
        OkHttpClientManager.Param params[] = new OkHttpClientManager.Param[]{param1, param2};
        OkHttpClientManager.postAsyn(Constants.URL_TAG, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.d("tag", "Error" + e);
            }

            @Override
            public void onResponse(String result) {
                mHandler.sendMessage(mHandler.obtainMessage(1, result));
            }
        });

        /**
         * 操作符的转换
         *
         * 第一次获取到token，用flatmap 把token对象转化成请求得到的数据
         *
         */
        HttpMethodsInCar.getInstance().getInCarApi().getToken("35568741598")
                .flatMap(new Func1<InCarApi.InCarToken, Observable<TegResult>>() {
                    @Override
                    public Observable<TegResult> call(InCarApi.InCarToken inCarToken) {

                        return HttpMethodsInCar.getInstance().getInCarApi().getTags(inCarToken.data.token, "0");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TegResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TegResult tegResult) {
                        Logger.v("请求标签===" + tegResult.toString());
                    }
                });

    }


    @Override
    public void initDatas() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.BROAD_TOTAL_DURATION);
        filter.addAction(Constants.BROAD_PLAY_STATUS);
        registerReceiver(mPbReceiver, filter);

        mChanLists = new ArrayList<>();
        mCatList = new ArrayList<>();
        int max = (int) (Runtime.getRuntime().maxMemory() / 1024 / 1024);
        Logger.d("当前可用内存==" + max);

        if (mSP.getBoolean("cache", false)) {
            mHandler.sendMessage(mHandler.obtainMessage(2));
        } else {
            getToken("255369712563");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public AlertDialog getEditCustomDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_custom, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("A New Version is Available");
        return builder.create();
    }

    public void hideFAB() {
        if (currNav.equals(Constants.NAVIGATE_CUSCHAN) || currNav.equals(Constants.NAVIGATE_MYCHANS)) {
            fab.setVisibility(View.VISIBLE);
        } else {

            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_msg) {
//            getEditCustomDialog().show();
            TestActivity.startTestActivity(this);
            return true;

        }

        if (id == R.id.action_aboutme) {

            AboutActivity.startAboutActivity(this);
            return true;

        }

        if (id == R.id.action_share) {
            sharePackageFile();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_chan) {
            showDetailFragment(1);

            currNav = Constants.NAVIGATE_MYCHANS;
            hideFAB();
        } else if (id == R.id.nav_sys_chan) {
            showDetailFragment(2);
            currNav = Constants.NAVIGATE_SYSCHANS;
            hideFAB();
        } else if (id == R.id.nav_cus_chan) {
            currNav = Constants.NAVIGATE_CUSCHAN;
            //打开自定义频道
            showDetailFragment(3);
            hideFAB();
        } else if (id == R.id.nav_play_lists) {

            if (infoLastPlay != null) {
                Intent intent = new Intent(MainActivity.this, PlayListActivity.class);
                intent.putExtra("playInfo", infoLastPlay);
                intent.putExtra("cId", PreferenceUtil.getInstance(MainActivity.this).getIntSP(Constants.KEY_CHAN_ID));
                //请求码 1 是 从我的音乐列表进入  2 是从底部进入
                startActivityForResult(intent, 2);
            }

            hideFAB();
        } else if (id == R.id.nav_share) {
            hideFAB();
        } else if (id == R.id.nav_setting) {
            SettingsActivity.startSettingActivity(this);
            hideFAB();
        } else if (id == R.id.id_iv_header) {
            hideFAB();
            Toast.makeText(this, "header", Toast.LENGTH_LONG).show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - currentTime > 1500) {
                Snackbar.make(fab, "再按一次退出程序", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                currentTime = System.currentTimeMillis();
            } else {
                ActivityCollector.finishAll();
            }

        }
    }

    private void showDetailFragment(int index) {
        Fragment details = null;

        android.app.FragmentTransaction transaction = manager.beginTransaction();


        switch (index) {
            case 0:
                details = FragmentFactory.getFragment(1);
                transaction.add(R.id.id_fl_fragment_container, details);// 初始化的时候增加第一个Fragment
                break;
            case 1:
                details = FragmentFactory.getFragment(1);
                transaction.replace(R.id.id_fl_fragment_container, details);// 初始化的时候增加第一个Fragment
                break;
            case 2:
                details = FragmentFactory.getFragment(2);
                navigationView.setCheckedItem(R.id.nav_sys_chan);
                transaction.replace(R.id.id_fl_fragment_container, details);// 替换Fragment
                break;
            case 3:
                details = FragmentFactory.getFragment(3);
                transaction.replace(R.id.id_fl_fragment_container, details);// 替换Fragment
                break;

        }
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        unregisterReceiver(mPbReceiver);

    }


    private void sharePackageFile() {

        PackageManager pm = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        List<ResolveInfo> resInfos = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> labelIntent = new ArrayList<>();
        for (int i = 0; i < resInfos.size(); i++) {
            ResolveInfo resInfo = resInfos.get(i);
            String packageName = resInfo.activityInfo.packageName;
            if (packageName.contains("tencent") || packageName.contains("blue")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(applicationInfo.sourceDir)));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                labelIntent.add(new LabeledIntent(intent, packageName, resInfo.loadLabel(pm), resInfo.icon));
            }
        }


        LabeledIntent[] extraIntents = labelIntent.toArray(new LabeledIntent[labelIntent.size()]);

        Intent openInChooser = Intent.createChooser(labelIntent.remove(0), warpChooserTitle("我的音乐"));
        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        startActivity(openInChooser);

    }

    /**
     * warp choose title and make app title accent
     *
     * @param appName app name
     * @return warped chooser title
     */
    private SpannableStringBuilder warpChooserTitle(String appName) {
        @SuppressLint("StringFormatMatches")
        String title = String.format(getString(R.string.select_transfer_way_apk, appName));
        ForegroundColorSpan fontSpanRed = new ForegroundColorSpan(getResources().getColor(R.color.colorAccent));
        int start = 2;
        int end = start + appName.length() + 3;
        SpannableStringBuilder mSpannableBuilder = new SpannableStringBuilder(title);
        mSpannableBuilder.setSpan(fontSpanRed, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return mSpannableBuilder;
    }

    /**
     * 加载所有频道信息
     *
     * @param url
     */
    private void loadAllChaninfos(String url) {
        OkHttpClientManager.Param param1 = new OkHttpClientManager.Param("token", PreferenceUtil.getInstance(MainActivity.this).getStringSP("token"));
        OkHttpClientManager.Param params[] = new OkHttpClientManager.Param[]{param1};
        OkHttpClientManager.postAsyn(url, params, new OkHttpClientManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.d("tag", "Error" + e);
                    }

                    @Override
                    public void onResponse(String u) {
//                Logger.json(u);
                        ChanResult result = new Gson().fromJson(u, new TypeToken<ChanResult>() {
                        }.getType());
                        Logger.w(result.data.toString());
/*                JSONArray jsonArray;
                JSONObject json;
                try {
                    json = new JSONObject(u);
                    jsonArray = json.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);


                        ChannelInfo info = new ChannelInfo(obj.getInt("id"), obj.getInt("ver"), obj.getInt("orderno"), obj.getString("icon"), obj.getString("name"), obj.getString("desc"), obj.getString("cat"), obj.getString("cat_id"));
                        mChanLists.add(info);
                    }*/
                        mChanLists = result.data;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                saveSingleChanInfo();
                            }
                        }).start();


                    }
                }

        );

    }

    /**
     * 保存用户的缓存信息
     */
    private void saveSingleChanInfo() {
        List<ChannelInfo> cache = null;
        //初始化我的频道信息，默认是取每个种类的第一个
        List<ChannelInfo> myChans = new ArrayList<>();
        //初始化自定义频道信息
        List<ChannelInfo> cusChans = new ArrayList<>();
        Map<String, List<ChannelInfo>> maps = new HashMap<>();
        for (ChannelInfo info : mChanLists) {
            if (maps.containsKey(info.getCat_id())) {

                maps.get(info.getCat_id()).add(info);
            } else {

                cache = new ArrayList<>();
                cache.add(info);
                maps.put(info.getCat_id(), cache);

            }
        }


        for (String key : maps.keySet()) {
            List<ChannelInfo> temp = maps.get(key);
            mCatList.add(new CatBean(key, temp.get(0).getCat()));
            Collections.sort(temp);
            //缓存各个种类所对应的系统频道。比如中文好歌下的频道，它的key  是中文好歌的id，有可能会跟  频道信息(中文排行榜等等)的id重复
            MyApplication.helper.put(key, (Serializable) temp);
            myChans.add(temp.get(0));
            PreferenceUtil.getInstance(this).addNewChan(String.valueOf(temp.get(0).getId()), true);
            PreferenceUtil.getInstance(this).addMyChan(String.valueOf(temp.get(0).getId()), true);
        }
        Collections.sort(mCatList);
        //缓存 种类，就是tab的标签，中文好歌，欧美金曲等等
        MyApplication.helper.put(Constants.CACHECATS, (Serializable) mCatList);
        //缓存我的频道，默认每个种类第一个的集合
        MyApplication.helper.put(Constants.CACHEMYCHANNELS, (Serializable) myChans);
        cusChans.add(myChans.get(0));
        cusChans.add(myChans.get(1));
        MyApplication.helper.put(Constants.CACHE_CUSCHANNELS, (Serializable) cusChans);

        Log.d("tag", "sendmsg");
        mHandler.sendEmptyMessage(0);
        mEditor.putBoolean("cache", true);
        mEditor.commit();

        if (mDialog != null) {
            mDialog.dismiss();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_rl_play_container:
                Log.i("tag", "onClick: ");


                if (infoLastPlay != null) {
                    Intent intent = new Intent(MainActivity.this, PlayListActivity.class);
                    intent.putExtra("playInfo", infoLastPlay);
                    intent.putExtra("cId", PreferenceUtil.getInstance(MainActivity.this).getIntSP(Constants.KEY_CHAN_ID));
                    //请求码 1 是 从我的音乐列表进入  2 是从底部进入
                    startActivityForResult(intent, 2);
                }
                break;

        }
    }
}
