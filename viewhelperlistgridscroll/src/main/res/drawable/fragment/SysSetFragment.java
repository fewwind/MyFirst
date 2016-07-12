package drawable.fragment;

import android.app.Fragment;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.incarmedia.R;
import com.incarmedia.fragment.convenientbanner.myviewpager.CBViewHolderCreator;
import com.incarmedia.fragment.convenientbanner.myviewpager.ConvenientBanner;
import com.incarmedia.fragment.convenientbanner.salvage.NetworkImageHolderView;
import com.incarmedia.incommon.incar.AppManager;
import com.incarmedia.incommon.incar.FileManager;
import com.incarmedia.incommon.incar.common;
import com.incarmedia.incommon.ui.MyTextView;
import com.incarmedia.incommon.util.Constant;
import com.incarmedia.util.VersionUtil;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SysSetFragment extends Fragment implements OnClickListener {

	Button cache;
	Button lowTune;
	Button midTune;
	Button highTune;
	Button aboutUs;
	Button help;
	Button mobileClient;
	TextView toneQualityTxt;
	LinearLayout toneQuality;
	View view;
	private View popupWindowView;
	PopupWindow popupWindow;
	File mp3Cache;
	File ablumLrc;
	long lrcSizes;
	long mp3Sizes;
	private Button update;
	private TextView banben;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		view = inflater.inflate(R.layout.fragment_sys, container,false);
		init();
		ablumLrc = new File(FileManager.AblumFilesDir);
		mp3Cache = new File(FileManager.MediaFilesDir);

		try {
			if (ablumLrc.exists()) {
				lrcSizes = FileManager.getFileSizes(ablumLrc);

			} else{  lrcSizes =0;}
			if (mp3Cache.exists()) {
				mp3Sizes = FileManager.getFileSizes(mp3Cache);

			} else {mp3Sizes = 0;}
			if ((lrcSizes + mp3Sizes) > 0) {

				cache.setText("缓存："
						+ FileManager.FormetFileSize(lrcSizes + mp3Sizes)
						+ "  点击清除");
			} else {
				cache.setText("当前缓存：0k");

			}
			highTune.setBackgroundResource(R.drawable.button_shape_select);

			aboutUs.setText("关于我们");
		} catch (Exception e) {
			e.printStackTrace();
		}
		view.setOnClickListener(this);
		return view;
	}

	public static SysSetFragment newInstance() {
		SysSetFragment fragment = new SysSetFragment();
		return fragment;
	}

	private void init() {
		cache = (Button) view.findViewById(R.id.cache);
		lowTune = (Button) view.findViewById(R.id.lowTune);
		midTune = (Button) view.findViewById(R.id.midTune);
		highTune = (Button) view.findViewById(R.id.highTune);
		aboutUs = (Button) view.findViewById(R.id.aboutUs);
		help = (Button) view.findViewById(R.id.help);
		mobileClient = (Button) view.findViewById(R.id.mobileClient);
		update = (Button) view.findViewById(R.id.update);
		// 隐藏音质选择 ，以后再添加
		toneQualityTxt = (TextView) view.findViewById(R.id.toneQualityTxt);
		toneQuality = (LinearLayout) view.findViewById(R.id.toneQuality);

		toneQualityTxt.setVisibility(View.GONE);
		toneQuality.setVisibility(View.GONE);

		cache.setOnClickListener(this);
		lowTune.setOnClickListener(this);
		midTune.setOnClickListener(this);
		highTune.setOnClickListener(this);
		aboutUs.setOnClickListener(this);
		help.setOnClickListener(this);
		mobileClient.setOnClickListener(this);
		update.setOnClickListener(this);
	}

	public void changeColor() {
		lowTune.setBackgroundResource(R.drawable.button_shape);
		midTune.setBackgroundResource(R.drawable.button_shape);
		highTune.setBackgroundResource(R.drawable.button_shape);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cache:

			if (FileManager.isDeleteFile()) {
				FileManager.deleteFile(ablumLrc);
				FileManager.deleteFile(mp3Cache);
					cache.setText("当前缓存：0k");
					Toast.makeText(getActivity(), "清除缓存成功！", Toast.LENGTH_SHORT).show();

			} else {
				cache.setText("当前缓存：0k");
				Toast.makeText(getActivity(), "缓存清完了，先去听歌吧！", Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.lowTune:
			Toast.makeText(getActivity(), "您点击了低", Toast.LENGTH_SHORT).show();
			changeColor();
			lowTune.setBackgroundResource(R.drawable.button_shape_select);
			break;
		case R.id.midTune:
			Toast.makeText(getActivity(), "您点击了中", Toast.LENGTH_SHORT).show();
			changeColor();
			midTune.setBackgroundResource(R.drawable.button_shape_select);
			break;
		case R.id.highTune:
			Toast.makeText(getActivity(), "您点击了高", Toast.LENGTH_SHORT).show();
			changeColor();
			highTune.setBackgroundResource(R.drawable.button_shape_select);
			break;
		case R.id.aboutUs:
			initabout();
			try {

				String versionType = Constant.NetUrlBase.contains("dev")?"测试版":"正式版";
				banben.setText("即时行乐  "
						+ VersionUtil.getVersionName(getActivity())+versionType);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case R.id.help:
			initHelp();

			break;
		case R.id.mobileClient:

			initPop();
			break;
		case R.id.update:
			updateApp();
			break;
		case R.id.close_pager:
			popupWindow.dismiss();
			break;
		}
	}

	boolean updateApp;

	/**
	 * 检查更新
	 */
	private void updateApp() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// //检查APP更新

				AppManager.updateApp();
				updateApp = AppManager.isUpdateApp();
				if (!updateApp) {
					common.shownote("未发现新版本");
				}
			}
		}).start();

	}



	public void initPop() {
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
		popupWindowView = inflater.inflate(R.layout.popupwindow, null);
		popupWindow = new PopupWindow(
				popupWindowView,
				view.getWidth(),
				getActivity().getResources().getDisplayMetrics().heightPixels * 15 / 16,
				true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.popupAnimation);

		popupWindow.showAtLocation(view, Gravity.RIGHT| Gravity.BOTTOM, 0, 0);

		TextView back = (TextView) popupWindowView.findViewById(R.id.back_Pop);
		ImageView code2 = (ImageView) popupWindowView.findViewById(R.id.code2);
		if (Constant.NetUrlBase.contains("dev")){
			code2.setBackgroundResource(R.drawable.incar_debug);
		} else{
			code2.setBackgroundResource(R.drawable.incar);
		}


		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (popupWindow != null) {
					popupWindow.dismiss();
				}
			}
		});

	}

	public void initabout() {
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
		popupWindowView = inflater.inflate(R.layout.about, null);
		banben = (TextView) popupWindowView.findViewById(R.id.banben);
		popupWindow = new PopupWindow(
				popupWindowView,
				view.getWidth(),
				getActivity().getResources().getDisplayMetrics().heightPixels * 15 / 16,
				true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.popupAnimation);

		popupWindow.showAtLocation(view, Gravity.RIGHT| Gravity.BOTTOM, 0, 0);

		
		MyTextView close = (MyTextView) popupWindowView.findViewById(R.id.close_pop);
		close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (popupWindow != null) {
					popupWindow.dismiss();
				}
			}
		});
	}

	private ConvenientBanner convenientBanner;;
	private List<ImageView> listViews;
	private LinearLayout pointGroup;
	private ImageView close_pager;
	public void initHelp() {	
		listViews = new ArrayList<ImageView>();
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
		popupWindowView = inflater.inflate(R.layout.zhidao, null);
		popupWindow = new PopupWindow(popupWindowView, getActivity()
				.getResources().getDisplayMetrics().widthPixels, getActivity()
				.getResources().getDisplayMetrics().heightPixels, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAtLocation(view, Gravity.RIGHT, 0, 0);
		convenientBanner = (ConvenientBanner) popupWindowView.findViewById(R.id.id_vp_help);
		close_pager =(ImageView) popupWindowView.findViewById(R.id.close_pager);
		close_pager.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				convenientBanner.stopTurning();
				popupWindow.dismiss();
			}
		});
		List<String> data = new ArrayList<String>();
		for (int i = 1; i < 7; i++) {
			data.add("http://mp3.huayuansoft.com/img/"+i+"_h.jpg");
		}
		convenientBanner.setPages(
				new CBViewHolderCreator<NetworkImageHolderView>() {
					@Override
					public NetworkImageHolderView createHolder() {
						return new NetworkImageHolderView();
					}
				}, data).setPageIndicator(new int[]{R.drawable.point_nomal,
				 R.drawable.point_bg})
//				 设置指示器的方向
				 .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//				 设置翻页的效果，不需要翻页效果可用不设
						.setPageTransformer(ConvenientBanner.Transformer.CubeOutTransformer);
;
//		mPager.setAdapter(new MyPagerAdapter(listViews));
//		mPager.setCurrentItem(Integer.MAX_VALUE/2-Integer.MAX_VALUE/2%listViews.size());
//		mPager.setOffscreenPageLimit(1);
//		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
//		popupWindowView.findViewById(R.id.close_pager).setOnClickListener(this);
		convenientBanner.startTurning(8000);
	}

	public class MyPagerAdapter extends PagerAdapter {
		public List<ImageView> mListViews;

		public MyPagerAdapter(List<ImageView> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
			object = null;
		}


		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(mListViews.get(position%mListViews.size()));
			return mListViews.get(position%mListViews.size());
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

	}

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		private int lastPosition;
		@Override
		public void onPageSelected(int position) {
			position = position%listViews.size();
			
			
			//改变指示点的状态
			//把当前点enbale 为true 
			pointGroup.getChildAt(position).setEnabled(true);
			
			//把上一个点设为false
			pointGroup.getChildAt(lastPosition).setEnabled(false);
			lastPosition = position;
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("SysSetFragment");
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SysSetFragment");
	}
}
