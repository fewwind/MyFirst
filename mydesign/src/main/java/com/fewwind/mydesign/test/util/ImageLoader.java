package com.fewwind.mydesign.test.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fewwind on 2016/3/31.
 */
public class ImageLoader {

    LruCache<String,Bitmap> mImageCache;

    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());



}
