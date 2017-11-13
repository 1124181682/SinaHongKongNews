package yk.sinahknews.ui.newsdetail;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import yk.sinahknews.R;
import yk.sinahknews.api.NewsJson;
import yk.sinahknews.ui.base.BaseActivity;
import yk.sinahknews.ui.base.BaseMVPActivity;

public class NewsDetailActivity extends BaseActivity {
   ArrayList<NewsJson.NewsDetailJson> newsDetailJsons;
  @BindView(R.id.vp_)
  ViewPager viewPager;
  NewsDetailViewPagerAdapter adapter;
  @Override
  protected int getLayoutId() {
    return R.layout.activity_news_detail;
  }

  @Override
  public void initViews() {
    newsDetailJsons = getIntent().getExtras().getParcelableArrayList("data");
    viewPager.setOffscreenPageLimit(2);
    adapter=new NewsDetailViewPagerAdapter(this,newsDetailJsons);
    viewPager.setAdapter(adapter);
    viewPager.setCurrentItem(getIntent().getExtras().getInt("position",0));
  }
}
