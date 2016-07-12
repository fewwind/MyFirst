package drawable.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.incarmedia.R;
import com.incarmedia.incommon.incar.common;
import com.incarmedia.incommon.util.Constant;
import com.incarmedia.incommon.webapi.HttpCallbackListener;
import com.incarmedia.incommon.webapi.HttpUtil;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserCenterFragment extends Fragment implements OnClickListener {

	private View view;

	private EditText userId;
	private EditText phoneNum;
	private EditText emailAdd;
//	private Button findUnable;
//	private Button findEnable;
	private EditText carModle;

	private Button saveBtn;

	private SharedPreferences sp;
	private Editor edit;
	private Handler mHandler;

	private ProgressDialog dialog ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_user, container, false);
		sp = getActivity().getSharedPreferences(Constant.deviceId,
				Context.MODE_PRIVATE);

		edit = sp.edit();
		mHandler = new Handler();
		init();
//		findEnable.setBackgroundResource(R.drawable.button_shape_select);
		return view;
	}

	public static UserCenterFragment newInstance() {
		UserCenterFragment fragment = new UserCenterFragment();
		return fragment;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
// 周围车可见空能暂时屏蔽
//		case R.id.findUnable:
//			changeColor();
//			findUnable.setBackgroundResource(R.drawable.button_shape_select);
//			Toast.makeText(getActivity(), "关了", Toast.LENGTH_SHORT).show();
//			break;
//		case R.id.findEnable:
//			changeColor();
//			findEnable.setBackgroundResource(R.drawable.button_shape_select);
//			Toast.makeText(getActivity(), "开啦", Toast.LENGTH_SHORT).show();
//			break;
		case R.id.carModle:
			break;

		case R.id.save:
			saveUserInfo();

			break;
		}
	}
	/**
	 * status :
	 * statuscode  0  提交成功   1 提交或获取失败  2 获取用户信息成功
	 * msg 提示信息
	 * 
	 */
	private void saveUserInfo() {
		
		String phone = phoneNum.getText().toString();
		String nickName = userId.getText().toString();
		String car = carModle.getText().toString();
		String email = emailAdd.getText().toString();
		if (TextUtils.isEmpty(nickName)||nickName.trim().length()<=0) {
			common.shownote("用户昵称不能为空");
			
			return;
		}

		if (!isMobileNO(phone)) {
			common.shownote("电话号码格式不对");
			return;
		}

		if (!isEmail(email)) {
			common.shownote("邮件格式不对");
			return;
		}
		dialog = ProgressDialog.show(getActivity(), null, "正在保存......");
		
		List<BasicNameValuePair> param = new LinkedList<BasicNameValuePair>();
		param.add(new BasicNameValuePair("deviceid",  Constant.deviceId));

		param.add(new BasicNameValuePair("nick", nickName));
		param.add(new BasicNameValuePair("email", email));

		param.add(new BasicNameValuePair("car", car));
		param.add(new BasicNameValuePair("phone", phone));

		edit.putString("nick", nickName);
		edit.putString("phone", phone);
		edit.putString("email", email);
		edit.putString("car", car);

		HttpUtil.sendHttpRequest(Constant.userInfoUrl, param, new HttpCallbackListener() {

			@Override
			public void onNetError() {
				common.shownote("保存失败，请检查网络");
			}

			@Override
			public void onFinish(final String response) {


				mHandler.post(new Runnable() {
					@Override
					public void run() {
						JSONObject json;
						try {
							json = new JSONObject(response);
							if (json.getJSONObject("status").getString("statuscode").equals("0")) {
								if (dialog != null) {
									dialog.dismiss();
								}
								common.shownote("保存成功");
								edit.commit();
							} else {
								if (dialog != null) {
									dialog.dismiss();
								}
								common.shownote("保存失败");

							}

							dialog.dismiss();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});



			}

			@Override
			public void onError(Exception e) {
				common.shownote("保存失败");
			}
		});
	}

//	public void changeColor() {
//		findEnable.setBackgroundResource(R.drawable.button_shape);
//		findUnable.setBackgroundResource(R.drawable.button_shape);
//	}

	private void init() {
		userId = (EditText) view.findViewById(R.id.userId);
		phoneNum = (EditText) view.findViewById(R.id.phoneNum);
		emailAdd = (EditText) view.findViewById(R.id.emailAdd);
//		findUnable = (Button) view.findViewById(R.id.findUnable);
//		findEnable = (Button) view.findViewById(R.id.findEnable);
		carModle = (EditText) view.findViewById(R.id.carModle);
		saveBtn = (Button) view.findViewById(R.id.save);
//		findUnable.setOnClickListener(this);
//		findEnable.setOnClickListener(this);

//		carModle.setOnClickListener(this);
		saveBtn.setOnClickListener(this);
//
//		if (sp.getInt("appCount", 0) <=1
//				|| TextUtils.isEmpty(sp.getString("nick", ""))) {

			if (!sp.contains("nick")){

			dialog = ProgressDialog.show(getActivity(), null, "正在加载数据");
			dialog.setCanceledOnTouchOutside(false);
			dialog.setCancelable(true);
			List<BasicNameValuePair> param = new LinkedList<BasicNameValuePair>();
			param.add(new BasicNameValuePair("deviceid", Constant.deviceId));
			
			HttpUtil.sendHttpRequest(Constant.userInfoUrl, param,
					new HttpCallbackListener() {

						@Override
						public void onNetError() {
							// TODO Auto-generated method stub
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									if (dialog!=null) {
										dialog.dismiss();
									}
								}
							});

							common.shownote("请求数据异常");
						}

						@Override
						public void onFinish(final String response) {
//							LogUtils.getIntance().e("第几次登录"+sp.getInt("appCount", 0) +response);
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									JSONObject json;
									JSONObject status;
									JSONObject data;
									try {
										json = new JSONObject(response);
										status = json.getJSONObject("status");
										data = json.getJSONObject("data");
										if (status.getString("statuscode").equals("2")) {

											if (data != null) {
												userId.setText(data
														.getString("nick"));
												phoneNum.setText(data
														.getString("phone"));
												emailAdd.setText(data
														.getString("email"));
												carModle.setText(data
														.getString("car"));


												edit.putString("nick", data
														.getString("nick"));
												edit.putString("phone", data
														.getString("phone"));
												edit.putString("email", data
														.getString("email"));
												edit.putString("car", data
														.getString("car"));
												edit.commit();
												if (dialog!=null) {
													dialog.dismiss();
												}
											} else {

												if (dialog!=null) {
													dialog.dismiss();
												}
											}


										} else {

											if (dialog!=null) {
												dialog.dismiss();
											}
										}



									} catch (JSONException e) {
										e.printStackTrace();
										if (dialog!=null) {
											dialog.dismiss();
										}
									}
								}
							});


						}

						@Override
						public void onError(Exception e) {
							common.shownote("请求失败请重试");
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									if (dialog!=null) {
										dialog.dismiss();
									}
								}
							});

						}
					});

		} else {
			userId.setText(sp.getString("nick", ""));
			phoneNum.setText(sp.getString("phone", ""));
			emailAdd.setText(sp.getString("email", ""));
			carModle.setText(sp.getString("car", ""));
		}

	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {

		String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	// 判断格式是否为email
	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("UserCenterFragment");
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("UserCenterFragment");
	}
}
