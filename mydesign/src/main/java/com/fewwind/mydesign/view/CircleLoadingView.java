package com.fewwind.mydesign.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fewwind on 2016/1/22.
 */
public class CircleLoadingView extends View {

        public CircleLoadingView(Context context) {
            this(context, null);
        }

        public CircleLoadingView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context, attrs);
        }

        private Paint mPaintCircleOuter;
        private Paint mPaintCircleInner;

        private void init(Context context, AttributeSet attrs) {
            mPaintCircleOuter = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaintCircleOuter.setStyle(Paint.Style.STROKE);
            mPaintCircleOuter.setStrokeWidth(3F);
            mPaintCircleOuter.setColor(Color.parseColor("#bfffffff"));

            mPaintCircleInner = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaintCircleInner.setColor(Color.parseColor("#bfffffff"));
        }

        private float mWidth;
        private float mHeight;
        private float mRadius;
        private RectF mOval;

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            mWidth = getWidth();
            mHeight = getHeight();
            mRadius = Math.min(mWidth / 2, mHeight / 2) - 6;

            mOval = new RectF(-mRadius + 9, -mRadius + 9, mRadius - 9, mRadius - 9);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.translate(mWidth / 2, mHeight / 2);
            canvas.drawCircle(0, 0, mRadius, mPaintCircleOuter);
            canvas.drawArc(mOval, -90F, mSweepAngel, true, mPaintCircleInner);
        }

        /**
         * 圆圈扫过的角度
         */
        private float mSweepAngel;

        /**
         * 设置加载进度
         */
        public void setProgress(int progress) {
            mSweepAngel = (float) (progress / 100.0 * 360);
            invalidate();
        }

}
