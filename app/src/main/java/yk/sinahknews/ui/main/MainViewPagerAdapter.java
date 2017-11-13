package yk.sinahknews.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import yk.sinahknews.app.NewsType;
import yk.sinahknews.ui.newsfragment.NewsFragment;

/**
 * Created by YK on 2017/11/13.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter{
  NewsType.NewsTypeModel[] datas;
  NewsFragment[] fragments;
  public MainViewPagerAdapter(FragmentManager fm, NewsType.NewsTypeModel[] datas) {
    super(fm);
    this.datas=datas;
    fragments=new NewsFragment[datas.length];
  }

  @Override
  public Fragment getItem(int position) {
    NewsFragment newsFragment=fragments[position];
    if (newsFragment==null){
      newsFragment = new NewsFragment();
      Bundle args=new Bundle();
      args.putParcelable("NewsType",datas[position]);
      newsFragment.setArguments(args);
    }

    return newsFragment;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {

  }

  @Override
  public int getCount() {
    return datas.length;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return datas[position].name;
  }
}
