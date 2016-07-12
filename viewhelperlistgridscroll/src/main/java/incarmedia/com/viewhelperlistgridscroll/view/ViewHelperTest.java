package incarmedia.com.viewhelperlistgridscroll.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by fewwind on 2016/1/27.
 */
public class ViewHelperTest extends LinearLayout {

    private ViewDragHelper viewHelper;

    public ViewHelperTest(Context context) {
        this(context, null);
    }

    public ViewHelperTest(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewHelperTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        viewHelper = ViewDragHelper.create(this,1.0f,new ViewDragHelper.Callback(){

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewHelper.shouldInterceptTouchEvent(ev);
    }
}
