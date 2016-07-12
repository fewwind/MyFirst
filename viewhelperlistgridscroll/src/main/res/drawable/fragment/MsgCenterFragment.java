package drawable.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.incarmedia.R;
import com.umeng.analytics.MobclickAgent;

public class MsgCenterFragment extends Fragment implements OnClickListener {

	private View view;
	private Button findEnable;
	private Button findUnable;
	private Button findEnabled;
	private Button findUnabled;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_msg, container,false);

		init();
		return view;
	}

	public static MsgCenterFragment newInstance() {
		MsgCenterFragment fragment = new MsgCenterFragment();
		return fragment;
	}

	private void init() {
		// TODO Auto-generated method stub
		findEnable = (Button) view.findViewById(R.id.id_btn_set_msg_open);
		findUnable = (Button) view.findViewById(R.id.id_btn_set_msg_close);
		findEnable.setBackgroundResource(R.drawable.button_shape_select);
		findUnable.setBackgroundResource(R.drawable.button_shape);
		findEnable.setOnClickListener(this);
		findUnable.setOnClickListener(this);
		findEnabled = (Button) view.findViewById(R.id.id_btn_set_msg_tan_open);
		findUnabled = (Button) view.findViewById(R.id.id_btn_set_msg_tan);
		findEnabled.setBackgroundResource(R.drawable.button_shape_select);
		findUnabled.setBackgroundResource(R.drawable.button_shape);
		findEnabled.setOnClickListener(this);
		findUnabled.setOnClickListener(this);

	}

	public void changeColor() {
		findEnable.setBackgroundResource(R.drawable.button_shape);
		findUnable.setBackgroundResource(R.drawable.button_shape);
	}
	public void changeColors() {
		findEnabled.setBackgroundResource(R.drawable.button_shape);
		findUnabled.setBackgroundResource(R.drawable.button_shape);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.id_btn_set_msg_close:
			changeColor();
			findUnable.setBackgroundResource(R.drawable.button_shape_select);
			Toast.makeText(getActivity(), "关了", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_btn_set_msg_open:
			changeColor();
			findEnable.setBackgroundResource(R.drawable.button_shape_select);
			Toast.makeText(getActivity(), "开啦", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_btn_set_msg_tan:
			changeColors();
			findUnabled.setBackgroundResource(R.drawable.button_shape_select);
			Toast.makeText(getActivity(), "关了", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_btn_set_msg_tan_open:
			changeColors();
			findEnabled.setBackgroundResource(R.drawable.button_shape_select);
			Toast.makeText(getActivity(), "开啦", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MsgCenterFragment");
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MsgCenterFragment");
	}
}
