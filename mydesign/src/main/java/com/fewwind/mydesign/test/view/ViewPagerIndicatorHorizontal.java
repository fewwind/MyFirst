package com.fewwind.mydesign.test.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fewwind.mydesign.R;

import java.util.List;

/**
 * Created by fewwind on 2016/mTabVisibleCount/19.
 */
public class ViewPagerIndicatorHorizontal extends HorizontalScrollView {


    private Paint mPaint;
    private Path mPath;
    private int mTriangleWidth;
    private int mTriangleHeight;

    LinearLayout container;
    private ViewPager mViewPager;
    private List<String> mTitles;
    public static final float RADIO_TRIANGLE_WIDTH = 1 / 6f;
    public static final int NORMAL_COLOR =0x77ffffff;
    public static final int SELECT_COLOR =0xffffffff;

    //初始化 画三角的时候移动的距离，
    private int mInitTranslationX;
    //    每次需要移动的距离
    private int mTranslationX;

    private int mTabVisibleCount = 3;

    public ViewPagerIndicatorHorizontal(Context context) {
        this(context, null);
    }

    public ViewPagerIndicatorHorizontal(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(mTabVisibleCount));

        //获取自定义属性，首页可见tab的数量

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTabVisibleCount = ta.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, mTabVisibleCount);


        this.removeAllViews();
        container= new LinearLayout(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.setLayoutParams(params);
        this.addView(container);
    }


    //当 xml 视图文件加载完毕后回调
    @Override
    protected void onFinishInflate() {
        int childCount = container.getChildCount();
        if (childCount == 0) return;
        for (int i = 0; i < childCount; i++) {
            View view = container.getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.width = Resources.getSystem().getDisplayMetrics().widthPixels / mTabVisibleCount;


            view.setLayoutParams(layoutParams);

        }

        setClickEvent();
        super.onFinishInflate();
    }

    //当空间的宽高发生变化时调用
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGLE_WIDTH);
        mInitTranslationX = w / mTabVisibleCount / 2 - mTriangleWidth / 2;

        initTriangle();


    }

    //初始化三角形
    private void initTriangle() {

        mTriangleHeight = mTriangleWidth / 2 - mTabVisibleCount;

        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }


    //设置所有传过来的 title。动态的生成tab数量和title
    public void setTabItemTitle(List<String> titles) {


        if (titles != null && titles.size() > 0) {
            this.mTitles = titles;

            for (int i = 0; i < mTitles.size(); i++) {
                container.addView(generateTitleView(mTitles.get(i)));
            }
        }

        setClickEvent();

    }

    //生成title 对应的textview
    private View generateTitleView(String s) {
        TextView tv = new TextView(getContext());
        //这里就不能  getlayoutparems，因为得到的是空的，根本没有设置布局参数，只能自己new
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.width = Resources.getSystem().getDisplayMetrics().widthPixels / mTabVisibleCount;
        tv.setLayoutParams(params);
        tv.setText(s);
        tv.setTextColor(NORMAL_COLOR);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    public void setViewPager(ViewPager viewpager){
        this.mViewPager =viewpager;

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Logger.d("Horizental==="+position+"--positionOffset--"+positionOffset+"--positionOffsetPixels--"+positionOffsetPixels);
                scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                setHightLight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setHightLight(0);
    }


    public void setHightLight(int pos) {

        for (int i = 0; i <container.getChildCount() ; i++) {

            //这里应该注意 ，有可能发生强转异常,所以应该这么些

//            View view = getChildAt(i);
//            if (view instanceof TextView)

            TextView tv = (TextView) container.getChildAt(i);

            if (pos==i){
                tv.setTextColor(SELECT_COLOR);
            } else{
                tv.setTextColor(NORMAL_COLOR);
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight());
        canvas.drawPath(mPath, mPaint);

        canvas.restore();
    }

    /**
     * 指示器 跟随手指进行滚动
     *
     * @param position
     * @param offset
     */
    public void scroll(int position, float offset) {
        int tabWidth = getWidth() / mTabVisibleCount;
        mTranslationX = (int) (tabWidth * (offset + position));

        //容器移动到tab处于最后一个的时候，应该继续移动
        if (position >= (mTabVisibleCount - 2) && offset > 0 && container.getChildCount() > mTabVisibleCount) {


            if (mTabVisibleCount == 1) {
                scrollTo(position * tabWidth + (int) (tabWidth * offset), 0);
            } else {

                scrollTo((position - (mTabVisibleCount - 2)) * tabWidth + (int) (tabWidth * offset), 0);
            }
        }


        invalidate();
    }

    public void setClickEvent(){

        for (int i = 0; i <container.getChildCount() ; i++) {
            View tv = container.getChildAt(i);
            if (tv instanceof TextView){
                final int finalI = i;
                tv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(finalI);
                    }
                });
            }

        }
    }
}
