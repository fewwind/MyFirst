package com.example.fewwind.myfirst.view;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.fewwind.myfirst.R;

/**
 * Created by fewwind on 2015/12/31.
 */
public class ModeIncarVIew extends FrameLayout {
    private Toast mToast;
    View space;

    IModeClickListener onclickPlayListener;
    public interface IModeClickListener{
        void onClickPlay();
    }

    public void setOnclickPlayListener(IModeClickListener listener){
        this.onclickPlayListener  = listener;
    }

    public ModeIncarVIew(Context context) {
        this(context, null);
    }

    public ModeIncarVIew(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ModeIncarVIew(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);

        findViewById(R.id.iv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        findViewById(R.id.mid).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String runningActivityName = getRunningActivityName(context);
                Log.e("tag", "onClick: ----------" + runningActivityName + "+++" + onclickPlayListener);
                if (onclickPlayListener!=null)
                onclickPlayListener.onClickPlay();

//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//                alertDialog.show();

                Dialog alertDialog = new AlertDialog.Builder(context).
                        setTitle("确定删除？").
                        setMessage("您确定删除该条信息吗？").
                        setIcon(R.drawable.ic_launcher).
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        }).
                        setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        }).
                        setNeutralButton("查看详情", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        }).
                        create();
                alertDialog.show();
            }
        });
    }

    public String getRunningActivityName(Context context){
        ActivityManager activityManager=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        Log.v("tag", "当前activity---"+runningActivity);
        return runningActivity;
    }
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.modle_incar, this);
        Log.v("tag", "行车模式初始化"+context.getClass().getSimpleName());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("tag", "Model_--onTouchEvent: " + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

    @Override
         public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.e("tag", "Incar__mode--InterceptTouch: " + super.onInterceptTouchEvent(ev));
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.v("tag", "当前activity---onWindowFocusChanged" );
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        Log.v("tag", "当前activity---onWindowVisibilityChanged");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.v("tag", "当前activity---onKeyDown" );
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.v("tag", "当前activity---onDetachedFromWindow");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.v("tag", "当前activity---onSizeChanged");
    }
}
