package incarmedia.com.mvpdemo;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;

import incarmedia.com.mvpdemo.bean.User;
import incarmedia.com.mvpdemo.loginmvp.view.LoginActivity;
import incarmedia.com.mvpdemo.presenter.UserInfoPresenter;
import incarmedia.com.mvpdemo.view.IShowUserView;

public class MainActivity extends AppCompatActivity implements IShowUserView, View.OnClickListener {

    private UserInfoPresenter infoPresenter;
    private ProgressDialog pd;
    FloatingActionButton fab;
    String sd1;
    String sd2;

    Button mRadio;
    Button mKala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        infoPresenter = new UserInfoPresenter(this);
        pd = new ProgressDialog(this);
        pd.setMessage("正在加载```");

        fab = (FloatingActionButton) findViewById(R.id.fab);
        mRadio = (Button) findViewById(R.id.id_radio);
        mKala = (Button) findViewById(R.id.id_kala);
        mRadio.setOnClickListener(this);
        mKala.setOnClickListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sd1 = getStoragePath(MainActivity.this, true);
                sd2 = getStoragePath(MainActivity.this, false);
                infoPresenter.getUserInfoToShow(1);


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                TextView textView = (TextView) (findViewById(R.id.id_tv));

                String BaseFilesDir = (TextUtils.isEmpty(sd2) ? sd1 : sd2) + "/incarmedia";
                textView.setText(sd1 + "\n" + sd2 + "\n" + BaseFilesDir + "\n" + getMedia());

            }
        });



    }


    public int getMedia() {
        ContentResolver contentResolver = this.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor == null) {
            return -1;
        } else {

            return cursor.getCount();
        }
    }

    @Override
    public void showLoading() {
        pd.show();
    }

    @Override
    public void hideLoading() {
        pd.hide();
    }

    @Override
    public void toMainActivity(User user, List<User> users) {
        Snackbar.make(fab, users.size() + "------", Snackbar.LENGTH_LONG)
                .setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("tag", "snackkBar");
                    }
                }).show();

        for (User u :
                users) {
            Log.d("tag", u.toString());
        }
    }

    @Override
    public void onFail() {

    }

    /**
     * 获取内置和外置 sd卡 ，为true时获取外置sd卡，为false获取内置sd卡
     *
     * @param mContext
     * @param is_removale
     * @return
     */
    public static String getStoragePath(Context mContext, boolean is_removale) {

        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String packageName = "com.incarmedia";
        String className = "com.incarmedia.main.SplashActivity";
        intent.setClassName(packageName, className);
        switch (v.getId()) {

//		intent.putExtra("incat_radio", "incat_radio");
//		startActivity(intent);
            case R.id.id_radio:
//                intent.putExtra("ACTION_INTENT", 0);
//                startActivity(intent);

                LoginActivity.startLoginActivity(v.getContext());
                break;
            case R.id.id_kala:
                intent.putExtra("ACTION_INTENT", 1);
                startActivity(intent);
                break;

        }
    }

    /**
     * 直接启动即时行乐对应的频道
     *
     * @param code 0：启动广播电台， 1：启动卡拉OK
     */
    public void startChannel(int code) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String packageName = "com.incarmedia";
        String className = "com.incarmedia.main.SplashActivity";
        intent.setClassName(packageName, className);
        intent.putExtra("ACTION_INTENT", 0);
        startActivity(intent);
    }
}
