package drawable.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.incarmedia.R;
import com.incarmedia.adapter.MyGridViewAdapter;
import com.incarmedia.adapter.MyViewPagerAdapter;
import com.incarmedia.bean.RadioInfoBean;
import com.incarmedia.incommon.incar.common;
import com.incarmedia.ui.view.CircleIndicator;
import com.incarmedia.util.DataBaseUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class RadioInfoFragment extends Fragment {

    private MyViewPagerAdapter adapter;
    MyGridViewAdapter adapterGrid;
    // private List<GridView> mLists;
    private List<GridView> mLists;
    private ViewPager mViewPager;
    CircleIndicator indicator;
    List<GridView> gridLists;
    List<RadioInfoBean> radioInfoLists;
    List<GridView> init = null;

    IRadioClickListener clickListener;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        clickListener = (IRadioClickListener) activity;
    }

    //
    // public void setOnRadioClickListener(IRadioClickListener listener){
    // this.clickListener = listener;
    // }
    //
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_radio_info, container,
                false);
        mViewPager = (ViewPager) view.findViewById(R.id.id_vp_radio_info);
        indicator = (CircleIndicator) view
                .findViewById(R.id.id_indiacator_radio);

        mLists = new ArrayList<>();
        gridLists = new ArrayList<GridView>();
        radioInfoLists = new ArrayList<RadioInfoBean>();
        radioInfoLists = DataBaseUtil.getRadioInfos(
                DataBaseUtil.LOVE, "1");
        mLists.addAll(init(radioInfoLists, getActivity()));

        adapter = new MyViewPagerAdapter(getActivity(), mLists);
        mViewPager.setAdapter(adapter);
        indicator.setViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        DataBaseUtil.openDataBase();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("RodioInfoFragment");
    }

    public void updataRadioInfo(List<RadioInfoBean> datas, Context context) {
        // mLists.clear();
        // for (int i = 0; i < adapterLists.size(); i++) {
        // adapterLists.get(i).notifyDataSetChanged();
        // }
        // System.out.println(datas.size()+"上海");
        // mLists.addAll(init(datas));
        // adapter.notifyDataSetChanged();
        //
        // init(datas);
        if (datas.size() == 0) {
            mViewPager.removeAllViews();
            indicator.setVisibility(View.GONE);
            common.shownote("暂时没有节目");
            return;
        }
        indicator.setVisibility(View.VISIBLE);
        mLists.clear();
        List<GridView> list = init(datas, context);
        mLists.addAll(list);
        // mViewPager.setAdapter(adapter);
        this.radioInfoLists.addAll(datas);
        adapter.notifyDataSetChanged();
        indicator.setViewPager(mViewPager);
    }

    static RadioInfoFragment fragment = null;

    public static RadioInfoFragment newInstance() {

        if (fragment == null) {
            fragment = new RadioInfoFragment();
        }

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("RodioInfoFragment");
    }

    public List<GridView> init(List<RadioInfoBean> json, Context context) {
        if (json.size() == 0) {
            return new ArrayList<GridView>();
        }
        gridLists.clear();
        final int PageCount = (int) Math.ceil(json.size() / (double) MyGridViewAdapter.PAGE_SIZE);
        for (int i = 0; i < PageCount; i++) {
            GridView gv = new GridView(context);

            gv.setAdapter(adapterGrid = new MyGridViewAdapter(context, json, i));
            gv.setGravity(Gravity.CENTER_VERTICAL);
            gv.setClickable(true);
            gv.setFocusable(true);

            gv.setHorizontalSpacing(40);
            gv.setVerticalSpacing(20);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			gv.setLayoutParams(lp);
            ViewGroup.LayoutParams layoutParams = gv.getLayoutParams();

//			gv.setNumColumns(GridView.AUTO_FIT);
            gv.setNumColumns(4);
            gv.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gv.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, false));
            gv.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    clickListener.onItemClickListener(mViewPager
                            .getCurrentItem() * MyGridViewAdapter.PAGE_SIZE + position);
                }
            });
            gridLists.add(gv);
        }
        return gridLists;
    }
}
