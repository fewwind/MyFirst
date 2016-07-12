package com.fewwind.mydesign.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.fewwind.mydesign.R;

/**
 * Created by fewwind on 2016/2/26.
 */
public class DrawCirlceVolume extends View {

    private Paint mPaintIn;
    private Paint mPaintOut;

    private int mCircleWidth;

    private int mFirstColor;
    private  int mSecondColor;

    private int mCurrentCount = 5;
    private Bitmap mBitmap;

    private int mSplitSize;
    private int mCountAll;
    private Rect mRect;



    public DrawCirlceVolume(Context context) {
        this(context, null);
    }
    public DrawCirlceVolume(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawCirlceVolume(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.DrawCirlceVolume);
        mFirstColor = t.getColor(R.styleable.DrawCirlceVolume_first_color, Color.BLACK);
        mSecondColor = t.getColor(R.styleable.DrawCirlceVolume_second_color,Color.CYAN);
        mBitmap = BitmapFactory.decodeResource(getResources(), t.getResourceId(R.styleable.DrawCirlceVolume_bg, 0));
        mCircleWidth = t.getDimensionPixelSize(R.styleable.DrawCirlceVolume_circle_width, 10);
        mCountAll = t.getInt(R.styleable.DrawCirlceVolume_dot_count, 12);
        mSplitSize = t.getInt(R.styleable.DrawCirlceVolume_split_size, 20);
        t.recycle();

        mPaintIn = new Paint();
        mPaintOut = new Paint();
        mRect = new Rect();
    }

    private void init(Context context) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaintIn.setAntiAlias(true);
        mPaintIn.setStrokeCap(Paint.Cap.ROUND);
        mPaintIn.setStrokeWidth(mCircleWidth);
        mPaintIn.setStyle(Paint.Style.STROKE);

        int center = getWidth()/2;
        int radius = center - mCircleWidth/2;
        /**
         * 画小方块
         */
        drawOval(canvas, center, radius);

        /**
         * 计算内切正方形的位置
         */
        int relRadius = radius - mCircleWidth / 2;// 获得内圆的半径
        /**
         * 内切正方形的距离顶部 = mCircleWidth + relRadius - √2 / 2
         */
        mRect.left = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        /**
         * 内切正方形的距离左边 = mCircleWidth + relRadius - √2 / 2
         */
        mRect.top = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        mRect.bottom = (int) (mRect.left + Math.sqrt(2) * relRadius);
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);


        /**
         * 如果图片比较小，那么根据图片的尺寸放置到正中心
         */
        if (mBitmap.getWidth() < Math.sqrt(2) * relRadius)
        {
            mRect.left = (int) (mRect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - mBitmap.getWidth() * 1.0f / 2);
            mRect.top = (int) (mRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - mBitmap.getHeight() * 1.0f / 2);
            mRect.right = (int) (mRect.left + mBitmap.getWidth());
            mRect.bottom = (int) (mRect.top + mBitmap.getHeight());

        }

        canvas.drawBitmap(mBitmap,null,mRect,mPaintIn);


    }

    private void drawOval(Canvas canvas, int center, int radius) {
            float itemSize = (360*1.0f- mCountAll*mSplitSize)/mCountAll;
        RectF oval = new RectF(center-radius,center-radius,center+radius,center+radius);
        mPaintIn.setColor(mFirstColor);
        for (int i = 0; i < mCountAll; i++) {
            canvas.drawArc(oval,i*(itemSize+mSplitSize),itemSize,false,mPaintIn);
        }

        mPaintIn.setColor(mSecondColor);

        for (int i = 0; i < mCurrentCount; i++) {
            canvas.drawArc(oval,(i-3)*(itemSize+mSplitSize),itemSize,false,mPaintIn);
        }
    }

    int yDown = 0;
    int yUp;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                yDown = (int) event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                yUp = (int) event.getY();

                if (yUp>yDown){
                    down();
                } else {
                    up();
                }

                break;
        }


        return true;
    }

    public void down(){
        if (mCurrentCount==0) return;
        mCurrentCount--;
        invalidate();
    }

    public void up(){
        if (mCurrentCount == mCountAll) return;
        mCurrentCount++;
        invalidate();
    }

}
