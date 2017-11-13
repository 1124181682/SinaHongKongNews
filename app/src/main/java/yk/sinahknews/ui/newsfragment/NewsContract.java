package yk.sinahknews.ui.newsfragment;

import android.content.Context;

import java.util.List;

import yk.sinahknews.app.NewsType;
import yk.sinahknews.ui.base.BasePresenter;
import yk.sinahknews.ui.base.IBaseView;

/**
 * Created by YK on 2017/11/14.
 */

public interface NewsContract {
  interface INewsView extends IBaseView {
    void showFirstContent(List<NewsItem> news);

    void getStartNewsFail();

    void showMoreContent(List<NewsItem> news,boolean hasNext);

    void getMoreNewsFail();
  }

  abstract class NewsPresent extends BasePresenter<INewsView> {
    @Override
    public void start() {
    }

    @Override
    public void detachView() {
      super.detachView();
    }

    public abstract void startGetNews(NewsType.NewsTypeModel newsType);

    public abstract void loadMore();

    public abstract void onItemClick(Context context,int position);
  }
}
