package com.example.fewwind.myfirst.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.fewwind.myfirst.AppService;
import com.example.fewwind.myfirst.R;

public abstract class BaseActivity extends AppCompatActivity  {
    private AppService inCarService;

    protected DrawerLayout mDrawerLayout = null;
   public ModeIncarVIew incarVIew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);
        Log.d("tag", "基类-----onCreate: ");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        incarVIew = (ModeIncarVIew) findViewById(R.id.id_incar_mode);
        incarVIew.setOnclickPlayListener(iModeClickListener);


    }


    public void bindService(){
//        incarVIew = (ModeIncarVIew) findViewById(R.id.id_incar_mode);
//        incarVIew.getRunningActivityName(getApplicationContext());
        bindService(new Intent(this, AppService.class), inCarServiceConn, Context.BIND_AUTO_CREATE);

    }


    ModeIncarVIew.IModeClickListener iModeClickListener = new ModeIncarVIew.IModeClickListener() {
        @Override
        public void onClickPlay() {
            Test();
        }
    };
    public  View mAnchor;
    public void addView (Context context,boolean show){
        mAnchor = ((Activity) context).findViewById(android.R.id.content);


        final ModeIncarVIew ll = new ModeIncarVIew(context);
//        final LinearLayout ll = new LinearLayout(context);
        Log.d("tag", "getSimpleName(): "+mAnchor.getClass().getSimpleName());

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ((ViewGroup) mAnchor).addView(ll, ((ViewGroup) mAnchor).getChildCount(), lp);

//        if (mAnchor.getClass().getSimpleName().equals("FrameLayout"))
//        {
//            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
//                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            ((ViewGroup) mAnchor).addView(ll, ((ViewGroup) mAnchor).getChildCount(), lp);
//
//        } else
//        {
//            FrameLayout frameLayout = new FrameLayout(context);
//            ViewGroup parent = (ViewGroup) mAnchor.getParent();
//            parent.removeView(mAnchor);
//            parent.addView(frameLayout, mAnchor.getLayoutParams());
//            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
//                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            frameLayout.addView(mAnchor, lp);
//
//            frameLayout.addView(ll);
//        }

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ll == null) return;
                ViewGroup parent = (ViewGroup) ll.getParent();
                if (parent instanceof RelativeLayout || parent instanceof FrameLayout)
                {
                    parent.removeView(ll);
                } else
                {
                    parent.removeView(ll);
                    View origin = parent.getChildAt(0);
                    ViewGroup graParent = (ViewGroup) parent.getParent();
                    graParent.removeView(parent);
                    graParent.addView(origin, parent.getLayoutParams());
                }

            }
        });
    }



    public void Test(){
        Log.e("tag", "Baseactivity--------onClickPlay: " );
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.d("tag", "Base----------dispatchTouchEvent:----- ");
//        return super.dispatchTouchEvent(ev);
//    }




    /*  public void addView (Context context,boolean show){
         mAnchor = ((Activity) context).findViewById(android.R.id.content);


        LinearLayout ll = new LinearLayout(context);
        ll.setBackgroundColor(Color.RED);
        if (mAnchor.getClass().getSimpleName().equals("FrameLayout"))
        {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) mAnchor).addView(ll, ((ViewGroup) mAnchor).getChildCount(), lp);

        } else
        {
            FrameLayout frameLayout = new FrameLayout(context);
            ViewGroup parent = (ViewGroup) mAnchor.getParent();
            parent.removeView(mAnchor);
            parent.addView(frameLayout, mAnchor.getLayoutParams());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            frameLayout.addView(mAnchor, lp);

            frameLayout.addView(ll);
        }

    }*/

    private ServiceConnection inCarServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            inCarService = ((AppService.IncarTimeBinder)service).getService();
            inCarService.setInCarTimeListener(incarTimeDrawerListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            inCarService = null;
        }
    };

    private AppService.ItimeDrawer incarTimeDrawerListener = new AppService.ItimeDrawer() {
        @Override
        public void openDrawer() {
            BaseActivity.this.listener.openDrawerListener();
        }
    };
    IopenDrawerListener listener;
    public interface IopenDrawerListener{
        void openDrawerListener();
    }

    public void setIncarTimeDrawerListener(IopenDrawerListener drawerListener){
        this.listener = drawerListener;
    }

    @Override
    protected void onDestroy() {

        Log.d("tag", "基类-----onDestroy ");
        super.onDestroy();
    }


    /**
     * fragment的view消失后回调
     */
    public void unbindService() {
        unbindService(inCarServiceConn);
    }

    public void drawerStatusListener(){
        DrawerLayout mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mainDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

//                Log.e("tag", "基类  onDrawerClosed: " +sd1+"\n"+sd2);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.e("tag", "onDrawerStateChanged: " );
            }
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }
}
