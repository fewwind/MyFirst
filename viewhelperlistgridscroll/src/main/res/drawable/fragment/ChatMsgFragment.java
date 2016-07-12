package drawable.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.incarmedia.R;
import com.incarmedia.adapter.CommonAdapter;
import com.incarmedia.adapter.ViewHolder;
import com.incarmedia.bean.ChatMsgBean;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChatMsgFragment extends Fragment {

	private ListView mLv;
	private CommonAdapter<ChatMsgBean> mAdapter;
	
	private List<Integer> mIcons;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_chat_msg,container, false);
		mLv = (ListView) view.findViewById(R.id.id_lv_chat_msg);
		mLv.setAdapter(mAdapter = new CommonAdapter<ChatMsgBean>(getActivity(), new ArrayList<ChatMsgBean>(), R.layout.item_chat_msg) {

			@Override
			public void convert(ViewHolder holder, ChatMsgBean t) {
				if (t!=null) {
					if (t.getType().equals("i")) {
						holder.setText(R.id.id_chat_msg_nick, t.getNick()+"  进入房间");
					} else {
						holder.setText(R.id.id_chat_msg_nick, t.getNick()+"  退出房间");
						
					}
					
					
					SimpleDateFormat df = new SimpleDateFormat("HH:mm");
					String dateTime = df.format(t.getTime()*1000L);
					holder.setText(R.id.id_chat_msg_time,dateTime);
					if (holder.getPosition()%2==0) {
						
						holder.setImageResource(R.id.id_chat_msg_head, mIcons.get(3));
					} else {
						holder.setImageResource(R.id.id_chat_msg_head, mIcons.get(1));
					}
				}
			}
		}) ;
		
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mIcons = new ArrayList<Integer>();
		for (int i = 1; i <5 ; i++) {
			mIcons.add(R.drawable.icon_head1);
			mIcons.add(R.drawable.icon_head2);
			mIcons.add(R.drawable.icon_head3);
			mIcons.add(R.drawable.icon_head4);
		}
		
		
	}

	public static ChatMsgFragment newInstance() {
		ChatMsgFragment fragment = null;
		if (fragment == null) {
			fragment = new ChatMsgFragment();
		}

		return fragment;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("ChatMsgFrangment");
	}
	
	public void updateDatas(List<ChatMsgBean> datas){
		mAdapter.updateDatas(datas);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("ChatMsgFrangment");
	}
}
