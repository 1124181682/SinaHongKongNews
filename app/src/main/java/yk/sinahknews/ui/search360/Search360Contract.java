package yk.sinahknews.ui.search360;

import android.content.Context;

import java.util.List;

import yk.sinahknews.ui.base.BasePresenter;
import yk.sinahknews.ui.base.IBaseView;
import yk.sinahknews.ui.newsfragment.NewsItem;

/**
 * Created by YK on 2017/11/22.
 */

public interface Search360Contract {
  abstract class Search360Presenter extends BasePresenter<ISearch360View>{
    @Override
    public void start() {

    }

    @Override
    public void detachView() {
      super.detachView();
    }

    public abstract void search(String world);
    public abstract void loadMore();
    public abstract void onItemClick(Context context, int position);

  }
  interface ISearch360View extends IBaseView{
    void searchShow(List<NewsItem> newsItems,boolean hasnext);
    void searchNewsFail();
    void showSearchMore(List<NewsItem> newsItems,boolean hasnext);
    void searchMoreFail();
  }

}
