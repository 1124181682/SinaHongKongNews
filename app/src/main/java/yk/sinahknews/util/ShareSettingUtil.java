package yk.sinahknews.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import yk.sinahknews.app.App;

/**
 * Created by YK on 2017/11/20.
 */

public class ShareSettingUtil {
  public static final String DEFAULT_FILE_NAME = "app_setting";

  public static void setStringSet(String fileName, String key, Set<String> value) {
    if (fileName == null) fileName = DEFAULT_FILE_NAME;
    SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(fileName, Context.MODE_MULTI_PROCESS);
    sharedPreferences.edit().putStringSet(key, value).commit();
  }

  public static Set<String> getStringSet(String fileName, String key) {
    if (fileName == null) fileName = DEFAULT_FILE_NAME;
    SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(fileName, Context.MODE_MULTI_PROCESS);
    return sharedPreferences.getStringSet(key, null);
  }

  public static boolean getBool(String fileName, String key) {
    if (fileName == null) fileName = DEFAULT_FILE_NAME;
    SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(fileName, Context.MODE_MULTI_PROCESS);
    return sharedPreferences.getBoolean(key, true);
  }
  public static String getString(String fileName, String key) {
    if (fileName == null) fileName = DEFAULT_FILE_NAME;
    SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(fileName, Context.MODE_MULTI_PROCESS);
    return sharedPreferences.getString(key, null);
  }
  public static void setBool(String fileName, String key, Boolean value) {
    if (fileName == null) fileName = DEFAULT_FILE_NAME;
    SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(fileName, Context.MODE_MULTI_PROCESS);
    SharedPreferences.Editor edit = sharedPreferences.edit();
    edit.putBoolean(key, value).commit();
  }

  public static void setString(String fileName, String key, String value) {
    if (fileName == null) fileName = DEFAULT_FILE_NAME;
    SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(fileName, Context.MODE_MULTI_PROCESS);
    SharedPreferences.Editor edit = sharedPreferences.edit();
    edit.putString(key, value).commit();
  }
}
