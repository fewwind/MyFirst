package drawable.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incarmedia.R;
import com.incarmedia.adapter.CommonAdapter;
import com.incarmedia.adapter.ViewHolder;
import com.incarmedia.bean.RadioInfoBean;
import com.incarmedia.bean.RadioProgramBean;
import com.incarmedia.incommon.incar.common;
import com.incarmedia.incommon.util.Constant;
import com.incarmedia.incommon.webapi.HttpCallbackListener;
import com.incarmedia.incommon.webapi.HttpUtil;
import com.incarmedia.ui.view.SmallBang;
import com.incarmedia.ui.view.SmallBangListener;
import com.incarmedia.util.DataBaseUtil;
import com.incarmedia.util.RadioMediaPlayer;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RadioPlayFragment extends Fragment implements View.OnClickListener {


    private RadioInfoBean mCurrRadioInfo;
    private SmallBang mSmallBang;
    ImageView mLove;
    TextView mRadioName;
    ImageView mRadioProgram;
    ListView mLvProgram;
    CommonAdapter mProgramAdapter;
    List<RadioProgramBean> programLists;

    SimpleDateFormat sdf;
    TextView mCurrTime;
    TextView mTvNull;
    Handler mHandler;
    boolean mTaskStart = true;
    String currTime;
    private static final String KEY_RADIO_INFO = "KEY_RADIO_INFO";
    private RelativeLayout.LayoutParams parms;
    private ImageView radio_play_bg;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBaseUtil.openDataBase();
        if (getArguments() != null) {
            mCurrRadioInfo = (RadioInfoBean) getArguments().getSerializable(KEY_RADIO_INFO);
        }
        sdf = new SimpleDateFormat("HH:mm");

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                currTime= sdf.format(new Date());
                mProgramAdapter.updateClearDatas(programLists);
                if (programLists.size()>0){
                    for (RadioProgramBean bean:programLists){
                        if (bean.getStart().compareTo(currTime)<0&&bean.getEnd().compareTo(currTime)>0){
                            mCurrTime.setText("(" + bean.getProgramname() + ")");
                        }
                    }
                }

            }
        };

    }

    Runnable getCurrProgramTask =  new Runnable() {
        @Override
        public void run() {
            currTime= sdf.format(new Date());
            if (programLists.size()>0){
                for (RadioProgramBean bean:programLists){
                    if (bean.getStart().compareTo(currTime)<0&&bean.getEnd().compareTo(currTime)>0){
                        mCurrTime.setText("(" + bean.getProgramname() + ")");
                    }
                }
            }
            if (mTaskStart)mHandler.postDelayed(getCurrProgramTask,1000*60);

        }
    };
    public void getRadioProgram(String url ,int chanId){

        List<BasicNameValuePair> param = new LinkedList<BasicNameValuePair>();
        param.add(new BasicNameValuePair("channelid", chanId + ""));

        HttpUtil.sendHttpRequest(url, param, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                String result = null;
                try {
                    JSONObject json = new JSONObject(response);
                    result =  json.getJSONArray("data").toString();
                    Gson gson=new Gson();
                    programLists.clear();
                    programLists=gson.fromJson(result, new TypeToken<ArrayList<RadioProgramBean>>(){}.getType());

                    mHandler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Exception e) {
            }

            @Override
            public void onNetError() {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_radio_play, container,
                false);
        mLove = (ImageView) view.findViewById(R.id.id_iv_radio_play_love);
        mRadioName = (TextView) view.findViewById(R.id.id_iv_radio_play_name);
        mRadioProgram = (ImageView) view.findViewById(R.id.id_iv_radio_program);
        mLvProgram = (ListView) view.findViewById(R.id.id_lv_radio_program);
        mCurrTime = (TextView) view.findViewById(R.id.id_iv_radio_play_curr);
        mTvNull  = (TextView) view.findViewById(R.id.id_iv_radio_null);
        radio_play_bg = (ImageView) view.findViewById(R.id.id_iv_radio_play_bg);
        parms =new RelativeLayout.LayoutParams(this.getResources().getDisplayMetrics().heightPixels*8/9,this.getResources().getDisplayMetrics().heightPixels*4/9);
        parms.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        parms.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        radio_play_bg.setLayoutParams(parms);
        mLvProgram.setAdapter(mProgramAdapter =new CommonAdapter<RadioProgramBean>(getActivity(),programLists =new ArrayList<RadioProgramBean>(),R.layout.item_radio_program) {
            @Override
            public void convert(ViewHolder holder, RadioProgramBean radioProgramBean) {
                holder.setText(R.id.id_tv_radio_program_name,radioProgramBean.getProgramname());
                holder.setText(R.id.id_tv_radio_program_time,"直播时间："+radioProgramBean.getStart()+"-"+radioProgramBean.getEnd());
            }
        });

        mSmallBang = SmallBang.attach2Window(getActivity());
        if (mCurrRadioInfo.getLove() == 0)
            mLove.setImageResource(R.drawable.common_player_favorite);
        else mLove.setImageResource(R.drawable.common_player_favorited);

        mRadioProgram.setOnClickListener(this);
        mLove.setOnClickListener(this);
        mRadioName.setText(mCurrRadioInfo.getChannelName());
        mTvNull.setText("获取失败");
        mLvProgram.setEmptyView(mTvNull);

       if (mCurrRadioInfo.getId()>0)getRadioProgram(Constant.urlRadioProgram,mCurrRadioInfo.getId());
        return view;
    }


    public void setPlayInfo(RadioInfoBean info){
        mRadioName.setText(info.getChannelName());
        getRadioProgram(Constant.urlRadioProgram, info.getId());
        //把 最新的频道信息 赋值给 当前的广播信息，不然点击事件还是原来的
        mCurrRadioInfo = info;
        if (info.getLove() == 0)
            mLove.setImageResource(R.drawable.common_player_favorite);
        else mLove.setImageResource(R.drawable.common_player_favorited);

    }

    public static RadioPlayFragment newInstance(RadioInfoBean infoBean) {
        RadioPlayFragment fragment = new RadioPlayFragment();

        Bundle args = new Bundle();
        args.putSerializable(KEY_RADIO_INFO, infoBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("RadioPlayFragment");
        mTaskStart = true;
       mHandler.postDelayed(getCurrProgramTask,2000);
    }


    public void like(View view){
        mSmallBang.bang(view);
        mSmallBang.setmListener(new SmallBangListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_iv_radio_play_love:

                if (mCurrRadioInfo.getLove() == 0) {
                    mCurrRadioInfo.setLove(1);
                    like(v);
                    mLove.setImageResource(R.drawable.common_player_favorited);
                    DataBaseUtil.updataLove(mCurrRadioInfo.getId(), 1);

                    RadioMediaPlayer.infoCurrentLists = DataBaseUtil.getRadioInfos(
                            DataBaseUtil.LOVE, "1");
                    common.shownote("收藏成功");
                } else {
                    common.shownote("取消成功");
                    mLove.setImageResource(R.drawable.common_player_favorite);
                    DataBaseUtil.updataLove(mCurrRadioInfo.getId(), 0);
                    like(v);
                    mCurrRadioInfo.setLove(0);

                    RadioMediaPlayer.infoCurrentLists = DataBaseUtil.getRadioInfos(
                            DataBaseUtil.LOVE, "1");
                }
                break;

            case R.id.id_iv_radio_program:
                if (!mLvProgram.isShown()){
                    mLvProgram.setVisibility(View.VISIBLE);
                } else {
                    mLvProgram.setVisibility(View.GONE);

                }

                break;

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mTaskStart = false;
        MobclickAgent.onPageEnd("RadioPlayFragment");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mHandler.removeCallbacksAndMessages(null);
    }
}
