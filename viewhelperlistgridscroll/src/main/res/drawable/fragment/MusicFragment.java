package drawable.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.incarmedia.R;
import com.incarmedia.activity.LocalMediaAdapter;
import com.incarmedia.incommon.incar.ChannelManager;
import com.incarmedia.incommon.incar.PluginManager;
import com.incarmedia.incommon.webapi.mediaiteminfo;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

;

public class MusicFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TYPE = "type";

    /**
     * type: 1 代表音乐fragment  2，代表视频fragment，这两个共用一个fragment，根据类别区分
     */
    private int type;


    private GridView mGv;
    LocalMediaAdapter mAdapter;
    
    List<mediaiteminfo> loadMusicList = new ArrayList<mediaiteminfo>();

    public static MusicFragment newInstance(int type) {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_local_music, container, false);
    	mGv = (GridView) view.findViewById(R.id.id_gv_media);
    	mGv.setAdapter(mAdapter = new LocalMediaAdapter(getActivity(), loadMusicList));
    	
    	
    	mGv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				mediaiteminfo info = loadMusicList.get(position);
				if (info.path.endsWith(".mp3")
						|| info.path.endsWith(".wma")) {
					PluginManager
							.startPlugin(getActivity(),
                                    ChannelManager.NativeChannels.get(-1),
                                    position);

				} else {
					PluginManager
							.startPlugin(getActivity(),
									ChannelManager.NativeChannels.get(-2),
									position);

				}
				
			}
		});
    	
        return view;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
      
    }
    
	
	public void updateMediaInfo(List<mediaiteminfo> infos){
		mAdapter.updateMediaInfo(infos);
	}

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(type==1)
            MobclickAgent.onPageEnd("MusicFragment");
        else
            MobclickAgent.onPageEnd("VideoFragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
        }
        if(type==1)
            MobclickAgent.onPageStart("MusicFragment");
        else
            MobclickAgent.onPageStart("VideoFragment");
    }
}
