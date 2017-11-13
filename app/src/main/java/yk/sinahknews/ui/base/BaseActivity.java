package yk.sinahknews.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by YK on 2017/11/13.
 * 适用于不使用MVP模式的UI
 */

public abstract class BaseActivity extends AppCompatActivity {
  public String TAG = "ykk:" + getClass().getSimpleName();
  private Unbinder bkbind;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setSystemUI();
    setContentView(getLayoutId());
    //留给MVP类型的activity
    setPresenter();
    startPresenter();
    bkbind = ButterKnife.bind(this);
    initViews();
  }

  protected void startPresenter() {
  }

  public void reload() {
    Intent intent = getIntent();
    overridePendingTransition(0, 0);
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    finish();
    overridePendingTransition(0, 0);
    startActivity(intent);
  }

  protected void setPresenter() {
  }

  ;

  protected void setSystemUI() {
  }

  protected abstract int getLayoutId();

  public void initViews() {
  }

  public void showToast(String msg) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
  }

  public void showLog(String log) {
    Log.d(TAG, log);
  }

  public void startActivity(Class<? extends AppCompatActivity> cls) {
    startActivity(new Intent(getApplicationContext(), cls));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (bkbind != null) bkbind.unbind();
  }
}
