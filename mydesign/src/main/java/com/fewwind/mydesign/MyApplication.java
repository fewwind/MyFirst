package com.fewwind.mydesign;

import android.app.Application;
import android.content.Context;

import com.fewwind.mydesign.disk.DiskLruCacheHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.Logger;

import java.io.IOException;

/**
 * Created by fewwind on 2015/10/15.
 */
public class MyApplication extends Application {

    public  static DiskLruCacheHelper helper;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        //创建默认的ImageLoader配置参数

        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
        context = getApplicationContext();

        Logger.init("tag");

        try {
            helper = new DiskLruCacheHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*    private void setupDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }*/

    public static synchronized MyApplication context() {
        return (MyApplication) context;
    }

}
