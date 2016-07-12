package drawable.fragment;

import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incarmedia.R;
import com.incarmedia.adapter.KaLaOKPersonalInfoAdapter;
import com.incarmedia.adapter.MyViewPagerAdapter;
import com.incarmedia.bean.KaLaOKInfoBean;
import com.incarmedia.incommon.incar.FileManager;
import com.incarmedia.incommon.incar.common;
import com.incarmedia.incommon.ui.KaLaOKDialogStyle;
import com.incarmedia.incommon.util.Constant;
import com.incarmedia.incommon.util.ImageUILUtil;
import com.incarmedia.incommon.webapi.HttpCallbackListener;
import com.incarmedia.incommon.webapi.HttpUtil;
import com.incarmedia.ui.view.CircleIndicator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.orhanobut.logger.Logger;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MySongFragment extends Fragment {
    private View view;
    private MyViewPagerAdapter adapter;
    private ViewPager ok_pager_mysong;
    private List<GridView> gridLists;
    private KaLaOKPersonalInfoAdapter adapterGrid;
    private int PAGE_SIZE = 4;  //单页数量
    private CircleIndicator indicator;
    private Handler mhandle;
    private List<GridView> mLists;
    private ArrayList<KaLaOKInfoBean> kalaInfoLists;
    private KaLaOKDialogStyle kaLaDialog;
    private ImageView tryPlay;
    private FrameLayout fl;
    private SeekBar seekbarPlay;
    private ImageView dialogplayPic;
    private MediaPlayer mediaPlayer3;
    private TextView dilagSongName;
    private ImageView kalaDialogClose;
    private Runnable updateRunnable;
    private File heSong;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_song, container, false);
        gridLists = new ArrayList<GridView>();
        ok_pager_mysong = (ViewPager) view.findViewById(R.id.id_viewpager_ok_mysong);
        indicator = (CircleIndicator) view.findViewById(R.id.id_indiacator_kala_mysong);
        kalaInfoLists = new ArrayList<KaLaOKInfoBean>();
        mLists = new ArrayList<>();
        if (mediaPlayer3 == null) {
            mediaPlayer3 = new MediaPlayer();
        }
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                while (kaLaDialog != null) {
                    if (mediaPlayer3!=null&&mediaPlayer3.isPlaying()) {
                        int mMax = mediaPlayer3.getDuration();
                        double sMax = seekbarPlay.getMax();
                        int pos = 0;

                        if (mMax > 0) {
                            pos = (int) (mediaPlayer3.getCurrentPosition() * sMax / mMax);
                        }
                        seekbarPlay.setProgress(pos);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        };
        mLists.addAll(init(kalaInfoLists, view.getContext()));
        adapter = new MyViewPagerAdapter(view.getContext(), mLists);
        ok_pager_mysong.setAdapter(adapter);
        indicator.setViewPager(ok_pager_mysong);
        ok_pager_mysong.setOffscreenPageLimit(3);
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("act", "2"));
        KaraUserHome(params);
        mhandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    mLists.clear();
                    mLists.addAll(init(kalaInfoLists, getActivity()));
                    indicator.setViewPager(ok_pager_mysong);
                    adapter.notifyDataSetChanged();
                }
            }
        };
        return view;
    }

    public List<GridView> init(List<KaLaOKInfoBean> json, final Context context) {
        if (json.size() == 0) {
            return new ArrayList<GridView>();
        }
        gridLists.clear();
        final int PageCount = (int) Math.ceil(json.size() / (double) PAGE_SIZE);
        for (int i = 0; i < PageCount; i++) {
            GridView gv = new GridView(context);
            gv.setAdapter(adapterGrid = new KaLaOKPersonalInfoAdapter(context, json, i, PAGE_SIZE));
            gv.setGravity(Gravity.CENTER_VERTICAL);
            gv.setClickable(true);
            gv.setFocusable(true);
            gv.setHorizontalSpacing(40);
            gv.setVerticalSpacing(20);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            gv.setLayoutParams(lp);
            ViewGroup.LayoutParams layoutParams = gv.getLayoutParams();
//			gv.setNumColumns(GridView.AUTO_FIT);
            gv.setNumColumns(1);
            gv.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gv.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, false));
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //播放录音界面还没有。
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    KaLaOKInfoBean bean = kalaInfoLists.get(ok_pager_mysong.getCurrentItem() * PAGE_SIZE + position);
                    heSong = new File((FileManager.RecordDir +"/"+ bean.getUrlNew().substring(bean.getUrlNew().lastIndexOf("/"),bean.getUrlNew().length())).replace(".aac",".m4a"));

                    if (heSong.exists()) {
                        try {
                            if(mediaPlayer3==null){
                                mediaPlayer3=new MediaPlayer();
                            }
                            mediaPlayer3.setDataSource(heSong.getAbsolutePath());
                            mediaPlayer3.prepare();
                            mediaPlayer3.start();
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }
                    if(kaLaDialog==null){
                        kaLaDialog = new KaLaOKDialogStyle(context, R.style.dialog2, R.layout.dialog_kala);
                    }
                    kaLaDialog.show();
                    Logger.i(heSong.getAbsolutePath());
                    fl = (FrameLayout) kaLaDialog.findViewById(R.id.id_fl_kala_loading);
                    if (fl != null) {
                        fl.setVisibility(View.GONE);
                    }
                    seekbarPlay = (SeekBar) kaLaDialog.findViewById(R.id.id_kala_seekbar_play);
                    dialogplayPic = (ImageView) kaLaDialog.findViewById(R.id.id_iv_kala_dialog_pic);
                    ImageUILUtil.initImageLoader(dialogplayPic,
                            Constant.urlKaLaOKIcon + bean.getIcon().replace("%$2%", ""), R.drawable.common_player_album_default);
                    dilagSongName = (TextView) kaLaDialog.findViewById(R.id.id_tv_kala_dialog_songName);
                    dilagSongName.setText("歌曲：" + bean.getSongName());
                    new Thread(updateRunnable).start();
                    kalaDialogClose = (ImageView) kaLaDialog.findViewById(R.id.id_iv_kala_dialog_close);
                    kalaDialogClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mediaPlayer3!=null&&mediaPlayer3.isPlaying()) {
                                mediaPlayer3.pause();
                                mediaPlayer3.release();
                                mediaPlayer3=null;
                            }
                            kaLaDialog.dismiss();
                        }
                    });
                    Button button = (Button) kaLaDialog.findViewById(R.id.id_bt_kala_save);
                    button.setText("关闭试听");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mediaPlayer3!=null&&mediaPlayer3.isPlaying()) {
                                mediaPlayer3.pause();
                                mediaPlayer3.release();
                                mediaPlayer3=null;
                            }
                            kaLaDialog.dismiss();


                        }
                    });
                    tryPlay = (ImageView) kaLaDialog.findViewById(R.id.id_kala_tryplay);
                    tryPlay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mediaPlayer3!=null){
                                if (mediaPlayer3.isPlaying()) {
                                    mediaPlayer3.pause();
                                    tryPlay.setImageResource(R.drawable.kala_tryplay);
                                } else {
                                    mediaPlayer3.start();
                                    tryPlay.setImageResource(R.drawable.play_stop);
                                }
                            }
                        }
                    });
                    seekbarPlay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        public void onProgressChanged(SeekBar seekBar, int progress,
                                                      boolean fromUser) {
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            // 采用浮点
                            if(mediaPlayer3!=null){
                                double dest = seekBar.getProgress();
                                double mMax = mediaPlayer3.getDuration();
                                double sMax = seekbarPlay.getMax();

                                mediaPlayer3.seekTo((int) (mMax * dest / sMax));
                            }
                        }
                    });
//                    Intent intent = new Intent();
//                    intent.setClass(context,KaLaPlayActivity.class);
//                    intent.putExtra("song",bean);
//                    startActivity(intent);
                }
            });
            gridLists.add(gv);
        }
        return gridLists;
    }

    private void KaraUserHome(List<BasicNameValuePair> param) {

        HttpUtil.sendHttpRequest(Constant.KaraUserHome, param, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {

                Type typeSongInFo = new TypeToken<ArrayList<KaLaOKInfoBean>>() {
                }.getType();
                try {
                    JSONObject json = new JSONObject(response);
                    String msc = null;
                    String status = json.getString("status");
                    String statusCode = new JSONObject(status).getString("statuscode");
                    if (!statusCode.equals("0")) {
                        common.shownote("获取失败！");
                        return;
                    }
                    Logger.json(response);
                    msc = json.getJSONArray("msc").toString();
                    Gson gson = new Gson();
                    kalaInfoLists = gson.fromJson(msc, typeSongInFo);
                    if (kalaInfoLists != null && kalaInfoLists.size() != 0) {
                        mhandle.sendEmptyMessage(0);
                    } else {
                        common.shownote("你暂时还没有录制歌曲，快点去录制吧！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onNetError() {
                common.shownote("网络开小差了，请检查网络");
            }
        });
    }
}
