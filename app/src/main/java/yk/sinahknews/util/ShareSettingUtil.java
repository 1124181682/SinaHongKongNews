package yk.sinahknews.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import yk.sinahknews.app.App;

/**
 * Created by YK on 2017/11/20.
 */

public class ShareSettingUtil {
  public static void setStringSet(String fileName,String key,Set<String> value){
    SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    sharedPreferences.edit().putStringSet(key,value).apply();
  }
  public static Set<String> getStringSet(String fileName,String key){
    SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    return sharedPreferences.getStringSet(key,null);
  }
}
