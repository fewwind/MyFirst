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
import com.incarmedia.bean.ChatContactsBean;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class ChatContactsFragment extends Fragment {

	private ListView mLv;
	private CommonAdapter<ChatContactsBean> mAdapter;

	private List<Integer> mIcons;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_chat_contact, container,
				false);
		mLv = (ListView) view.findViewById(R.id.id_lv_chat_contact);
		mLv.setAdapter(mAdapter = new CommonAdapter<ChatContactsBean>(
				getActivity(), new ArrayList<ChatContactsBean>(),
				R.layout.item_chat_contacts) {

			@Override
			public void convert(ViewHolder holder, ChatContactsBean t) {
				if (t != null) {
					holder.setText(R.id.id_chat_contact_nick, t.getNick());
					
					if (holder.getPosition() % 2 == 0) {

						holder.setImageResource(R.id.id_chat_contact_head,
								mIcons.get(3));
					} else {
						holder.setImageResource(R.id.id_chat_contact_head,
								mIcons.get(1));
					}

				}
			}
		});
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mIcons = new ArrayList<Integer>();
		for (int i = 1; i < 5; i++) {
			mIcons.add(R.drawable.icon_head1);
			mIcons.add(R.drawable.icon_head2);
			mIcons.add(R.drawable.icon_head3);
			mIcons.add(R.drawable.icon_head4);
		}
		

	}

	public static ChatContactsFragment newInstance() {
		ChatContactsFragment fragment = null;
		if (fragment == null) {
			fragment = new ChatContactsFragment();
		}

		return fragment;
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("ChatContactsFragment");
	}

	public void updateDatas(List<ChatContactsBean> datas) {
		 mAdapter.updateClearDatas(datas);
		 mAdapter.notifyDataSetChanged();

//		mLv.setAdapter(mAdapter = new CommonAdapter<ChatContactsBean>(
//				getActivity(), datas, R.layout.item_chat_contacts) {
//
//			@Override
//			public void convert(ViewHolder holder, ChatContactsBean t) {
//				if (t != null) {
//					holder.setText(R.id.id_chat_contact_nick, t.getNick());
//
//					if (holder.getPosition() % 2 == 0) {
//
//						holder.setImageResource(R.id.id_chat_contact_head,
//								mIcons.get(3));
//					} else {
//						holder.setImageResource(R.id.id_chat_contact_head,
//								mIcons.get(1));
//					}
//
//				}
//			}
//		});
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("ChatContactsFragment");
	}
}
