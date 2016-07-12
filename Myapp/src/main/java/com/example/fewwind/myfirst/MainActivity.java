package com.example.fewwind.myfirst;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.internal.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;

import com.example.fewwind.myfirst.Util.HttpUtils;
import com.example.fewwind.myfirst.serializable.Book;
import com.example.fewwind.myfirst.serializable.PerActivity;
import com.example.fewwind.myfirst.serializable.Person;
import com.example.fewwind.myfirst.serializable.SeriActivity;
import com.example.fewwind.myfirst.view.BaseActivity;
import com.example.fewwind.myfirst.view.CircleLoadingView;
import com.example.fewwind.myfirst.view.ModeIncarVIew;
import com.example.fewwind.myfirst.view.MyViewGroup;
import com.example.fewwind.myfirst.view.SimpleView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends BaseActivity implements ModeIncarVIew.IModeClickListener,BaseActivity.IopenDrawerListener{
    private DrawerLayout mainDrawerLayout = null;

    private FloatingActionButton fabBtn;
    private boolean open = true;
    ImageView iv;
    MyViewGroup group;
    SimpleView my_view;
    List<String> mLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setIncarTimeDrawerListener(this);
        iv = (ImageView) findViewById(R.id.iv);
        mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        my_view = (SimpleView) findViewById(R.id.my_view);
        for (int i = 0; i <3 ; i++) {
            mLists.add("PopList---为啥这么么窄呢"+i);
        }
        listPop = new ListPopupWindow(this);
        listPop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mLists));
        listPop.setWidth(500);
        listPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        listPop.setAnchorView(my_view);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
        listPop.setModal(true);//设置是否是模式

        fabBtn = (FloatingActionButton) findViewById(R.id.id_fab);
        group = (MyViewGroup) findViewById(R.id.my_group);
//        group.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.e("tag", "MyViewGroup-----TouchListener ");
//                return false;
//            }
//        });
//        group.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("tag", "MyViewGroup-----onClick: ");
//            }
//        });
//        my_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listPop.show();
//                Log.e("tag", "View---onClick: ");
//            }
//        });

        findViewById(R.id.id_btn_shape).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                Intent intent = new Intent(MainActivity.this, AreaClick.class);
                startActivity(intent);
                Bitmap bitmap = rotateBitmap(MainActivity.this, BitmapFactory.decodeResource(getResources(), R.drawable.icon_gift), 20);
                iv.setImageBitmap(bitmap);
                Log.e("tag", "onClick: ");
*/

//                mainDrawerLayout.openDrawer(Gravity.LEFT);
                 DrawerLayout drawerLayout  = ((DrawerLayout)(findViewById(R.id.drawer_layout)));
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT))
                drawerLayout.openDrawer(Gravity.LEFT);
                incarVIew.getRunningActivityName(MainActivity.this);

            }
        });


    }


    ListPopupWindow listPop;

    public void show() {
        MaterialDialog mMaterialDialog = new MaterialDialog(this);

        mMaterialDialog.show();


    }


    public void Serializable(View view) {

        Intent intent = new Intent(this, SeriActivity.class);
        intent.putExtra("ser", new Person("gulong", "88"));
        startActivity(intent);
        incarVIew.setOnclickPlayListener(this);
        incarVIew.getRunningActivityName(MainActivity.this);
//		Intent intent = new Intent(Intent.ACTION_VIEW);
//		String packageName = "com.incarmedia";
//		String className = "com.incarmedia.main.SplashActivity";
//		intent.setClassName(packageName, className);
//		intent.putExtra("incat_radio", "incat_radio");
//		startActivity(intent);
//        finish();

//        mDrawerLayout.openDrawer(Gravity.LEFT);

    }
    /**
     * 用自定义的dialog展示图片信息
     */
    public void showPicture(int layoutId) {
        Dialog dialog = new Dialog(this,R.style.dialog_no_frame);
        // setContentView可以设置为一个View也可以简单地指定资源ID
        // LayoutInflater
        // li=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        // View v=li.inflate(R.layout.dialog_layout, null);
        // dialog.setContentView(v);
        dialog.setContentView(layoutId);
        CircleLoadingView view = (CircleLoadingView) dialog.findViewById(R.id.id_loading);
        view.setProgress(60);
        dialog.show();
    }
    public void Parcelable(View view) {
        Intent intent = new Intent(this, PerActivity.class);
        intent.putExtra("pac", new Book("欢乐英雄", "古龙", 2015));
//        startActivity(intent);

//        FragmentDialogCommon dialogCommon = FragmentDialogCommon.newInstance(R.layout.fragment_edit_name);dialogCommon.show(getFragmentManager(),"tag");

//        showPicture(R.layout.fragment_edit_name);
/*
        LayoutInflater inflater = LayoutInflater.from(this);
        View alter = inflater.inflate(R.layout.fragment_edit_name, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(alter);
        builder.create();
        builder.show();*/






/*        PopupMenu menu = new PopupMenu(MainActivity.this,view);

        menu.getMenuInflater().inflate(R.menu.pop_menu, menu.getMenu());

        makePopForceShowIcon(menu);
        menu.show();*/

        String start = "12:20";
        String end = "13:20";
        int a = start.compareTo(end);
        Log.e("tag", "Parcelable: " + a);
//    super.addView(MainActivity.this, true);

        Matrix matrix = new Matrix();
        iv.setScaleType(ImageView.ScaleType.MATRIX); //required
        matrix.postRotate((float) 30, iv.getWidth() / 2, iv.getHeight() / 2);
        iv.setImageMatrix(matrix);
        String NetUrlBase = "http://dev.in-carmedia.com/";
        final String doLoginUrl = NetUrlBase + "/system/dologin";

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils.postReq(doLoginUrl, "deviceid=1231310");
            }
        }).start();

    }

    private Bitmap rotateBitmap(Context context,Bitmap srcBitmap,float degree) {

        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap,0,0,srcBitmap.getWidth(),srcBitmap.getHeight(),matrix,true);


        return  bitmap;
    }

    /**
     * 设置悬浮按钮显示和隐藏的方法
     */
    private void fabToggle() {


        if (open) {
            TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                    -1.0f);
            mHiddenAction.setDuration(1500);
            fabBtn.startAnimation(mHiddenAction);
            final ScaleAnimation animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(200);//设置动画持续时间
            fabBtn.setAnimation(animation);


            fabBtn.setVisibility(View.GONE);
            open = false;
        } else {
            TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            mShowAction.setDuration(1500);
            // fabBtn.startAnimation(mShowAction);

            final ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(200);//设置动画持续时间
            fabBtn.setAnimation(animation);
            fabBtn.setVisibility(View.VISIBLE);
            open = true;
        }
    }


    //使用反射让popupMenu 显示菜单icon
    private void makePopForceShowIcon(PopupMenu popupMenu) {
        //d,a[f
        try {
            Field mFieldPopup = popupMenu.getClass().getDeclaredField("mPopup");
            mFieldPopup.setAccessible(true);
            MenuPopupHelper mPopup = (MenuPopupHelper) mFieldPopup.get(popupMenu);
            mPopup.setForceShowIcon(true);
        } catch (Exception e) {

        }
    }

    /**
     * material dialog
     */
    public void show0() {

        final MaterialDialog mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle("MaterialDialog");
        mMaterialDialog.setMessage("Hello world!");
        mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });
        mMaterialDialog.setNegativeButton("CANCEL", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });

        mMaterialDialog.show();
    }

    @Override
    public void onClickPlay() {
        Log.e("tag", "onClickPlay: ");
    }

    @Override
    public void Test() {
        super.Test();
    }

    @Override
    public void openDrawerListener() {
//        Log.e("tag", "MainActivity: " );
//        mainDrawerLayout.openDrawer(Gravity.LEFT);

    }
    @Override
    protected void onStart() {
        super.onStart();
//        addView(MainActivity.this, true);
//        mDrawerLayout.openDrawer(Gravity.LEFT);
        bindService();
        drawerStatusListener();
    }


    @Override
    protected void onStop() {
        super.onStop();
        unbindService();
        Log.e("tag", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("tag", "onDestroy: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("tag", "onPause: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("tag", "onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("tag", "onResume: ");
    }


}
