package yk.sinahknews.ui.other;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;
import yk.sinahknews.R;
import yk.sinahknews.ui.base.BaseActivity;
import yk.sinahknews.ui.main.MainActivity;
import yk.sinahknews.util.RxUtil;

public class WelcomActivity extends BaseActivity {

  @Override
  protected int getLayoutId() {
    return R.layout.activity_welcom;
  }

  @Override
  public void initViews() {
    RxPermissions rxPermissions=new RxPermissions(this);
    rxPermissions.request(Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE)
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
}
