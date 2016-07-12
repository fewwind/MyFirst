package drawable.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.incarmedia.R;
import com.incarmedia.adapter.MyGridViewAdapterCataSelec;
import com.incarmedia.adapter.MyViewPagerAdapter;
import com.incarmedia.bean.ProviceBean;
import com.incarmedia.ui.view.CircleIndicator;
import com.incarmedia.ui.view.GradientIconView;
import com.incarmedia.ui.view.GradientTextView;
import com.incarmedia.util.DataBaseUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;


public class RadioCataFragment extends Fragment implements View.OnClickListener {

    private MyGridViewAdapterCataSelec mAdapter;
    private ViewPager mViewPager;
    private CircleIndicator indicator;
    private MyViewPagerAdapter mAdapterVP;
    private List<GridView> gridLists;
    private List<GridView> mGridLists;
    private List<ProviceBean> mLists;

    GradientIconView btnProvinceIcon;
    GradientIconView btnContentIcon;
    GradientTextView btnContentTv;
    GradientTextView btnProvinceTv;
    LinearLayout btnProvince;
    LinearLayout btnContent;


    private IRadioCataClickListener cataClickListener;
    private static RadioCataFragment fragment;
    List<ProviceBean> proviceBeanList;

    public static String CURRENT_TYPE = DataBaseUtil.PROVINCE;

    public RadioCataFragment() {
        // Required empty public constructor
    }


    public static RadioCataFragment newInstance() {

        if (fragment == null)
            fragment = new RadioCataFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBaseUtil.openDataBase();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        cataClickListener = (IRadioCataClickListener) activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_radio_cata, container, false);

        initView(view);


        gridLists = new ArrayList<>();
        mGridLists = new ArrayList<>();

        mLists = new ArrayList<>();

        if(CURRENT_TYPE==DataBaseUtil.PROVINCE){
            showIndicator(0);
            mLists.add(new ProviceBean("本地"));
            proviceBeanList = DataBaseUtil.getProvince();
        } else if(CURRENT_TYPE==DataBaseUtil.CATA_LOG){
            showIndicator(1);
            proviceBeanList = DataBaseUtil.getCataLog();
        }



        mLists.addAll(proviceBeanList);
        List<GridView> gridViews = initGridList(mLists, getActivity());
        mGridLists.addAll(gridViews);

        mAdapterVP = new MyViewPagerAdapter(getActivity(), mGridLists);
        mViewPager.setAdapter(mAdapterVP);
        indicator.setViewPager(mViewPager);


        btnProvince.setOnClickListener(this);
        btnContent.setOnClickListener(this);

        return view;
    }


    private void initView(View view) {
        indicator = (CircleIndicator) view
                .findViewById(R.id.id_indiacator_radio_cata);
        mViewPager = (ViewPager) view.findViewById(R.id.id_vp_radio_cata_select);
        btnProvinceIcon = (GradientIconView) view.findViewById(R.id.id_iconfont_radio_province);
        btnProvinceTv = (GradientTextView) view.findViewById(R.id.id_tv_radio_province);
        btnContentIcon = (GradientIconView) view.findViewById(R.id.id_iconfont_radio_content);
        btnContentTv = (GradientTextView) view.findViewById(R.id.id_tv_radio_content);
        btnProvince = (LinearLayout) view.findViewById(R.id.id_ll_radio_province);
        btnContent = (LinearLayout) view.findViewById(R.id.id_ll_radio_content);

    }


    public void showIndicator(int pos) {
        if (pos == 0) {
            btnProvinceIcon.setIconAlpha(1.0f);
            btnProvinceTv.setTextViewAlpha(1.0f);
            btnContentIcon.setIconAlpha(0);
            btnContentTv.setTextViewAlpha(0);
        } else {
            btnProvinceIcon.setIconAlpha(0f);
            btnProvinceTv.setTextViewAlpha(0f);
            btnContentIcon.setIconAlpha(1.0f);
            btnContentTv.setTextViewAlpha(1.0f);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public List<GridView> initGridList(final List<ProviceBean> json, Context context) {
        gridLists.clear();
        if (json.size() == 0) {
            return new ArrayList<GridView>();
        }
        final int PageCount = (int) Math.ceil(json.size() / (double) MyGridViewAdapterCataSelec.PAGE_SIZE);
        for (int i = 0; i < PageCount; i++) {
            GridView gv = new GridView(context);

            gv.setAdapter(new MyGridViewAdapterCataSelec(context, json, i));
            gv.setGravity(Gravity.CENTER);
            gv.setClickable(true);
            gv.setFocusable(true);
//			gv.setLayoutParams(new LayoutParams(source))
            gv.setHorizontalSpacing(40);
            gv.setVerticalSpacing(60);
//			gv.setNumColumns(GridView.AUTO_FIT);
            gv.setNumColumns(4);
            gv.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gv.setColumnWidth(Resources.getSystem().getDisplayMetrics().widthPixels * 4 / 15);
            gv.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, false));
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    cataClickListener.onItemClickListener(json.get(mViewPager
                            .getCurrentItem() * MyGridViewAdapterCataSelec.PAGE_SIZE + position));
                }
            });
            gridLists.add(gv);
        }
        return gridLists;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_ll_radio_province:

                showIndicator(0);
                CURRENT_TYPE = DataBaseUtil.PROVINCE;
                mGridLists.clear();
                mGridLists.addAll(initGridList(DataBaseUtil.getProvince(), getActivity()));
                mAdapterVP.notifyDataSetChanged();
                indicator.setViewPager(mViewPager);
                break;
            case R.id.id_ll_radio_content:
                showIndicator(1);

                CURRENT_TYPE = DataBaseUtil.CATA_LOG;
                mGridLists.clear();
                List<GridView> list = initGridList(DataBaseUtil.getCataLog(), getActivity());
                mGridLists.addAll(list);
                mAdapterVP.notifyDataSetChanged();
                indicator.setViewPager(mViewPager);
                break;

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("RadioCataFragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("RadioCataFragment");
    }
}
