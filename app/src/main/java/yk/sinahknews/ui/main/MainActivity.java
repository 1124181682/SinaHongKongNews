package yk.sinahknews.ui.main;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import yk.sinahknews.ui.other.AboutActivity;
import yk.sinahknews.ui.managefocus.ManageFocusActivity;
import yk.sinahknews.R;
import yk.sinahknews.ui.base.BaseMVPActivity;

public class MainActivity extends BaseMVPActivity<MainPresenter> implements NavigationView.OnNavigationItemSelectedListener {
  private static MainActivity instance;
  @BindView(R.id.viewpager)
  ViewPager viewPager;
  @BindView(R.id.tablayout)
  TabLayout tabLayout;
  @BindView(R.id.navigationview)
  NavigationView navigationView;
  @BindView(R.id.drawer_layout)
  DrawerLayout drawerLayout;
  @BindView(R.id.iv_open_close)
  ImageView ivOpenClose;
  MainViewPagerAdapter viewPagerAdapter;
  FragmentManager fragmentManager;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override
  public void initViews() {
    fragmentManager = getSupportFragmentManager();
    viewPagerAdapter = new MainViewPagerAdapter(fragmentManager, mPresenter.getViewPagerNewsTypes());
    viewPager.setOffscreenPageLimit(0);
    viewPager.setAdapter(viewPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
    navigationView.setNavigationItemSelectedListener(this);
    ivOpenClose.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        drawerLayout.openDrawer(Gravity.START,true);
      }
    });
    instance=this;
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()){
      case R.id.nav_manage:
        startActivity(ManageFocusActivity.class);
        break;
      case R.id.nav_about:
        startActivity(AboutActivity.class);
    }
    return true;
  }
  public static void reloadActivity(){
    instance.reload();
  }
}
