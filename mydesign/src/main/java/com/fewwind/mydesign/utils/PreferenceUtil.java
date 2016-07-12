package com.fewwind.mydesign.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by fewwind on 2015/10/22.
 */
public class PreferenceUtil {

    private static SharedPreferences sharedPreferences;
    private static PreferenceUtil instance;

    private static SharedPreferences.Editor mEditor;

    public PreferenceUtil(final Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static final PreferenceUtil getInstance(Context context) {
        if (instance == null) {

            synchronized (PreferenceUtil.class) {
                if (instance == null) {
                    instance = new PreferenceUtil(context);
                    mEditor = sharedPreferences.edit();
                }
            }

        }
        return instance;
    }

    public static void addNewChan(String id, boolean flag) {
        mEditor.putBoolean(id, true);
        mEditor.commit();
    }

    public static void cancleNewChan(String id, boolean flag) {
        mEditor.putBoolean(id, false);
        mEditor.commit();
    }

    public static void addSP(String key, String val) {
        mEditor.putString(key, val);
        mEditor.commit();
    }

    public static void addSPInt(String key, int val) {
        mEditor.putInt(key, val);
        mEditor.commit();
    }

    public static void addBoolInt(String key, boolean val) {
        mEditor.putBoolean(key, val);
        mEditor.commit();
    }

    public static boolean getBoolSP(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public static int getIntSP(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public static String getStringSP(String key) {
        return sharedPreferences.getString(key, "cuowu");
    }

    public static boolean isNewChan(String id) {
        return sharedPreferences.getBoolean(id, false);
    }

    public static void addMyChan(String id, boolean flag) {
        mEditor.putBoolean(id + "my", true);
        mEditor.commit();
    }

    public static void deleteMyChan(String id, boolean flag) {
        mEditor.putBoolean(id + "my", false);
        mEditor.commit();
    }

    public static boolean isMyChan(String id) {
        return sharedPreferences.getBoolean(id + "my", false);
    }
}
