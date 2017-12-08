package yk.sinahknews.ui.other;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import yk.sinahknews.R;
import yk.sinahknews.app.App;
import yk.sinahknews.component.NotificationCloseClickReceiver;
import yk.sinahknews.component.SearchNotificationService;
import yk.sinahknews.util.ShareSettingUtil;

import static yk.sinahknews.app.App.getAppContext;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

  /**
   * A preference value change listener that updates the preference's summary
   * to reflect its new value.
   */
  private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener =
      new Preference.OnPreferenceChangeListener() {

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
          String key = preference.getKey();
          if (key.equals("notifications_open_message")) {
            boolean value = (boolean) newValue;
            if (value) {
            } else {
              //关闭通知
              ActivityManager activityManager =
                  (ActivityManager) App.getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
              List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(50);
              for (ActivityManager.RunningServiceInfo runningService : runningServices) {
                if (runningService.service.getClassName().equals(SearchNotificationService.class.getName())){
                  Intent intent=new Intent(App.getAppContext().getApplicationContext(),
                      SearchNotificationService.class);
                  App.getAppContext().stopService(intent);
                }
              }
            }
            ShareSettingUtil.setBool(null, "notifications_open_message", value);
            SwitchPreference switchPreference= (SwitchPreference) preference;
            switchPreference.setChecked(value);

          } else if (key.equals("notifications_ringtone_type")) {
            String value= (String) newValue;
            RingtonePreference ringtonePreference= (RingtonePreference) preference;
            ShareSettingUtil.setString(null,"notifications_ringtone_type",value);
            ringtonePreference.setSummary(value);
          } else if (key.equals("notifications_open_vibrate")) {
            boolean value = (boolean) newValue;
            ShareSettingUtil.setBool(null, "notifications_open_vibrate", value);
            SwitchPreference switchPreference= (SwitchPreference) preference;
            switchPreference.setChecked(value);
          }
          return true;
        }
      };


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * Helper method to determine if the device has an extra-large screen. For
   * example, 10" tablets are extra-large.
   */
  private static boolean isXLargeTablet(Context context) {
    return (context.getResources().getConfiguration().screenLayout
        & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
  }

  /**
   * Binds a preference's summary to its value. More specifically, when the
   * preference's value is changed, its summary (line of text below the
   * preference title) is updated to reflect the value. The summary is also
   * immediately updated upon calling this method. The exact display format is
   * dependent on the type of preference.
   *
   * @see #sBindPreferenceSummaryToValueListener
   */
  private static void bindPreferenceSummaryToValue(Preference preference) {
    // Set the listener to watch for value changes.
    preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

    // Trigger the listener immediately with the preference's
    // current value.
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupActionBar();
  }

  /**
   * Set up the {@link android.app.ActionBar}, if the API is available.
   */
  private void setupActionBar() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      // Show the Up button in the action bar.
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean onIsMultiPane() {
    return isXLargeTablet(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public void onBuildHeaders(List<Header> target) {
    loadHeadersFromResource(R.xml.pref_headers, target);
  }

  /**
   * This method stops fragment injection in malicious applications.
   * Make sure to deny any unknown fragments here.
   */
  protected boolean isValidFragment(String fragmentName) {
    return PreferenceFragment.class.getName().equals(fragmentName)
        || GeneralPreferenceFragment.class.getName().equals(fragmentName)
        || DataSyncPreferenceFragment.class.getName().equals(fragmentName)
        || NotificationPreferenceFragment.class.getName().equals(fragmentName);
  }

  /**
   * This fragment shows general preferences only. It is used when the
   * activity is showing a two-pane settings UI.
   */
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public static class GeneralPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.pref_general);
      setHasOptionsMenu(true);

      // Bind the summaries of EditText/List/Dialog/Ringtone preferences
      // to their values. When their values change, their summaries are
      // updated to reflect the new value, per the Android Design
      // guidelines.
      bindPreferenceSummaryToValue(findPreference("example_text"));
      bindPreferenceSummaryToValue(findPreference("example_list"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      if (id == android.R.id.home) {
        startActivity(new Intent(getActivity(), SettingsActivity.class));
        return true;
      }
      return super.onOptionsItemSelected(item);
    }
  }

  /**
   * This fragment shows notification preferences only. It is used when the
   * activity is showing a two-pane settings UI.
   */
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public static class NotificationPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.pref_notification);
      setHasOptionsMenu(true);

      // Bind the summaries of EditText/List/Dialog/Ringtone preferences
      // to their values. When their values change, their summaries are
      // updated to reflect the new value, per the Android Design
      // guidelines.
      Preference notifications_open_message = findPreference("notifications_open_message");
      bindPreferenceSummaryToValue(notifications_open_message);
      //设置初始值
      boolean open_message_value = ShareSettingUtil.getBool(null, "notifications_open_message");
      notifications_open_message.setDefaultValue(open_message_value);
      Preference ringtone_type = findPreference("notifications_ringtone_type");
      String ringtone_type_value = ShareSettingUtil.getString(null, "notifications_ringtone_type");
      if (ringtone_type_value==null){
        ringtone_type.setDefaultValue("content://settings/system/notification_sound");
      }else {
        //无铃声的值是 ""
        ringtone_type.setDefaultValue(ringtone_type_value);
      }
      bindPreferenceSummaryToValue(ringtone_type);
      Preference open_vibrate = findPreference("notifications_open_vibrate");
      boolean open_vibrate_value = ShareSettingUtil.getBool(null, "open_vibrate");
      open_vibrate.setDefaultValue(open_vibrate_value);
      bindPreferenceSummaryToValue(open_vibrate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      if (id == android.R.id.home) {
        getActivity().onBackPressed();
      }
      return super.onOptionsItemSelected(item);
    }
  }

  /**
   * This fragment shows data and sync preferences only. It is used when the
   * activity is showing a two-pane settings UI.
   */
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public static class DataSyncPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.pref_data_sync);
      setHasOptionsMenu(true);

      // Bind the summaries of EditText/List/Dialog/Ringtone preferences
      // to their values. When their values change, their summaries are
      // updated to reflect the new value, per the Android Design
      // guidelines.
      bindPreferenceSummaryToValue(findPreference("sync_frequency"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      if (id == android.R.id.home) {
        getActivity().onBackPressed();
        return true;
      }
      return super.onOptionsItemSelected(item);
    }
  }
}
