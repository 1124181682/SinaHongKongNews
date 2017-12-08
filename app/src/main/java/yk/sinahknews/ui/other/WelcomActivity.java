package yk.sinahknews.ui.other;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;
import yk.sinahknews.R;
import yk.sinahknews.ui.base.BaseActivity;
import yk.sinahknews.ui.main.MainActivity;
import yk.sinahknews.util.RxUtil;

public class WelcomActivity extends BaseActivity {
  Handler handler=new Handler();
  @Override
  protected int getLayoutId() {
    return R.layout.activity_welcom;
  }

  @Override
  public void initViews() {
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        RxPermissions rxPermissions=new RxPermissions(WelcomActivity.this);
        rxPermissions.request(Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.READ_PHONE_STATE)
            .compose(RxUtil.<Boolean>all_io())
            .subscribe(new Consumer<Boolean>() {
              @Override
              public void accept(Boolean permission) throws Exception {
                if (permission){
                  startActivity(MainActivity.class);
                  finish();
                }else {
                  AlertDialog.Builder builder=new AlertDialog.Builder(WelcomActivity.this);
                  builder.setMessage("没有授予必要的权限，无法继续运行");
                  builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                      finish();
                    }
                  });
                  builder.create().show();
                }
              }
            });
      }
    },100);

  }
}
