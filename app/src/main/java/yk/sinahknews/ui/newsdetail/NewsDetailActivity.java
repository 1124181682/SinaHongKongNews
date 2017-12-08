package yk.sinahknews.ui.newsdetail;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import yk.sinahknews.R;
import yk.sinahknews.api.NewsJson;
import yk.sinahknews.ui.base.BaseActivity;
import yk.sinahknews.ui.base.BaseMVPActivity;
import yk.sinahknews.util.AnimatorUtil;
import yk.sinahknews.util.eyes.Eyes;

public class NewsDetailActivity extends BaseActivity {
  ArrayList<NewsJson.NewsDetailJson> newsDetailJsons;
  @BindView(R.id.vp_)
  ViewPager viewPager;
  NewsDetailViewPagerAdapter adapter;
  @BindView(R.id.fab_)
  FloatingActionButton mFAB;
  Handler mHandle = new Handler();
  int defalutStatusbarColor;
  @Override
  protected int getLayoutId() {
    return R.layout.activity_news_detail;
  }

  @Override
  protected void setSystemUI() {
    if (Eyes.setStatusBarLightMode(this, Color.WHITE)) {
      defalutStatusbarColor=Color.WHITE;
      return;
    } else {
      defalutStatusbarColor=getResources().getColor(R.color.colorPrimaryDark);
      Eyes.setStatusBarColor(this, defalutStatusbarColor);
    }
  }

  NewsDetailViewPagerAdapter.PaletteSettingCallBack paletteSettingCallBack = new
      NewsDetailViewPagerAdapter.PaletteSettingCallBack() {

        @Override
        public void fail() {
        }

        @Override
        public void succse(Bitmap img) {
          if (img==null) return;
          Palette palette = Palette.from(img).generate();
          Eyes.setStatusBarColor(NewsDetailActivity.this
              ,palette.getDarkVibrantColor(defalutStatusbarColor));
        }
      };

  @Override
  public void initViews() {
    newsDetailJsons = getIntent().getExtras().getParcelableArrayList("data");
    viewPager.setOffscreenPageLimit(2);
    adapter = new NewsDetailViewPagerAdapter(this, newsDetailJsons);
    viewPager.setAdapter(adapter);
    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        adapter.getFirstImageByPosition(position, mHandle, paletteSettingCallBack);
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
    viewPager.setCurrentItem(getIntent().getExtras().getInt("position", 0));
    loadShowFloatingButton();
    mFAB.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        NestedScrollView nestedScrollView = (NestedScrollView) adapter.getItemView(viewPager
            .getCurrentItem());
        nestedScrollView.scrollTo(0, 0);
      }
    });

  }

  public void loadShowFloatingButton() {
    mHandle.postDelayed(new Runnable() {
      @Override
      public void run() {
        AnimatorUtil.scaleHide(mFAB);
      }
    }, 200);
  }
}
