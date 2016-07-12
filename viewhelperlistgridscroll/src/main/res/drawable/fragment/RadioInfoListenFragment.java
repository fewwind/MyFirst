package drawable.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.incarmedia.R;
import com.incarmedia.adapter.MyGridViewAdapterListen;
import com.incarmedia.adapter.MyViewPagerAdapter;
import com.incarmedia.bean.RadioInfoBean;
import com.incarmedia.ui.view.CircleIndicator;
import com.incarmedia.util.DataBaseUtil;
import com.incarmedia.util.RadioMediaPlayer;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;


public class RadioInfoListenFragment extends Fragment {
    private MyGridViewAdapterListen mAdapter;
    private ViewPager mViewPager;
    CircleIndicator indicator;
    private MyViewPagerAdapter mAdapterVP;
    List<GridView> gridLists;
    private List<GridView> mGridLists;
    List<RadioInfoBean> mLists;

    private static final String KEY_PEOVINCE = "KEY_PEOVINCE";

    private String mParam;

    private IRadioInfoLisenClickListener mListener;

    public RadioInfoListenFragment() {
    }


/*    // TODO: Rename and change types and number of parameters
    public static RadioInfoListenFragment newInstance(String param1) {
        RadioInfoListenFragment fragment = new RadioInfoListenFragment();
        Bundle args = new Bundle();
        args.putString(KEY_PEOVINCE, param1);
        fragment.setArguments(args);
        return fragment;
    }*/

    static  RadioInfoListenFragment fragment;
    // TODO: Rename and change types and number of parameters
    public static RadioInfoListenFragment newInstance(String param1) {
        if (fragment==null) fragment = new RadioInfoListenFragment();

        Bundle args = new Bundle();
        args.putString(KEY_PEOVINCE, param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBaseUtil.openDataBase();
        if (getArguments() != null) {
            mParam = getArguments().getString(KEY_PEOVINCE);
        }

        gridLists = new ArrayList<>();
        mLists = new ArrayList<>();
        mGridLists = new ArrayList<>();

        if (RadioCataFragment.CURRENT_TYPE.equals(DataBaseUtil.CATA_LOG)){
            mLists = DataBaseUtil.getRadioInfos(DataBaseUtil.CATA_LOG,mParam);
        }  else if (RadioCataFragment.CURRENT_TYPE.equals(DataBaseUtil.PROVINCE)){
            if (mParam.equals("本地"))
            mLists = DataBaseUtil.getRadioInfos(DataBaseUtil.PROVINCE,"北京");
            else mLists = DataBaseUtil.getRadioInfos(DataBaseUtil.PROVINCE,mParam);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_radio_info_listen, container, false);
        indicator = (CircleIndicator) view
                .findViewById(R.id.id_indiacator_radio_listen);
        mViewPager = (ViewPager) view.findViewById(R.id.id_vp_radio_cata_listen);

        List<GridView> gridViews = initGridList(mLists, getActivity(),getActivity());
        mGridLists.addAll(gridViews);

        mAdapterVP = new MyViewPagerAdapter(getActivity(), mGridLists);
        mViewPager.setAdapter(mAdapterVP);
        indicator.setViewPager(mViewPager);



        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IRadioInfoLisenClickListener) {
            mListener = (IRadioInfoLisenClickListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public List<GridView> initGridList(final List<RadioInfoBean> json, Context context,Activity activity) {
        gridLists.clear();
        if (json.size() == 0) {
            return new ArrayList<GridView>();
        }
        final int PageCount = (int) Math.ceil(json.size() / (double) MyGridViewAdapterListen.PAGE_SIZE);
        for (int i = 0; i < PageCount; i++) {
            GridView gv = new GridView(context);

            gv.setAdapter(new MyGridViewAdapterListen(context, json, i,activity));
            gv.setGravity(Gravity.CENTER);
            gv.setClickable(true);
            gv.setFocusable(true);
//			gv.setLayoutParams(new LayoutParams(source))
            gv.setHorizontalSpacing(20);
            gv.setVerticalSpacing(30);
//			gv.setNumColumns(GridView.AUTO_FIT);
            gv.setNumColumns(4);
            gv.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
//            gv.setColumnWidth(Resources.getSystem().getDisplayMetrics().widthPixels * 4 / 15);
            gv.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, false));
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
//
//                    cataClickListener.onItemClickListener(json.get(mViewPager
//                            .getCurrentItem() * MyGridViewAdapterListen.PAGE_SIZE + position));
//                    LogUtils.getIntance().d(json.get(mViewPager
//                            .getCurrentItem() * MyGridViewAdapterListen.PAGE_SIZE + position).getName());
                }
            });
            gridLists.add(gv);
        }
        return gridLists;
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("RadioInfoListenFragment");

            RadioMediaPlayer.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("RadioInfoListenFragment");
    }
}
