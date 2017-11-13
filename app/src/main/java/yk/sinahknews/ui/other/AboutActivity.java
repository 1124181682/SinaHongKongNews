package yk.sinahknews.ui.other;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import yk.sinahknews.R;
import yk.sinahknews.ui.base.BaseActivity;

public class AboutActivity extends BaseActivity {
  @BindView(R.id.img_)
  ImageView imageView;
  @Override
  protected int getLayoutId() {
    return R.layout.activity_about;
  }

  @Override
  public void initViews() {
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    View.OnClickListener clickgoback = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    };
    fab.setOnClickListener(clickgoback);
    imageView.setOnClickListener(clickgoback);
  }
}
