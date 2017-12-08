package yk.sinahknews.ui.newsdetail;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.FutureTarget;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import yk.sinahknews.R;
import yk.sinahknews.api.NewsJson;
import yk.sinahknews.app.GlideApp;
import yk.sinahknews.ui.search360.Search360Activity;

/**
 * Created by YK on 2017/11/15.
 */

public class NewsDetailViewPagerAdapter extends PagerAdapter {
  Context mContext;
  ArrayList<NewsJson.NewsDetailJson> mDatas;
  List<ViewGroup> mViews = new ArrayList<>();

  public NewsDetailViewPagerAdapter(Context context, ArrayList<NewsJson.NewsDetailJson> data) {
    this.mContext = context;
    mDatas = data;
    for (int i = 0; i < data.size(); i++) {
      mViews.add(null);
    }
  }

  private String paletteImgUrl;
  Handler mHandle;
  PaletteSettingCallBack callBack;
  Thread settingPalette = new Thread(new Runnable() {
    @Override
    public void run() {
      String url = paletteImgUrl;
      FutureTarget<File> fileFutureTarget = GlideApp.with(mContext).load(paletteImgUrl)
          .downloadOnly(100, 100);
      try {
        File file = fileFutureTarget.get();
        if (!url.equals(paletteImgUrl)) {
          return;
        }
        final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        mHandle.post(new Runnable() {
          @Override
          public void run() {
            callBack.succse(bitmap);
          }
        });

      } catch (Exception e) {
        e.printStackTrace();
        mHandle.post(new Runnable() {
          @Override
          public void run() {
            callBack.fail();
          }
        });
      }
    }
  });

  public void getFirstImageByPosition(int position, final Handler handler, @NonNull final
  PaletteSettingCallBack callBack) {
    if (position < 0 || position >= getCount()) {
      throw new IllegalArgumentException("position范围异常");
    }
    final String[] imageUrls = mDatas.get(position).getImageUrls();
    if (imageUrls == null || imageUrls.length < 1) return;
    paletteImgUrl = imageUrls[0];
    if (mHandle == null) mHandle = handler;
    if (this.callBack == null) this.callBack = callBack;
    new Thread(settingPalette).start();
  }

  public interface PaletteSettingCallBack {
    void fail();

    void succse(Bitmap img);
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
    mViews.set(position, null);
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    ViewGroup viewroot = (ViewGroup) LayoutInflater.from(this.mContext).inflate(R.layout
            .item_viewpager_newsdetail
        , null);
    NewsJson.NewsDetailJson json = mDatas.get(position);
    final TextView tvcontent = (TextView) viewroot.findViewById(R.id.tv_content);
    tvcontent.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
      int menuitem360ID;
      @Override
      public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuItem menuItem360 = menu.add("使用360新闻搜索");
        menuItem360.setIcon(mContext.getResources().getDrawable(R.drawable.ic_search360_black_24dp));
        menuItem360.setEnabled(true);
        menuitem360ID =menuItem360.getItemId();
        menuItem360.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
      }

      @Override
      public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return true;
      }

      @Override
      public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId()==menuitem360ID){
          CharSequence selectString = tvcontent.getText().subSequence(tvcontent.getSelectionStart(),
              tvcontent.getSelectionEnd());
          Intent intent=new Intent();
          intent.setAction("yk.sinahknews.action.360_SEARCH");
          if (selectString.length()>5){
            selectString=selectString.subSequence(0,5);
          }
          intent.putExtra(Search360Activity.INTENT_VALUE_SEARCH_WORD,selectString.toString());
          try {
            mContext.startActivity(intent);
          }catch (ActivityNotFoundException e){
            Toast.makeText(mContext,"无应用可接受搜索",Toast.LENGTH_SHORT).show();
          }

          mode.finish();
          return true;
        }
        mode.finish();
        return false;
      }

      @Override
      public void onDestroyActionMode(ActionMode mode) {

      }
    });
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
      imageviewpager.setAdapter(new ImgViewPagerAdapter(this.mContext, json.getImageUrls()));
    }
    container.addView(viewroot);
    mViews.set(position, viewroot);

    return viewroot;
  }

  public ViewGroup getItemView(int position) {
    return mViews.get(position);
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
      return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      ImageView imageView = new ImageView(mContext);
      imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT));
      imageView.setImageResource(R.mipmap.ic_launcher);
      GlideApp.with(mContext).load(datas[position]).
          placeholder(mContext.getResources().getDrawable(R.drawable.loading))
          .error(R.drawable.img_load_error).into(imageView);
      container.addView(imageView);
      return imageView;
    }
  }
}
