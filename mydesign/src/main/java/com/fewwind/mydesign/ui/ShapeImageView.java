package com.fewwind.mydesign.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.Arrays;

/**
 * Created by admin on 2016/4/25.
 */
public class ShapeImageView extends ImageView {
    private static final int SHAPEE_CIRCLR = 2;
    private static final int SHAPEE_ROUND_RECT = 1;

    private int mShapeMode = 0;
    private float mRadius = 0;
    private Shape mShape;
    private Paint mPaint;

//    PorterDuff.Mode.SRC_IN：取交集，显示上层。
//    PorterDuff.Mode.DST_IN：取交集，显示下层。
    public ShapeImageView(Context context) {
        this(context,null);
    }

    public ShapeImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShapeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    private void init(AttributeSet attrs) {

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB){
            setLayerType(LAYER_TYPE_HARDWARE,null);
        }
        if (attrs!=null){

        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setFilterBitmap(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed)
            switch (mShapeMode){
                case SHAPEE_ROUND_RECT:
                    break;
                case SHAPEE_CIRCLR:
                    int min = Math.min(getWidth(),getHeight());
                    mRadius = min/2;
                    break;
            }

        if (mShape==null){
            float[] radius = new float[8];
            Arrays.fill(radius,mRadius);
            mShape = new RoundRectShape(radius,null,null);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int saveCount = canvas.getSaveCount();
        canvas.save();

        super.onDraw(canvas);
        switch (mShapeMode) {
            case SHAPEE_ROUND_RECT:
            case SHAPEE_CIRCLR:
                if (mShape != null) {
                    mShape.draw(canvas, mPaint);
                }
                break;
        }

        canvas.restoreToCount(saveCount);
    }
}
