package com.fewwind.mydesign.test;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fewwind.mydesign.R;
import com.fewwind.mydesign.test.view.MyScrollView;
import com.orhanobut.logger.Logger;

public class ZhiHu2Activity extends AppCompatActivity implements MyScrollView.OnScrollListener {

    private MyScrollView mScrollView;
//    随着页面滑动的详情
    private RelativeLayout mUserDetail;
//    固定在顶部的详情
    private RelativeLayout mTopUserDetail;

    private WebView mWebView;
    private LinearLayout mBotton;
    private Animation showAnim;
    private Animation dismissAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_hu2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("仿知乎");
        setSupportActionBar(toolbar);

  /*      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        initView();
    }

    private void initView() {
        mUserDetail = (RelativeLayout) findViewById(R.id.user_detail);
        mTopUserDetail = (RelativeLayout) findViewById(R.id.top_user_detail);
        mScrollView = (MyScrollView) findViewById(R.id.myscrollview);
        mScrollView.setOnScrollerListener(this);

        mBotton = (LinearLayout) findViewById(R.id.ll_bottom);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl("http://www.zhihu.com/question/28057213");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        //当布局中所有的View都测量完后回回调的方法，我们在这个方法中可以拿到View的宽和高
        //在这个方法中调用onScroll是为什么？
        //因为我们要在onScroll中获得mUserDetail距顶部的高度
        //只有在所有的View都测量完后我们才能拿到这个高度值，否则我们拿到的是0
        //所以在onGlobalLayout中调用一下onScroll方法，我们一定可以拿到mUserDetail这个View
        //距离屏幕顶部的距离，从而设置给我们的mTopUserDetail这个View，实现两个View的重合
        findViewById(R.id.container).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onScroll(mScrollView.getScrollY());
            }
        });

        showAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_show);
        dismissAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bottom_dismiss);
    }

    @Override
    public void onScroll(int scrollY) {


        //在最开始mUserDetail距离屏幕顶部是有一段距离的，而最开始scrollY=0，
        //所以在最开始的时候我们取两者的最大值就可以使两个View重合起来
        //因为我们是在所有的View都测量完毕后调用过onScroll方法的，
        //所以mUserDetail.getTop()得到的值是正确的值
        int userDetailView2Top = Math.max(scrollY, mUserDetail.getTop());
        Logger.i(userDetailView2Top+"===滑动  垂直距离==="+scrollY);
        //调用mTopUserDetail的layout方法，设置其在屏幕上的位置
        mTopUserDetail.layout(0, userDetailView2Top, mTopUserDetail.getWidth(), userDetailView2Top + mTopUserDetail.getHeight());
    }

    int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    @Override
    public void onUpScroll() {
//        Logger.i("up");
        if (mBotton.isShown()){
            ObjectAnimator anim1 = ObjectAnimator.ofFloat(mBotton, "translationY",200);
            anim1.setDuration(200);
            anim1.start();

            anim1.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mBotton.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
//            mBotton.clearAnimation();
//            mBotton.startAnimation(dismissAnim);
//            mBotton.setVisibility(View.GONE);

//
//            mTopUserDetail.clearAnimation();
//            mTopUserDetail.startAnimation(dismissAnim);
//            mTopUserDetail.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDownScroll() {
        if (!mBotton.isShown()){
            ObjectAnimator anim1 = ObjectAnimator.ofFloat(mBotton, "translationY",  0);
            anim1.setDuration(200);
            anim1.start();
            mBotton.setVisibility(View.VISIBLE);
            anim1.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
//            mBotton.clearAnimation();
//            mBotton.startAnimation(showAnim);
//            mBotton.setVisibility(View.VISIBLE);

//
//            mTopUserDetail.clearAnimation();
//            mTopUserDetail.startAnimation(showAnim);
//            mTopUserDetail.setVisibility(View.VISIBLE);
        }
    }





    public static void startZhiHu2Activity(Context context){
        Intent intent = new Intent(context,ZhiHu2Activity.class);
        context.startActivity(intent);}
}
