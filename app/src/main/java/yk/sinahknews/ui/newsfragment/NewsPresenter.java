package yk.sinahknews.ui.newsfragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.adapter.rxjava2.HttpException;
import yk.sinahknews.api.NewsJson;
import yk.sinahknews.api.RetrofitFactory;
import yk.sinahknews.app.NewsType;
import yk.sinahknews.ui.newsdetail.NewsDetailActivity;
import yk.sinahknews.util.RxUtil;

import static yk.sinahknews.util.TimeUtil.StampToDate;

/**
 * Created by YK on 2017/11/14.
 */

public class NewsPresenter extends NewsContract.NewsPresent {
  int nowPageNum = 1;
  boolean isHasNext;
  NewsType.NewsTypeModel newsType;
  ArrayList<NewsJson.NewsDetailJson> newsDetailJsons = new ArrayList<>();

  @Override
  public void startGetNews(NewsType.NewsTypeModel newsType) {
    this.newsType = newsType;
    getNewsDataList(1)
        .subscribe(new Consumer<List<NewsItem>>() {
          @Override
          public void accept(@NonNull List<NewsItem> datas) throws Exception {
            mView.showFirstContent(datas);
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            if (throwable instanceof HttpException||throwable instanceof NullPointerException) {
              mView.getStartNewsFail();
            }
          }
        });
  }

  private Observable<List<NewsItem>> getNewsDataList(int pageNum) {
    return RetrofitFactory.getApiService().getNews(newsType.id, pageNum)
        .map(new Function<NewsJson, List<NewsItem>>() {
          @Override
          public List<NewsItem> apply(NewsJson newsJson) throws Exception {
            if (newsJson == null || newsJson.getDataType() == null)
              throw new NullPointerException();
            isHasNext = newsJson.isHasNext();
            nowPageNum++;
            NewsJson.NewsDetailJson[] jsonsArray = newsJson.getData();
            newsDetailJsons.addAll(Arrays.asList(jsonsArray));
            List<NewsItem> newsItems = NewsJson2NewsItems(jsonsArray);
            return newsItems;
          }
        }).compose(RxUtil.<List<NewsItem>>io_main());
  }

  @Override
  public void loadMore() {
    getNewsDataList(nowPageNum).subscribe(new Consumer<List<NewsItem>>() {
      @Override
      public void accept(List<NewsItem> newsItems) throws Exception {
        mView.showMoreContent(newsItems,isHasNext);
      }
    }, new Consumer<Throwable>() {
      @Override
      public void accept(Throwable throwable) throws Exception {
        mView.getMoreNewsFail();
      }
    });
  }

  @Override
  public void onItemClick(Context context, int position) {
    Intent intent=new Intent();
    intent.putParcelableArrayListExtra("data", newsDetailJsons);
    intent.putExtra("position",position);
    intent.setClass(context.getApplicationContext(),NewsDetailActivity.class);
    context.startActivity(intent);
  }
  //数据的清洗与转换
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
      if (imageUrls == null || imageUrls.length == 1) {
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
