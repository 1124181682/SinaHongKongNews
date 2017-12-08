package yk.sinahknews.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationCloseClickReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    context.stopService(new Intent(context.getApplicationContext(),SearchNotificationService.class));
  }
}
