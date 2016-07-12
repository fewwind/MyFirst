package com.fewwind.mydesign.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fewwind on 2016/2/26.
 */
public class DrawText extends View {
    private Paint paint;
    private String text = "我是画的";

    Rect rectF = new Rect();
    public DrawText(Context context) {
        this(context, null);
    }

    public DrawText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(20);
        //  用 画笔 获取当前字符串的大小，并赋值给 rect；那么rect跟 rectf的区别是什么呢？
        //计算描绘字体 所需要的范围
        paint.getTextBounds(text,0,text.length(),rectF);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int modeWid = MeasureSpec.getMode(widthMeasureSpec);
        int modeHei = MeasureSpec.getMode(heightMeasureSpec);
        if (modeWid == MeasureSpec.AT_MOST||modeWid==MeasureSpec.UNSPECIFIED){
            paint.setTextSize(20);
            paint.getTextBounds(text,0,text.length(),rectF);
            width = getPaddingLeft()+getPaddingRight()+rectF.width();

        }
        if (modeHei == MeasureSpec.AT_MOST||modeHei==MeasureSpec.UNSPECIFIED){
            paint.setTextSize(20);
            paint.getTextBounds(text,0,text.length(),rectF);
            height = getPaddingTop()+getPaddingBottom()+rectF.height();
        }
//这个方法决定了当前 view的大小
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.BLACK);
        RectF rectRound =  new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRoundRect(rectRound,25,25,paint);


        paint.setColor(Color.WHITE);
        /**
         * 画文字的方法中间接受两个参数 x，y，具体指的是x  文字左边的位置，，，，，y-----指的是baseline也就是左下角距离 view顶部的距离
         */
//        canvas.drawText(text,getWidth()/2-rectF.width()/2,getHeight()/2+0,paint);
        canvas.drawText(text,getWidth()/2-rectF.width()/2,getHeight()/2+rectF.height()/2,paint);
    }
}
