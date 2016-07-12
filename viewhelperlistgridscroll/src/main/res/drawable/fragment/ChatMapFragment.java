package drawable.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.incarmedia.R;
import com.incarmedia.bean.ChatContactsBean;
import com.umeng.analytics.MobclickAgent;

import java.util.List;
import java.util.Random;

public class ChatMapFragment extends Fragment {

	FrameLayout llLayout;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_chat_map, container,
				false);
		llLayout = (FrameLayout) view.findViewById(R.id.id_ll_chat_map);
		return view;
	}

	public static ChatMapFragment newInstance() {
		ChatMapFragment fragment = null;
		if (fragment == null) {
			fragment = new ChatMapFragment();
		}

		return fragment;
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("ChatMapFrangment");
	}

	public void changeMap(final List<ChatContactsBean> datas) {
		llLayout.removeAllViews();
		for (int i = 0; i < datas.size(); i++) {
			ChatContactsBean bean = datas.get(i);

			TextView nickTv = new TextView(getActivity());
			nickTv.setGravity(Gravity.CENTER_VERTICAL);
			Drawable drawable = getResources().getDrawable(
					R.drawable.icon_chat_map_pos);
			// / 这一步必须要做,否则不会显示.
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			nickTv.setCompoundDrawables(drawable, null, null, null);
			nickTv.setCompoundDrawablePadding(10);
			nickTv.setText(bean.getNick());

			LayoutParams params = new LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			int x = new Random().nextInt(llLayout.getWidth() - 180);
			int y = new Random().nextInt(llLayout.getHeight() - 180);
			// params.setMargins(new
			// Random().nextInt(100)+Resources.getSystem().getDisplayMetrics().widthPixels-llLayout.getWidth(),
			// x, x, x);
			params.leftMargin = x;
			params.topMargin = y;
			llLayout.addView(nickTv, params);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("ChatMapFrangment");
	}
}
