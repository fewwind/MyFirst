package drawable.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incarmedia.R;
import com.incarmedia.adapter.KaLaOKPersonalInfoAdapter;
import com.incarmedia.adapter.MyViewPagerAdapter;
import com.incarmedia.bean.KaLaOKInfoBean;
import com.incarmedia.incommon.util.Constant;
import com.incarmedia.incommon.webapi.HttpCallbackListener;
import com.incarmedia.incommon.webapi.HttpUtil;
import com.incarmedia.ui.view.CircleIndicator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyUpLoadFragment extends Fragment {
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
        mLists.addAll(init(kalaInfoLists, view.getContext()));
        adapter = new MyViewPagerAdapter(view.getContext(), mLists);
        ok_pager_mysong.setAdapter(adapter);
        indicator.setViewPager(ok_pager_mysong);
        ok_pager_mysong.setOffscreenPageLimit(3);
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("act",   "2"));
        KaraUserHome(params);
        mhandle = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==0){
                    mLists.clear();
                    mLists.addAll(init(kalaInfoLists,  getActivity()));
                    indicator.setViewPager(ok_pager_mysong);
                    adapter.notifyDataSetChanged();
                }
            }
        };
        return view;
    }

    public List<GridView> init(List<KaLaOKInfoBean> json, Context context) {
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
//            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view,
//                                        int position, long id) {
//                    clickListener.onItemClickListener(mViewPager
//                            .getCurrentItem() * MyGridViewAdapter.PAGE_SIZE + position);
//                }
//            });
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
                    msc = json.getJSONArray("msc").toString();
                    Gson gson = new Gson();
                    kalaInfoLists = gson.fromJson(msc, typeSongInFo);
                    mhandle.sendEmptyMessage(0);
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
}
