package com.fewwind.mydesign.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by fewwind on 2016/3/14.
 */
public class ThreadExecutorMy {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAX_POOL_SIZE = 2 * CPU_COUNT + 1;
    private static final int KEEP_ALIVE = 1;


    private static final BlockingQueue<Runnable> BLOCKING_QUEUE = new ArrayBlockingQueue<Runnable>(10);
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger atomicInteger = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {


            return new Thread(r, "我的线程池----##" + atomicInteger.getAndIncrement());
        }
    };

    public void getBitmap(Activity context) {
        View decorView = context.getWindow().getDecorView();
        Bitmap bitmap = Bitmap.createBitmap(decorView.getMeasuredWidth(), decorView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        decorView.draw(new Canvas(bitmap));
//        mImageView.setImageBitmap(bitmap);
    }


    public void FutureTaskTest() {

        FutureTask<String> fTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Logger.i("calling ");
                return "Hello  FutureTask";
            }
        }) {
            @Override
            protected void done() {
                try {
                    String s = get();
                    Logger.w("done  调用了---"+s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                super.done();
            }
        };

        Executor executor  = Executors.newSingleThreadExecutor();
        executor.execute(fTask);
    }


}
