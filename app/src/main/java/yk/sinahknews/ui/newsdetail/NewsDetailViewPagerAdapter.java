package yk.sinahknews.ui.newsdetail;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import yk.sinahknews.R;
import yk.sinahknews.api.NewsJson;

/**
 * Created by YK on 2017/11/15.
 */

public class NewsDetailViewPagerAdapter extends PagerAdapter {
  Context mContext;
  ArrayList<NewsJson.NewsDetailJson> mDatas;

  public NewsDetailViewPagerAdapter(Context context, ArrayList<NewsJson.NewsDetailJson> data) {
    this.mContext = context;
    mDatas = data;
  }

  @Override
  public int getCount() {
    return mDatas.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    ViewGroup viewroot = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_newsdetail
        , null);
    NewsJson.NewsDetailJson json = mDatas.get(position);
    TextView tvcontent = (TextView) viewroot.findViewById(R.id.tv_content);
    TextView tvTitle = (TextView) viewroot.findViewById(R.id.tv_title);
    TextView tvtime = (TextView) viewroot.findViewById(R.id.tv_time);
    TextView tvtag = (TextView) viewroot.findViewById(R.id.tv_tags);
    TextView tvauthor = (TextView) viewroot.findViewById(R.id.tv_author);
    tvTitle.setText(json.getTitle());
    tvcontent.setText(json.getContent());
    tvtime.setText(json.getPublishDateStr());
    tvtag.setText(json.tagsString);
    tvauthor.setText(json.getPosterScreenName());
    ViewPager imageviewpager = (ViewPager) viewroot.findViewById(R.id.viewpager);
    //设置stackview
    if (json.getImageUrls() == null) {
      imageviewpager.setVisibility(View.GONE);
    } else {
      imageviewpager.setOffscreenPageLimit(2);
      imageviewpager.setAdapter(new ImgViewPagerAdapter(mContext,json.getImageUrls()));
    }
    container.addView(viewroot);
    return viewroot;
  }

  static class ImgViewPagerAdapter extends PagerAdapter {
    String[] datas;
    Context mContext;
    ArrayList<View> views;
    public ImgViewPagerAdapter(Context context, String[] imageUrls) {
      this.datas = imageUrls;
      mContext = context;
    }

    @Override
    public int getCount() {
      return datas.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
      return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      ImageView imageView=new ImageView(mContext);
      imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT));
      imageView.setImageResource(R.mipmap.ic_launcher);
      Glide.with(mContext).load(datas[position]).into(imageView);
      container.addView(imageView);
      return imageView;
    }
  }
}
