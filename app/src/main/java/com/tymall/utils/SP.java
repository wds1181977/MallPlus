package com.tymall.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.tymall.app.MyApplication;

import java.util.HashMap;
import java.util.Map;



/**
 * Created by wpy on 2016/4/12.
 */
public class SP {
    private static final String assertHint = "SP使用前必须初始化，请先调用init函数";

    private static final String DEFAULT_SP_FILE_NAME = "_default_";

    private static Map<String, SharedPreferences> spMap = new HashMap<>();

    private static Map<String, SharedPreferences.Editor> editorMap = new HashMap<>();

    private synchronized static void init(@NonNull String fileName) {
        if (editorMap.get(fileName) != null)
            return;

        if (fileName.equals(DEFAULT_SP_FILE_NAME))
            spMap.put(fileName, PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance()));
        else
            spMap.put(fileName, MyApplication.getInstance().getSharedPreferences(fileName, MyApplication.MODE_PRIVATE));

        editorMap.put(fileName, spMap.get(fileName).edit());
    }

    public static void set(String key, int value) {
        set(DEFAULT_SP_FILE_NAME, key, value);
    }

    public static void set(String fileName, String key, int value) {
        init(fileName);
        editorMap.get(fileName).putInt(key, value).apply();
    }

    public static void set(String fileName, String key, String value) {
        init(fileName);
        editorMap.get(fileName).putString(key, value).apply();
    }


    public static void set(String key, String value) {
        set(DEFAULT_SP_FILE_NAME, key, value);
    }

    public static void set(String fileName, String key, boolean value) {
        init(fileName);
        editorMap.get(fileName).putBoolean(key, value).apply();
    }

    public static void set(String key, boolean value) {
        set(DEFAULT_SP_FILE_NAME, key, value);
    }

    public static void set(String fileName, String key, float value) {
        init(fileName);
        editorMap.get(fileName).putFloat(key, value).apply();
    }

    public static void set(String key, float value) {
        set(DEFAULT_SP_FILE_NAME, key, value);
    }

    public static void set(String fileName, String key, long value) {
        init(fileName);
        editorMap.get(fileName).putLong(key, value).apply();
    }

    public static void set(String key, long value) {
        set(DEFAULT_SP_FILE_NAME, key, value);
    }

    public static int get(String fileName, String key, int def) {
        init(fileName);
        return spMap.get(fileName).getInt(key, def);
    }

    public static int get(String key, int def) {
        return get(DEFAULT_SP_FILE_NAME, key, def);
    }

    public static boolean get(String fileName, String key, boolean def) {
        init(fileName);
        return spMap.get(fileName).getBoolean(key, def);
    }

    public static boolean get(String key, boolean def) {
        return get(DEFAULT_SP_FILE_NAME, key, def);
    }

    public static String get(String fileName, String key, String def) {
        init(fileName);
        return spMap.get(fileName).getString(key, def);
    }

    public static String get(String key, String def) {
        return get(DEFAULT_SP_FILE_NAME, key, def);
    }

    public static float get(String fileName, String key, float def) {
        init(fileName);
        return spMap.get(fileName).getFloat(key, def);
    }

    public static float get(String key, float def) {
        return get(DEFAULT_SP_FILE_NAME, key, def);
    }

    public static long get(String fileName, String key, long def) {
        init(fileName);
        return spMap.get(fileName).getLong(key, def);
    }


    public static long get(String key, long def) {
        return get(DEFAULT_SP_FILE_NAME, key, def);
    }


    public static boolean remove(String fileName, String key) {
        init(fileName);
        return editorMap.get(fileName).remove(key).commit();
    }

    public static boolean remove(String key) {
        return remove(DEFAULT_SP_FILE_NAME, key);
    }
}