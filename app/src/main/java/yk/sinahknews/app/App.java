package yk.sinahknews.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by YK on 2017/11/13.
 */

public class App extends Application {
  private static App instance;
  @Override
  public void onCreate() {
    super.onCreate();
    instance=this;
  }
  public static Context getAppContext(){
    return instance;
  }
}
