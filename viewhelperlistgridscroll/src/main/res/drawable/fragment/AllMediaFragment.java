package drawable.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.incarmedia.R;
import com.incarmedia.adapter.MyExpandableAdapter;
import com.incarmedia.incommon.webapi.mediaiteminfo;
import com.umeng.analytics.MobclickAgent;

import java.util.List;
import java.util.Map;


public class AllMediaFragment extends Fragment {


	 /**
     * type: 0,代表全部文件   1 代表音乐fragment  2，代表视频fragment，这两个共用一个fragment，根据类别区分
     */
    private int type;

    ExpandableListView mELv;
    MyExpandableAdapter mAdapter;
    public static AllMediaFragment newInstance(int type) {
        AllMediaFragment fragment = new AllMediaFragment();
        Bundle args = new Bundle();
        args.putInt(MusicFragment.TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    public AllMediaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(MusicFragment.TYPE);
          
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_all_media, container, false);
    	mELv = (ExpandableListView) view.findViewById(R.id.id_elv_all_media);
    	
    	mELv.setAdapter(mAdapter = new MyExpandableAdapter(getActivity(), null, null));
    	mELv.setGroupIndicator(null);
        return view;
    }

	public void updateMedaiData(List<String> parent,
			Map<String, List<mediaiteminfo>> map,List<mediaiteminfo> musicInfos,List<mediaiteminfo> videoInfos) {
		mAdapter.updateMedaiData(parent, map, musicInfos, videoInfos);
	}
    
    


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
      
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("AllMediaFragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("AllMediaFragment");
    }
}
