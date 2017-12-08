package yk.sinahknews.component;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import yk.sinahknews.R;
import yk.sinahknews.ui.search360.Search360Activity;
import yk.sinahknews.util.ShareSettingUtil;

public class SearchNotificationService extends Service {
  public static final int NOTIFICATION_ID = 1001;
  boolean showed;

  @Override
  public IBinder onBind(Intent intent) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (!showed) {
      showNotification();
      showed = true;
    }
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onDestroy() {
    stopForeground(true);
  }

  private void showNotification() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    builder.setSmallIcon(R.mipmap.ic_launcher_round);
    RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remoteviews_notification);
    Intent closeIntent = new Intent(getApplicationContext(), NotificationCloseClickReceiver.class);
    PendingIntent closePendingIntent =
        PendingIntent.getBroadcast(getApplicationContext(), 0, closeIntent, 0);
    remoteViews.setOnClickPendingIntent(R.id.iv_close, closePendingIntent);
    builder.setCustomContentView(remoteViews);
    Intent intent = new Intent(getApplicationContext(), Search360Activity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
    builder.setContentIntent(pendingIntent);
    String notifications_ringtone_type = ShareSettingUtil.getString(null, "notifications_ringtone_type");
    if (notifications_ringtone_type != null && !notifications_ringtone_type.equals("")) {
      builder.setSound(Uri.parse(notifications_ringtone_type));
    }
    if (notifications_ringtone_type.equals("")) {
      builder.setSound(null);
    }
    boolean notifications_open_vibrate = ShareSettingUtil.getBool(null, "notifications_open_vibrate");
    if (notifications_open_vibrate && notifications_ringtone_type == null) {
      builder.setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE);
    }
    if (notifications_open_vibrate&&notifications_ringtone_type!=null){
      builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
    }
    if (!notifications_open_vibrate) {
      builder.setVibrate(null);
    }
    startForeground(NOTIFICATION_ID, builder.build());
  }
}
