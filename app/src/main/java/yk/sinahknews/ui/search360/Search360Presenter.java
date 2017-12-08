package yk.sinahknews.ui.search360;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import yk.sinahknews.api.NewsJson;
import yk.sinahknews.api.RetrofitFactory;
import yk.sinahknews.ui.newsdetail.NewsDetailActivity;
import yk.sinahknews.ui.newsfragment.NewsItem;
import yk.sinahknews.util.RxUtil;

import static yk.sinahknews.util.TimeUtil.StampToDate;

/**
 * Created by YK on 2017/11/22.
 */

public class Search360Presenter extends Search360Contract.Search360Presenter {
  boolean isHasNext;
  int nowPageNum=1;
  String word;
  ArrayList<NewsJson.NewsDetailJson> newsDetailJsons = new ArrayList<>();
  Disposable mDisposable=null;
  Disposable mLoadMoreDisposable=null;
  boolean isNewSearch=true;
  @Override
  public void search(String kw) {
    if (mDisposable!=null){
      mDisposable.dispose();
      mDisposable=null;
    }
    if (mLoadMoreDisposable!=null){
      mLoadMoreDisposable.dispose();
      mLoadMoreDisposable=null;
    }
    Observable<List<NewsItem>> newsDataList = getNewsDataList(kw, 1);
    Disposable subscribe = newsDataList.subscribe(new Consumer<List<NewsItem>>() {
      @Override
      public void accept(List<NewsItem> newsItems) throws Exception {
         mView.searchShow(newsItems,isHasNext);
      }
    }, new Consumer<Throwable>() {
      @Override
      public void accept(Throwable throwable) throws Exception {
         mView.searchNewsFail();
      }
    });
    word=kw;
    mDisposable=subscribe;
  }

  @Override
  public void loadMore() {
    if (mDisposable!=null){
      mDisposable.dispose();
      mDisposable= null;
    }
    Disposable subscribe = getNewsDataList(word, nowPageNum).subscribe(new Consumer<List<NewsItem>>() {
      @Override
      public void accept(List<NewsItem> newsItems) throws Exception {
        mView.showSearchMore(newsItems,isHasNext);
      }
    }, new Consumer<Throwable>() {
      @Override
      public void accept(Throwable throwable) throws Exception {
       mView.searchMoreFail();
      }
    });
    mLoadMoreDisposable=subscribe;
  }

  @Override
  public void onItemClick(Context context, int position) {
    Intent intent=new Intent();
    intent.putParcelableArrayListExtra("data", newsDetailJsons);
    intent.putExtra("position",position);
    intent.setClass(context.getApplicationContext(),NewsDetailActivity.class);
    context.startActivity(intent);
  }

  private Observable<List<NewsItem>> getNewsDataList(String kw, final int pageNum) {
    return RetrofitFactory.getApiService().search360News(kw, pageNum)
        .map(new Function<NewsJson, List<NewsItem>>() {
          @Override
          public List<NewsItem> apply(NewsJson newsJson) throws Exception {
            if (newsJson == null || newsJson.getDataType() == null)
              throw new NullPointerException();
            isHasNext = newsJson.isHasNext();

            NewsJson.NewsDetailJson[] jsonsArray = newsJson.getData();
            if (pageNum==1) {
              if (newsDetailJsons.size()!=0){
                newsDetailJsons.clear();
              }
//
//              newsDetailJsons=new ArrayList<>();
            }
            newsDetailJsons.addAll(Arrays.asList(jsonsArray));
            nowPageNum=pageNum+1;
            List<NewsItem> newsItems = NewsJson2NewsItems(jsonsArray);
            return newsItems;
          }
        }).compose(RxUtil.<List<NewsItem>>io_main());
  }
  private List<NewsItem> NewsJson2NewsItems(NewsJson.NewsDetailJson[] newsJson) {
    List<NewsItem> retLists = new ArrayList<>();
    for (NewsJson.NewsDetailJson json : newsJson) {
      if (json == null) continue;
      String tag = "";
      if (json.getTags() != null) {
        for (String stag : json.getTags()) {
          tag += stag + " ";
        }
      }
      json.tagsString=tag;
      long timestamp = json.getPublishDate() * 1000;
      String time = StampToDate(String.valueOf(timestamp));
      json.setPublishDateStr(time);

      String[] imageUrls = json.getImageUrls();
      if (imageUrls == null) {
        imageUrls = null;
        json.setImageUrls(null);
      }else {
        json.setImageUrls(imageUrls=Arrays.copyOf(imageUrls,imageUrls.length-1));
      }
      NewsItem newsItem = new NewsItem(json.getTitle(), imageUrls
          , time, json.getPosterScreenName(), tag);
      retLists.add(newsItem);
    }
    return retLists;
  }
}
