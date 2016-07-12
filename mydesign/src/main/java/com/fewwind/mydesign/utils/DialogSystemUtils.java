package com.fewwind.mydesign.utils;

/**
 * Created by fewwind on 2016/4/6.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.fewwind.mydesign.R;

import java.lang.ref.WeakReference;

/*Created by Zack White on 2016/4/1.
        *how to use:
        *DialogSystemUtils.INSTANCE.showDialog(getApplicationContext(),"");
        */

public enum DialogSystemUtils {

    INSTANCE;

    private Dialog mDialog;
    private boolean autoDismiss = true;//默认自动消失
    private boolean isFloat = false;//是否是悬浮的
    private boolean canceledOnTouchOutside = true;//默认点击外面消失

    private int locationX = -1;//X位置
    private int locationY = -1;//Y位置

    private int dialogWindowAnim = -1;//动画id
    private float dimAmount = -1;//-1表示使用默认背景透明度
    private float alpha = -1;//-1表示使用默认透明度

    private DialogSystemUtilsInterface listener;//暴露接口

    public void showDialog(Context context, View view) {
        showDialog(context, view, null);
    }

    public void showDialog(Context context, String text) {
        showDialog(context, null, text);
    }

    public void showDialog(Context context, View contentView, String text) {
        showDialog(context, contentView, text, false);
    }

    public void showDialog(Context context, View contentView, String text, boolean newData) {

        //不等于空，没有显示，不是新数据
        if (mDialog != null && !mDialog.isShowing() && !newData) {
            mDialog.show();
            return;
        }

        //初始化数据，并且第一次显示
        initDialog(context, contentView, text);
    }

    private void initDialog(Context context, View contentView, String text) {

        //默认文字的显示
        if (contentView == null) {
            contentView = View.inflate(context, R.layout.default_dialog_bg, null);
            if (!TextUtils.isEmpty(text)) {
                ((TextView) contentView.findViewById(R.id.textView)).setText(text);
            }
        }

        //新建Dialog对象
        mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题头
        mDialog.setContentView(contentView);//设置布局文件

        //是否为悬浮窗
        if (isFloat) {
            //在19之前的TYPE_TOAST不支持点击事件，需要SYSTEM_ALERT_WINDOW权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);//设置为悬浮窗大于19
            } else {
                mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
            }
        }

        mDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);

        Window dialogWindow = mDialog.getWindow();//获取窗口控制器
        dialogWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));//设置背景
//        dialogWindow.setWindowAnimations(dialogWindowAnim == -1 ? R.style.dialogWindowAnim : dialogWindowAnim);  //添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); //获取属性

        lp.x = locationX == -1 ? Gravity.CENTER : locationX; // 新位置X坐标
        lp.y = locationY == -1 ? Gravity.CENTER : locationY; // 新位置Y坐标
        lp.dimAmount = dimAmount == -1 ? 0.4f : dimAmount; //背景透明度
        lp.alpha = alpha == -1 ? 1.0f : alpha; // 透明度

        dialogWindow.setAttributes(lp); //设置属性

        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0, 2000);//发送关闭消息
                if (listener != null) {
                    listener.onShow();
                }
            }
        });

        mDialog.show();
    }

    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            if (listener != null) {
                listener.onDismiss();
            }
        }
    }

    private MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        WeakReference<DialogSystemUtils> mInstance;

        MyHandler(DialogSystemUtils instance) {
            this.mInstance = new WeakReference<>(instance);
        }

        @Override
        public void handleMessage(Message msg) {
            DialogSystemUtils instance = mInstance.get();
            switch (msg.what) {
                case 0: {
                    if (instance.autoDismiss) {
                        instance.dismissDialog();
                    }
                    break;
                }
            }
        }
    }

    public interface DialogSystemUtilsInterface {
        void onShow();

        void onDismiss();
    }

    /**
     * 重置所有的状态
     */
    public void resetDialogInformation() {
        locationX = -1;
        locationY = -1;
        dimAmount = -1;
        alpha = -1;
        dialogWindowAnim = -1;
        autoDismiss = true;
        isFloat = false;
        mDialog = null;
        listener = null;
    }

    public DialogSystemUtilsInterface getListener() {
        return listener;
    }

    public boolean isAutoDismiss() {
        return autoDismiss;
    }

    public void setAutoDismiss(boolean autoDismiss) {
        this.autoDismiss = autoDismiss;
    }

    public void setListener(DialogSystemUtilsInterface listener) {
        this.listener = listener;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getDimAmount() {
        return dimAmount;
    }

    public void setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
    }

    public boolean isFloat() {
        return isFloat;
    }

    public void setIsFloat(boolean isFloat) {
        this.isFloat = isFloat;
    }

    public boolean isCanceledOnTouchOutside() {
        return canceledOnTouchOutside;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }
}