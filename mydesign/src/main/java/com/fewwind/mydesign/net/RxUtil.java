package com.fewwind.mydesign.net;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by fewwind on 2016/3/17.
 */
public class RxUtil {

    /**
     * 如何生成 observe对象
     * http://gudong.name/advanced/2016/01/21/RxJava_In_AppPlus.html
     * http://www.devtf.cn/?p=734
     * @param func
     * @param <T>
     * @return
     */
    public static <T>Observable<T> makeOnservable(final Callable<T> func){

        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(func.call());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                subscriber.onCompleted();
            }
        });
    }
}
